package com.mtt.email;

import com.mtt.email.preperation.EmailUrlScheme;
import com.mtt.email.preperation.TemplateEmailModel;
import com.mtt.test.BaseIntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.internet.MimeMessage;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 */
public class DefaultEmailSenderIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private EmailSender emailSender;

    private Wiser wiser;

    @Before
    public void init() {
        wiser = new Wiser();
        wiser.setPort(2500);
        wiser.start();
    }

    @After
    public void teardown() throws Exception {
        wiser.stop();
        wiser = null;
    }

    @Test
    public void testSendsEmail() throws Exception {

        ReflectionTestUtils.setField(emailSender, "synchronous", true);

        emailSender.send(new TestEmailCreator());

        List<WiserMessage> messages = wiser.getMessages();
        assertThat(messages.size(), equalTo(1));

        MimeMessage message = messages.get(0).getMimeMessage();
        assertThat(message.getSubject(), equalTo("A Test Message"));
        assertThat(message.getAllRecipients()[0].toString(), equalTo("joe.bloggs@gumtree.com"));
        assertThat(message.getFrom()[0].toString(), equalTo("noreply@gumtree.com"));
        assertThat(message.getContent().toString(), containsString("Hello Joe!"));
    }

    public class TestEmailCreator implements EmailCreator {
        @Override
        public void createEmail(TemplateEmailModel model, EmailUrlScheme urlScheme) {
            model.setToAddress("joe.bloggs@gumtree.com");
            model.setSubject("A Test Message");
            model.setFromAddress("noreply@gumtree.com");
            model.addObject("name", "Joe");
            model.setPlainTextTemplate("test-email-template");
        }
    }
}
