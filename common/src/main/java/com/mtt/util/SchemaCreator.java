package com.mtt.util;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

/**
 * SchemaCreater For the H2 Database
 */
public class SchemaCreator implements InitializingBean, DisposableBean, BeanNameAware, ResourceLoaderAware {

    private String changeLogResource;

    private JdbcTemplate jdbcTemplate;

    private String beanName;

    private ResourceLoader resourceLoader;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setChangeLogResource(String changeLogResource) {
        this.changeLogResource = changeLogResource;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(changeLogResource);
        Assert.notNull(jdbcTemplate);
        rebuildDatabase();
    }

    @Override
    public void destroy() throws Exception {
        disableReferentialIntegrity();
        jdbcTemplate.execute("DROP ALL OBJECTS [DELETE FILES]");
        enableReferentialIntegrity();
    }

    /**
     *
     * @throws Exception when
     */
    public void rebuildDatabase() throws Exception {
        disableReferentialIntegrity();
        jdbcTemplate.execute("DROP ALL OBJECTS");
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setBeanName(beanName);
        springLiquibase.setResourceLoader(resourceLoader);
        springLiquibase.setChangeLog(changeLogResource);
        springLiquibase.setDataSource(jdbcTemplate.getDataSource());
        springLiquibase.afterPropertiesSet();
        enableReferentialIntegrity();
    }

    private void enableReferentialIntegrity() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    private void disableReferentialIntegrity() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
    }
}