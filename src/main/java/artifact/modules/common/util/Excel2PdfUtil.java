package artifact.modules.common.util;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Excel2PdfUtil {

    private HSSFWorkbook wb;
    private Document document;
    private Font defaultFont;
    private HSSFSheet currentSheet;

    public void load(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            wb = new HSSFWorkbook(fis);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        document = new Document(PageSize.A4);
    }

    public void load(HSSFWorkbook wb) {
        this.wb = wb;
    }


    private PdfPTable handleSheet(HSSFSheet sheet) {
        if (sheet == null) {
            throw new RuntimeException("sheet should not be null !");
        }
        int tableColNum = getMaxColumnNumber(sheet);
        currentSheet = sheet;
        PdfPTable table = new PdfPTable(tableColNum + 1);

        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            for (int j = 0; j < tableColNum; j++) {
                HSSFCell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                if (cell == null) {
                    table.addCell("");
                } else {
                    table.addCell(handleCell(cell));

                }
            }

            table.addCell(getDefaultCell());
            table.completeRow();

        }
        //列宽
        float[] widths = new float[tableColNum + 1];
        for (int i = 0; i < tableColNum; i++) {
            widths[i] = sheet.getColumnWidth(i);
        }
        widths[tableColNum] = 0.01f;
        try {
            table.setWidths(widths);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //单元格合并
        List<CellRangeAddress> mergedList = sheet.getMergedRegions();

        for (CellRangeAddress merged : mergedList) {
            int firstColumn = merged.getFirstColumn(), firstRow = merged.getFirstRow(), lastColumn = merged.getLastColumn(), lastRow = merged.getLastRow();
            PdfPCell cell = table.getRow(firstRow).getCells()[firstColumn];
            cell.setColspan(lastColumn - firstColumn + 1);
            cell.setRowspan(lastRow - firstRow + 1);
            sheet.getRow(firstRow).getCell(firstColumn);
        }
        //重复边框处理
        for (int i = 0; i < table.getRows().size(); i++) {
            PdfPRow row = table.getRow(i);
            for (int j = 0; j < row.getCells().length - 1; j++) {//多一列空白列
                PdfPCell cell = row.getCells()[j];


            }
        }


        return table;
    }

    private PdfPCell handleCell(HSSFCell cell) {

        String value = cell.toString();
        PdfPCell ret = getDefaultCell();
        ret.setPhrase(new Paragraph(value, getDefaultFont()));

        handleStyle(cell, ret);

        //设置最小单元格高度
        ret.setMinimumHeight(20);
        return ret;

    }


    private void handleStyle(HSSFCell cell, PdfPCell tcell) {
        CellStyle style = cell.getCellStyle();
        //水平对齐
        HorizontalAlignment horizontal = style.getAlignmentEnum();
        switch (horizontal) {
            case RIGHT:
                tcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                break;
            case CENTER:
                tcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                break;
            default:
                break;

        }
        //垂直对齐
        VerticalAlignment vertical = style.getVerticalAlignmentEnum();
        switch (vertical) {
            case TOP:
                tcell.setVerticalAlignment(Element.ALIGN_TOP);
                break;
            case CENTER:
                tcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            default:
                break;
        }

        //背景色
        HSSFColor hssfColor = wb.getCustomPalette().getColor(style.getFillForegroundColor());
        BaseColor color = handleColor(hssfColor);
        if (color != null) {
            tcell.setBackgroundColor(handleColor(hssfColor));
        }

        //边框
        handleBorder(cell, tcell);

    }

    private PdfPCell getDefaultCell() {
        PdfPCell ret = new PdfPCell();

        ret.setVerticalAlignment(Element.ALIGN_BOTTOM);
        ret.setHorizontalAlignment(Element.ALIGN_LEFT);
        ret.setBorder(Rectangle.NO_BORDER);
        return ret;
    }

    /**
     * 获取sheet最大列数
     *
     * @param sheet
     * @return
     */
    private int getMaxColumnNumber(HSSFSheet sheet) {
        int tableColNum = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            int colNum = row.getLastCellNum();
            tableColNum = tableColNum < colNum ? colNum : tableColNum;
        }
        return tableColNum;
    }

    private BaseColor handleColor(HSSFColor color) {
        short[] triplet = color.getTriplet();
        //过滤默认黑色
        int count = 0;
        for (int i = 0; i < triplet.length; i++) {
            count += triplet[i];
        }
        if (count == 0) {
            return null;
        }
        return new BaseColor(triplet[0], triplet[1], triplet[2], 255);
    }

    private void handleBorder(HSSFCell cell, PdfPCell tcell) {
        CellStyle style = cell.getCellStyle();

        boolean topNecessary = true, leftNecessary = true;
        HSSFCell compare;
        int i = cell.getRowIndex();
        int j = cell.getColumnIndex();
        if (i > 1) {
            compare = currentSheet.getRow(i - 1).getCell(j);
            topNecessary = compare.getCellStyle().getBorderBottomEnum().equals(BorderStyle.NONE);
        }
        if (j > 1) {
            compare = currentSheet.getRow(i).getCell(j - 1);
            leftNecessary = compare.getCellStyle().getBorderRightEnum().equals(BorderStyle.NONE);
        }

        BorderStyle borderStyle;

        List<Integer> borderList = new ArrayList<>(4);

        //头部
        borderStyle = style.getBorderTopEnum();
        if (borderStyle != BorderStyle.NONE && topNecessary) {
            borderList.add(Rectangle.TOP);
            tcell.setBorderColor(handleColor(wb.getCustomPalette().getColor(style.getTopBorderColor())));
        }
        //右边
        borderStyle = style.getBorderRightEnum();
        if (borderStyle != BorderStyle.NONE) {
            borderList.add(Rectangle.RIGHT);
            tcell.setBorderColor(handleColor(wb.getCustomPalette().getColor(style.getRightBorderColor())));
        }
        //底部
        borderStyle = style.getBorderBottomEnum();
        if (borderStyle != BorderStyle.NONE) {
            borderList.add(Rectangle.BOTTOM);
            tcell.setBorderColor(handleColor(wb.getCustomPalette().getColor(style.getBottomBorderColor())));
        }
        //左边
        borderStyle = style.getBorderLeftEnum();
        if (borderStyle != BorderStyle.NONE && leftNecessary) {
            borderList.add(Rectangle.LEFT);
            tcell.setBorderColor(handleColor(wb.getCustomPalette().getColor(style.getLeftBorderColor())));
        }
        Integer borders = 0;
        for (Integer border : borderList) {
            borders += border;
        }
        tcell.setBorder(borders);

    }

    public void fetch(String path) throws Exception {
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        PdfPTable table = handleSheet(wb.getSheetAt(0));
        document.open();
        document.add(table);
        document.close();

        writer.close();

    }

    /**
     * 获取默认字体样式
     *
     * @return
     */
    private Font getDefaultFont() {
        if (defaultFont == null) {
            try {
                BaseFont baseFont = BaseFont.createFont("src/main/resources/msyh.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                defaultFont = new Font(baseFont, 12, Font.NORMAL);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        return defaultFont;
    }

}
