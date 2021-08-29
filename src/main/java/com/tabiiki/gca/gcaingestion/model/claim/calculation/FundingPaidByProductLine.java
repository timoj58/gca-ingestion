package com.tabiiki.gca.gcaingestion.model.claim.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FundingPaidByProductLine {
    private String productCode;
    private String productDescription;
    private String startDate;
    private String endDate;
    private String salesVolume;
    private String fundingPerUnit;
    private String totalFundingPaid;
}
