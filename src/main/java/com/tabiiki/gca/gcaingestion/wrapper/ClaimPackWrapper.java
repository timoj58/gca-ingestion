package com.tabiiki.gca.gcaingestion.wrapper;

import com.tabiiki.gca.gcaingestion.model.ClaimPack;
import com.tabiiki.gca.gcaingestion.rule.IngestionRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Builder
@AllArgsConstructor
@Getter
public class ClaimPackWrapper {

    private final String id;
    private final Optional<ClaimPack> claimPack;
    private final List<IngestionRule> failures;

}
