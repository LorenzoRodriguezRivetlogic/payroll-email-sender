<%@ include file="/html/init.jsp"%>

<%
	String fileId = ParamUtil.getString(request, WebKeys.FILE_ID);

	List<FileColumn> list = FileUtil.getFileColumns(fileId);
%>

<portlet:renderURL var="returnUrl">
	<portlet:param name="mvcPath" value="<%= WebKeys.VIEW_URL %>" />
	<portlet:param name="fileId" value="<%= fileId %>" />
</portlet:renderURL>


<portlet:actionURL name="sendData" var="sendDataURL">
	<portlet:param name="backURL" value="<%= currentURL %>"/>
</portlet:actionURL>

<liferay-ui:header showBackURL="true" backURL="<%= returnUrl.toString() %>"  title="data-mapping" />

<aui:form action="<%= sendDataURL %>" method="post" name="fm">

	<aui:input name="fileId" type="hidden" value='<%= fileId %>'/>
	
	<liferay-ui:search-container delta="10" emptyResultsMessage="no-columns-were-found" >
		
		<liferay-ui:search-container-results
			results="<%= list %>"
			total="<%= list.size() %>" />
	
		<liferay-ui:search-container-row
			className="com.rivetlogic.payrollemailsender.model.FileColumn"
			keyProperty="id"
			modelVar="column" >
			
			<liferay-ui:search-container-column-text property="name" name="name-csv" />
            <liferay-ui:search-container-column-text name="use-html">
			    <aui:input name="useInHtml" label="" type="checkbox" checked="true" />
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="contains-email">
			    <aui:input name="containsEmail" label="" type="radio" value="<%= column.getId() %>" required="true"/>
			</liferay-ui:search-container-column-text>
			
		</liferay-ui:search-container-row>
	
		<liferay-ui:search-iterator />
	
	</liferay-ui:search-container>
	
	<aui:button-row>
        <aui:button type="submit" value="continue-button" />
    </aui:button-row>
</aui:form>
