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

    @Key("mapping.crud.jdbc")
    String mappingCrudJdbcUrl();

    @Key("content.hub.url")
    String contentHubUrl();

    @Key("state.machine.url")
    String stateMachineUrl();

    @Key("worker.sequoya.jdbc")
    String workerSequoyaJdbcUrl();
}
