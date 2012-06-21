package com.mtt.fixtures;


import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Fixture for putting database into a pre-defined state prior to running tests.
 */
public final class DbUnitFixture implements Fixture {

    private Resource dataSetResource;

    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @param dataSetResource dataSetResource the dataset
     * @param jdbcTemplate  Teplate
     */
    public DbUnitFixture(Resource dataSetResource, JdbcTemplate jdbcTemplate) {
        this.dataSetResource = dataSetResource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run() {
        try {
            IDataSet dataSet = new FlatXmlDataSet(dataSetResource.getInputStream());
            disableReferentialIntegrity();
            IDatabaseConnection dbConn = new DatabaseDataSourceConnection(jdbcTemplate.getDataSource());
            DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
            enableReferentialIntegrity();
        } catch (Exception ex) {
            throw new FixtureException(ex);
        }
    }

    @Override
    public void cleanUp() {
        try {
            IDataSet dataSet = new FlatXmlDataSet(dataSetResource.getInputStream());
            disableReferentialIntegrity();
            IDatabaseConnection dbConn = new DatabaseDataSourceConnection(jdbcTemplate.getDataSource());
            DatabaseOperation.TRUNCATE_TABLE.execute(dbConn, dataSet);
            enableReferentialIntegrity();
        } catch (Exception ex) {
            throw new FixtureException(ex);
        }
    }

    private void disableReferentialIntegrity() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
    }

    private void enableReferentialIntegrity() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }
}
