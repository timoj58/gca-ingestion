package com.tabiiki.gca.gcaingestion.model.claim;

import com.tabiiki.gca.gcaingestion.model.claim.actor.Auditor;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Retailer;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Supplier;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingDue;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingDueLine;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByInvoice;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class Claim {

    private String claimIdentificationDate;
    private String claimNumber;
    private String claimAmount;
    private String claimCurrency;
    private String claimType;
    private String rootCauseSummary;
    private String claimDescription;

    private List<OmittedProduct> omittedProducts;

    private Promo promo;
    private Retailer retailer;
    private Supplier supplier;
    private Auditor auditor;

    private FundingDue fundingDue;
    private FundingPaidByInvoice fundingPaidByInvoice;
    private FundingPaidByProduct fundingPaidByProduct;

}
