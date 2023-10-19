package com.adeo.syn.beans;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Utils {
    @Bean
    public Faker getFaker() {
        return new Faker();
    }
}
