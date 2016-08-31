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

<div id="play">
	<aui:container>
		<aui:row>
			<aui:col width="25">
				<div class="sidebar" id="params">
					<% 
					for (FileColumn fileColumn : params) {
					%>
					<div class="drag alert alert-info">
						<%= fileColumn.getNameToUse() %>
					</div>
					<% 
					}
					%>
				</div>
			</aui:col>
			<aui:col width="75">
				
				    <div id="drop"></div>
				
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
</div>

<script type="text/javascript">
	YUI().use('dd-drop', 'dd-constrain', function(Y) {

	    //Get all the divs with the class drag
	    var drags = Y.Node.all('div.drag');
	    //Walk through each one
	    drags.each(function(v, k) {
	        //Create the new Drag Instance
	        var dd = new Y.DD.Drag({
	            //Give it the node
	            node: v,
	            //Set the dragMode to intersect
	            dragMode: 'intersect',
	            //Attach the data here.
	        }).plug(Y.Plugin.DDConstrained, {
	            //Keep it inside the work area
	            constrain2node: '#play'
	        });
	        //Prevent the default end event (this moves the node back to its start position)
	        dd.on('drag:end', function(e) {
	            e.preventDefault();
	        });
	    });
	
	    var drop = new Y.DD.Drop({
	        node: '#drop'
	    });
	    
	    //Listen for a drop:hit on this target
	    drop.on('drop:hit', function(e) {
	        //Now we get the drag instance that triggered the drop hit
	        var drag = e.drag;
	        //get the data from it
	        var data = drag.get('data');

	        //Do something with the data
	        var out = ['id: ' + drag.get('node').get('id')];
	        Y.each(data, function(v, k) {
	            out[out.length] = k + ': ' + v;
	        });
	        var str = '<p><strong>Dropped</strong>: ' + out.join(', ') + '</p>';
	        this.get('node').set('innerHTML', str);
	    });
	});
</script>