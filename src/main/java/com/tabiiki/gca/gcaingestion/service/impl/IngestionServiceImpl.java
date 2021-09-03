package com.tabiiki.gca.gcaingestion.service.impl;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.tabiiki.gca.gcaingestion.facade.S3Facade;
import com.tabiiki.gca.gcaingestion.rule.IngestionRule;
import com.tabiiki.gca.gcaingestion.service.IngestionService;
import com.tabiiki.gca.gcaingestion.service.PublishService;
import com.tabiiki.gca.gcaingestion.transform.ClaimPackTransformer;
import com.tabiiki.gca.gcaingestion.util.S3ObjectConverter;
import com.tabiiki.gca.gcaingestion.wrapper.ClaimPackWrapper;
import com.tabiiki.gca.gcaingestion.wrapper.IngestionKeyWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class IngestionServiceImpl implements IngestionService {

    private final S3Facade s3Facade;
    private final PublishService publishService;

    @Override
    public void ingest(S3EventNotification event) {
        log.info("ingesting {}", event.toJson());

        Flux.fromStream(event.getRecords().stream())
                .subscribe(record ->
                        Mono.just(prepare(record))
                                .map(this::extract)
                                .map(this::validate)
                                .doOnNext(publishService::publish)
                                .subscribe());

    }

    private IngestionKeyWrapper prepare(
            S3EventNotification.S3EventNotificationRecord record
    ) {
        final List<IngestionRule> failures = new ArrayList<>();

        var key = record.getS3().getObject().getKey();
        var id = key.replace("upload/", "").replace(".xlsx", "");

        log.info("key: {}, id: {}", key, id);
        log.info("timmytime {}", key);

        return new IngestionKeyWrapper(key, id, failures);
    }

    private ClaimPackWrapper extract(
            IngestionKeyWrapper wrapper
    ) {
        var key = wrapper.getLeft();
        var id = wrapper.getMiddle();
        var failures = wrapper.getRight();
        try {
            var claimPack = ClaimPackTransformer.transform(
                    S3ObjectConverter.convertExcel(s3Facade.get(key)), key);

            if (!claimPack.getExceptions().isEmpty()) {
                failures.add(IngestionRule.EXCEL_FIELDS);
            }
            return new ClaimPackWrapper(id, Optional.of(claimPack), failures);
        } catch (IOException e) {
            failures.add(IngestionRule.EXCEL_FATAL);
            log.error("failed to convert excel file for {}", key, e);
        }
        return new ClaimPackWrapper(id, Optional.empty(), failures);
    }

    private ClaimPackWrapper validate(
            ClaimPackWrapper wrapper
    ) {
        var id = wrapper.getLeft();
        var optionalClaimPack = wrapper.getMiddle();
        var failures = wrapper.getRight();

        optionalClaimPack.ifPresent(claimPack -> {
            //TODO rule engine part.
        });

        return wrapper;
    }

}
