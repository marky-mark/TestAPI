package com.mtt.concurrent;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Factory for creating thread pool {@link java.util.concurrent.ExecutorService}
 */
public final class ThreadPoolFactoryBean extends AbstractFactoryBean<ExecutorService> {

    private int threadCount;

    /**
     * Constructor.
     *
     * @param threadCount the number of threads.
     */
    public ThreadPoolFactoryBean(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public Class<?> getObjectType() {
        return ExecutorService.class;
    }

    @Override
    protected ExecutorService createInstance() throws Exception {
        return Executors.newFixedThreadPool(threadCount);
    }

    @Override
    protected void destroyInstance(ExecutorService instance) throws Exception {
        instance.shutdown();
    }
}
