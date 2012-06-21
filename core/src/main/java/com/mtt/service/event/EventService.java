package com.mtt.service.event;

import org.springframework.context.ApplicationEvent;

/**
 * Service for raising events.
 */
public interface EventService {

    /**
     * Publish event but delay until after successful completion of the transaction tied to the
     * current thread.
     *
     * @param event the event to publish
     */
    void publishAfterTransactionCommitted(ApplicationEvent event);

    /**
     * Publish event.
     *
     * @param event the event to publish
     */
    void publish(ApplicationEvent event);
}
