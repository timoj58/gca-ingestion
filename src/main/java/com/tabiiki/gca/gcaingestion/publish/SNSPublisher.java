package com.tabiiki.gca.gcaingestion.publish;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabiiki.gca.gcaingestion.message.IngestionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Slf4j
@Component
public class SNSPublisher {

    private final SnsClient snsClient;
    private final String gcaIngestionTopic;

    @Autowired
    public SNSPublisher(
            @Value("${sns.ingestion-topic}") String gcaIngestionTopic,
            SnsClient snsClient
    ) {
        this.gcaIngestionTopic = gcaIngestionTopic;
        this.snsClient = snsClient;
    }

    public void publish(IngestionMessage ingestionMessage) {
        try {
            String message = new ObjectMapper().writeValueAsString(ingestionMessage);
            log.info("publishing to topic:  {}", message);
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .topicArn(gcaIngestionTopic)
                    .build();

            PublishResponse result = snsClient.publish(request);
            log.info("sns status code {}", result.sdkHttpResponse().statusCode());
        } catch (JsonProcessingException e) {
            log.error("json processing error", e);
        }
    }
}
