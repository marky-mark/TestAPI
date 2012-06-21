package com.mtt.email.preperation;

/**
 * Collection of Gumtree seller email templates.
 */
public enum EmailTemplate {

    USER_ACTIVATION_HTML("user-activation-email-html"),
    USER_ACTIVATION_PLAIN_TEXT("user-activation-email-plain-text");

    private String templateName;

    /**
     * Constructor.
     *
     * @param templateName the template name
     */
    EmailTemplate(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
