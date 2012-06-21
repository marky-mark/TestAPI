package com.mtt.test;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * A {@link org.springframework.test.context.TestExecutionListener} that loads db dataset via DBUnit.
 */
public final class CleanInsertTestExecutionListener implements TestExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(CleanInsertTestExecutionListener.class);

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        // location of the data set
        String dataSetResourcePath = null;
        String dataSourceName = null;

        // first, the annotation on the test class
        DataSetLocation dsLocation = testContext.getTestInstance().getClass().getAnnotation(DataSetLocation.class);
        if (dsLocation != null) {
            // found the annotation
            dataSetResourcePath = dsLocation.value();
            dataSourceName = StringUtils.hasLength(dsLocation.dataSourceName()) ? dsLocation.dataSourceName() : null;
            LOG.info("annotated test, using data set: {}", dataSetResourcePath);
        } else {
            // no annotation, let's try with the name of the test
            String tempDsRes = testContext.getTestInstance().getClass().getName();
            tempDsRes = StringUtils.replace(tempDsRes, ".", "/");
            tempDsRes = "/" + tempDsRes + "-dataset.xml";
            if (getClass().getResourceAsStream(tempDsRes) != null) {
                LOG.info("detected default dataset: {}", tempDsRes);
                dataSetResourcePath = tempDsRes;
            } else {
                LOG.info("no default dataset");
            }
        }

        if (dataSetResourcePath != null) {
            Resource dataSetResource = testContext.getApplicationContext().getResource(
                    dataSetResourcePath
            );

            IDataSet dataSet = new FlatXmlDataSet(dataSetResource.getInputStream());
            DataSource dataSource;
            if (dataSourceName != null) {
                dataSource = (DataSource) testContext.getApplicationContext().getBean(dataSourceName);
            } else {
                dataSource = testContext.getApplicationContext().getBean(DataSource.class);
            }
            JdbcTemplate template = new JdbcTemplate(dataSource);
            template.execute("SET REFERENTIAL_INTEGRITY FALSE");
            IDatabaseConnection dbConn = new DatabaseDataSourceConnection(dataSource);
            DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
            template.execute("SET REFERENTIAL_INTEGRITY TRUE");
        } else {
            LOG.info("{} does not have any data set, no data injection", testContext.getClass().getName());
        }
    }

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {

    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {

    }
}