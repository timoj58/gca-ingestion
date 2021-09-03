package com.tabiiki.gca.gcaingestion.wrapper;

import com.tabiiki.gca.gcaingestion.model.ClaimPack;
import com.tabiiki.gca.gcaingestion.rule.IngestionRule;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ClaimPackWrapper extends Triple<String, Optional<ClaimPack>, List<IngestionRule>> {

    private final String id;
    private final Optional<ClaimPack> claimPack;
    private final List<IngestionRule> failures;

    @Override
    public String getLeft() {
        return id;
    }

    @Override
    public Optional<ClaimPack> getMiddle() {
        return claimPack;
    }

    @Override
    public List<IngestionRule> getRight() {
        return failures;
    }
}
