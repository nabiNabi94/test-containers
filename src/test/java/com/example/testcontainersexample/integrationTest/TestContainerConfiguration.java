package com.example.testcontainersexample.integrationTest;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.stream.Stream;

@Testcontainers
@ContextConfiguration(initializers = TestContainerConfiguration.TestcontainersInitializers.class)
public  class TestContainerConfiguration {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:12")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");

    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.0.0"));


    public static class TestcontainersInitializers implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.jdbc=" + postgres.getJdbcUrl(),
                    "spring.datasource.username" + postgres.getUsername(),
                    "spring.datasource.password" + postgres.getPassword(),
                    "spring.kafka.bootstrap-servers=" + kafka.getBootstrapServers()
            );
        }

        @BeforeAll

        public static void before() {

            Startables.deepStart(Stream.of(kafka, postgres));
        }
        @AfterAll
        public static void after(){
            kafka.stop();
            postgres.stop();
        }
    }

}
