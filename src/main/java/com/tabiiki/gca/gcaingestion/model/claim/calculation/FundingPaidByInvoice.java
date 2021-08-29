package com.tabiiki.gca.gcaingestion.model.claim.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class FundingPaidByInvoice {
    private String total;
    private List<FundingPaidByInvoiceLine> fundingPaidByInvoiceLines;

}
