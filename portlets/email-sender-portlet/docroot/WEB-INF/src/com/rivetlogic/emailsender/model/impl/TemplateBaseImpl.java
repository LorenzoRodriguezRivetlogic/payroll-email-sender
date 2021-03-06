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

package com.rivetlogic.emailsender.model.impl;

import com.liferay.portal.kernel.exception.SystemException;

import com.rivetlogic.emailsender.model.Template;
import com.rivetlogic.emailsender.service.TemplateLocalServiceUtil;

/**
 * The extended model base implementation for the Template service. Represents a row in the &quot;RL_Template&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link TemplateImpl}.
 * </p>
 *
 * @author lorenzorodriguez
 * @see TemplateImpl
 * @see com.rivetlogic.emailsender.model.Template
 * @generated
 */
public abstract class TemplateBaseImpl extends TemplateModelImpl
	implements Template {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a template model instance should use the {@link Template} interface instead.
	 */
	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			TemplateLocalServiceUtil.addTemplate(this);
		}
		else {
			TemplateLocalServiceUtil.updateTemplate(this);
		}
	}
}