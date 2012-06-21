package com.mtt.email.preperation;

import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;

/**
 * For preparing an email to be sent.
 */
public interface EmailPreparator {

    /**
     * Populate the given {@link org.springframework.mail.javamail.MimeMessageHelper}.
     *
     * @param helper the helper to populate
     * @throws javax.mail.MessagingException exception in underlying mail library when error occurs
     */
    void prepareMessage(MimeMessageHelper helper) throws MessagingException;

    /**
     * @return whether this object will be prepare a multipart email or not.
     */
    boolean isMultipart();
}
