package com.mtt.email.preperation.impl;

import com.mtt.email.preperation.EmailTemplateEngine;
import com.mtt.email.preperation.TemplateEmailModel;
import com.mtt.email.preperation.TemplateEmailModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of Factory to that utilises the configured
 */
@Component
public final class TemplateEngineEmailModelFactory implements TemplateEmailModelFactory {

    @Autowired
    private EmailTemplateEngine templateEngine;

    @Override
    public TemplateEmailModel newInstance() {
        return new TemplateEngineEmailModel(templateEngine);
    }
}
