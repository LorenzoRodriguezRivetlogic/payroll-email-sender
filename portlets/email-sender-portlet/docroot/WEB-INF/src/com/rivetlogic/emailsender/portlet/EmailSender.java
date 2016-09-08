package com.rivetlogic.emailsender.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.rivetlogic.emailsender.model.FileColumn;
import com.rivetlogic.emailsender.model.Template;
import com.rivetlogic.emailsender.service.TemplateLocalServiceUtil;
import com.rivetlogic.emailsender.util.FileUtil;
import com.rivetlogic.emailsender.util.MailUtil;
import com.rivetlogic.emailsender.util.Utils;
import com.rivetlogic.emailsender.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * Portlet implementation class EmailSender
 */
public class EmailSender extends MVCPortlet {
	
	private static final Log LOG = LogFactoryUtil.getLog(EmailSender.class);
	 
	public void uploadCsv(ActionRequest request, ActionResponse response) throws PortletException,IOException {
		
        UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(request);
        
        long fileId = 0l;
        try {
            fileId = FileUtil.storeAttachments(upload);
        } catch(Exception e) {
            LOG.error("Error adding applicant to job:", e);
            SessionErrors.add(request, "apply-error");
            SessionMessages.add(request, PortalUtil.getPortletId(request) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
        }
		
        response.setRenderParameter(WebKeys.JSP_PAGE, WebKeys.DATA_MAPPING_URL);
        response.setRenderParameter(WebKeys.FILE_ID, String.valueOf(fileId));
        PortalUtil.copyRequestParameters(request, response);
        
        sendRedirect(request, response);
	}
	
	public void sendData(ActionRequest request, ActionResponse response) 
			throws IOException, PortletException, SystemException, PortalException {
		
		List<FileColumn> columnsToUse = new ArrayList<FileColumn>();
		
		String fileId = ParamUtil.getString(request, WebKeys.FILE_ID);
		int containsEmail = ParamUtil.getInteger(request, WebKeys.CONTAINS_EMAIL);
		String uses[] = ParamUtil.getParameterValues(request, WebKeys.USE_IN_HTML);
		
		List<FileColumn> columns = FileUtil.getFileColumns(fileId);
		FileColumn columnEmail = new FileColumn();
		
		for (FileColumn fileColumn : columns) {
			fileColumn.setUse(Boolean.valueOf(uses[fileColumn.getId()]));
			
			if (fileColumn.getId() == containsEmail) {
				fileColumn.setIsEmail(true);
				columnEmail = fileColumn;
			}
			
			if (fileColumn.getUse()) {
				columnsToUse.add(fileColumn);
			}
		}
		
		response.setRenderParameter(WebKeys.JSP_PAGE, WebKeys.EMAIL_CONFIGURATION_URL);
        response.setRenderParameter(WebKeys.FILE_ID, String.valueOf(fileId));
        response.setRenderParameter(WebKeys.COLUMNS_TO_USE, JSONFactoryUtil.looseSerialize(columnsToUse));
        response.setRenderParameter(WebKeys.COLUMN_EMAIL, JSONFactoryUtil.looseSerialize(columnEmail));
        PortalUtil.copyRequestParameters(request, response);
        
        sendRedirect(request, response);
    }
	
	public void showPreview(ActionRequest request, ActionResponse response) throws IOException {
		List<FileColumn> params = (List<FileColumn>) JSONFactoryUtil.looseDeserialize(ParamUtil.getString(request, WebKeys.COLUMNS_TO_USE));
		FileColumn emailColumn = (FileColumn) JSONFactoryUtil.looseDeserialize(ParamUtil.getString(request, WebKeys.COLUMN_EMAIL));
		String fileId = ParamUtil.getString(request, WebKeys.FILE_ID);
		String templateValue = ParamUtil.getString(request, WebKeys.CONTENT);
		String emailSender = ParamUtil.getString(request, WebKeys.SENDER_EMAIL);
	    String subject = ParamUtil.getString(request, WebKeys.EMAIL_SUBJECT);
	    
	    templateValue = Utils.removeEscapeChars(templateValue);
	    
	    response.setRenderParameter(WebKeys.JSP_PAGE, WebKeys.PREVIEW_URL);
	    response.setRenderParameter(WebKeys.FILE_ID, String.valueOf(fileId));
	    response.setRenderParameter(WebKeys.COLUMNS_TO_USE, JSONFactoryUtil.looseSerialize(params));
	    response.setRenderParameter(WebKeys.COLUMN_EMAIL, JSONFactoryUtil.looseSerialize(emailColumn));
	    response.setRenderParameter(WebKeys.CONTENT, templateValue);
	    response.setRenderParameter(WebKeys.SENDER_EMAIL, emailSender);
	    response.setRenderParameter(WebKeys.EMAIL_SUBJECT, subject);
        
        sendRedirect(request, response);
	}
	
	public void sendEmails(ActionRequest request, ActionResponse response) throws IOException {
		String fileId = ParamUtil.getString(request, WebKeys.FILE_ID);
		String emailColumn = ParamUtil.getString(request, WebKeys.COLUMN_EMAIL);
		String columnsToUse = ParamUtil.getString(request, WebKeys.COLUMNS_TO_USE);
	    String content = ParamUtil.getString(request, WebKeys.CONTENT);
	    String senderEmail = ParamUtil.getString(request, WebKeys.SENDER_EMAIL);
	    String emailSubject = ParamUtil.getString(request, WebKeys.EMAIL_SUBJECT);
	    
	    try {
		    List<FileColumn> columns = (List<FileColumn>) JSONFactoryUtil.looseDeserialize(columnsToUse);
		    FileColumn email = (FileColumn) JSONFactoryUtil.looseDeserialize(emailColumn);
		    
		    List<Map<String, String>> data = FileUtil.getFileRows(fileId, columns, email);
	    
			MailUtil.sendEmails(senderEmail, content, emailSubject, data);
			FileUtil.deleteFileAndFolder(Long.parseLong(fileId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) 
			throws IOException, PortletException {

		String action = ParamUtil.getString(resourceRequest, "action");
		
		if (action.equals(WebKeys.ACTION_LOAD)) {
			loadAction(resourceRequest, resourceResponse);
		}
		   
		if (action.equals(WebKeys.ACTION_UPDATE)) {
			updateAction(resourceRequest, resourceResponse);
		}
		
		if (action.equals(WebKeys.ACTION_SAVE)) {
			saveAction(resourceRequest, resourceResponse);
		}
		
		if (action.equals(WebKeys.ACTION_DELETE)) {
			deleteAction(resourceRequest, resourceResponse);
		}
    }
	
	private void loadAction(ResourceRequest resourceRequest, ResourceResponse resourceResponse) 
			throws IOException, PortletException {
		
		String templateId = ParamUtil.getString(resourceRequest, "templateId");
        Template template = null;
        try {
        	template = TemplateLocalServiceUtil.getTemplate(Long.parseLong(templateId));
        } catch (PortalException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }
 
        resourceResponse.setContentType("text/html");
        PrintWriter writer = resourceResponse.getWriter();
        if(template == null){
            writer.print("");
        }else{
        	writer.print(template.getValue());
        }
        writer.flush();
        writer.close();
        super.serveResource(resourceRequest, resourceResponse);
	}
	
	private void updateAction(ResourceRequest resourceRequest, ResourceResponse resourceResponse) 
			throws IOException, PortletException {
		
		Long templateId = Long.parseLong(ParamUtil.getString(resourceRequest, "templateId"));
		String name = ParamUtil.getString(resourceRequest, "name");
		String template = ParamUtil.getString(resourceRequest, "template");
        
		resourceResponse.setContentType("text/html");
        PrintWriter writer = resourceResponse.getWriter();

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Template.class.getName(), resourceRequest);
			TemplateLocalServiceUtil.updateTemplate(serviceContext.getUserId(), templateId, name, template, serviceContext);
			
			writer.print("success");
		} catch (PortalException e) {
			writer.print("error");
			e.printStackTrace();
		} catch (SystemException e) {
			writer.print("error");
			e.printStackTrace();
		}
		
        writer.flush();
        writer.close();
        super.serveResource(resourceRequest, resourceResponse);
	}
	
	private void saveAction(ResourceRequest resourceRequest, ResourceResponse resourceResponse) 
			throws IOException, PortletException {
		
		String name = ParamUtil.getString(resourceRequest, "name");
		String template = ParamUtil.getString(resourceRequest, "template");
        
		resourceResponse.setContentType("text/html");
        PrintWriter writer = resourceResponse.getWriter();

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Template.class.getName(), resourceRequest);
			Template newTemplate = TemplateLocalServiceUtil.addTemplate(serviceContext.getUserId(), name, template, serviceContext);
			
			writer.print(newTemplate.getTemplateId());
		} catch (PortalException e) {
			writer.print("error");
			e.printStackTrace();
		} catch (SystemException e) {
			writer.print("error");
			e.printStackTrace();
		}
		
        writer.flush();
        writer.close();
        super.serveResource(resourceRequest, resourceResponse);
	}
	
	private void deleteAction(ResourceRequest resourceRequest, ResourceResponse resourceResponse) 
			throws IOException, PortletException {
		
		Long templateId = Long.parseLong(ParamUtil.getString(resourceRequest, "templateId"));
        
		resourceResponse.setContentType("text/html");
        PrintWriter writer = resourceResponse.getWriter();

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Template.class.getName(), resourceRequest);
		    TemplateLocalServiceUtil.deleteTemplate(templateId, serviceContext);
		    
			writer.print("success");
		} catch (PortalException e) {
			writer.print("error");
			e.printStackTrace();
		} catch (SystemException e) {
			writer.print("error");
			e.printStackTrace();
		}
		
        writer.flush();
        writer.close();
        super.serveResource(resourceRequest, resourceResponse);
	}
}
