package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.model.claim.Claim;
import com.tabiiki.gca.gcaingestion.transform.calculation.FundingDueTransformer;
import com.tabiiki.gca.gcaingestion.transform.calculation.FundingPaidByInvoiceTransformer;
import com.tabiiki.gca.gcaingestion.transform.calculation.FundingPaidByProductTransformer;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

@UtilityClass
public class CalculationTransformer {

    public Claim transform(Sheet sheet) {
        return Claim.builder()
                .fundingDue(FundingDueTransformer.transform(sheet))
                .fundingPaidByInvoice(FundingPaidByInvoiceTransformer.transform(sheet))
                .fundingPaidByProduct(FundingPaidByProductTransformer.transform(sheet))
                .build();
    }
}
