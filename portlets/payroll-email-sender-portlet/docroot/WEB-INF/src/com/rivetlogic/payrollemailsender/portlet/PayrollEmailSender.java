package com.rivetlogic.payrollemailsender.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.rivetlogic.payrollemailsender.model.FileColumn;
import com.rivetlogic.payrollemailsender.util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

/**
 * Portlet implementation class PayrollEmailSender
 */
public class PayrollEmailSender extends MVCPortlet {
	
	private static final Log LOG = LogFactoryUtil.getLog(PayrollEmailSender.class);
 
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
		
        response.setRenderParameter("jspPage", "/html/payrollemailsender/data-mapping.jsp");
        response.setRenderParameter("fileId", String.valueOf(fileId));
        PortalUtil.copyRequestParameters(request, response);
        
        sendRedirect(request, response);
	}
	
	public void sendData(ActionRequest request, ActionResponse response) 
			throws IOException, PortletException, SystemException, PortalException {
		
		List<FileColumn> columnsToUse = new ArrayList<FileColumn>();
		
		String fileId = ParamUtil.getString(request, "selectedFile");
		String fields[] = ParamUtil.getParameterValues(request,"field");
		int containsEmail = ParamUtil.getInteger(request, "containsEmail");
		String uses[] = ParamUtil.getParameterValues(request,"useInHtml");
		
		List<FileColumn> columns = FileUtil.getFileColumns(fileId);
		
		for (FileColumn fileColumn : columns) {
			fileColumn.setNameToUse(fields[fileColumn.getId()]);
			fileColumn.setUse(Boolean.valueOf(uses[fileColumn.getId()]));
			
			if (fileColumn.getId() == containsEmail) {
				fileColumn.setIsEmail(true);
			}
			
			if (fileColumn.getUse() || fileColumn.getIsEmail()) {
				columnsToUse.add(fileColumn);
			}
		}
		
		response.setRenderParameter("jspPage", "/html/payrollemailsender/email-configuration.jsp");
        response.setRenderParameter("fileId", String.valueOf(fileId));
        request.setAttribute("params", columnsToUse);
        PortalUtil.copyRequestParameters(request, response);
        
        sendRedirect(request, response);
    }
}
