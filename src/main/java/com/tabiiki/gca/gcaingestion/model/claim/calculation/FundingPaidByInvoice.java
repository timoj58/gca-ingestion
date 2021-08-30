package com.tabiiki.gca.gcaingestion.model.claim.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class FundingPaidByInvoice {
    private final String totalNet;
    private final String totalVat;
    private final String totalGross;

    private final List<FundingPaidByInvoiceLine> fundingPaidByInvoiceLines;

}
