<%@ include file="/html/init.jsp"%>

<%
	String fileId = ParamUtil.getString(request, WebKeys.FILE_ID);
	String emailString = ParamUtil.getString(request, WebKeys.COLUMN_EMAIL) ;
	String paramsAttr = ParamUtil.getString(request, WebKeys.COLUMNS_TO_USE);
	String emailSender = ParamUtil.getString(request, WebKeys.SENDER_EMAIL);
	String subject = ParamUtil.getString(request, WebKeys.EMAIL_SUBJECT);
	String template = ParamUtil.getString(request, WebKeys.CONTENT);
	
	List<FileColumn> params = (List<FileColumn>) JSONFactoryUtil.looseDeserialize(paramsAttr);
	FileColumn emailColumn = (FileColumn) JSONFactoryUtil.looseDeserialize(emailString);
	
	String bckTemplate = "";
	
	if (template.isEmpty()) {
		bckTemplate = Utils.generateHtmlTable(params);
	} else {
		bckTemplate += template;
	}
%>

<portlet:renderURL var="returnUrl">
	<portlet:param name="mvcPath" value="<%= WebKeys.DATA_MAPPING_URL %>" />
    <portlet:param name="fileId" value="<%= fileId %>" />
</portlet:renderURL>

<portlet:actionURL name="showPreview" var="showPreviewURL">
	<portlet:param name="mvcPath" value="<%= WebKeys.PREVIEW_URL %>" />
</portlet:actionURL>

<liferay-ui:header showBackURL="true" backURL="<%= returnUrl.toString() %>"  title="email-config" />

<aui:container>
	<aui:row>
		<aui:col>
			<aui:form action='<%= showPreviewURL %>' name='fm' method="post">

				<aui:fieldset>
					<aui:input name="columnsToUse" value="<%= paramsAttr %>" type="hidden" />
					<aui:input name="emailColumn" value="<%= emailString %>" type="hidden" />
					<aui:input name="fileId" value="<%= fileId %>" type="hidden" />
					<aui:input name="content" value="" type="hidden" />
				
					<aui:input name="senderEmail" label="subscription-email" required="true" value="<%= emailSender %>">
						<aui:validator name="email"/>
					</aui:input>
					
					<aui:input name="emailSubject" label="subscription-subject" required="true" value="<%= subject %>" />			
					
					<div class="columns">
						<div class="editor">
						    <div cols="10" id="editor1" name="editor1" rows="10"  contenteditable="true"></div>
						</div>
						<div class="contacts">
						    <h3>List of Droppable Tags</h3>
						    <ul id="contactList">
						        <% 
								int count = 0;
								for (FileColumn fileColumn : params) {
								%>
								<li>
								    <div class="contact h-card" data-contact="<%= count %>" draggable="true" tabindex="0"><%= fileColumn.getName() %></div>
								</li>
								<% 
								count++;
								}
								%>
					        </ul>
					    </div>
					</div>
				</aui:fieldset>
						
				<aui:button-row>
					<aui:button type="submit" value="show-preview" onClick="submitToPreview();" />
				</aui:button-row>
			</aui:form>
		</aui:col>
	</aui:row>
</aui:container>

<script type="text/javascript">
	Liferay.on('portletReady',
	   	function(event) {    
			if('_' + event.portletId + '_' == '<portlet:namespace/>'){
				CKEDITOR.instances.editor1.setData('<%= bckTemplate %>');
			}
	   	}
	);      
	function submitToPreview() {
		var template = CKEDITOR.instances.editor1.getData();
		var senderEmail = document.getElementById("<portlet:namespace />senderEmail").value;
		var emailSubject = document.getElementById("<portlet:namespace />emailSubject").value;
		
		document.getElementById("<portlet:namespace />content").value = template;
		
		if (senderEmail === "") {
			return;
		}
		
		if (emailSubject === "") {
			return;
		}
		if (!validateEmail(senderEmail)) {
			return;	
		}
		
		document.<portlet:namespace />fm.submit();
	}
	function validateEmail(email) {
	    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(email);
	}
</script>