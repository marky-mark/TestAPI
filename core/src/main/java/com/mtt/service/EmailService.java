package com.mtt.service;

import com.mtt.email.preperation.EmailPreparator;

/**
 * Service for sending emails.
 */
public interface EmailService {

    /**
     * Send an email using the given {@link com.mtt.email.preperation.EmailPreparator}
     *
     * @param emailPreparator the source for creating the email
     */
    void sendEmail(EmailPreparator emailPreparator);
}
