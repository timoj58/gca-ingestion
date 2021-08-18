package com.tabiiki.gca.gcaingestion.component;

import com.tabiiki.gca.gcaingestion.facade.IS3Facade;
import com.tabiiki.gca.gcaingestion.listener.SQSListener;
import com.tabiiki.gca.gcaingestion.publish.SNSPublisher;
import com.tabiiki.gca.gcaingestion.service.IngestionService;
import com.tabiiki.gca.gcaingestion.service.impl.IngestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import static org.mockito.Mockito.mock;

public class GcaIngestionComponentTest {

    private final IS3Facade is3Facade = mock(IS3Facade.class);
    private final SnsClient snsClient = mock(SnsClient.class);
    private final SqsAsyncClient sqsAsyncClient = mock(SqsAsyncClient.class);

    private final SNSPublisher snsPublisher = new SNSPublisher("fake", snsClient);

    private final IngestionService ingestionService = new IngestionServiceImpl(is3Facade, snsPublisher);
    private final SQSListener sqsListener = new SQSListener("fake", ingestionService, sqsAsyncClient);

    @BeforeEach
    public void init(){
        //TODO this needs a lot more thought for now.
    }

    @Test
    public void happyPathTest() {

    }

}
