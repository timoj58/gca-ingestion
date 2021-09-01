package com.tabiiki.gca.gcaingestion.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabiiki.gca.gcaingestion.facade.S3Facade;
import com.tabiiki.gca.gcaingestion.facade.SNSFacade;
import com.tabiiki.gca.gcaingestion.message.IngestionMessage;
import com.tabiiki.gca.gcaingestion.message.IngestionStatus;
import com.tabiiki.gca.gcaingestion.model.ClaimPack;
import com.tabiiki.gca.gcaingestion.rule.IngestionRule;
import com.tabiiki.gca.gcaingestion.service.PublishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class PublishServiceImpl implements PublishService {

    private final S3Facade s3Facade;
    private final SNSFacade snsFacade;

    @Override
    public void publish(Triple<String, Optional<ClaimPack>, List<IngestionRule>> finalClaim) {
        var id = finalClaim.getLeft();
        var failures = finalClaim.getRight();
        log.info("publishing {}", id);

        Mono.just(finalClaim)
                .doOnNext(this::upload)
                .doFinally(notify ->
                        snsFacade.publish(
                                IngestionMessage.builder()
                                        .id(UUID.fromString(id))
                                        .ingestionStatus(failures.isEmpty() ?
                                                IngestionStatus.SUCCESS : IngestionStatus.ERROR)
                                        .ruleFailures(failures)
                                        .build()
                        )
                ).subscribe();

    }

    private void upload(Triple<String, Optional<ClaimPack>, List<IngestionRule>> finalClaim) {
        var id = finalClaim.getLeft();
        var optionalClaimPack = finalClaim.getMiddle();
        var failures = finalClaim.getRight();

        optionalClaimPack.ifPresent(claimPack -> {
            try {
                s3Facade.put("final/" + id, new ObjectMapper().writeValueAsString(claimPack));
            } catch (JsonProcessingException e) {
                log.error("json error on upload for {}", id, e);
                failures.add(IngestionRule.EXCEL_FATAL);
            }
        });
    }
}
