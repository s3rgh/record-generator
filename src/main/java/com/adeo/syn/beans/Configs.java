package com.adeo.syn.beans;

import com.adeo.syn.configs.CredentialsConfig;
import com.adeo.syn.configs.DatabaseCredentialsConfig;
import com.adeo.syn.configs.EnvConfig;
import org.aeonbits.owner.ConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.adeo.syn")
@Configuration
public class Configs {

    @Bean
    public EnvConfig getEnvConfig() {
        return ConfigFactory.create(EnvConfig.class);
    }

    @Bean
    public CredentialsConfig getCredentialsConfig() {
        return ConfigFactory.create(CredentialsConfig.class);
    }

    @Bean
    public DatabaseCredentialsConfig getDatabaseCredentialsConfig() {
        return ConfigFactory.create(DatabaseCredentialsConfig.class);
    }
}
