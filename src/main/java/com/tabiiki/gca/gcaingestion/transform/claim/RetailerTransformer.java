package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.exception.RetailerException;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Retailer;
import com.tabiiki.gca.gcaingestion.util.TransformExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

@UtilityClass
public class RetailerTransformer {

    public Retailer transform(Sheet sheet, List<RuntimeException> exceptions, String key) {
        var retailerName = TransformExceptionHandler.handle(exceptions::add, () -> transformRetailerName(sheet), key);
        var retailerFinancialYear = TransformExceptionHandler.handle(exceptions::add, () -> transformRetailerFinancialYear(sheet), key);
        return Retailer.builder()
                .retailerName(retailerName.map(m -> (String) m).orElse(""))
                .retailerFinancialYear(retailerFinancialYear.map(m -> (String) m).orElse(""))
                .build();
    }

    private String transformRetailerName(Sheet sheet) throws RetailerException {
        try {
            return "";
        } catch (Exception e) {
            throw new RetailerException("retailerName");
        }
    }

    private String transformRetailerFinancialYear(Sheet sheet) throws RetailerException {
        try {
            return "";
        } catch (Exception e) {
            throw new RetailerException("retailerFinancialYear");
        }
    }
}
