 <%@ include file="/html/init.jsp"%>

<% 
	String fileId = ParamUtil.getString(request, WebKeys.FILE_ID);

	if (!fileId.isEmpty()) {
		FileUtil.deleteFileAndFolder(Long.parseLong(fileId));
	}
%>

<h3><liferay-ui:message key="upload-title" /></h3>

<portlet:actionURL name="uploadCsv" var="uploadURL">
</portlet:actionURL>

 <portlet:renderURL var="templateManagerURL">
	<portlet:param name="mvcPath" value="<%= WebKeys.TEMPLATE_MANAGER_URL %>" />
</portlet:renderURL>
 

<aui:form action="<%= uploadURL %>" method="POST" enctype="multipart/form-data"> 
    <aui:row>
        <aui:column columnWidth="50">
        	<aui:input name="attachedFile" type="file" required="true">
				<aui:validator name="acceptFiles">'csv'</aui:validator>
			</aui:input>
        </aui:column>
    </aui:row>

    <aui:button-row>
        <aui:button type="submit" value="continue-button" />
    </aui:button-row>
</aui:form>

<a href="<%= templateManagerURL.toString() %>"><liferay-ui:message key="manage-templates" /></a>