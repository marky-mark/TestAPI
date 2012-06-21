package com.mtt.event.listener;

import com.mtt.email.EmailSender;
import com.mtt.email.types.UserActivationEmailCreator;
import com.mtt.event.NewUserRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * This listener is responsible for handling post user registration logic. This includes
 * triggering the email activation loop.
 */
@Component
public final class UserActivationEmailListener implements ApplicationListener<NewUserRegisteredEvent> {

    @Autowired
    private EmailSender emailSender;

    @Override
    public void onApplicationEvent(NewUserRegisteredEvent newUserRegisteredEvent) {
        if (newUserRegisteredEvent.getUserActivationKey() != null) {
            emailSender.send(new UserActivationEmailCreator(newUserRegisteredEvent.getUserActivationKey()));
        }
    }
}
