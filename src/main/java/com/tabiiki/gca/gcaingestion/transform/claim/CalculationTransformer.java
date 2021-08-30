package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.model.claim.Claim;
import com.tabiiki.gca.gcaingestion.transform.calculation.FundingDueTransformer;
import com.tabiiki.gca.gcaingestion.transform.calculation.FundingPaidByInvoiceTransformer;
import com.tabiiki.gca.gcaingestion.transform.calculation.FundingPaidByProductTransformer;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

@UtilityClass
public class CalculationTransformer {

    public Claim transform(Sheet sheet, List<RuntimeException> exceptions, String key) {

        return Claim.builder()
                .fundingDue(FundingDueTransformer.transform(sheet, exceptions, key))
                .fundingPaidByInvoice(FundingPaidByInvoiceTransformer.transform(sheet, exceptions, key))
                .fundingPaidByProduct(FundingPaidByProductTransformer.transform(sheet, exceptions, key))
                .build();
    }
}
