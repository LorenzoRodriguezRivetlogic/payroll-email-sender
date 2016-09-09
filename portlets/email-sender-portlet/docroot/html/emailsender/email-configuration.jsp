<%@ include file="/html/init.jsp"%>

<%
	String fileId = ParamUtil.getString(request, WebKeys.FILE_ID);
	String emailString = ParamUtil.getString(request, WebKeys.COLUMN_EMAIL) ;
	String paramsAttr = ParamUtil.getString(request, WebKeys.COLUMNS_TO_USE);
	String emailSender = ParamUtil.getString(request, WebKeys.SENDER_EMAIL);
	String subject = ParamUtil.getString(request, WebKeys.EMAIL_SUBJECT);
	String template = ParamUtil.getString(request, WebKeys.CONTENT);
	String templateName = ParamUtil.getString(request, WebKeys.TEMPLATE_NAME);
	Long templateId = ParamUtil.getLong(request, WebKeys.TEMPLATE_ID, 0);

	List<FileColumn> params = (List<FileColumn>) JSONFactoryUtil.looseDeserialize(paramsAttr);
	FileColumn emailColumn = (FileColumn) JSONFactoryUtil.looseDeserialize(emailString);
	
	List<Template> templates = TemplateLocalServiceUtil.getTemplates(scopeGroupId);
	
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

<portlet:resourceURL var="resourceURL"/>

<portlet:actionURL name="showPreview" var="showPreviewURL">
	<portlet:param name="mvcPath" value="<%= WebKeys.PREVIEW_URL %>" />
</portlet:actionURL>

<liferay-ui:header showBackURL="true" backURL="<%= returnUrl.toString() %>"  title="email-config" />

<aui:container>
	<aui:row>
		<aui:col>
			<aui:form action="<%= showPreviewURL %>" name="<portlet:namespace />fm"  method="post">

				<aui:fieldset>
					<aui:input name="columnsToUse" value="<%= paramsAttr %>" type="hidden" />
					<aui:input name="emailColumn" value="<%= emailString %>" type="hidden" />
					<aui:input name="fileId" value="<%= fileId %>" type="hidden" />
					<aui:input name="templateId" value="" type="hidden" />
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
								    <div class="contact h-card" data-contact="<%= count %>" draggable="true" tabindex="0" ><%= fileColumn.getName() %></div>
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
				<div class="templateSelector">
					<aui:select id="templates" name="templates" label="templates" showEmptyOption="true" >
						<% 
						for (Template templateSel : templates) {
						%>
							<aui:option value="<%= templateSel.getTemplateId() %>"  selected="<%= templateId == templateSel.getTemplateId() %>">
								<liferay-ui:message key="<%= templateSel.getName() %>" />
							</aui:option>
						<% 
						}
						%>
					</aui:select>
					<aui:input name="name" value="<%= templateName %>"/>
					<aui:button-row>
						<aui:button type="button" id="load" value="load" onClick="callServeResource()" />
						<aui:button type="button" id="update" value="update" onClick="callUpdateResource()" />
						<aui:button type="button" id="delete" value="delete" onClick="callDeleteResource()" />
						<aui:button type="button" id="save" value="save" onClick="callSaveResource()" />
					</aui:button-row>
				</div>
			</aui:form>
		</aui:col>
	</aui:row>
</aui:container>

<script type="text/javascript">
	Liferay.on('portletReady',function(event) {    
			if('_' + event.portletId + '_' == '<portlet:namespace/>'){
				CKEDITOR.instances.editor1.setData('<%= bckTemplate %>');
			}
	   	}
	);   
	function submitToPreview() {
		var template = CKEDITOR.instances.editor1.getData();
		document.getElementById("<portlet:namespace />content").value = template;
		
		document.<portlet:namespace />fm.submit();
	}
	function callServeResource(){
	    AUI().use('aui-base', function(A){
	    	var option = A.one('#<portlet:namespace/>templates');
			var template = option.val();
			if (template === '') {
				alert('Select a template');
				return;
			}
			
	        A.io.request('<%=resourceURL.toString()%>', {
				method: 'post',
				data: {
					<portlet:namespace />action: 'load',
					<portlet:namespace />templateId: template
				},
				on: {
				     success: function() {
				    	var label = A.one('#<portlet:namespace/>templates option:selected').attr('text');
						document.getElementById('<portlet:namespace />name').value = label;
						document.getElementById('<portlet:namespace />templateId').value = template;
				     	CKEDITOR.instances.editor1.setData(this.get('responseData'));
				     }
				}
			});
	    });
	}
	function callSaveResource(){
	    AUI().use('aui-base', function(A){
	    	var name = A.one('#<portlet:namespace/>name').attr('value');
			if (name === '') {
				alert('The name cannot be empty');
				return;
			}
			
			var template = CKEDITOR.instances.editor1.getData();
			if (template === '') {
				alert('The template cannot be empty');
				return;
			}
			
			A.io.request('<%=resourceURL.toString()%>', {
				method: 'post',
				data: {
					<portlet:namespace />action: 'save',
					<portlet:namespace />name: name,
					<portlet:namespace />template: template
				},
				on: {
				     success: function() {
				     	var result = this.get('responseData');
				    	if (result === 'error') {
				    		alert('Error save the template save');
						} else {
							var select = A.one('#<portlet:namespace/>templates');
				    		var option  = A.Node.create( '<option value=\"'+result+'\">'+name+'</option>');
				    		select.append(option);
							
							alert('Template saved');
						}
				     }
				}
			});
	    });
	}
	function callUpdateResource(){
	    AUI().use('aui-base', function(A){
	    	var option = A.one('#<portlet:namespace/>templates');
			var templateId = option.val();
			if (templateId === '') {
				alert('Select a template');
			}
			
			var name = A.one('#<portlet:namespace/>name').attr('value');
			if (name === '') {
				alert('The name cannot be empty');
				return;
			}
			
			var template = CKEDITOR.instances.editor1.getData();
			if (template === '') {
				alert('The template cannot be empty');
				return;
			}
			
			A.io.request('<%=resourceURL.toString()%>', {
				method: 'post',
				data: {
					<portlet:namespace />action: 'update',
					<portlet:namespace />templateId: templateId,
					<portlet:namespace />name: name,
					<portlet:namespace />template: template
				},
				on: {
				     success: function() {
				    	 var result = this.get('responseData');
				    	 if (result === 'success') {
				    		alert('Template updated');
						 } else {
							alert('Error updating the template save');
						 }
				     }
				}
			});
	    });
	}
	function callDeleteResource(){
	    AUI().use('aui-base', function(A){
	    	var option = A.one('#<portlet:namespace/>templates');
			var template = option.val();
			if (template === '') {
				alert('Select a template');
			}
			
			A.io.request('<%=resourceURL.toString()%>', {
				method: 'post',
				data: {
					<portlet:namespace />action: 'delete',
					<portlet:namespace />templateId: template,
				},
				on: {
				     success: function() {
				    	 var result = this.get('responseData');
				    	 if (result === 'success') {
				    	 	A.one('#<portlet:namespace/>templates option[value=\''+template+'\']').remove();
				    	 	document.getElementById('<portlet:namespace />name').value = '';
				    	 	alert('Template deleted');
						 } else {
							alert('Error deleting the template save');
						 }
				     }
				}
			});
	    });
	}
</script>
