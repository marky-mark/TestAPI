package com.mtt.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Base class for integration tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/mtt/test-application-context.xml")
@TestExecutionListeners(
        {
                DependencyInjectionTestExecutionListener.class,
                CleanInsertTestExecutionListener.class,
                TransactionalTestExecutionListener.class
        }
)
public abstract class BaseIntegrationTest /* extends BaseShiroTest*/ {
}