package com.adeo.syn.configs;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:${STAGE}/env.properties"})
public interface EnvConfig extends Config {

    @Key("STAGE")
    String currentEnv();

    @Key("staging.area.crud.url")
    String stagingAreaCrudUrl();

    @Key("mongo.database.host")
    String mongoHost();

    @Key("mapping.crud.url")
    String mappingCrudUrl();

    @Key("mapping.crud.jdbc")
    String mappingCrudJdbcUrl();

    @Key("content.hub.url")
    String contentHubUrl();

    @Key("content.hub.jdbc")
    String contentHubJdbcUrl();

    @Key("state.machine.url")
    String stateMachineUrl();

    @Key("state.machine.jdbc")
    String stateMachineJdbcUrl();

    @Key("worker.admin.tool.url")
    String workerAdminToolUrl();

    @Key("template.generator.url")
    String templateGeneratorUrl();

    @Key("staging.area.database")
    String stagingAreaDatabase();

    @Key("worker.admin.tool.database")
    String workerAdminToolDatabase();

    @Key("search.api.url")
    String searchApiUrl();

    @Key("matching.url")
    String matchingUrl();

    @Key("matching.jdbc")
    String matchingJdbcUrl();

    @Key("merging.url")
    String mergingUrl();

    @Key("merging.jdbc")
    String mergingJdbcUrl();

    @Key("worker.sequoya.url")
    String workerSequoyaUrl();

    @Key("worker.sequoya.jdbc")
    String workerSequoyaJdbcUrl();
}
