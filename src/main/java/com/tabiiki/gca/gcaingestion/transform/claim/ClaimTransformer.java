package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.model.claim.Claim;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

@UtilityClass
public class ClaimTransformer {

    public Claim transform(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);

        return CalculationTransformer.transform(sheet).toBuilder()
                .omittedProducts(OmittedProductTransformer.transform(sheet))
                .build();
    }
}
