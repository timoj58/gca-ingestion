package com.tabiiki.gca.gcaingestion.transform.calculation;

import com.tabiiki.gca.gcaingestion.exception.FundingPaidByInvoiceException;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByInvoice;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByInvoiceLine;
import com.tabiiki.gca.gcaingestion.util.TransformExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class FundingPaidByInvoiceTransformer {

    public FundingPaidByInvoice transform(Sheet sheet, List<RuntimeException> exceptions, String key) {

        var total = TransformExceptionHandler.handle(exceptions::add, () -> transformTotal(sheet), key);

        return FundingPaidByInvoice.builder()
                .total(total.map(m -> (String) m).orElse(""))
                .fundingPaidByInvoiceLines(transformLines(sheet, exceptions, key))
                .build();
    }

    private String transformTotal(Sheet sheet) throws FundingPaidByInvoiceException {
        try {
            return "";
        } catch (Exception e) {
            throw new FundingPaidByInvoiceException("total");
        }
    }

    private List<FundingPaidByInvoiceLine> transformLines(Sheet sheet, List<RuntimeException> exceptions, String key) {
        List<FundingPaidByInvoiceLine> fundingPaidByInvoiceLines = new ArrayList<>();

        return fundingPaidByInvoiceLines;
    }


    private Optional<Object> transformCell(Supplier<Object> transformSupplier, List<RuntimeException> exceptions, String key, int row, String value) {
        return TransformExceptionHandler.handle(
                (e) -> exceptions.add(new FundingPaidByInvoiceException(String.format("line %d %s", row, value))),
                transformSupplier, key);
    }
}
