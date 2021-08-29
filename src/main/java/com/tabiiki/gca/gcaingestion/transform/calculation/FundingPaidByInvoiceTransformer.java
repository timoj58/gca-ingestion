package com.tabiiki.gca.gcaingestion.transform.calculation;

import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByInvoice;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

@UtilityClass
public class FundingPaidByInvoiceTransformer {

    public FundingPaidByInvoice transform(Sheet sheet) {
        return FundingPaidByInvoice.builder().build();
    }
}
