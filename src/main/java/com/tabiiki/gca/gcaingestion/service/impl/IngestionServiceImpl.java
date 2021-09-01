package com.tabiiki.gca.gcaingestion.service.impl;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.tabiiki.gca.gcaingestion.facade.S3Facade;
import com.tabiiki.gca.gcaingestion.model.ClaimPack;
import com.tabiiki.gca.gcaingestion.rule.IngestionRule;
import com.tabiiki.gca.gcaingestion.service.IngestionService;
import com.tabiiki.gca.gcaingestion.service.PublishService;
import com.tabiiki.gca.gcaingestion.transform.ClaimPackTransformer;
import com.tabiiki.gca.gcaingestion.util.S3ObjectConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
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

    private Triple<String, String, List<IngestionRule>> prepare(
            S3EventNotification.S3EventNotificationRecord record
    ) {
        final List<IngestionRule> failures = new ArrayList<>();

        var key = record.getS3().getObject().getKey();
        var id = key.replace("upload/", "").replace(".xlsx", "");

        log.info("key: {}, id: {}", key, id);

        return Triple.of(key, id, failures);
    }

    private Triple<String, Optional<ClaimPack>, List<IngestionRule>> extract(
            Triple<String, String, List<IngestionRule>> config
    ) {
        var key = config.getLeft();
        var id = config.getMiddle();
        var failures = config.getRight();
        try {
            var claimPack = ClaimPackTransformer.transform(
                    S3ObjectConverter.convertExcel(s3Facade.get(key)), key);

            if (!claimPack.getExceptions().isEmpty()) {
                failures.add(IngestionRule.EXCEL_FIELDS);
            }
            return Triple.of(id, Optional.of(claimPack), failures);
        } catch (IOException e) {
            failures.add(IngestionRule.EXCEL_FATAL);
            log.error("failed to convert excel file for {}", key, e);
        }
        return Triple.of(id, Optional.empty(), failures);
    }

    private Triple<String, Optional<ClaimPack>, List<IngestionRule>> validate(
            Triple<String, Optional<ClaimPack>, List<IngestionRule>> config
    ) {
        var id = config.getLeft();
        var optionalClaimPack = config.getMiddle();
        var failures = config.getRight();

        optionalClaimPack.ifPresent(claimPack -> {
            //TODO rule engine part.
        });

        return config;
    }

}
