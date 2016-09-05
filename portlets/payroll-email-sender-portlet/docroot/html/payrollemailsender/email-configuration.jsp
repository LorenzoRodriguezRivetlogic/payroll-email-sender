<%@ include file="/html/init.jsp"%>

<%
	String fileId = ParamUtil.getString(request, WebKeys.FILE_ID);
	String emailString = ParamUtil.getString(request, WebKeys.COLUMN_EMAIL) ;
	String paramsAttr = ParamUtil.getString(request, WebKeys.COLUMNS_TO_USE);
	String emailSender = ParamUtil.getString(request, WebKeys.SENDER_EMAIL);
	String subject = ParamUtil.getString(request, WebKeys.EMAIL_SUBJECT);
	
	List<FileColumn> params = (List<FileColumn>) JSONFactoryUtil.looseDeserialize(paramsAttr);
	FileColumn emailColumn = (FileColumn) JSONFactoryUtil.looseDeserialize(emailString);
%>

<portlet:renderURL var="returnUrl">
	<portlet:param name="mvcPath" value="<%= WebKeys.DATA_MAPPING_URL %>" />
    <portlet:param name="fileId" value="<%= fileId %>" />
</portlet:renderURL>

<portlet:actionURL name="showPreview" var="showPreviewURL">
	<portlet:param name="mvcPath" value="<%= WebKeys.PREVIEW_URL %>" />
</portlet:actionURL>

<portlet:actionURL name="saveTemplate" var="saveTemplateURL">
	<portlet:param name="jspPage" value="<%= WebKeys.EMAIL_CONFIGURATION_URL %>" />
</portlet:actionURL>

<liferay-ui:header showBackURL="true" backURL="<%= returnUrl.toString() %>"  title="email-config" />

<aui:container>
	<aui:row>
		<aui:col width="25">
			<div class="sidebar" id="params">
				<% 
				for (FileColumn fileColumn : params) {
				%>
				<div class="drag alert alert-info">
					<%= fileColumn.getName() %>
				</div>
				<% 
				}
				%>
			</div>
		</aui:col>
		<aui:col width="75">
			<form method="post" action="<%= showPreviewURL %>" enctype="multipart/form-data" onsubmit="<portlet:namespace />extractCodeFromEditor()" name="<portlet:namespace />fm" >
			
				<aui:fieldset>
					<aui:input name="columnsToUse" value="<%= paramsAttr %>" type="hidden" />
					<aui:input name="emailColumn" value="<%= emailString %>" type="hidden" />
					<aui:input name="fileId" value="<%= fileId %>" type="hidden" />
				
					<aui:input name="senderEmail" label="subscription-email" required="true" value="<%= emailSender %>">
						<aui:validator name="email"/>
					</aui:input>
					
					<aui:input name="emailSubject" label="subscription-subject" required="true" value="<%= subject %>" />			
							
					<aui:field-wrapper label="">		
						<liferay-ui:input-editor />
						<input name="<portlet:namespace />content" type="hidden" value="" />
                    </aui:field-wrapper>

				</aui:fieldset>
						
				<aui:button-row>
    				<aui:button type="submit" value="show-preview" />
				</aui:button-row>
			</form>
		</aui:col>
	</aui:row>
</aui:container>

<script type="text/javascript">
	function <portlet:namespace />initEditor() {
		return '<table border="1" cellpadding="1" cellspacing="1" style="width:500px;"><tbody><tr><td>Columna</td><td>Columna</td></tr><tr><td>$column_a</td><td>$column_b</td></tr></tbody></table>';
	}
	
	function <portlet:namespace />extractCodeFromEditor() {
		var x = document.<portlet:namespace />fm.<portlet:namespace />content.value = window.<portlet:namespace />editor.getHTML();
		submitForm(document.<portlet:namespace />fm);
	}
</script>