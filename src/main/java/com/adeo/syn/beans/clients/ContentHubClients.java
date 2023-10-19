package com.adeo.syn.beans.clients;

import com.adeo.syn.configs.CredentialsConfig;
import com.adeo.syn.configs.EnvConfig;
import com.adeo.syndication.qa.contenthub.ApiClient;
import com.adeo.syndication.qa.contenthub.api.ExperienceApiApi;
import com.adeo.syndication.qa.contenthub.api.GoldenRecordsControllerApi;
import com.adeo.syndication.qa.contenthub.api.ModelsControllerApi;
import com.adeo.syndication.qa.contenthub.api.PartnerRecordsControllerApi;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContentHubClients extends AbstractClient {
    @Autowired
    private EnvConfig envConfig;

    @Autowired
    private CredentialsConfig credentialsConfig;

    @Bean
    public PartnerRecordsControllerApi getPartnerRecordsControllerApi() {
        return ApiClient.api(ApiClient.Config.apiConfig()
                        .reqSpecSupplier(() -> getRequestSpecBuilder(
                                envConfig.contentHubUrl(),
                                credentialsConfig.contentHubEditorUserName(),
                                credentialsConfig.contentHubEditorPassword())))
                .partnerRecordsController();
    }

    @Bean
    public GoldenRecordsControllerApi getGoldenRecordsControllerApi() {
        return ApiClient.api(ApiClient.Config.apiConfig()
                        .reqSpecSupplier(() -> getRequestSpecBuilder(
                                envConfig.contentHubUrl(),
                                credentialsConfig.contentHubEditorUserName(),
                                credentialsConfig.contentHubEditorPassword())
                                .setContentType(ContentType.JSON)))
                .goldenRecordsController();
    }

    @Bean
    public ModelsControllerApi getModelsControllerApi() {
        return ApiClient.api(ApiClient.Config.apiConfig()
                        .reqSpecSupplier(() -> getRequestSpecBuilder(
                                envConfig.contentHubUrl(),
                                credentialsConfig.contentHubEditorUserName(),
                                credentialsConfig.contentHubEditorPassword())))
                .modelsController();
    }

    @Bean
    public ExperienceApiApi getExperienceApiApi() {
        return ApiClient.api(ApiClient.Config.apiConfig()
                .reqSpecSupplier(() -> getRequestSpecBuilder(
                        envConfig.contentHubUrl(),
                        credentialsConfig.contentHubEditorUserName(),
                        credentialsConfig.contentHubEditorPassword())
                        .setContentType(ContentType.JSON)))
                .experienceApi();
    }
}
