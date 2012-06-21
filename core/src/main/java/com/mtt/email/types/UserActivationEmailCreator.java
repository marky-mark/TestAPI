package com.mtt.email.types;


import com.mtt.domain.entity.UserActivationKey;
import com.mtt.email.EmailCreator;
import com.mtt.email.preperation.EmailUrlScheme;
import com.mtt.email.preperation.TemplateEmailModel;

/**
 * Lazy creator for user activation email.
 */
public final class UserActivationEmailCreator implements EmailCreator {

    private UserActivationKey activationKey;

    /**
     * Constructor.
     *
     * @param activationKey the activationKey
     */
    public UserActivationEmailCreator(UserActivationKey activationKey) {
        this.activationKey = activationKey;
    }

    @Override
    public void createEmail(TemplateEmailModel emailModel, EmailUrlScheme urlScheme) {
        UserActivationEmailBuilder emailBuilder = new UserActivationEmailBuilder(urlScheme);
        emailBuilder.activationKey(activationKey).build(emailModel);
    }

    public UserActivationKey getActivationKey() {
        return activationKey;
    }
}
