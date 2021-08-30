package com.tabiiki.gca.gcaingestion.service.impl;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabiiki.gca.gcaingestion.facade.S3Facade;
import com.tabiiki.gca.gcaingestion.facade.SNSFacade;
import com.tabiiki.gca.gcaingestion.message.IngestionMessage;
import com.tabiiki.gca.gcaingestion.message.IngestionStatus;
import com.tabiiki.gca.gcaingestion.model.ClaimPack;
import com.tabiiki.gca.gcaingestion.rule.IngestionRule;
import com.tabiiki.gca.gcaingestion.service.IngestionService;
import com.tabiiki.gca.gcaingestion.transform.ClaimPackTransformer;
import com.tabiiki.gca.gcaingestion.util.S3ObjectConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class IngestionServiceImpl implements IngestionService {

    private final S3Facade s3Facade;
    private final SNSFacade snsFacade;

    @Override
    public void ingest(S3EventNotification event) {
        log.info("ingesting {}", event.toJson());

        event.getRecords()
                .forEach(record -> {
                    List<IngestionRule> failures = new ArrayList<>();

                    var key = record.getS3().getObject().getKey();
                    var id = key.replace("upload/", "").replace(".xlsx", "");
                    log.info("key: {}, id: {}", key, id);

                    Mono.just(Pair.of(key, id))
                            .doOnNext(keys -> process(keys, failures))
                            .doFinally(publish -> publish(id, failures))
                            .subscribe();
                });

    }

    private void process(Pair<String, String> keys, List<IngestionRule> failures) {
        var key = keys.getLeft();
        var id = keys.getRight();
        try {
            var claimPack = extract(key, failures);
            validate(claimPack, failures);
            s3Facade.put("final/" + id, new ObjectMapper().writeValueAsString(claimPack));
        } catch (IOException e) {
            failures.add(IngestionRule.EXCEL_FATAL);
            log.error("failed to convert excel file for {}", key, e);
        }

    }

    private ClaimPack extract(String key, List<IngestionRule> failures) throws IOException {
        var claimPack = ClaimPackTransformer.transform(
                S3ObjectConverter.convertExcel(s3Facade.get(key)), key);
        if (!claimPack.getExceptions().isEmpty()) {
            failures.add(IngestionRule.EXCEL_FIELDS);
        }
        return claimPack;
    }

    private void validate(ClaimPack claimPack, List<IngestionRule> failures) {
        //TODO rule engine part.
    }

    private void publish(String id, List<IngestionRule> failures) {
        snsFacade.publish(
                IngestionMessage.builder()
                        .id(UUID.fromString(id))
                        .ingestionStatus(failures.isEmpty() ? IngestionStatus.SUCCESS : IngestionStatus.ERROR)
                        .ruleFailures(failures)
                        .build()
        );
    }

}
