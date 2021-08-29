package com.tabiiki.gca.gcaingestion.util;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;


@UtilityClass
public class CellUtils {

    public String getValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case NUMERIC:
                        return String.valueOf(cell.getNumericCellValue());
                    case STRING:
                        return cell.getStringCellValue();
                }
            default:
                return ""; //not supported
        }
    }
}
