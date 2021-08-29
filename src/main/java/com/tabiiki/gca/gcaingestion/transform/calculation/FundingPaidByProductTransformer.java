package com.tabiiki.gca.gcaingestion.transform.calculation;

import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByProduct;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByProductLine;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class FundingPaidByProductTransformer {
    public FundingPaidByProduct transform(Sheet sheet) {
        List<FundingPaidByProductLine> fundingPaidByProductLines = new ArrayList<>();

        var total = "";

        return FundingPaidByProduct.builder()
                .total(total)
                .fundingPaidByProductLines(fundingPaidByProductLines)
                .build();
    }
}
