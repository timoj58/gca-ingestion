package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.exception.OmittedProductsException;
import com.tabiiki.gca.gcaingestion.model.claim.OmittedProduct;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;


@UtilityClass
public class OmittedProductTransformer {
    public List<OmittedProduct> transform(Sheet sheet) throws OmittedProductsException {
        List<OmittedProduct> omittedProducts = new ArrayList<>();
        return omittedProducts;
    }
}
