<%@include file="/html/init.jsp"%>

<%
	String mvcPath = ParamUtil.getString(request, "mvcPath");
	ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	Template template = (Template) row.getObject(); 
%>

<liferay-ui:icon-menu>
	<portlet:renderURL var="editURL">
		<portlet:param name="templateId" value="<%= String.valueOf(template.getTemplateId()) %>" />
		<portlet:param name="mvcPath" value="<%= WebKeys.TEMPLATE_EDIT_URL %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="edit" message="Edit" url="<%= editURL.toString() %>" />

	<portlet:actionURL name="deleteTemplate" var="deleteURL">
		<portlet:param name="templateId" value="<%= String.valueOf(template.getTemplateId()) %>" />
	</portlet:actionURL>
	
	<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />

</liferay-ui:icon-menu>

