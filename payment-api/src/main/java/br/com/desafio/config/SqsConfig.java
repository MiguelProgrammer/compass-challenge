package br.com.desafio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class SqsConfig {

    @Bean
    public SqsAsyncClient sqsAsyncClient() {

        String url = "https://localhost.localstack.cloud:4566";

        return SqsAsyncClient.builder()
                .endpointOverride(URI.create(url))
                .region(Region.SA_EAST_1)
                .build();
    }

}
