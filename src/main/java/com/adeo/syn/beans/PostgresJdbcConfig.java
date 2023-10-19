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

//    @Bean
//    @Qualifier("contentHubDataSource")
//    public DataSource contentHubDataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName("org.postgresql.Driver")
//                .url(envConfig.contentHubJdbcUrl())
//                .username(databaseCredentials.contentHubUser())
//                .password(databaseCredentials.contentHubPassword())
//                .build();
//    }

//    @Bean
//    @Qualifier("stateMachineDataSource")
//    public DataSource stateMachineDataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName("org.postgresql.Driver")
//                .url(envConfig.stateMachineJdbcUrl())
//                .username(databaseCredentials.stateMachineUser())
//                .password(databaseCredentials.stateMachinePassword())
//                .build();
//    }

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

//    @Bean
//    @Qualifier("matchingDataSource")
//    public DataSource matchingDataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName("org.postgresql.Driver")
//                .url(envConfig.matchingJdbcUrl())
//                .username(databaseCredentials.matchingName())
//                .password(databaseCredentials.matchingPassword())
//                .build();
//    }

//    @Bean
//    @Qualifier("mergingDataSource")
//    public DataSource mergingDataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName("org.postgresql.Driver")
//                .url(envConfig.mergingJdbcUrl())
//                .username(databaseCredentials.mergingName())
//                .password(databaseCredentials.mergingPassword())
//                .build();
//    }

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

//    @Bean
//    @Qualifier("contentHubJdbc")
//    NamedParameterJdbcTemplate contentHubJdbc(@Qualifier("contentHubDataSource") DataSource contentHubDataSource) {
//        return new NamedParameterJdbcTemplate(contentHubDataSource);
//    }
//
    @Bean
    @Qualifier("mappingCrudJdbc")
    NamedParameterJdbcTemplate mappingCrudJdbc(@Qualifier("mappingCrudDataSource") DataSource mappingCrudDataSource) {
        return new NamedParameterJdbcTemplate(mappingCrudDataSource);
    }
//
//    @Bean
//    @Qualifier("stateMachineJdbc")
//    NamedParameterJdbcTemplate stateMachineJdbc(@Qualifier("stateMachineDataSource") DataSource stateMachineDataSource) {
//        return new NamedParameterJdbcTemplate(stateMachineDataSource);
//    }
//
//    @Bean
//    @Qualifier("matchingJdbc")
//    NamedParameterJdbcTemplate matchingJdbc(@Qualifier("matchingDataSource") DataSource matchingDataSource) {
//        return new NamedParameterJdbcTemplate(matchingDataSource);
//    }
//
//    @Bean
//    @Qualifier("mergingJdbc")
//    NamedParameterJdbcTemplate mergingJdbc(@Qualifier("mergingDataSource") DataSource mergingDataSource) {
//        return new NamedParameterJdbcTemplate(mergingDataSource);
//    }
//
    @Bean
    @Qualifier
    NamedParameterJdbcTemplate workerSequoyaJdbc(@Qualifier("workerSequoyaDataSource") DataSource workerSequoyaDataSource) {
        return new NamedParameterJdbcTemplate(workerSequoyaDataSource);
    }
}
