package com.tabiiki.gca.gcaingestion.transform.epos;

import com.tabiiki.gca.gcaingestion.exception.EposException;
import com.tabiiki.gca.gcaingestion.model.epos.Epos;
import com.tabiiki.gca.gcaingestion.model.epos.EposLine;
import com.tabiiki.gca.gcaingestion.util.CellUtils;
import com.tabiiki.gca.gcaingestion.util.TransformExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class EposTransformer {

    public Epos transform(Workbook workbook, List<RuntimeException> exceptions, String key) {
        Sheet sheet = workbook.getSheetAt(1);

        sheet.removeRow(sheet.getRow(0));
        sheet.removeRow(sheet.getRow(1));

        var from = TransformExceptionHandler.handle(exceptions::add, () -> transformFrom(sheet), key);
        var to = TransformExceptionHandler.handle(exceptions::add, () -> transformTo(sheet), key);
        var eposLines = transformLines(sheet, exceptions, key);


        return Epos.builder()
                .from(from.map(m -> (String) m).orElse(""))
                .to(to.map(m -> (String) m).orElse(""))
                .eposLines(eposLines)
                .build();
    }

    private List<EposLine> transformLines(Sheet sheet, List<RuntimeException> exceptions, String key) {
        List<EposLine> eposLines = new ArrayList<>();

        sheet.forEach(
                row -> {
                    if (row.getCell(0) != null) {  //as poi doesnt get blank lines very well.  need to test all lines
                        eposLines.add(EposLine.builder()
                                .supplierNumber(
                                        transformCell(() -> CellUtils.getValue(row.getCell(0)),
                                                exceptions, key, row.getRowNum(), "supplierNumber")
                                                .map(m -> (String) m).orElse("")
                                )
                                .supplierName(
                                        transformCell(() -> CellUtils.getValue(row.getCell(1)),
                                                exceptions, key, row.getRowNum(), "supplierName")
                                                .map(m -> (String) m).orElse("")
                                )
                                .productCode(
                                        transformCell(() -> CellUtils.getValue(row.getCell(2)),
                                                exceptions, key, row.getRowNum(), "productCode")
                                                .map(m -> (String) m).orElse("")
                                )
                                .productDescription(
                                        transformCell(() -> CellUtils.getValue(row.getCell(3)),
                                                exceptions, key, row.getRowNum(), "productDescription")
                                                .map(m -> (String) m).orElse("")
                                )
                                .date(
                                        transformCell(() -> CellUtils.getValue(row.getCell(4)),
                                                exceptions, key, row.getRowNum(), "date")
                                                .map(m -> (String) m).orElse("")
                                )
                                .totalUnitsSold(
                                        transformCell(() -> CellUtils.getValue(row.getCell(5)),
                                                exceptions, key, row.getRowNum(), "totalUnitsSold")
                                                .map(m -> (String) m).orElse("")
                                )
                                .salesIncVat(
                                        transformCell(() -> CellUtils.getValue(row.getCell(6)),
                                                exceptions, key, row.getRowNum(), "salesIncVat")
                                                .map(m -> (String) m).orElse("")
                                )
                                .salesExVat(
                                        transformCell(() -> CellUtils.getValue(row.getCell(7)),
                                                exceptions, key, row.getRowNum(), "salesExVat")
                                                .map(m -> (String) m).orElse("")
                                )
                                .averageSellingPrice(
                                        transformCell(() -> CellUtils.getValue(row.getCell(8)),
                                                exceptions, key, row.getRowNum(), "averageSellingPrice")
                                                .map(m -> (String) m).orElse("")
                                )
                                .returnedUnits(
                                        transformCell(() -> CellUtils.getValue(row.getCell(9)),
                                                exceptions, key, row.getRowNum(), "returnedUnits")
                                                .map(m -> (String) m).orElse("")
                                )
                                .returnsIncVat(
                                        transformCell(() -> CellUtils.getValue(row.getCell(10)),
                                                exceptions, key, row.getRowNum(), "returnsIncVat")
                                                .map(m -> (String) m).orElse("")
                                )
                                .returnsExVat(
                                        transformCell(() -> CellUtils.getValue(row.getCell(11)),
                                                exceptions, key, row.getRowNum(), "returnsExVat")
                                                .map(m -> (String) m).orElse("")
                                )
                                .returnsAverageSellingPrice(
                                        transformCell(() -> CellUtils.getValue(row.getCell(12)),
                                                exceptions, key, row.getRowNum(), "returnsAverageSellingPrice")
                                                .map(m -> (String) m).orElse("")
                                )
                                .build());
                    }
                }
        );

        return eposLines;
    }

    private Optional<Object> transformCell(Supplier<Object> transformSupplier, List<RuntimeException> exceptions, String key, int row, String value) {
        return TransformExceptionHandler.handle(
                (e) -> exceptions.add(new EposException(String.format("line %d %s", row, value))),
                transformSupplier, key);
    }

    private String transformFrom(Sheet sheet) throws EposException {
        try {
            return "";
        } catch (Exception e) {
            throw new EposException("from");
        }
    }

    private String transformTo(Sheet sheet) throws EposException {
        try {
            return "";
        } catch (Exception e) {
            throw new EposException("to");
        }
    }
}
