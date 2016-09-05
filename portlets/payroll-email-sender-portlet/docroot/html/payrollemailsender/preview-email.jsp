<%@ include file="/html/init.jsp"%>

<%
	String fileId = ParamUtil.getString(request, WebKeys.FILE_ID);
	String emailString = ParamUtil.getString(request, WebKeys.COLUMN_EMAIL) ;
	String paramsAttr = ParamUtil.getString(request, WebKeys.COLUMNS_TO_USE);
	String template = ParamUtil.getString(request, WebKeys.CONTENT);
	String emailSender = ParamUtil.getString(request, WebKeys.SENDER_EMAIL);
	String subject = ParamUtil.getString(request, WebKeys.EMAIL_SUBJECT);
	List<FileColumn> params = (List<FileColumn>) JSONFactoryUtil.looseDeserialize(paramsAttr);
	FileColumn email = (FileColumn) JSONFactoryUtil.looseDeserialize(emailString);
	
	Map<String, String> firstRow = FileUtil.getFirstDataRow(fileId, params, email);
	
	for (Map.Entry<String, String> entry : firstRow.entrySet()) {
	    String key = entry.getKey();
	    String value = entry.getValue();
	    
	    template = template.replace(key, value);
	}
%>

<portlet:actionURL name="sendEmails" var="sendEmailsURL">
	<portlet:param name="backURL" value="<%= currentURL %>"/>
</portlet:actionURL>

<portlet:renderURL var="returnUrl">
	<portlet:param name="mvcPath" value="<%= WebKeys.EMAIL_CONFIGURATION_URL %>" />
    <portlet:param name="fileId" value="<%= fileId %>" />
    <portlet:param name="emailColumn" value="<%= emailString %>" />
    <portlet:param name="columnsToUse" value="<%= paramsAttr %>" />
    <portlet:param name="senderEmail" value="<%= emailSender %>" />
    <portlet:param name="emailSubject" value="<%= subject %>" />
</portlet:renderURL>

<liferay-ui:header showBackURL="true" backURL="<%= returnUrl.toString() %>"  title="preview" />

<table>
	<tr>
		<td><liferay-ui:message key="label-from" /></td>
		<td><%= emailSender %></td>
	</tr>
	<tr>
		<td><liferay-ui:message key="label-to" /></td>
		<td><%= firstRow.get("email") %></td>
	</tr>
	<tr>
		<td><liferay-ui:message key="label-subject" /></td>
		<td><%= subject %></td>
	</tr>
</table>
<div style="border: 1px solid #d3d3d3; padding: 10px;">
	<%= template %>
</div>

<aui:form action="<%= sendEmailsURL %>" method="post" name="fm">
	<aui:button-row>
        <aui:button type="submit" value="send-button" />
    </aui:button-row>
</aui:form>