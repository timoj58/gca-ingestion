package com.tabiiki.gca.gcaingestion.listener;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.tabiiki.gca.gcaingestion.service.IngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import javax.annotation.PostConstruct;


@RequiredArgsConstructor
@Slf4j
@Component
public class SQSListener {

    private final IngestionService ingestionService;
    private final SqsAsyncClient sqsAsyncClient;
    private final String queueUrl;

    @Autowired
    public SQSListener(
            @Value("${sqs.gca-ingestion-url}") String queueUrl,
            IngestionService ingestionService,
            SqsAsyncClient sqsAsyncClient
    ) {
        this.queueUrl = queueUrl;
        this.ingestionService = ingestionService;
        this.sqsAsyncClient = sqsAsyncClient;
    }

    @PostConstruct
    private void init() {
        Mono<ReceiveMessageResponse> receiveMessageResponse = Mono.fromFuture(() ->
                sqsAsyncClient.receiveMessage(
                        ReceiveMessageRequest.builder()
                                .maxNumberOfMessages(5)
                                .queueUrl(queueUrl)
                                .waitTimeSeconds(10)
                                .visibilityTimeout(30)
                                .build()
                )
        );

        //TODO graceful shut down as errors otherwise...needs latch on shut down.
        receiveMessageResponse
                .repeat()
                .retry()
                .map(ReceiveMessageResponse::messages)
                .map(Flux::fromIterable)
                .flatMap(messageFlux -> messageFlux)
                .subscribe(message ->
                    Mono.just(S3EventNotification.parseJson(message.body()))
                            .doOnNext(ingestionService::ingest)
                            //note always delete event as we handle errors via ingestion service
                            .doFinally(delete ->
                                    sqsAsyncClient.deleteMessage(DeleteMessageRequest.builder().queueUrl(queueUrl).receiptHandle(message.receiptHandle()).build())
                                            .thenAccept(deleteMessageResponse -> log.info("deleted message with handle " + message.receiptHandle())))
                            .subscribe()
                );
    }
}

