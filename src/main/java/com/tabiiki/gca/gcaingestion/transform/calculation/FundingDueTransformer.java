package com.tabiiki.gca.gcaingestion.transform.calculation;

import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingDue;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

@UtilityClass
public class FundingDueTransformer {
    public FundingDue transform(Sheet sheet) {
        return FundingDue.builder().build();
    }
}
