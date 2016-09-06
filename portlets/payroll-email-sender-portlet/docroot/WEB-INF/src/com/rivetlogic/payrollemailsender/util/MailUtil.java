package com.rivetlogic.payrollemailsender.util;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.rivetlogic.payrollemailsender.model.FileColumn;

import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class MailUtil {
    
    public static void sendEmails(String senderEmail, String body, String subject, List<Map<String, String>> data) throws Exception  {
        InternetAddress from = new InternetAddress(senderEmail);
        
        for (Map<String, String> dataRow : data) {
        	MailMessage message = generateMailMessage(from, dataRow, body, subject);
        	MailServiceUtil.sendEmail(message);
		}
    }
    
    private static MailMessage generateMailMessage(InternetAddress from, Map<String, String> dataRow, String body, String subject) 
    		throws AddressException {
		
    	InternetAddress to = new InternetAddress(dataRow.get(WebKeys.EMAIL_TO_SEND));
    	String tmpBody = body;
    	
    	for (Map.Entry<String, String> entry : dataRow.entrySet()) {
    	    String key = entry.getKey();
    	    String value = entry.getValue();
    	    
    	    if (key.equals(WebKeys.EMAIL_TO_SEND)) {
    	    	continue;
    	    }
    	    
    	    tmpBody = tmpBody.replace(key, value);
    	}
    	
    	return new MailMessage(from, to, subject, tmpBody, true);
	}
}