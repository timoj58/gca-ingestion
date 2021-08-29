package com.tabiiki.gca.gcaingestion.model.claim.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FundingDueLine {
    private String promoCode;
    private String productDescription;
    private String startDate;
    private String endDate;
    private String salesVolume;
    private String fundingPerUnit;
    private String totalFundingDue;


}
