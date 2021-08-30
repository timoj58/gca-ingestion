package com.tabiiki.gca.gcaingestion.model.claim.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FundingDueLine {
    private final String productCode;
    private final String productDescription;
    private final String startDate;
    private final String endDate;
    private final String salesVolume;
    private final String fundingPerUnit;
    private final String totalFundingDue;


}
