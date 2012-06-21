package com.mtt.email.preperation;

/**
 * Factory for creating email templates
 */
public interface TemplateEmailModelFactory {

    /**
     * @return a new instance
     */
    TemplateEmailModel newInstance();
}
