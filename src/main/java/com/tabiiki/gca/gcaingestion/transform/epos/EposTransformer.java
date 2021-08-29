package com.tabiiki.gca.gcaingestion.transform.epos;

import com.tabiiki.gca.gcaingestion.model.epos.Epos;
import com.tabiiki.gca.gcaingestion.model.epos.EposLine;
import com.tabiiki.gca.gcaingestion.util.CellUtils;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class EposTransformer {

    public Epos transform(Workbook workbook) {
        List<EposLine> eposLines = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(1);

        sheet.removeRow(sheet.getRow(0));
        sheet.removeRow(sheet.getRow(1));

        sheet.forEach(
                row -> {
                    if (row.getCell(0) != null) {  //as poi doesnt get blank lines very well.  need to test all lines
                        eposLines.add(EposLine.builder()
                                .supplierNumber(CellUtils.getValue(row.getCell(0)))
                                .supplierName(CellUtils.getValue(row.getCell(1)))
                                .productCode(CellUtils.getValue(row.getCell(2)))
                                .productDescription(CellUtils.getValue(row.getCell(3)))
                                .date(CellUtils.getValue(row.getCell(4)))
                                .totalUnitsSold(CellUtils.getValue(row.getCell(5)))
                                .salesIncVat(CellUtils.getValue(row.getCell(6)))
                                .salesExVat(CellUtils.getValue(row.getCell(7)))
                                .averageSellingPrice(CellUtils.getValue(row.getCell(8)))
                                .returnedUnits(CellUtils.getValue(row.getCell(9)))
                                .returnsIncVat(CellUtils.getValue(row.getCell(10)))
                                .returnsExVat(CellUtils.getValue(row.getCell(11)))
                                .returnsAverageSellingPrice(CellUtils.getValue(row.getCell(12)))
                                .build());
                    }
                }
        );

        return Epos.builder()
                .eposLines(eposLines)
                .build();
    }
}
