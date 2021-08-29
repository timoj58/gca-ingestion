package com.tabiiki.gca.gcaingestion.model.claim.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class FundingDue {
    private String totalFundingDue;
    private String lessFundingClaimed;
    private String claimTotal;
    private List<FundingDue> fundingDues;
}
