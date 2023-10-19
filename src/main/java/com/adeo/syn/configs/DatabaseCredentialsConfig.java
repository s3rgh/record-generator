package com.adeo.syn.configs;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:${STAGE}/credentials.properties" // for local run
})
public interface DatabaseCredentialsConfig extends Config {

    @Key("CONTENT_HUB_DB_USER")
    String contentHubUser();

    @Key("CONTENT_HUB_DB_PASSWORD")
    String contentHubPassword();

    @Key("STATE_MACHINE_DB_USER")
    String stateMachineUser();

    @Key("STATE_MACHINE_DB_PASSWORD")
    String stateMachinePassword();

    @Key("MAPPING_CRUD_DB_USER")
    String mappingCrudUser();

    @Key("MAPPING_CRUD_DB_PASSWORD")
    String mappingCrudPassword();

    @Key("STAGING_AREA_DB_USER")
    String stagingAreaUser();

    @Key("STAGING_AREA_DB_PASSWORD")
    String stagingAreaPassword();

    @Key("WORKER_ADMIN_TOOL_DB_USER")
    String workerAdminToolUser();

    @Key("WORKER_ADMIN_TOOL_DB_PASSWORD")
    String workerAdminToolPassword();

    @Key("WORKER_SEQUOYA_DB_NAME")
    String workerSequoyaName();

    @Key("WORKER_SEQUOYA_DB_PASSWORD")
    String workerSequoyaPassword();
}
