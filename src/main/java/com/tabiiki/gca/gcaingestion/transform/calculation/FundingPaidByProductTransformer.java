package com.tabiiki.gca.gcaingestion.transform.calculation;

import com.tabiiki.gca.gcaingestion.exception.FundingPaidByProductException;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByProduct;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByProductLine;
import com.tabiiki.gca.gcaingestion.util.CellUtils;
import com.tabiiki.gca.gcaingestion.util.TransformExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class FundingPaidByProductTransformer {
    public FundingPaidByProduct transform(Sheet sheet, List<RuntimeException> exceptions, String key) {

        return FundingPaidByProduct.builder()
                .total(transformTotal(sheet, exceptions, key))
                .fundingPaidByProductLines(transformLines(sheet, exceptions, key))
                .build();
    }

    private String transformTotal(Sheet sheet, List<RuntimeException> exceptions, String key) {
        var endCell = CellUtils.locate(sheet, "Evidence Upload", new FundingPaidByProductException("failed to locate table"));
        var row = sheet.getRow(endCell.getRowIndex() - 2);

        return transformCell(() -> CellUtils.getValue(row.getCell(6)),
                exceptions, key, row.getRowNum(), "total")
                .map(m -> (String) m).orElse("");
    }

    private List<FundingPaidByProductLine> transformLines(Sheet sheet, List<RuntimeException> exceptions, String key) {
        List<FundingPaidByProductLine> fundingPaidByProductLines = new ArrayList<>();

        try {

            var startCell = CellUtils.locate(sheet, "Funding paid by product (if available)", new FundingPaidByProductException("failed to locate table"));
            var endCell = CellUtils.locate(sheet, "Evidence Upload", new FundingPaidByProductException("failed to locate table"));

            for (int i = startCell.getRowIndex() + 3; i < endCell.getRowIndex() - 2; i++) {
                Row row = sheet.getRow(i);

                if (!row.getCell(0).toString().isEmpty()) {  //as poi doesnt get blank lines very well.  need to test all lines
                    fundingPaidByProductLines.add(FundingPaidByProductLine.builder()
                            .productCode(
                                    transformCell(() -> CellUtils.getValue(row.getCell(0)),
                                            exceptions, key, row.getRowNum(), "productCode")
                                            .map(m -> (String) m).orElse("")
                            )
                            .productDescription(
                                    transformCell(() -> CellUtils.getValue(row.getCell(1)),
                                            exceptions, key, row.getRowNum(), "productDescription")
                                            .map(m -> (String) m).orElse("")
                            )
                            .startDate(
                                    transformCell(() -> CellUtils.getValue(row.getCell(2)),
                                            exceptions, key, row.getRowNum(), "startDate")
                                            .map(m -> (String) m).orElse("")
                            )
                            .startDate(
                                    transformCell(() -> CellUtils.getValue(row.getCell(3)),
                                            exceptions, key, row.getRowNum(), "startDate")
                                            .map(m -> (String) m).orElse("")
                            )
                            .salesVolume(
                                    transformCell(() -> CellUtils.getValue(row.getCell(4)),
                                            exceptions, key, row.getRowNum(), "salesVolume")
                                            .map(m -> (String) m).orElse("")
                            )
                            .fundingPerUnit(
                                    transformCell(() -> CellUtils.getValue(row.getCell(5)),
                                            exceptions, key, row.getRowNum(), "fundingPerUnit")
                                            .map(m -> (String) m).orElse("")
                            )
                            .totalFundingPaid(
                                    transformCell(() -> CellUtils.getValue(row.getCell(6)),
                                            exceptions, key, row.getRowNum(), "totalFundingPaid")
                                            .map(m -> (String) m).orElse("")
                            )
                            .build());
                }
            }


        } catch (RuntimeException e) {
            exceptions.add(e);
        }


        return fundingPaidByProductLines;
    }

    private Optional<Object> transformCell(Supplier<Object> transformSupplier, List<RuntimeException> exceptions, String key, int row, String value) {
        return TransformExceptionHandler.handle(
                (e) -> exceptions.add(new FundingPaidByProductException(String.format("line %d %s", row, value))),
                transformSupplier, key);
    }
}
