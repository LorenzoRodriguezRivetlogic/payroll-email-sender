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

package com.rivetlogic.emailsender.service.persistence;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.rivetlogic.emailsender.model.Template;
import com.rivetlogic.emailsender.service.TemplateLocalServiceUtil;

/**
 * @author lorenzorodriguez
 * @generated
 */
public abstract class TemplateActionableDynamicQuery
	extends BaseActionableDynamicQuery {
	public TemplateActionableDynamicQuery() throws SystemException {
		setBaseLocalService(TemplateLocalServiceUtil.getService());
		setClass(Template.class);

		setClassLoader(com.rivetlogic.emailsender.service.ClpSerializer.class.getClassLoader());

		setPrimaryKeyPropertyName("templateId");
	}
}