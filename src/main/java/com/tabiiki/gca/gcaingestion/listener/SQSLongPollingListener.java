package com.tabiiki.gca.gcaingestion.listener;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.tabiiki.gca.gcaingestion.facade.SQSMessageClientFacade;
import com.tabiiki.gca.gcaingestion.service.IngestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicBoolean;


@Slf4j
@Component
public class SQSLongPollingListener {

    private final SQSMessageClientFacade sqsMessageClientFacade;
    private final IngestionService ingestionService;
    private final AtomicBoolean repeater = new AtomicBoolean(true);

    @Autowired
    public SQSLongPollingListener(
            SQSMessageClientFacade sqsMessageClientFacade,
            IngestionService ingestionService
    ) {
        this.sqsMessageClientFacade = sqsMessageClientFacade;
        this.ingestionService = ingestionService;
    }

    @PostConstruct
    public void init() {

        log.info("starting listener...");

        //do not listen to intellij hints, we need the supplier version as it wraps the response until subscription
        Mono.fromFuture(() -> sqsMessageClientFacade.receive())
                .repeat(repeater::get)
                .retry()
                .map(ReceiveMessageResponse::messages)
                .map(Flux::fromIterable)
                .flatMap(messageFlux -> messageFlux)
                .subscribe(message ->
                        Mono.just(
                                S3EventNotification.parseJson(message.body())
                        ).doOnNext(ingestionService::ingest)
                                .doFinally(delete -> sqsMessageClientFacade.delete(message))
                                .subscribe()
                );
    }


    @PreDestroy
    public void shutdown() {
        log.info("shutdown hook...");
        sqsMessageClientFacade.close();
        repeater.set(false);
    }
}

