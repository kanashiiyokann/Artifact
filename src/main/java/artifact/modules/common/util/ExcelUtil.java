package artifact.modules.common.util;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {

    private HSSFWorkbook wb;
    private Sheet currentSheet;
    private Row currentRow;
    private Integer rowIndex;
    private Integer colIndex;
    private Short colorIndex = 8;
    private Style style;
    private Map<String, Short> colorPalette = new HashMap<>();

    public ExcelUtil() {
        wb = new HSSFWorkbook();
        rowIndex = -1;
        colIndex = -1;
    }

    public ExcelUtil prepare() {
        nextSheet();
        return nextRow();
    }

    public ExcelUtil nextSheet(Integer... widths) {
        nextSheet();
        for (int i = 0; i < widths.length; i++) {
            currentSheet.setColumnWidth(i, widths[i] * 256);
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

    public ExcelUtil nextCell(Object value, Integer colspan, Integer rowspan, Style style) throws RuntimeException {

        Cell cell = currentRow.createCell(++colIndex);
        cell.setCellValue(String.valueOf(value));
        style = style == null ? this.style : style;
        if (style != null) {
            cell.setCellStyle(style.cellStyle);
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
            cell.setCellStyle(this.style.cellStyle);
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

    private Short getColor(int red, int green, int blue) throws RuntimeException {
        int[] colors = new int[]{red, green, blue};
        for (int color : colors) {
            if (color > 255 || color < 0) {
                throw new IllegalArgumentException("out of the color range:0~255 .");
            }
        }
        String key = String.format("%s,%s,%s", red, green, blue);
        Short color = colorPalette.get(key);
        if (color != null) {
            return color;
        }

        HSSFPalette palette = wb.getCustomPalette();
        palette.setColorAtIndex(colorIndex, (byte) red, (byte) green, (byte) blue);
        color = colorIndex;
        colorPalette.put(key, color);
        this.colorIndex = (short) (this.colorIndex + 1);
        return color;
    }


    public Style createStyle() {
        Style style = new Style();
        style.cellStyle = wb.createCellStyle();
        style.instance = this;
        return style;
    }

    public ExcelUtil clearStyle() {
        this.style = null;
        return this;
    }

    public ExcelUtil setStyle(Style style) {
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

    private static Map<Integer, VerticalAlignment> align_vertical = new HashMap<Integer, VerticalAlignment>(3) {{
        put(-1, VerticalAlignment.TOP);
        put(0, VerticalAlignment.CENTER);
        put(1, VerticalAlignment.BOTTOM);

    }};

    private static Map<Integer, HorizontalAlignment> align_horizontal = new HashMap<Integer, HorizontalAlignment>(3) {{
        put(-1, HorizontalAlignment.LEFT);
        put(1, HorizontalAlignment.RIGHT);
        put(0, HorizontalAlignment.CENTER);

    }};

    private static Map<Integer, BorderStyle> border_style = new HashMap<Integer, BorderStyle>(3) {{
        put(1, BorderStyle.THIN);
    }};

    public class Style implements StyleConstant {
        private CellStyle cellStyle;
        private ExcelUtil instance;

        private Style() {
        }

        public void setVerticalAlign(Integer algin) {
            if (algin == null || algin > 1 || algin < -1) {
                throw new IllegalArgumentException("the align should between -1 and 1 !");
            }
            VerticalAlignment verticalAlignment = ExcelUtil.align_vertical.get(algin);
            this.cellStyle.setVerticalAlignment(verticalAlignment);
        }

        public void setHorizontalAlign(Integer algin) {
            if (algin == null || algin > 1 || algin < -1) {
                throw new IllegalArgumentException("the align should between -1 and 1 !");
            }
            HorizontalAlignment horizontalAlign = ExcelUtil.align_horizontal.get(algin);
            this.cellStyle.setAlignment(horizontalAlign);
        }

        public void setBackgroundColor(int red, int green, int blue) {
            Short color = instance.getColor(red, green, blue);
            this.cellStyle.setFillForegroundColor(color);
            this.cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        public void setBorderColor(int red, int green, int blue) {
            Short color = instance.getColor(red, green, blue);
            this.cellStyle.setBottomBorderColor(color);
            this.cellStyle.setTopBorderColor(color);
            this.cellStyle.setLeftBorderColor(color);
            this.cellStyle.setRightBorderColor(color);

            this.cellStyle.setBorderBottom(BorderStyle.THIN);
            this.cellStyle.setBorderTop(BorderStyle.THIN);
            this.cellStyle.setBorderRight(BorderStyle.THIN);
            this.cellStyle.setBorderLeft(BorderStyle.THIN);
        }

        public void setBorder(Integer border) {
            if (border == null || border != 1) {
                throw new IllegalArgumentException("the border style only support 1 now !");
            }

            BorderStyle style = ExcelUtil.border_style.get(border);
            this.cellStyle.setBorderBottom(style);
            this.cellStyle.setBorderTop(style);
            this.cellStyle.setBorderRight(style);
            this.cellStyle.setBorderLeft(style);

        }


    }

    public interface StyleConstant {
        Integer ALIGNMENT_TOP = -1;
        Integer ALIGNMENT_CENTER = 0;
        Integer ALIGNMENT_BOTTOM = 1;
        Integer ALIGNMENT_LEFT = -1;
        Integer ALIGNMENT_RIGHT = 1;
        Integer BORDER_THIN = 1;

    }
}
