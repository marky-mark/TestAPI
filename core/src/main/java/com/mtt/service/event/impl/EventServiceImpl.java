package com.mtt.service.event.impl;

import com.mtt.service.event.EventService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Implementation of event service
 */
@Service
public final class EventServiceImpl implements EventService, ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void publishAfterTransactionCommitted(ApplicationEvent event) {
        TransactionSynchronizationManager.registerSynchronization(new EventTransactionSynchronization(event));
    }

    @Override
    public void publish(ApplicationEvent event) {
        eventPublisher.publishEvent(event);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Class used to execute event publishing only after successful transaction commit.
     */
    private final class EventTransactionSynchronization extends TransactionSynchronizationAdapter {

        private ApplicationEvent applicationEvent;

        private EventTransactionSynchronization(ApplicationEvent applicationEvent) {
            this.applicationEvent = applicationEvent;
        }

        @Override
        public void afterCommit() {
            eventPublisher.publishEvent(applicationEvent);
        }
    }
}
