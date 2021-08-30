package com.tabiiki.gca.gcaingestion.transform.calculation;

import com.tabiiki.gca.gcaingestion.exception.FundingPaidByInvoiceException;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByInvoice;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingPaidByInvoiceLine;
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
public class FundingPaidByInvoiceTransformer {

    public FundingPaidByInvoice transform(Sheet sheet, List<RuntimeException> exceptions, String key) {


        return FundingPaidByInvoice.builder()
                .totalNet(transformTotalNet(sheet, exceptions, key))
                .totalVat(transformTotalVat(sheet, exceptions, key))
                .totalGross(transformTotalGross(sheet, exceptions, key))
                .fundingPaidByInvoiceLines(transformLines(sheet, exceptions, key))
                .build();
    }

    private String transformTotalNet(Sheet sheet, List<RuntimeException> exceptions, String key) {
        var endCell = CellUtils.locate(sheet, "Funding paid by product (if available)", new FundingPaidByInvoiceException("failed to locate table"));
        var row = sheet.getRow(endCell.getRowIndex() - 3);

        return transformCell(() -> CellUtils.getValue(row.getCell(3)),
                exceptions, key, row.getRowNum(), "totalNet")
                .map(m -> (String) m).orElse("");
    }

    private String transformTotalVat(Sheet sheet, List<RuntimeException> exceptions, String key) {
        var endCell = CellUtils.locate(sheet, "Funding paid by product (if available)", new FundingPaidByInvoiceException("failed to locate table"));
        var row = sheet.getRow(endCell.getRowIndex() - 3);

        return transformCell(() -> CellUtils.getValue(row.getCell(4)),
                exceptions, key, row.getRowNum(), "totalVat")
                .map(m -> (String) m).orElse("");
    }

    private String transformTotalGross(Sheet sheet, List<RuntimeException> exceptions, String key) {
        var endCell = CellUtils.locate(sheet, "Funding paid by product (if available)", new FundingPaidByInvoiceException("failed to locate table"));
        var row = sheet.getRow(endCell.getRowIndex() - 3);

        return transformCell(() -> CellUtils.getValue(row.getCell(5)),
                exceptions, key, row.getRowNum(), "totalGross")
                .map(m -> (String) m).orElse("");
    }

    private List<FundingPaidByInvoiceLine> transformLines(Sheet sheet, List<RuntimeException> exceptions, String key) {
        List<FundingPaidByInvoiceLine> fundingPaidByInvoiceLines = new ArrayList<>();

        try {

            var startCell = CellUtils.locate(sheet, "Funding Paid by invoice", new FundingPaidByInvoiceException("failed to locate table"));
            var endCell = CellUtils.locate(sheet, "Funding paid by product (if available)", new FundingPaidByInvoiceException("failed to locate table"));

            for (int i = startCell.getRowIndex() + 3; i < endCell.getRowIndex() - 3; i++) {
                Row row = sheet.getRow(i);

                if (!row.getCell(0).toString().isEmpty()) {  //as poi doesnt get blank lines very well.  need to test all lines
                    fundingPaidByInvoiceLines.add(FundingPaidByInvoiceLine.builder()
                            .invoiceNumber(
                                    transformCell(() -> CellUtils.getValue(row.getCell(0)),
                                            exceptions, key, row.getRowNum(), "invoiceNumber")
                                            .map(m -> (String) m).orElse("")
                            )
                            .invoiceDate(
                                    transformCell(() -> CellUtils.getValue(row.getCell(1)),
                                            exceptions, key, row.getRowNum(), "invoiceDate")
                                            .map(m -> (String) m).orElse("")
                            )
                            .invoiceDescription(
                                    transformCell(() -> CellUtils.getValue(row.getCell(2)),
                                            exceptions, key, row.getRowNum(), "invoiceDescription")
                                            .map(m -> (String) m).orElse("")
                            )
                            .netAmount(
                                    transformCell(() -> CellUtils.getValue(row.getCell(3)),
                                            exceptions, key, row.getRowNum(), "netAmount")
                                            .map(m -> (String) m).orElse("")
                            )
                            .vat(
                                    transformCell(() -> CellUtils.getValue(row.getCell(4)),
                                            exceptions, key, row.getRowNum(), "vat")
                                            .map(m -> (String) m).orElse("")
                            )
                            .gross(
                                    transformCell(() -> CellUtils.getValue(row.getCell(5)),
                                            exceptions, key, row.getRowNum(), "gross")
                                            .map(m -> (String) m).orElse("")
                            )
                            .build());
                }
            }


        } catch (RuntimeException e) {
            exceptions.add(e);
        }

        return fundingPaidByInvoiceLines;
    }


    private Optional<Object> transformCell(Supplier<Object> transformSupplier, List<RuntimeException> exceptions, String key, int row, String value) {
        return TransformExceptionHandler.handle(
                (e) -> exceptions.add(new FundingPaidByInvoiceException(String.format("line %d %s", row, value))),
                transformSupplier, key);
    }
}
