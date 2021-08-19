package com.tabiiki.gca.gcaingestion.component;

import com.amazonaws.services.s3.model.S3Object;
import com.tabiiki.gca.gcaingestion.facade.S3Facade;
import com.tabiiki.gca.gcaingestion.listener.SQSLongPollingListener;
import com.tabiiki.gca.gcaingestion.facade.SQSMessageClientFacade;
import com.tabiiki.gca.gcaingestion.facade.SNSFacade;
import com.tabiiki.gca.gcaingestion.service.IngestionService;
import com.tabiiki.gca.gcaingestion.service.impl.IngestionServiceImpl;
import com.tabiiki.gca.gcaingestion.util.SqsTestMessageBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.http.SdkHttpResponse;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

public class GcaIngestionComponentTest {

    private final S3Facade is3Facade = mock(S3Facade.class);
    private final SnsClient snsClient = mock(SnsClient.class);
    private final SQSMessageClientFacade sqsMessageClientFacade = mock(SQSMessageClientFacade.class);

    private final SNSFacade snsFacade = new SNSFacade("fake", snsClient);

    private final IngestionService ingestionService = new IngestionServiceImpl(is3Facade, snsFacade);
    private final SQSLongPollingListener sqsLongPollingListener = new SQSLongPollingListener(sqsMessageClientFacade, ingestionService);


    @BeforeEach
    public void init(){

        PublishResponse publishResponse = mock(PublishResponse.class);
        SdkHttpResponse sdkHttpResponse = mock(SdkHttpResponse.class);

        when(snsClient.publish(any(PublishRequest.class))).thenReturn(publishResponse);
        when(publishResponse.sdkHttpResponse()).thenReturn(sdkHttpResponse);
        when(sdkHttpResponse.statusCode()).thenReturn(200);
    }

    @Test
    public void smokeTest() throws InterruptedException {

        CompletableFuture<ReceiveMessageResponse> message = CompletableFuture.supplyAsync(
                () -> ReceiveMessageResponse.builder().messages(
                        SqsTestMessageBuilder.testMessage(UUID.randomUUID())
                ).build());

        when(is3Facade.get(anyString())).thenReturn(new S3Object());
        when(sqsMessageClientFacade.receive()).thenReturn(message);
        CompletableFuture.runAsync(sqsLongPollingListener::init);

        Thread.sleep(1000); //due to subscription / netty async

        sqsLongPollingListener.shutdown();

        verify(is3Facade, atLeastOnce()).put(anyString(), anyString());
        verify(snsClient, atLeastOnce()).publish(any(PublishRequest.class));
        verify(sqsMessageClientFacade, atLeastOnce()).delete(any(Message.class));
    }

}
