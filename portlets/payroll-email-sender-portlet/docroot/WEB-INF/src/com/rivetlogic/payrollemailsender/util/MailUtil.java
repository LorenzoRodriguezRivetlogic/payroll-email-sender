package com.rivetlogic.payrollemailsender.util;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.util.ContentUtil;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class MailUtil {

    public static final String DEFAULT_SUBJECT = "default-mail-subject";
    public static final String DEFAULT_TEMPLATE = "content/email.vm";
    
    /*public static void sendNotification(PortletRequest req, Subscription subscription, String body) throws Exception  {
        InternetAddress from = getFromAddress(req);
        InternetAddress to = new InternetAddress(subscription.getEmailAddress());
        MailMessage message = new MailMessage(from, to, getSubject(req), body, true);
        MailServiceUtil.sendEmail(message);
    }
    
    private static InternetAddress getFromAddress(PortletRequest req) throws AddressException {
        PortletPreferences prefs = req.getPreferences();
        String email = GetterUtil.getString(prefs.getValue(PrefsKeys.SUBS_EMAIL, StringPool.BLANK));
        return new InternetAddress(email);
    }*/
    
    public static String getSubject(PortletRequest req) {
        PortletPreferences prefs = req.getPreferences();
        String subject = GetterUtil.getString(prefs.getValue(PrefsKeys.SUBS_SUBJECT, StringPool.BLANK));
        if(Validator.equals(prefs, StringPool.BLANK)) {
            subject = LanguageUtil.get(req.getLocale(), DEFAULT_SUBJECT);
        }
        return subject;
    }
    
    /*private static String evaluateTemplate(PortletRequest req, Job job) throws Exception {
        StringWriter writer = new StringWriter();
        String template = getTemplate(req);
        Map<String,Object> map = getTemplateVariables(req, job);
        Velocity.init();
        Velocity.evaluate(new VelocityContext(map), writer, "velocityLogTag", template);
        return writer.toString();
    }
    
    private static Map<String, Object> getTemplateVariables(PortletRequest req, Job job) throws PortalException, SystemException {
        Map<String, Object> map = new HashMap<String,Object>();
        String url = StringPool.BLANK;
        
        ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
        long plid = PortalUtil.getPlidFromPortletId(themeDisplay.getScopeGroupId(), "jobsboard_WAR_jobsboardportlet");
        if(plid != 0) {
            PortletURL portalUrl = PortletURLFactoryUtil.create(req, "jobsboard_WAR_jobsboardportlet", plid, PortletRequest.RENDER_PHASE);
            portalUrl.setParameter("redirect", portalUrl.toString());
            portalUrl.setParameter("mvcPath", "/html/jobsboard/job-details.jsp");
            portalUrl.setParameter("jobId", Long.toString(job.getJobId()));
            url = portalUrl.toString();
        }
        
        map.put("jobName", job.getName());
        map.put("jobDescription", job.getDescription());
        map.put("address", url);
        
        return map;
    }
    
    public static String generateNotification(PortletRequest req, Job job) throws Exception {
        return evaluateTemplate(req, job);
    }*/
    
    public static String getTemplate(PortletRequest req) {
        PortletPreferences prefs = req.getPreferences();
        String template = GetterUtil.getString(prefs.getValue(PrefsKeys.SUBS_TEMPLATE, StringPool.BLANK));
        if(Validator.equals(template, StringPool.BLANK)) {
            template = MailUtil.getDefaultTemplate();
        }
        return template;
    }
    
    public static String getDefaultTemplate() {
        return ContentUtil.get(DEFAULT_TEMPLATE);
    }
}