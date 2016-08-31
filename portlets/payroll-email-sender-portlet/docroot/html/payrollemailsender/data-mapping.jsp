<%@ include file="/html/init.jsp"%>

<%
	String backURL = ParamUtil.getString(request, "backURL");
	String fileId = ParamUtil.getString(request, "fileId");

	List<FileColumn> list = FileUtil.getFileColumns(fileId);
%>

<portlet:actionURL name="sendData" var="sendDataURL">
	<portlet:param name="backURL" value="<%= backURL %>"/>
</portlet:actionURL>

<liferay-ui:header showBackURL="true" backURL="<%= backURL %>"  title="data-mapping" />

<aui:form action="<%= sendDataURL %>" method="post" name="fm">

	<aui:input name="selectedFile" type="hidden" value='<%= fileId %>'/>
	
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
			    <aui:input name="useInHtml" label="" type="checkbox" />
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="contains-email">
			    <aui:input name="containsEmail" label="" type="radio" value="<%= column.getId() %>"/>
			</liferay-ui:search-container-column-text>
			
		</liferay-ui:search-container-row>
	
		<liferay-ui:search-iterator />
	
	</liferay-ui:search-container>
	
	<aui:button-row>
        <aui:button type="submit" value="Continue" />
    </aui:button-row>
</aui:form>
