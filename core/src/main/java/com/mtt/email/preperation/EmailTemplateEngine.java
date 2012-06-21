package com.mtt.email.preperation;

import java.util.Map;

/**
 * Engine for generating email content body using a template
 */
public interface EmailTemplateEngine {

    /**
     * Generate content for a given template using the passed in template model.
     *
     * @param templateName the template to process
     * @param model    the template model
     * @return generated content
     */
    String processTemplate(String templateName, Map<String, Object> model);
}
