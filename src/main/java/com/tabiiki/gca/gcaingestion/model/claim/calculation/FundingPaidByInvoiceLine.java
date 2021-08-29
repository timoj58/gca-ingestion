package com.tabiiki.gca.gcaingestion.model.claim.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FundingPaidByInvoiceLine {
    private String invoiceNumber;
    private String invoiceDate;
    private String invoiceDescription;
    private String netAmount;
    private String vat;
    private String gross;
}
