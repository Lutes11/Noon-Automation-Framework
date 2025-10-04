package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ExcelFileManager {
    private XSSFWorkbook workbook;
    private Sheet sheet;

    public ExcelFileManager(String filePath, String sheetName) {
        try {
            // لو الملف جوه resources
            java.io.InputStream is =
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            if (is == null) {
                // fallback لو ممرر مسار مطلق
                is = new java.io.FileInputStream(new java.io.File(filePath));
            }
            workbook = new XSSFWorkbook(is);

            if (sheetName == null || sheetName.isBlank()) {
                sheet = workbook.getNumberOfSheets() > 0 ? workbook.getSheetAt(0) : null;
            } else {
                sheet = workbook.getSheet(sheetName);
                if (sheet == null) {
                    // جرّب case-insensitive
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                            sheet = workbook.getSheetAt(i);
                            break;
                        }
                    }
                }
            }
            if (sheet == null) throw new IllegalArgumentException("Sheet not found: " + sheetName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Excel: " + filePath, e);
        }
    }

    public int getColumnCount() { return sheet.getRow(0).getPhysicalNumberOfCells(); }
    public int getRowCount()     { return sheet.getPhysicalNumberOfRows(); }

    public String getSpecificCellValue(int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(colIndex);
        if (row == null || cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }
}
