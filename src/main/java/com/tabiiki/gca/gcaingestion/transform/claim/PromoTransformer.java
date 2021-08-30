package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.exception.ClaimHeaderException;
import com.tabiiki.gca.gcaingestion.exception.PromoException;
import com.tabiiki.gca.gcaingestion.model.claim.Promo;
import com.tabiiki.gca.gcaingestion.util.CellUtils;
import com.tabiiki.gca.gcaingestion.util.TransformExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

@UtilityClass
public class PromoTransformer {

    public Promo transform(Sheet sheet, List<RuntimeException> exceptions, String key) {
        var promoStartDate = TransformExceptionHandler.handle(exceptions::add, () -> transformPromoStartDate(sheet), key);
        var promoEndDate = TransformExceptionHandler.handle(exceptions::add, () -> transformPromoEndDate(sheet), key);
        var promoDiscountName = TransformExceptionHandler.handle(exceptions::add, () -> transformPromoDiscountName(sheet), key);

        return Promo.builder()
                .promoStartDate(promoStartDate.map(m -> (String) m).orElse(""))
                .promoEndDate(promoEndDate.map(m -> (String) m).orElse(""))
                .promoDiscountName(promoDiscountName.map(m -> (String) m).orElse(""))
                .build();
    }

    private String transformPromoStartDate(Sheet sheet) throws PromoException {
        return CellUtils.search(sheet, "Promo Start date", new ClaimHeaderException("promoStartDate"));
    }

    private String transformPromoEndDate(Sheet sheet) throws PromoException {
        return CellUtils.search(sheet, "Promo End date", new ClaimHeaderException("promoEndDate"));
    }

    private String transformPromoDiscountName(Sheet sheet) throws PromoException {
        return CellUtils.search(sheet, "Promo Discount Name", new ClaimHeaderException("promoDiscountName"));
    }
}
