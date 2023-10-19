package com.adeo.syn.configs;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:${STAGE}/credentials.properties" // for local run
})
public interface DatabaseCredentialsConfig extends Config {

    @Key("MAPPING_CRUD_DB_USER")
    String mappingCrudUser();

    @Key("MAPPING_CRUD_DB_PASSWORD")
    String mappingCrudPassword();

    @Key("WORKER_SEQUOYA_DB_NAME")
    String workerSequoyaName();

    @Key("WORKER_SEQUOYA_DB_PASSWORD")
    String workerSequoyaPassword();
}
