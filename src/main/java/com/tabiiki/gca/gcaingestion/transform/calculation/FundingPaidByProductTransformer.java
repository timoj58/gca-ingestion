package com.tabiiki.gca.gcaingestion.transform.calculation;

import com.tabiiki.gca.gcaingestion.exception.FundingPaidByProductException;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByProduct;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByProductLine;
import com.tabiiki.gca.gcaingestion.util.TransformExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class FundingPaidByProductTransformer {
    public FundingPaidByProduct transform(Sheet sheet, List<RuntimeException> exceptions, String key) {

        var total = TransformExceptionHandler.handle(exceptions::add, () -> transformTotal(sheet), key);

        return FundingPaidByProduct.builder()
                .total(total.map(m -> (String) m).orElse(""))
                .fundingPaidByProductLines(transformLines(sheet, exceptions, key))
                .build();
    }

    private String transformTotal(Sheet sheet) throws FundingPaidByProductException {
        try {
            return "";
        } catch (Exception e) {
            throw new FundingPaidByProductException("total");
        }
    }

    private List<FundingPaidByProductLine> transformLines(Sheet sheet, List<RuntimeException> exceptions, String key) {
        List<FundingPaidByProductLine> fundingPaidByProductLines = new ArrayList<>();

        return fundingPaidByProductLines;
    }

    private Optional<Object> transformCell(Supplier<Object> transformSupplier, List<RuntimeException> exceptions, String key, int row, String value) {
        return TransformExceptionHandler.handle(
                (e) -> exceptions.add(new FundingPaidByProductException(String.format("line %d %s", row, value))),
                transformSupplier, key);
    }
}
