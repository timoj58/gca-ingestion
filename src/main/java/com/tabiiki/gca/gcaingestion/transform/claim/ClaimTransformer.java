package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.model.claim.Claim;
import com.tabiiki.gca.gcaingestion.model.claim.Promo;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Auditor;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Retailer;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Supplier;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

@UtilityClass
public class ClaimTransformer {

    public Claim transform(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);

        var claimIdentificationDate = "";
        var claimNumber = "";
        var claimAmount = "";
        var claimCurrency = "";
        var claimType = "";
        var rootCauseSummary = "";
        var claimDescription = "";


        return CalculationTransformer.transform(sheet)
                .toBuilder()
                .claimIdentificationDate(claimIdentificationDate)
                .claimNumber(claimNumber)
                .claimAmount(claimAmount)
                .claimCurrency(claimCurrency)
                .claimType(claimType)
                .rootCauseSummary(rootCauseSummary)
                .claimDescription(claimDescription)
                .omittedProducts(OmittedProductTransformer.transform(sheet))
                .promo(transformPromo(sheet))
                .retailer(transformRetailer(sheet))
                .supplier(transformSupplier(sheet))
                .auditor(transformAuditor(sheet))
                .build();
    }

    private Promo transformPromo(Sheet sheet) {
        return Promo.builder().build();
    }

    private Supplier transformSupplier(Sheet sheet) {
        return Supplier.builder().build();
    }

    private Auditor transformAuditor(Sheet sheet) {
        return Auditor.builder().build();
    }

    private Retailer transformRetailer(Sheet sheet) {
        return Retailer.builder().build();
    }

}
