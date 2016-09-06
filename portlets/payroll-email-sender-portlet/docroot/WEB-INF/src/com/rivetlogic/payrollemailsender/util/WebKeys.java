package com.rivetlogic.payrollemailsender.util;

public interface WebKeys extends com.liferay.portal.kernel.util.WebKeys{
	String PARAM_CSV = "csv";
	String PARAM_MIME = "text/csv";
	String DATA_MAPPING_URL = "/html/payrollemailsender/data-mapping.jsp";
	String EMAIL_CONFIGURATION_URL = "/html/payrollemailsender/email-configuration.jsp";
	String PREVIEW_URL = "/html/payrollemailsender/preview-email.jsp";
	String VIEW_URL = "/html/payrollemailsender/view.jsp";
	String JSP_PAGE = "jspPage";
	String FILE_ID = "fileId";
	String CONTAINS_EMAIL = "containsEmail";
	String USE_IN_HTML = "useInHtml";
	String COLUMNS_TO_USE = "columnsToUse";
	String COLUMN_EMAIL = "emailColumn";
	String CONTENT = "content";
	String SENDER_EMAIL = "senderEmail";
	String EMAIL_SUBJECT = "emailSubject";
	String EMAIL_TO_SEND = "emailToSend";
	String DEFAULT_FILE_NAME = "CSV - ";
	String DEFAULT_FOLDER_NAME = "Temp Files";
    String DEFAULT_FOLFER_DESC = "Uploaded temp files";
}
