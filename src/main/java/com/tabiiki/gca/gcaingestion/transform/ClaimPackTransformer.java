package com.tabiiki.gca.gcaingestion.transform;

import com.tabiiki.gca.gcaingestion.model.ClaimPack;
import com.tabiiki.gca.gcaingestion.model.claim.Claim;
import com.tabiiki.gca.gcaingestion.model.epos.Epos;
import com.tabiiki.gca.gcaingestion.model.epos.EposLine;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
@UtilityClass
public class ClaimPackTransformer {

    private Function<Cell, String> getValue = (cell) -> {
        switch (cell.getCellType()){
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case FORMULA:
                switch (cell.getCachedFormulaResultType()){
                    case NUMERIC:
                        return String.valueOf(cell.getNumericCellValue());
                    case STRING:
                        return cell.getStringCellValue();
                }
            default:
                return ""; //not supported
        }
    };

    public ClaimPack transform(Workbook workbook) {

        return ClaimPack.builder()
                .epos(transformEpos(workbook))
                .claim(transformClaim(workbook))
                .build();
    }

    private Epos transformEpos(Workbook workbook){
        List<EposLine> eposLines = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(1);

        sheet.removeRow(sheet.getRow(0));
        sheet.removeRow(sheet.getRow(1));

        sheet.forEach(
                row -> {
                   if(row.getCell(0) != null) {  //as poi doesnt get blank lines very well.  need to test all lines
                       eposLines.add(EposLine.builder()
                               .supplierNumber(getValue.apply(row.getCell(0)))
                               .supplierName(getValue.apply(row.getCell(1)))
                               .productCode(getValue.apply(row.getCell(2)))
                               .productDescription(getValue.apply(row.getCell(3)))
                               .date(getValue.apply(row.getCell(4)))
                               .totalUnitsSold(getValue.apply(row.getCell(5)))
                               .salesIncVat(getValue.apply(row.getCell(6)))
                               .salesExVat(getValue.apply(row.getCell(7)))
                               .averageSellingPrice(getValue.apply(row.getCell(8)))
                               .returnedUnits(getValue.apply(row.getCell(9)))
                               .returnsIncVat(getValue.apply(row.getCell(10)))
                               .returnsExVat(getValue.apply(row.getCell(11)))
                               .returnsAverageSellingPrice(getValue.apply(row.getCell(12)))
                               .build());
                   }
                }
        );

        return Epos.builder()
                .eposLines(eposLines)
                .build();
    }

    private Claim transformClaim(Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);

        return Claim.builder().build();
    }
}
