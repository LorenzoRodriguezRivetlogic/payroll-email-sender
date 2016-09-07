 <%@ include file="/html/init.jsp"%>
 
 <%
	 long templateId = ParamUtil.getLong(renderRequest, WebKeys.TEMPLATE_ID);
	
	 Template template = null;
	
	 if (templateId > 0) {
		 template = TemplateLocalServiceUtil.getTemplate(templateId);
	 }
 %>
 
 <portlet:renderURL var="returnUrl">
	<portlet:param name="mvcPath" value="<%= WebKeys.TEMPLATE_MANAGER_URL %>" />
</portlet:renderURL>
 
<liferay-ui:header showBackURL="true" backURL="<%= returnUrl.toString() %>"  title="add-template" />
 
<portlet:actionURL name="addTemplate" var="addTemplateURL"></portlet:actionURL>

<aui:form action="<%= addTemplateURL %>" name="<portlet:namespace />fm">
	<aui:model-context bean="<%= template %>" model="<%= Template.class %>" />
	
	<aui:fieldset>
		<aui:input name="templateId" type="hidden" />
		<aui:input name="name"></aui:input>
		<aui:input name="value"></aui:input>
	</aui:fieldset>
	
	<aui:button-row>
		<aui:button type="submit"></aui:button>
	</aui:button-row>
</aui:form>