package com.tabiiki.gca.gcaingestion.service.impl;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.tabiiki.gca.gcaingestion.facade.IS3Facade;
import com.tabiiki.gca.gcaingestion.message.IngestionMessage;
import com.tabiiki.gca.gcaingestion.message.IngestionStatus;
import com.tabiiki.gca.gcaingestion.publish.SNSPublisher;
import com.tabiiki.gca.gcaingestion.service.IngestionService;
import com.tabiiki.gca.gcaingestion.util.S3ObjectConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class IngestionServiceImpl implements IngestionService {

    private final IS3Facade s3Facade;
    private final SNSPublisher snsPublisher;

    @Override
    public void ingest(S3EventNotification event) {
        log.info("ingesting {}", event.toJson());

        event.getRecords()
                .forEach(record -> {
                    var key = record.getS3().getObject().getKey();
                    var id = key.replace("upload/", "");
                    log.info("key: {}, id: {}", key, id);
                    s3Facade.put("final/" + id, S3ObjectConverter.convert(s3Facade.get(key)));
                    snsPublisher.publish(
                            IngestionMessage.builder()
                                    .id(UUID.fromString(id))
                                    .ingestionStatus(IngestionStatus.SUCCESS)
                                    .ruleFailures(Collections.emptyList())
                                    .build()
                    );
                });


    }
}
