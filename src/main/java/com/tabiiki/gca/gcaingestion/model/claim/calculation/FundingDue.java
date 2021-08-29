package com.tabiiki.gca.gcaingestion.model.claim.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class FundingDue {
    private final String totalFundingDue;
    private final String lessFundingClaimed;
    private final String claimTotal;
    private final List<FundingDueLine> fundingDues;
}
