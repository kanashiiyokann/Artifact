package artifact.modules.common.util;

import org.apache.poi.hssf.record.PaletteRecord;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class ExcelUtil {

    private HSSFWorkbook wb;
    private Sheet currentSheet;
    private Row currentRow;
    private Integer rowIndex;
    private Integer colIndex;
    private Short colorIndex = 8;
    private CellStyle style;

    public ExcelUtil() {
        wb = new HSSFWorkbook();
        rowIndex = -1;
        colIndex = -1;
    }

    public ExcelUtil prepare() {
        nextSheet();
        return nextRow();
    }

    public ExcelUtil nextSheet(Integer... widths){
        nextSheet();
        for(int i=0;i<widths.length;i++) {
            currentSheet.setColumnWidth(i,widths[i]*256);
        }
        return this;
    }
    public ExcelUtil nextRow() {
        currentRow = currentSheet.createRow(++rowIndex);
        colIndex = -1;
        return this;
    }

    public void nextSheet(String sheetName) {
        currentSheet = wb.createSheet(sheetName);
    }

    public ExcelUtil nextSheet() {
        currentSheet = wb.createSheet();
        rowIndex = -1;
        colIndex = -1;
        return this;
    }

    public ExcelUtil nextCell(Object value, Integer colspan, Integer rowspan, CellStyle style) throws RuntimeException {

        Cell cell = currentRow.createCell(++colIndex);
        cell.setCellValue(String.valueOf(value));
        style = style == null ? this.style : style;
        if (style != null) {
            cell.setCellStyle(style);
        }
        try {
            colspan--;
            rowspan--;
            if (colspan < 0) {
                String message = "col span should greater than 0;";
                throw new IllegalArgumentException(message);
            }
            if (rowspan < 0) {
                String message = "row span should greater than 0;";
                throw new IllegalArgumentException(message);
            }
            if (colspan != 0 || rowspan != 0) {
                CellRangeAddress region = new CellRangeAddress(rowIndex, rowIndex + rowspan, colIndex, colIndex + colspan);
                currentSheet.addMergedRegion(region);
                rowIndex += rowspan;
                colIndex += colspan;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public ExcelUtil nextCell(Object value, Integer colspan, Integer rowspan) throws RuntimeException {
        return nextCell(value, colspan, rowspan, null);
    }


    public ExcelUtil nextCell(String value) {

        Cell cell = currentRow.createCell(++colIndex);
        if (this.style != null) {
            cell.setCellStyle(this.style);
        }
        if (value != null) {
            cell.setCellValue(value);
        }

        return this;
    }

    public ExcelUtil nextCell(String... values) {

        for (String value : values) {
            nextCell(value);
        }

        return this;
    }

    public Short getColor(int red, int green, int blue) throws RuntimeException {
        int[] colors = new int[]{red, green, blue};
        for (int color : colors) {
            if (color > 255 || color < 0) {
                throw new IllegalArgumentException("out of the color range:0~255 .");
            }
        }

        HSSFPalette palette = wb.getCustomPalette();
        palette.setColorAtIndex(colorIndex, (byte) red, (byte) green, (byte) blue);
        Short ret = colorIndex;
        colIndex++;
        return ret;
    }


    public CellStyle createStyle() {
        return wb.createCellStyle();
    }

    public ExcelUtil clearStyle() {
        this.style = null;
        return this;
    }

    public ExcelUtil setStyle(CellStyle style) {
        this.style = style;
        return this;
    }

    public void fetch(String path) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        // 写入在输出流
        wb.write(fileOutputStream);
        // 关闭输出流
        fileOutputStream.close();
    }

//    private CellStyle getStyle(Style style) {
//
//        CellStyle cs = wb.createCellStyle();
//        cs.setAlignment(style.getHorizontalAlign());
//        cs.setVerticalAlignment(style.getVerticalAlign());
//        cs.setFillBackgroundColor();
//        return cs;
//    }

//
//    class Style implements StyleConstant {
//        private VerticalAlignment verticalAlign = VERTICAL_BOTTOM;
//        private HorizontalAlignment horizontalAlign = HORIZONTAL_LEFT;
//        private Object BorderColor;
//        private Object Color;
//        private Object BackgroudColor;
//
//
//        public VerticalAlignment getVerticalAlign() {
//            return verticalAlign;
//        }
//
//        public void setVerticalAlign(VerticalAlignment verticalAlign) {
//            this.verticalAlign = verticalAlign;
//        }
//
//        public HorizontalAlignment getHorizontalAlign() {
//            return horizontalAlign;
//        }
//
//        public void setHorizontalAlign(HorizontalAlignment horizontalAlign) {
//            this.horizontalAlign = horizontalAlign;
//        }
//    }
//
//
//    interface StyleConstant {
//        VerticalAlignment VERTICAL_CENTER = VerticalAlignment.CENTER;
//        VerticalAlignment VERTICAL_TOP = VerticalAlignment.TOP;
//        VerticalAlignment VERTICAL_BOTTOM = VerticalAlignment.BOTTOM;
//        HorizontalAlignment HORIZONTAL_RIGHT = HorizontalAlignment.RIGHT;
//        HorizontalAlignment HORIZONTAL_LEFT = HorizontalAlignment.LEFT;
//        HorizontalAlignment HORIZONTAL_CENTER = HorizontalAlignment.CENTER;
//    }
}
