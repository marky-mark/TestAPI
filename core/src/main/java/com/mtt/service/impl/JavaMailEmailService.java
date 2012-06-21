package com.mtt.service.impl;

import com.mtt.email.preperation.EmailPreparator;
import com.mtt.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * Implementation {@link com.mtt.service.EmailService} that utilises Spring Mail helpers.
 */
@Service
public final class JavaMailEmailService implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaMailEmailService.class);

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendEmail(final EmailPreparator emailPreparator) {
        try {
            mailSender.send(new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    emailPreparator.prepareMessage(new MimeMessageHelper(mimeMessage, emailPreparator.isMultipart()));
                }
            });
            LOGGER.debug("mail sent :)");
        } catch (RuntimeException ex) {
            LOGGER.error("Failure to send mail " + ex.getCause());

        }
    }
}
