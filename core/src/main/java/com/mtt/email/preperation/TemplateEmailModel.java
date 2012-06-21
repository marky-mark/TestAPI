package com.mtt.email.preperation;

/**
 * Represents the model for an email.
 */
public interface TemplateEmailModel extends EmailPreparator {

    /**
     * Set the email address to where the email should be sent.
     *
     * @param emailAddress the email address to where the email should be sent.
     */
    void setToAddress(String emailAddress);

    /**
     * Set the email address from where emails should addressed.
     *
     * @param emailAddress the email address from where emails should addressed.
     */
    void setFromAddress(String emailAddress);

    /**
     * Set the chosen subject for the email.
     *
     * @param subject the chosen subject for the email.
     */
    void setSubject(String subject);

    /**
     * Add an object to the email model.
     *
     * @param key   the unique key for the model object
     * @param value the value for the key
     */
    void addObject(String key, Object value);

    /**
     * Set the template for generating plain text body part.
     *
     * @param templateName the template for generating plain text body part.
     */
    void setPlainTextTemplate(String templateName);

    /**
     * Set the template for generating html body part.
     *
     * @param templateName the template for generating html body part.
     */
    void setHtmlTemplate(String templateName);
}
