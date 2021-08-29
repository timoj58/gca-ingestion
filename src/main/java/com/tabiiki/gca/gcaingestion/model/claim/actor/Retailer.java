package com.tabiiki.gca.gcaingestion.model.claim.actor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Retailer {
    private final String retailerName;
    private final String retailerFinancialYear;
}
