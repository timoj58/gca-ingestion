package com.tabiiki.gca.gcaingestion.model.claim.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FundingPaidByInvoiceLine {
    private final String invoiceNumber;
    private final String invoiceDate;
    private final String invoiceDescription;
    private final String netAmount;
    private final String vat;
    private final String gross;
}
