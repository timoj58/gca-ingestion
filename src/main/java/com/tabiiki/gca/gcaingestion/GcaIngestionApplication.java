package com.tabiiki.gca.gcaingestion;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
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

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .withRegion(Regions.EU_WEST_2).build();
    }

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .region(Region.EU_WEST_2)
                .build();

    }

}
