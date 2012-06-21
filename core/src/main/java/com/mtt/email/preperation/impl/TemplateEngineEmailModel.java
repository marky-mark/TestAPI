package com.mtt.email.preperation.impl;

import com.mtt.email.preperation.EmailTemplateEngine;
import com.mtt.email.preperation.TemplateEmailModel;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

/**

 */
public final class TemplateEngineEmailModel implements TemplateEmailModel {

    private EmailTemplateEngine templateEngine;

    private String toAddress;

    private String fromAddress;

    private String subject;

    private String plainTextTemplate;

    private String htmlTemplate;

    private Map<String, Object> modelMap = new HashMap<String, Object>();

    /**
     * Constructor.
     *
     * @param templateEngine the template engine for generating body content parts
     */
    public TemplateEngineEmailModel(EmailTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void setToAddress(String emailAddress) {
        Assert.notNull(emailAddress);
        toAddress = emailAddress;
    }

    @Override
    public void setFromAddress(String emailAddress) {
        Assert.notNull(emailAddress);
        fromAddress = emailAddress;
    }

    @Override
    public void setSubject(String subject) {
        Assert.notNull(subject);
        this.subject = subject;
    }

    @Override
    public void addObject(String key, Object value) {
        modelMap.put(key, value);
    }

    @Override
    public void setPlainTextTemplate(String templateName) {
        plainTextTemplate = templateName;
    }

    @Override
    public void setHtmlTemplate(String templateName) {
        htmlTemplate = templateName;
    }

    @Override
    public boolean isMultipart() {
        return htmlTemplate != null && plainTextTemplate != null;
    }

//    @Override
//    public EventLogMessage log(EventLogMessage message) {
//        message.addModelObject("to", toAddress)
//                .addModelObject("from", fromAddress)
//                .addModelObject("html_template", htmlTemplate)
//                .addModelObject("plain_text_template", plainTextTemplate)
//                .addModelObject("subject", subject);
//        return message;
//    }

    @Override
    public void prepareMessage(MimeMessageHelper helper) throws MessagingException {
        helper.setTo(toAddress);
        helper.setFrom(fromAddress);
        helper.setSubject(subject);

        if (plainTextTemplate != null && htmlTemplate != null) {
            helper.setText(
                    templateEngine.processTemplate(plainTextTemplate, modelMap),
                    templateEngine.processTemplate(htmlTemplate, modelMap));
        } else if (plainTextTemplate != null) {
            helper.setText(templateEngine.processTemplate(plainTextTemplate, modelMap));
        } else if (htmlTemplate != null) {
            helper.setText(templateEngine.processTemplate(htmlTemplate, modelMap), true);
        }
    }
}
