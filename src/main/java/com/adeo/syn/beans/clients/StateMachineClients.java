package com.adeo.syn.beans.clients;

import com.adeo.syn.configs.CredentialsConfig;
import com.adeo.syn.configs.EnvConfig;
import com.adeo.syndication.qa.statemachine.ApiClient;
import com.adeo.syndication.qa.statemachine.api.ControllerForAdministrationApi;
import com.adeo.syndication.qa.statemachine.api.ControllerForSupplierProductStatesManagementApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StateMachineClients extends AbstractClient {
    @Autowired
    private EnvConfig envConfig;

    @Autowired
    private CredentialsConfig credentialsConfig;

    @Bean
    public ControllerForSupplierProductStatesManagementApi getControllerForSupplierProductStatesManagementApi() {
        return ApiClient.api(ApiClient.Config.apiConfig().reqSpecSupplier(
                        () -> getRequestSpecBuilder(
                                envConfig.stateMachineUrl(),
                                credentialsConfig.stateMachineDpoUserName(),
                                credentialsConfig.stateMachineDpoPassword())))
                .controllerForSupplierProductStatesManagement();
    }

    @Bean
    public ControllerForAdministrationApi getControllerForAdministrationApi() {
        return ApiClient.api(ApiClient.Config.apiConfig().reqSpecSupplier(
                        () -> getRequestSpecBuilder(
                                envConfig.stateMachineUrl(),
                                credentialsConfig.stateMachineAdminPassword(),
                                credentialsConfig.stateMachineAdminPassword())))
                .controllerForAdministration();
    }
}
