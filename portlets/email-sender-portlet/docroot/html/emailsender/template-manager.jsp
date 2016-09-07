 <%@ include file="/html/init.jsp"%>
 
 <portlet:renderURL var="returnUrl">
	<portlet:param name="mvcPath" value="<%= WebKeys.VIEW_URL %>" />
</portlet:renderURL>
 
 <liferay-ui:header showBackURL="true" backURL="<%= returnUrl.toString() %>"  title="template-manager" />
 
 <aui:button-row cssClass="guestbook-buttons">

    <portlet:renderURL var="addTemplateURL">
        <portlet:param name="mvcPath" value="<%= WebKeys.TEMPLATE_EDIT_URL %>" />
    </portlet:renderURL>

	<aui:button onClick="<%= addTemplateURL.toString() %>" value="add-template" />
</aui:button-row>

<liferay-ui:search-container total="<%= TemplateLocalServiceUtil.getTemplatesCount() %>" 
	emptyResultsMessage="no-templates">
    <liferay-ui:search-container-results 
    	results="<%= TemplateLocalServiceUtil.getTemplates(searchContainer.getStart(), searchContainer.getEnd()) %>" />

    <liferay-ui:search-container-row
        className="com.rivetlogic.emailsender.model.Template" modelVar="template">

        <liferay-ui:search-container-column-text property="name" />

        <liferay-ui:search-container-column-text property="createDate" name="create-date" />
        
        <liferay-ui:search-container-column-jsp path="/html/emailsender/template-actions.jsp" align="right" />

    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator />
</liferay-ui:search-container>