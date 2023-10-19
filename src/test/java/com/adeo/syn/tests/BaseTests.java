package com.adeo.syn.tests;

import com.adeo.syn.beans.Configs;
import com.adeo.syn.beans.Utils;
import com.adeo.syn.beans.clients.StateMachineClients;
import com.adeo.syn.mappingcrud.repository.MappingCrudRepository;
import com.adeo.syn.workersequoya.repository.WorkerSequoyaRepository;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.MINUTES;

@SpringBootTest(classes = {PartnerRecordFactoryTests.class,
        StateMachineClients.class,
        Configs.class,
        Utils.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTests {

    @BeforeAll
    void setUp() {
        Awaitility.setDefaultTimeout(Duration.of(2, MINUTES));
        Awaitility.setDefaultPollInterval(3, TimeUnit.SECONDS);
        Awaitility.ignoreExceptionsByDefault();
    }

    @AfterAll
    static void clearGeneratedData() {

    }
}
