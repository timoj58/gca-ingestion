package com.tabiiki.gca.gcaingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;


@SpringBootApplication
@EnableWebFlux
public class GcaIngestionApplication {

    public static void main(String[] args) {
        SpringApplication.run(GcaIngestionApplication.class, args);
    }

    @Bean
    public SqsAsyncClient amazonSQSAsyncClient() {
        return SqsAsyncClient.builder()
                .region(Region.EU_WEST_2)
                .build();
    }

}
