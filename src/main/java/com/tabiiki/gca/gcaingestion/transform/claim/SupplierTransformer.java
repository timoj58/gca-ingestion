package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.exception.SupplierException;
import com.tabiiki.gca.gcaingestion.model.claim.actor.Supplier;
import com.tabiiki.gca.gcaingestion.util.TransformExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

@UtilityClass
public class SupplierTransformer {

    public Supplier transform(Sheet sheet, List<RuntimeException> exceptions, String key) {

        var supplierName = TransformExceptionHandler.handle(exceptions::add, () -> transformSupplierName(sheet), key);
        var supplierAccountNumber = TransformExceptionHandler.handle(exceptions::add, () -> transformSupplierAccountNumber(sheet), key);
        var supplierContactName = TransformExceptionHandler.handle(exceptions::add, () -> transformSupplierContactName(sheet), key);
        var supplierContactEmail = TransformExceptionHandler.handle(exceptions::add, () -> transformSupplierContactEmail(sheet), key);
        var supplierContactPhone = TransformExceptionHandler.handle(exceptions::add, () -> transformSupplierContactPhone(sheet), key);

        return Supplier.builder()
                .supplierName(supplierName.map(m -> (String) m).orElse(""))
                .supplierContactEmail(supplierContactEmail.map(m -> (String) m).orElse(""))
                .supplierContactName(supplierContactName.map(m -> (String) m).orElse(""))
                .supplierContactPhone(supplierContactPhone.map(m -> (String) m).orElse(""))
                .supplierAccountNumber(supplierAccountNumber.map(m -> (String) m).orElse(""))
                .build();

    }

    private String transformSupplierName(Sheet sheet) throws SupplierException {
        try {
            return "";
        } catch (Exception e) {
            throw new SupplierException("supplierName");
        }
    }

    private String transformSupplierContactEmail(Sheet sheet) throws SupplierException {
        try {
            return "";
        } catch (Exception e) {
            throw new SupplierException("supplierContactEmail");
        }
    }

    private String transformSupplierContactName(Sheet sheet) throws SupplierException {
        try {
            return "";
        } catch (Exception e) {
            throw new SupplierException("supplierContactName");
        }
    }

    private String transformSupplierContactPhone(Sheet sheet) throws SupplierException {
        try {
            return "";
        } catch (Exception e) {
            throw new SupplierException("supplierContactPhone");
        }
    }

    private String transformSupplierAccountNumber(Sheet sheet) throws SupplierException {
        try {
            return "";
        } catch (Exception e) {
            throw new SupplierException("supplierAccountNumber");
        }
    }
}
