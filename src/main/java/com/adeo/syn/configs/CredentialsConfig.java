package com.adeo.syn.configs;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:${STAGE}/credentials.properties" // for local run
})
public interface CredentialsConfig extends Config {
    @Key("STAGING_AREA_ADMIN_NAME")
    String stagingAreaAdminName();

    @Key("STAGING_AREA_ADMIN_PASSWORD")
    String stagingAreaAdminPassword();

    @Key("CONTENT_HUB_EDITOR_NAME")
    String contentHubEditorUserName();

    @Key("CONTENT_HUB_EDITOR_PASSWORD")
    String contentHubEditorPassword();

    @Key("CONTENT_HUB_ADMIN_NAME")
    String contentHubAdminUserName();

    @Key("CONTENT_HUB_ADMIN_PASSWORD")
    String contentHubAdminPassword();

    @Key("CONTENT_HUB_READER_NAME")
    String contentHubReaderUserName();

    @Key("CONTENT_HUB_READER_PASSWORD")
    String contentHubReaderPassword();

    @Key("STATE_MACHINE_EDITOR_USER")
    String stateMachineEditorUserName();

    @Key("STATE_MACHINE_EDITOR_PASSWORD")
    String stateMachineEditorPassword();

    @Key("STATE_MACHINE_READER_USER")
    String stateMachineReaderUserName();

    @Key("STATE_MACHINE_READER_PASSWORD")
    String stateMachineReaderPassword();

    @Key("STATE_MACHINE_DPO_USER")
    String stateMachineDpoUserName();

    @Key("STATE_MACHINE_DPO_PASSWORD")
    String stateMachineDpoPassword();

    @Key("STATE_MACHINE_ADMIN_USER")
    String stateMachineAdminUserName();

    @Key("STATE_MACHINE_ADMIN_PASSWORD")
    String stateMachineAdminPassword();

    @Key("TEMPLATE_GENERATOR_ADMIN_NAME")
    String templateGeneratorAdminName();

    @Key("TEMPLATE_GENERATOR_ADMIN_PASSWORD")
    String templateGeneratorAdminPassword();

    @Key("KAFKA_SASL_JAAS_CONFIG")
    String kafkaJaasConfig();

    @Key("MAPPING_CRUD_ADMIN_NAME")
    String mappingCrudAdminName();

    @Key("MAPPING_CRUD_ADMIN_PASSWORD")
    String mappingCrudAdminPassword();

    @Key("MAPPING_CRUD_EDITOR_NAME")
    String mappingCrudEditorName();

    @Key("MAPPING_CRUD_EDITOR_PASSWORD")
    String mappingCrudEditorPassword();

    @Key("MAPPING_CRUD_READER_NAME")
    String mappingCrudReaderName();

    @Key("MAPPING_CRUD_READER_PASSWORD")
    String mappingCrudReaderPassword();

    @Key("SEARCH_API_SECURITY_ADMIN_NAME")
    String searchApiSecurityAdminName();

    @Key("SEARCH_API_SECURITY_ADMIN_PASSWORD")
    String searchApiSecurityAdminPassword();

    @Key("SEARCH_API_SECURITY_EDITOR_NAME")
    String searchApiSecurityEditorName();

    @Key("SEARCH_API_SECURITY_EDITOR_PASSWORD")
    String searchApiSecurityEditorPassword();

    @Key("MATCHING_SECURITY_DPO_NAME")
    String matchingDpoName();

    @Key("MATCHING_SECURITY_DPO_PASSWORD")
    String matchingDpoPassword();

    @Key("MATCHING_SECURITY_EDITOR_NAME")
    String matchingEditorName();

    @Key("MATCHING_SECURITY_EDITOR_PASSWORD")
    String matchingEditorPassword();

    @Key("MATCHING_SECURITY_READER_NAME")
    String matchingReaderName();

    @Key("MATCHING_SECURITY_READER_PASSWORD")
    String matchingReaderPassword();

    @Key("MERGING_SECURITY_ADMIN_NAME")
    String mergingSecurityAdminName();

    @Key("MERGING_SECURITY_ADMIN_PASSWORD")
    String mergingSecurityAdminPassword();

    @Key("MERGING_SECURITY_EDITOR_NAME")
    String mergingSecurityEditorName();

    @Key("MERGING_SECURITY_EDITOR_PASSWORD")
    String mergingSecurityEditorPassword();

    @Key("MERGING_SECURITY_READER_NAME")
    String mergingSecurityReaderName();

    @Key("MERGING_SECURITY_READER_PASSWORD")
    String mergingSecurityReaderPassword();
}
