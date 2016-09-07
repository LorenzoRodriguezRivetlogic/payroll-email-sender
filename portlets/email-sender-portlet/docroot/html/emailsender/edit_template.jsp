 <%@ include file="/html/init.jsp"%>
 
 <%
	long templateId = ParamUtil.getLong(renderRequest, WebKeys.TEMPLATE_ID);
	
 	String value = ""; 
	Template template = null;
	
	if (templateId > 0) {
		template = TemplateLocalServiceUtil.getTemplate(templateId);
		value = template.getValue();
	}
 %>
 
 <portlet:renderURL var="returnUrl">
	<portlet:param name="mvcPath" value="<%= WebKeys.TEMPLATE_MANAGER_URL %>" />
</portlet:renderURL>
 
<liferay-ui:header showBackURL="true" backURL="<%= returnUrl.toString() %>"  title="template" />
 
<portlet:actionURL name="addTemplate" var="addTemplateURL"></portlet:actionURL>

<aui:form action="<%= addTemplateURL %>" name="<portlet:namespace />fm" method="post">
	<aui:model-context bean="<%= template %>" model="<%= Template.class %>" />
	
	<aui:fieldset>
		<aui:input name="templateId" type="hidden" />
		<aui:input name="value" value="" type="hidden" />
		<aui:input name="name" required="true"></aui:input>
		
		<div class="columns" style="height: 350px;">
			<div class="editor" style="width: 100%; height: auto;">
			    <div cols="10" id="editor1" name="editor1" rows="10"  contenteditable="true"></div>
			</div>
			<div class="contacts" style="visibility: hidden; width: 0%; height: 0%;">
			    <h3>List of Droppable Tags</h3>
			    <ul id="contactList">
					<li>
					    <div class="contact h-card" data-contact="0" draggable="true" tabindex="0">TEST</div>
					</li>
		        </ul>
		    </div>
		</div>
	</aui:fieldset>
	
	<aui:button-row>
		<aui:button type="submit" onClick="submitToSave();"></aui:button>
	</aui:button-row>
</aui:form>
<script type="text/javascript">
	Liferay.on('portletReady',
	   	function(event) {    
			if('_' + event.portletId + '_' == '<portlet:namespace/>'){
				CKEDITOR.instances.editor1.setData('<%= value %>');
			}
	   	}
	); 
	function submitToSave() {
		var template = CKEDITOR.instances.editor1.getData();
		document.getElementById("<portlet:namespace />value").value = template;
		
		document.<portlet:namespace />fm.submit();
	}
</script>