package com.ledger.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Autowired
    private Environment environment;

    @Bean
    DataSource dataSource() { // Get database configuration
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty("database.url"));
        driverManagerDataSource.setUsername(environment.getProperty("database.user"));
        driverManagerDataSource.setPassword(environment.getProperty("database.password"));
        driverManagerDataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("database.driver")));
        return driverManagerDataSource;
    }
}
