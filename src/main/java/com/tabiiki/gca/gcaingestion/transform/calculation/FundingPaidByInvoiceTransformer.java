package com.tabiiki.gca.gcaingestion.transform.calculation;

import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByInvoice;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByInvoiceLine;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class FundingPaidByInvoiceTransformer {

    public FundingPaidByInvoice transform(Sheet sheet) {
        List<FundingPaidByInvoiceLine> fundingPaidByInvoiceLines = new ArrayList<>();

        var total = "";

        return FundingPaidByInvoice.builder()
                .total(total)
                .fundingPaidByInvoiceLines(fundingPaidByInvoiceLines)
                .build();
    }
}