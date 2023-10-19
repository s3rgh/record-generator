package com.adeo.syn.beans;

import com.adeo.syn.configs.DatabaseCredentialsConfig;
import com.adeo.syn.configs.EnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class PostgresJdbcConfig {

    @Autowired
    private EnvConfig envConfig;

    @Autowired
    private DatabaseCredentialsConfig databaseCredentials;

    @Bean
    @Qualifier("mappingCrudDataSource")
    public DataSource mappingCrudDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(envConfig.mappingCrudJdbcUrl())
                .username(databaseCredentials.mappingCrudUser())
                .password(databaseCredentials.mappingCrudPassword())
                .build();
    }

    @Bean
    @Qualifier("workerSequoyaDataSource")
    public DataSource workerSequoyaDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(envConfig.workerSequoyaJdbcUrl())
                .username(databaseCredentials.workerSequoyaName())
                .password(databaseCredentials.workerSequoyaPassword())
                .build();
    }

    @Bean
    @Qualifier("mappingCrudJdbc")
    NamedParameterJdbcTemplate mappingCrudJdbc(@Qualifier("mappingCrudDataSource") DataSource mappingCrudDataSource) {
        return new NamedParameterJdbcTemplate(mappingCrudDataSource);
    }

    @Bean
    @Qualifier
    NamedParameterJdbcTemplate workerSequoyaJdbc(@Qualifier("workerSequoyaDataSource") DataSource workerSequoyaDataSource) {
        return new NamedParameterJdbcTemplate(workerSequoyaDataSource);
    }
}
