package com.adeo.syn.beans.clients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

import static com.adeo.syndication.qa.contenthub.JacksonObjectMapper.jackson;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RestAssuredConfig.config;

public abstract class AbstractClient {

    protected RequestSpecBuilder getRequestSpecBuilder(String baseUrl) {
        return new RequestSpecBuilder()
                .setConfig(config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(jackson())))
                .addFilter(new AllureRestAssured())
                .addFilter(new ErrorLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .setContentType(ContentType.JSON)
                .setUrlEncodingEnabled(false)
                .setBaseUri(baseUrl);
    }

    protected RequestSpecBuilder getRequestSpecBuilder(String baseUrl, String user, String password) {
        return getRequestSpecBuilder(baseUrl).setAuth(getBasicAuthScheme(user, password));
    }

    private PreemptiveBasicAuthScheme getBasicAuthScheme(String user, String password) {
        var basicAuthScheme = new PreemptiveBasicAuthScheme();
        basicAuthScheme.setUserName(user);
        basicAuthScheme.setPassword(password);
        return basicAuthScheme;
    }
}
