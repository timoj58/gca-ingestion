package com.tabiiki.gca.gcaingestion.model;

import com.tabiiki.gca.gcaingestion.model.claim.Claim;
import com.tabiiki.gca.gcaingestion.model.epos.Epos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ClaimPack {
    private final Claim claim;
    private final Epos epos;
}