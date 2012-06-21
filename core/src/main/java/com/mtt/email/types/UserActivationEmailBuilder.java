package com.mtt.email.types;

import com.mtt.domain.entity.User;
import com.mtt.domain.entity.UserActivationKey;
import com.mtt.email.preperation.EmailConstants;
import com.mtt.email.preperation.EmailTemplate;
import com.mtt.email.preperation.EmailUrlScheme;
import com.mtt.email.preperation.TemplateEmailModel;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Builder for user activation email
 */
public final class UserActivationEmailBuilder {

    private static final String SUBJECT = "Welcome to MTT test";

    public static final String ACTIVATION_LINK_MODEL_KEY = "activation_link";

    public static final String NAME_MODEL_KEY = "name";

    private UserActivationKey activationKey;

    private EmailUrlScheme urlScheme;

    /**
     * Constructor.
     *
     * @param urlScheme the url scheme for generating email links
     */
    public UserActivationEmailBuilder(EmailUrlScheme urlScheme) {
        Assert.notNull(urlScheme);
        this.urlScheme = urlScheme;
    }

    /**
     * Populate the {@link com.mtt.email.preperation.TemplateEmailModel} with content for the user activation email.
     *
     * @param model the model to populate
     */
    public void build(TemplateEmailModel model) {
        model.setFromAddress(EmailConstants.EMAIL_FROM);
        model.setSubject(SUBJECT);
        model.setHtmlTemplate(EmailTemplate.USER_ACTIVATION_HTML.getTemplateName());
        model.setPlainTextTemplate(EmailTemplate.USER_ACTIVATION_PLAIN_TEXT.getTemplateName());

        if (activationKey != null && activationKey.getUser() != null) {
            User user = activationKey.getUser();
            model.setToAddress(user.getUsername());
            model.addObject(NAME_MODEL_KEY, user.getFirstName());
            model.addObject(
                    ACTIVATION_LINK_MODEL_KEY,
                    urlScheme.createUserActivationUrl(
                            urlEncode(user.getUsername()),
                            urlEncode(activationKey.getKey())
                    )
            );
        }
    }

    /**
     * Set the activation key
     *
     * @param activationKey the activation key
     * @return this
     */
    public UserActivationEmailBuilder activationKey(UserActivationKey activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    private String urlEncode(String activationKey) {
        try {
            return URLEncoder.encode(activationKey, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return activationKey;
        }
    }
}
