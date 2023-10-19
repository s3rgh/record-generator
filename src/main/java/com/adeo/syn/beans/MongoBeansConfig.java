package com.adeo.syn.beans;

import com.adeo.syn.configs.DatabaseCredentialsConfig;
import com.adeo.syn.configs.EnvConfig;
import com.adeo.syn.utils.FileUtils;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import de.dentrassi.crypto.pem.PemKeyStoreProvider;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

@Component
public class MongoBeansConfig {

    @Autowired
    private EnvConfig envConfig;

    @Autowired
    private DatabaseCredentialsConfig databaseCredentials;

    @SneakyThrows
    @Lazy
    @Bean(name = "stagingAreaMongo")
    public MongoTemplate getStagingAreaMongo() {
        var connectionString = "mongodb://"
                + databaseCredentials.stagingAreaUser() + ":"
                + databaseCredentials.stagingAreaPassword()
                + "@" + envConfig.mongoHost() + ":11100/"
                + envConfig.stagingAreaDatabase();

        var sslContext = createSSLContext();

        var settings = getMongoClientSettings(connectionString, sslContext);

        var client = MongoClients.create(settings);

        return new MongoTemplate(client, envConfig.stagingAreaDatabase());
    }

    @Lazy
    @Bean(name = "workerAdminToolMongo")
    public MongoTemplate getWorkerAdminToolMongo() {
        var connectionString = "mongodb://"
                + databaseCredentials.workerAdminToolUser() + ":"
                + databaseCredentials.workerAdminToolPassword()
                + "@" + envConfig.mongoHost() + ":11100/"
                + envConfig.workerAdminToolDatabase();

        var sslContext = createSSLContext();

        var settings = getMongoClientSettings(connectionString, sslContext);

        var client = MongoClients.create(settings);

        return new MongoTemplate(client, envConfig.workerAdminToolDatabase());
    }

    @NotNull
    private MongoClientSettings getMongoClientSettings(String connectionString, SSLContext sslContext) {
        return MongoClientSettings.builder()
                .applyToSslSettings(builder -> {
                    builder.enabled(true);
                    builder.context(sslContext);
                })
                .applyConnectionString(
                        new ConnectionString(connectionString))
                .build();
    }

    @SneakyThrows
    private SSLContext createSSLContext() {
        char[] passPhrase = "changeit".toCharArray();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream(FileUtils.getFileFromResources("ssl/client-all.p12")), passPhrase);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passPhrase);

        KeyStore keyStore = KeyStore.getInstance("PEM", new PemKeyStoreProvider());
        keyStore.load(new FileInputStream(FileUtils.getFileFromResources("ssl/lmru-ca.pem")), passPhrase);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(keyStore);

        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context;
    }
}
