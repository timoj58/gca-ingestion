package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.model.claim.Claim;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingDue;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByInvoice;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByProduct;
import com.tabiiki.gca.gcaingestion.transform.calculation.FundingDueTransformer;
import com.tabiiki.gca.gcaingestion.transform.calculation.FundingPaidByInvoiceTransformer;
import com.tabiiki.gca.gcaingestion.transform.calculation.FundingPaidByProductTransformer;
import com.tabiiki.gca.gcaingestion.util.TransformExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

@UtilityClass
public class CalculationTransformer {

    public Claim transform(Sheet sheet, List<RuntimeException> exceptions) {

        var fundingDue = TransformExceptionHandler.handle(exceptions::add, () -> FundingDueTransformer.transform(sheet));
        var fundingPaidByInvoice = TransformExceptionHandler.handle(exceptions::add, () -> FundingPaidByInvoiceTransformer.transform(sheet));
        var fundingPaidByProduct = TransformExceptionHandler.handle(exceptions::add, () -> FundingPaidByProductTransformer.transform(sheet));

        return Claim.builder()
                .fundingDue(fundingDue.map(m -> (FundingDue) m).orElseGet(() -> FundingDue.builder().build()))
                .fundingPaidByInvoice(fundingPaidByInvoice.map(m -> (FundingPaidByInvoice) m).orElseGet(() -> FundingPaidByInvoice.builder().build()))
                .fundingPaidByProduct(fundingPaidByProduct.map(m -> (FundingPaidByProduct) m).orElseGet(() -> FundingPaidByProduct.builder().build()))
                .build();
    }
}
