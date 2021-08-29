package com.tabiiki.gca.gcaingestion.model.claim.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class FundingPaidByProduct {
    private final String total;
    private final List<FundingPaidByProductLine> fundingPaidByProductLines;
}
