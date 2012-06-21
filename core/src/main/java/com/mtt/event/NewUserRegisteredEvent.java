package com.mtt.event;

import com.mtt.domain.entity.UserActivationKey;
import org.springframework.context.ApplicationEvent;

public class NewUserRegisteredEvent extends ApplicationEvent{

    private UserActivationKey userActivationKey;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never <code>null</code>)
     */
    public NewUserRegisteredEvent(Object source, UserActivationKey userActivationKey) {
        super(source);
        this.userActivationKey = userActivationKey;
    }

    public UserActivationKey getUserActivationKey() {
        return userActivationKey;
    }
}
