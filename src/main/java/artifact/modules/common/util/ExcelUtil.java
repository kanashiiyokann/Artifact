package artifact.modules.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtil {

    private Workbook wb;
    private Sheet currentSheet;
    private Row currentRow;
    private Integer rowIndex;
    private Integer colIndex;


    public ExcelUtil prepare() {
        wb = new HSSFWorkbook();
        rowIndex = -1;
        colIndex = -1;
        return this;
    }

    public ExcelUtil nextRow() {
        currentRow = currentSheet.createRow(++rowIndex);
        return this;
    }

    public void nextSheet(String sheetName) {
        currentSheet = wb.createSheet(sheetName);
    }

    public ExcelUtil nextSheet() {
        currentSheet = wb.createSheet();
        return this;
    }


    public void nextCell(Object value, Integer colspan, Integer rowspan, Style style) {


    }


    public ExcelUtil nextCell(Object... values) {
        for (Object value : values) {
            Cell cell = currentRow.createCell(++colIndex);
            cell.setCellValue(String.valueOf(value));
        }

        return this;
    }

    public void fetch(String path) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        // 写入在输出流
        wb.write(fileOutputStream);
        // 关闭输出流
        fileOutputStream.close();
    }


    class Style {
    }


    interface StyleConstant {

    }
}
