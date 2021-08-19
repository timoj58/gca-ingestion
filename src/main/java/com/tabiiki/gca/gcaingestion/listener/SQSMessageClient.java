package com.tabiiki.gca.gcaingestion.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class SQSMessageClient {

    private final SqsAsyncClient sqsAsyncClient;
    private final String queueUrl;

    @Autowired
    public SQSMessageClient(
            @Value("${aws.sqs.gca-ingestion-url}") String queueUrl,
            SqsAsyncClient sqsAsyncClient
    ) {
        this.queueUrl = queueUrl;
        this.sqsAsyncClient = sqsAsyncClient;
    }

    public CompletableFuture<ReceiveMessageResponse> receive() {
        return sqsAsyncClient.receiveMessage(
                ReceiveMessageRequest.builder()
                        .maxNumberOfMessages(5)
                        .queueUrl(queueUrl)
                        .waitTimeSeconds(10)
                        .visibilityTimeout(30)
                        .build());
    }

    public void delete(Message message) {
        sqsAsyncClient.deleteMessage(
                DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(message.receiptHandle())
                        .build()
        ).thenAccept(deleteMessageResponse -> log.info("deleted message with handle " + message.receiptHandle()));
    }

    public void close() {
        sqsAsyncClient.close();
    }
}
