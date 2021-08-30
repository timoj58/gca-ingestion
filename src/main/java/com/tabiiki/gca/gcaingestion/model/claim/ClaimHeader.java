package com.tabiiki.gca.gcaingestion.model.claim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ClaimHeader {
    private final String claimIdentificationDate;
    private final String claimNumber;
    private final String claimAmount;
    private final String claimCurrency;
    private final String claimType;
    private final String rootCauseSummary;
    private final String claimDescription;

}
