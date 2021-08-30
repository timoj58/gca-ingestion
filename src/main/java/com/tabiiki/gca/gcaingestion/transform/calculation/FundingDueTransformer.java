package com.tabiiki.gca.gcaingestion.transform.calculation;

import com.tabiiki.gca.gcaingestion.exception.FundingDueException;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingDue;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingDueLine;
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
public class FundingDueTransformer {
    public FundingDue transform(Sheet sheet, List<RuntimeException> exceptions, String key) {

        var claimTotal = TransformExceptionHandler.handle(exceptions::add, () -> transformClaimTotal(sheet), key);
        var lessFundingClaimed = TransformExceptionHandler.handle(exceptions::add, () -> transformLessFundingClaimed(sheet), key);
        var totalFundingDue = TransformExceptionHandler.handle(exceptions::add, () -> transformTotalFundingDue(sheet), key);

        return FundingDue.builder()
                .claimTotal(claimTotal.map(m -> (String) m).orElse(""))
                .lessFundingClaimed(lessFundingClaimed.map(m -> (String) m).orElse(""))
                .totalFundingDue(totalFundingDue.map(m -> (String) m).orElse(""))
                .fundingDues(transformLines(sheet, exceptions, key))
                .build();

    }


    private String transformClaimTotal(Sheet sheet) throws FundingDueException {
        try {
            return "";
        } catch (Exception e) {
            throw new FundingDueException("claimTotal");
        }
    }


    private String transformLessFundingClaimed(Sheet sheet) throws FundingDueException {
        try {
            return "";
        } catch (Exception e) {
            throw new FundingDueException("lessFundingClaimed");
        }
    }


    private String transformTotalFundingDue(Sheet sheet) throws FundingDueException {
        try {
            return "";
        } catch (Exception e) {
            throw new FundingDueException("totalFundingDue");
        }
    }

    private List<FundingDueLine> transformLines(Sheet sheet, List<RuntimeException> exceptions, String key) {
        List<FundingDueLine> fundingDueLines = new ArrayList<>();

        try {

            var startCell = CellUtils.locate(sheet, "Funding Due:", new FundingDueException("failed to locate table"));
            var endCell = CellUtils.locate(sheet, "Funding Paid by invoice", new FundingDueException("failed to locate table"));

            for (int i = startCell.getRowIndex() + 3; i < endCell.getRowIndex() - 5; i++) {
                Row row = sheet.getRow(i);

                if (!row.getCell(0).toString().isEmpty()) {  //as poi doesnt get blank lines very well.  need to test all lines
                    fundingDueLines.add(FundingDueLine.builder()
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
                            .totalFundingDue(
                                    transformCell(() -> CellUtils.getValue(row.getCell(6)),
                                            exceptions, key, row.getRowNum(), "totalFundingDue")
                                            .map(m -> (String) m).orElse("")
                            )
                            .build());
                }
            }


        } catch (RuntimeException e) {
            exceptions.add(e);
        }

        return fundingDueLines;
    }

    private Optional<Object> transformCell(Supplier<Object> transformSupplier, List<RuntimeException> exceptions, String key, int row, String value) {
        return TransformExceptionHandler.handle(
                (e) -> exceptions.add(new FundingDueException(String.format("line %d %s", row, value))),
                transformSupplier, key);
    }

}
