package com.mtt.email;

import com.mtt.email.preperation.EmailUrlScheme;
import com.mtt.email.preperation.TemplateEmailModel;

public interface EmailCreator {

    void createEmail(TemplateEmailModel emailModel, EmailUrlScheme urlScheme);
}
