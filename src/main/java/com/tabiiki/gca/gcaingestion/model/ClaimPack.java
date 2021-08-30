package com.tabiiki.gca.gcaingestion.model;

import com.tabiiki.gca.gcaingestion.model.claim.Claim;
import com.tabiiki.gca.gcaingestion.model.epos.Epos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class ClaimPack {
    private final Claim claim;
    private final Epos epos;
    private final List<RuntimeException> exceptions;

    public List<String> getExceptions() {
        return exceptions.stream().map(Throwable::getMessage).collect(Collectors.toList());
    }

}
