package com.tabiiki.gca.gcaingestion.util;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


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

    public String search(Sheet sheet, String value, RuntimeException exception) {
        try {
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (getValue(cell).trim().equalsIgnoreCase(value)) {
                        return getValue(row.getCell(cell.getColumnIndex() + 1));
                    }
                }
            }
            throw exception;
        } catch (Exception e) {
            throw exception;
        }

    }

    public Cell locate(Sheet sheet, String key, RuntimeException exception) {
        try {
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (getValue(cell).trim().equalsIgnoreCase(key)) {
                        return cell;
                    }
                }
            }
            throw exception;
        } catch (Exception e) {
            throw exception;
        }
    }
}
