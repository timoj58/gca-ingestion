package com.tabiiki.gca.gcaingestion.model.claim;

import com.tabiiki.gca.gcaingestion.model.claim.actor.Auditor;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Retailer;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Supplier;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingDue;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByInvoice;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Claim {

    private final String claimIdentificationDate;
    private final String claimNumber;
    private final String claimAmount;
    private final String claimCurrency;
    private final String claimType;
    private final String rootCauseSummary;
    private final String claimDescription;

    private final List<OmittedProduct> omittedProducts;

    private final Promo promo;
    private final Retailer retailer;
    private final Supplier supplier;
    private final Auditor auditor;

    private final FundingDue fundingDue;
    private final FundingPaidByInvoice fundingPaidByInvoice;
    private final FundingPaidByProduct fundingPaidByProduct;

    private final List<RuntimeException> exceptions;

}
