package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.model.claim.Claim;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

@UtilityClass
public class ClaimTransformer {

    public Claim transform(Workbook workbook, List<RuntimeException> exceptions, String key) {

        Sheet sheet = workbook.getSheetAt(0);

        return CalculationTransformer.transform(sheet, exceptions, key)
                .toBuilder()
                .claimHeader(ClaimHeaderTransformer.transform(sheet, exceptions, key))
                .omittedProducts(OmittedProductTransformer.transform(sheet, exceptions, key))
                .promo(PromoTransformer.transform(sheet, exceptions, key))
                .retailer(RetailerTransformer.transform(sheet, exceptions, key))
                .supplier(SupplierTransformer.transform(sheet, exceptions, key))
                .auditor(AuditorTransformer.transform(sheet, exceptions, key))
                .build();
    }

}
