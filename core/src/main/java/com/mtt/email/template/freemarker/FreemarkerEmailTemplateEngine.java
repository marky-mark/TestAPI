package com.mtt.email.template.freemarker;


import com.mtt.email.exception.EmailContentCreationException;
import com.mtt.email.preperation.EmailTemplateEngine;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**

 */
@Component
public final class FreemarkerEmailTemplateEngine implements EmailTemplateEngine {

    @Autowired
    private Configuration freemarkerConfiguration;

    @Override
    public String processTemplate(String templateName, Map<String, Object> model) {
        try {
            String ftlTemplate = templateName + ".ftl";
            StringWriter writer = new StringWriter();
            Template template = freemarkerConfiguration.getTemplate(ftlTemplate);
            if (template != null) {
                template.process(model, writer);
            } else {
                throw new EmailContentCreationException("Unrecognised template: " + ftlTemplate);
            }
            return writer.toString();
        } catch (IOException ioex) {
            throw new EmailContentCreationException(ioex);
        } catch (TemplateException tex) {
            throw new EmailContentCreationException(tex);
        }
    }
}
