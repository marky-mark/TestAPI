package com.mtt.email;


public interface EmailSender {

    /**
     * Submit a new email task for dispatch. Defaults to async dispatch.
     *
     * @param emailCreator the email creator
     */
    void send(EmailCreator emailCreator);

}
