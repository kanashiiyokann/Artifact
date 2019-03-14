package artifact.modules.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.IOException;

public class PoiUtil {

    private Sheet currentSheet;
    private Row currentRow;
    private Integer rowIndex;
    private Integer colIndex;


    public void excel(String path) throws Exception {

        Workbook wb = new HSSFWorkbook();
        currentSheet=    wb.createSheet("Sheet1");

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        wb.write(fileOutputStream);
        fileOutputStream.close();
    }

    public void test1(String path) throws IOException {
        //定义一个工作蒲
        Workbook wb = new HSSFWorkbook();
        // 创建sheet页面
        Sheet sheet = wb.createSheet("第一个sheet");
        // 创建一行
        Row row = sheet.createRow(1);
        //设置行高
       // row.setHeightInPoints(30);
        // 创建一个单元格
        Cell cell = row.createCell(1);
        cell.setCellValue("合并单元格");
        // 合并单元格（起始行,结束行,起始列,结束列）
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 2));
        // 定义一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        // 写入在输出流
        wb.write(fileOutputStream);
        // 关闭输出流
        fileOutputStream.close();
    }

    public  void generateHeader(){



    }
    public void nextRow(){
        currentRow=currentSheet.createRow(++rowIndex);
    }

    public void AddCell(Object value,Integer colspan,Integer rowspan,Style style){}


    class Style{}


    interface StyleConstant{

    }
}
