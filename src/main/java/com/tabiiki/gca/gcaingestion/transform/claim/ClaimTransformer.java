package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.exception.AuditorException;
import com.tabiiki.gca.gcaingestion.exception.PromoException;
import com.tabiiki.gca.gcaingestion.exception.RetailerException;
import com.tabiiki.gca.gcaingestion.exception.SupplierException;
import com.tabiiki.gca.gcaingestion.model.claim.Claim;
import com.tabiiki.gca.gcaingestion.model.claim.OmittedProduct;
import com.tabiiki.gca.gcaingestion.model.claim.Promo;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Auditor;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Retailer;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Supplier;
import com.tabiiki.gca.gcaingestion.util.TransformerExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Collections;
import java.util.List;

@UtilityClass
public class ClaimTransformer {

    public Claim transform(Workbook workbook, List<RuntimeException> exceptions) {

        Sheet sheet = workbook.getSheetAt(0);

        var claimIdentificationDate = "";
        var claimNumber = "";
        var claimAmount = "";
        var claimCurrency = "";
        var claimType = "";
        var rootCauseSummary = "";
        var claimDescription = "";

        var promo = TransformerExceptionHandler.handle(exceptions::add, () -> transformPromo(sheet));
        var supplier = TransformerExceptionHandler.handle(exceptions::add, () -> transformSupplier(sheet));
        var auditor = TransformerExceptionHandler.handle(exceptions::add, () -> transformAuditor(sheet));
        var retailer = TransformerExceptionHandler.handle(exceptions::add, () -> transformRetailer(sheet));
        var omittedProducts = TransformerExceptionHandler.handle(exceptions::add, () -> OmittedProductTransformer.transform(sheet));


        return CalculationTransformer.transform(sheet)
                .toBuilder()
                .claimIdentificationDate(claimIdentificationDate)
                .claimNumber(claimNumber)
                .claimAmount(claimAmount)
                .claimCurrency(claimCurrency)
                .claimType(claimType)
                .rootCauseSummary(rootCauseSummary)
                .claimDescription(claimDescription)
                .omittedProducts(omittedProducts.map(m -> (List<OmittedProduct>) m).orElseGet(Collections::emptyList))
                .promo(promo.map(m -> (Promo) m).orElseGet(() -> Promo.builder().build()))
                .retailer(retailer.map(m -> (Retailer) m).orElseGet(() -> Retailer.builder().build()))
                .supplier(supplier.map(m -> (Supplier) m).orElseGet(() -> Supplier.builder().build()))
                .auditor(auditor.map(m -> (Auditor) m).orElseGet(() -> Auditor.builder().build()))
                .build();
    }

    private Promo transformPromo(Sheet sheet) throws PromoException {
        return Promo.builder().build();
    }

    private Supplier transformSupplier(Sheet sheet) throws SupplierException {
        return Supplier.builder().build();
    }

    private Auditor transformAuditor(Sheet sheet) throws AuditorException {
        return Auditor.builder().build();
    }

    private Retailer transformRetailer(Sheet sheet) throws RetailerException {
        return Retailer.builder().build();
    }

}
