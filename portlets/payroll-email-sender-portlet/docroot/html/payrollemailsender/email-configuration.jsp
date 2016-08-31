<%@ include file="/html/init.jsp"%>

<%
	String backURL = ParamUtil.getString(request, "backURL");
	String fileId = ParamUtil.getString(request, "fileId");
	
	List<FileColumn> params = (List<FileColumn>) request.getAttribute("params");
	
	pageContext.setAttribute(PrefsKeys.SUBS_EMAIL, GetterUtil.getString(portletPreferences.getValue(PrefsKeys.SUBS_EMAIL, StringPool.BLANK)));
	pageContext.setAttribute(PrefsKeys.SUBS_SUBJECT, MailUtil.getSubject(renderRequest));

	String template = MailUtil.getTemplate(renderRequest);
%>

<liferay-ui:header showBackURL="true" backURL="<%= backURL %>"  title="email-config" />

<portlet:actionURL name="sendMails" var="sendMailsURL">
</portlet:actionURL>

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
			<aui:form action="<%= sendMailsURL %>">
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			
				<aui:fieldset>
					<aui:input name="preferences--email--" label="subscription-email" required="true" value="${ email }">
						<aui:validator name="email"/>
					</aui:input>
					
					<aui:input name="preferences--subject--" label="subscription-subject" required="true" value="${ subject }" />
					
					<aui:field-wrapper label="subscription-template">					
						<textarea class="receiver yui3-dd-drop" rows="10" cols="80">
			            </textarea>
			            
						<liferay-ui:input-editor name="preferences--template--"/>
					</aui:field-wrapper>
				</aui:fieldset>
						
				<aui:button-row>
					<aui:button type="submit"/>
				</aui:button-row>
			</aui:form>
		</aui:col>
	</aui:row>
</aui:container>