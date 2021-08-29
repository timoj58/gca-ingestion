package com.tabiiki.gca.gcaingestion.transform.calculation;

import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByProduct;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

@UtilityClass
public class FundingPaidByProductTransformer {
    public FundingPaidByProduct transform(Sheet sheet) {
        return FundingPaidByProduct.builder().build();
    }
}
