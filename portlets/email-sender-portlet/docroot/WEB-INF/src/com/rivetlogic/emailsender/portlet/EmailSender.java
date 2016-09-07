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
import com.rivetlogic.emailsender.util.WebKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

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
	    
	    templateValue = templateValue.replace("\n", "");
	    templateValue = templateValue.replace("\t", "");
	    
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

	public void addTemplate(ActionRequest request, ActionResponse response)
	        throws PortalException, SystemException, IOException {

	    ServiceContext serviceContext = ServiceContextFactory.getInstance(Template.class.getName(), request);

	    String name = ParamUtil.getString(request, WebKeys.TEMPLATE_NAME);
	    String value = ParamUtil.getString(request, WebKeys.TEMPLATE_VALUE);
	    long templateId = ParamUtil.getLong(request, WebKeys.TEMPLATE_ID);

	    try {
	    	if (templateId > 0) {
				TemplateLocalServiceUtil.updateTemplate(serviceContext.getUserId(), templateId, name, value, serviceContext);
				SessionMessages.add(request, "templateEdited");
			} else {
				TemplateLocalServiceUtil.addTemplate(serviceContext.getUserId(), name, value, serviceContext);
		        SessionMessages.add(request, "templateAdded");
			}  
	        
	    } catch (Exception e) {
	        SessionErrors.add(request, e.getClass().getName());
	        PortalUtil.copyRequestParameters(request, response);
	        response.setRenderParameter("mvcPath", WebKeys.TEMPLATE_EDIT_URL);
	    }
	    
	    response.setRenderParameter(WebKeys.JSP_PAGE, WebKeys.TEMPLATE_MANAGER_URL);
	    sendRedirect(request, response);
	}
	
	public void deleteTemplate (ActionRequest request, ActionResponse response) throws IOException {

	    long templateId = ParamUtil.getLong(request, WebKeys.TEMPLATE_ID);

	    try {

	       ServiceContext serviceContext = ServiceContextFactory.getInstance(Template.class.getName(), request);

	       TemplateLocalServiceUtil.deleteTemplate(templateId, serviceContext);
	    } catch (Exception e) {
	       SessionErrors.add(request, e.getClass().getName());
	    }
	    
	    response.setRenderParameter(WebKeys.JSP_PAGE, WebKeys.TEMPLATE_MANAGER_URL);
	    sendRedirect(request, response);
	}
}