/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.rivetlogic.emailsender.model;

import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Template}.
 * </p>
 *
 * @author lorenzorodriguez
 * @see Template
 * @generated
 */
public class TemplateWrapper implements Template, ModelWrapper<Template> {
	public TemplateWrapper(Template template) {
		_template = template;
	}

	@Override
	public Class<?> getModelClass() {
		return Template.class;
	}

	@Override
	public String getModelClassName() {
		return Template.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("templateId", getTemplateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long templateId = (Long)attributes.get("templateId");

		if (templateId != null) {
			setTemplateId(templateId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	/**
	* Returns the primary key of this template.
	*
	* @return the primary key of this template
	*/
	@Override
	public long getPrimaryKey() {
		return _template.getPrimaryKey();
	}

	/**
	* Sets the primary key of this template.
	*
	* @param primaryKey the primary key of this template
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_template.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this template.
	*
	* @return the uuid of this template
	*/
	@Override
	public java.lang.String getUuid() {
		return _template.getUuid();
	}

	/**
	* Sets the uuid of this template.
	*
	* @param uuid the uuid of this template
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_template.setUuid(uuid);
	}

	/**
	* Returns the template ID of this template.
	*
	* @return the template ID of this template
	*/
	@Override
	public long getTemplateId() {
		return _template.getTemplateId();
	}

	/**
	* Sets the template ID of this template.
	*
	* @param templateId the template ID of this template
	*/
	@Override
	public void setTemplateId(long templateId) {
		_template.setTemplateId(templateId);
	}

	/**
	* Returns the group ID of this template.
	*
	* @return the group ID of this template
	*/
	@Override
	public long getGroupId() {
		return _template.getGroupId();
	}

	/**
	* Sets the group ID of this template.
	*
	* @param groupId the group ID of this template
	*/
	@Override
	public void setGroupId(long groupId) {
		_template.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this template.
	*
	* @return the company ID of this template
	*/
	@Override
	public long getCompanyId() {
		return _template.getCompanyId();
	}

	/**
	* Sets the company ID of this template.
	*
	* @param companyId the company ID of this template
	*/
	@Override
	public void setCompanyId(long companyId) {
		_template.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this template.
	*
	* @return the user ID of this template
	*/
	@Override
	public long getUserId() {
		return _template.getUserId();
	}

	/**
	* Sets the user ID of this template.
	*
	* @param userId the user ID of this template
	*/
	@Override
	public void setUserId(long userId) {
		_template.setUserId(userId);
	}

	/**
	* Returns the user uuid of this template.
	*
	* @return the user uuid of this template
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _template.getUserUuid();
	}

	/**
	* Sets the user uuid of this template.
	*
	* @param userUuid the user uuid of this template
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_template.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this template.
	*
	* @return the user name of this template
	*/
	@Override
	public java.lang.String getUserName() {
		return _template.getUserName();
	}

	/**
	* Sets the user name of this template.
	*
	* @param userName the user name of this template
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_template.setUserName(userName);
	}

	/**
	* Returns the create date of this template.
	*
	* @return the create date of this template
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _template.getCreateDate();
	}

	/**
	* Sets the create date of this template.
	*
	* @param createDate the create date of this template
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_template.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this template.
	*
	* @return the modified date of this template
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _template.getModifiedDate();
	}

	/**
	* Sets the modified date of this template.
	*
	* @param modifiedDate the modified date of this template
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_template.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this template.
	*
	* @return the name of this template
	*/
	@Override
	public java.lang.String getName() {
		return _template.getName();
	}

	/**
	* Sets the name of this template.
	*
	* @param name the name of this template
	*/
	@Override
	public void setName(java.lang.String name) {
		_template.setName(name);
	}

	/**
	* Returns the value of this template.
	*
	* @return the value of this template
	*/
	@Override
	public java.lang.String getValue() {
		return _template.getValue();
	}

	/**
	* Sets the value of this template.
	*
	* @param value the value of this template
	*/
	@Override
	public void setValue(java.lang.String value) {
		_template.setValue(value);
	}

	@Override
	public boolean isNew() {
		return _template.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_template.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _template.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_template.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _template.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _template.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_template.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _template.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_template.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_template.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_template.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new TemplateWrapper((Template)_template.clone());
	}

	@Override
	public int compareTo(com.rivetlogic.emailsender.model.Template template) {
		return _template.compareTo(template);
	}

	@Override
	public int hashCode() {
		return _template.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.rivetlogic.emailsender.model.Template> toCacheModel() {
		return _template.toCacheModel();
	}

	@Override
	public com.rivetlogic.emailsender.model.Template toEscapedModel() {
		return new TemplateWrapper(_template.toEscapedModel());
	}

	@Override
	public com.rivetlogic.emailsender.model.Template toUnescapedModel() {
		return new TemplateWrapper(_template.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _template.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _template.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_template.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TemplateWrapper)) {
			return false;
		}

		TemplateWrapper templateWrapper = (TemplateWrapper)obj;

		if (Validator.equals(_template, templateWrapper._template)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _template.getStagedModelType();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Template getWrappedTemplate() {
		return _template;
	}

	@Override
	public Template getWrappedModel() {
		return _template;
	}

	@Override
	public void resetOriginalValues() {
		_template.resetOriginalValues();
	}

	private Template _template;
}