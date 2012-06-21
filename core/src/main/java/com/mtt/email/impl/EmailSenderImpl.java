package com.mtt.email.impl;

import com.mtt.email.EmailCreator;
import com.mtt.email.EmailSender;
import com.mtt.email.preperation.EmailUrlScheme;
import com.mtt.email.preperation.TemplateEmailModel;
import com.mtt.email.preperation.TemplateEmailModelFactory;
import com.mtt.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class EmailSenderImpl implements EmailSender {

    @Autowired
    @Qualifier("emailDispatchExecutor")
    private ExecutorService executorService;

    @Value("${mtt.emailSender.synchronous:false}")
    private boolean synchronous;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TemplateEmailModelFactory emailModelFactory;

    @Autowired
    private EmailUrlScheme emailUrlScheme;

    @Override
    public void send(EmailCreator emailCreator) {
        if (!synchronous) {
            executorService.submit(new SendEmailTask(emailCreator, emailService, emailUrlScheme, emailModelFactory));
        } else {
            new SendEmailTask(emailCreator, emailService, emailUrlScheme, emailModelFactory).run();
        }
    }

    /**
     * Task for creating and sending an email
     */
    public final class SendEmailTask implements Runnable {

        private EmailCreator creator;

        private EmailService emailService;

        private EmailUrlScheme emailUrlScheme;

        private TemplateEmailModelFactory emailModelFactory;

        /**
         * Constructor.
         *
         * @param creator           the email creator
         * @param emailService      the email service
         * @param emailUrlScheme    the email url scheme
         * @param emailModelFactory the email model factory
         */
        public SendEmailTask(
                EmailCreator creator,
                EmailService emailService,
                EmailUrlScheme emailUrlScheme,
                TemplateEmailModelFactory emailModelFactory) {
            this.creator = creator;
            this.emailService = emailService;
            this.emailUrlScheme = emailUrlScheme;
            this.emailModelFactory = emailModelFactory;
        }

        @Override
        public void run() {
            TemplateEmailModel emailModel = emailModelFactory.newInstance();
            creator.createEmail(emailModel, emailUrlScheme);
            emailService.sendEmail(emailModel);
        }
    }
}
