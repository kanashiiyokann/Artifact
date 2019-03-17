package artifact;


import artifact.modules.common.util.ExcelUtil;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConsoleTest {

    private static Pattern p = Pattern.compile("\\S+");

    public static void main(String[] args) throws Exception {

        test();
    }


    public static void test() throws Exception {
        String path = "C:\\Users\\yayi\\Desktop\\text1.xls";

        ExcelUtil excelUtil = new ExcelUtil();

        CellStyle style_center = excelUtil.createStyle();
        style_center.setAlignment(HorizontalAlignment.CENTER);

        CellStyle style_right = excelUtil.createStyle();
        style_right.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle style_header = excelUtil.createStyle();
        Short color_ashy = excelUtil.getColor(192, 192, 192);
        Short color_black = excelUtil.getColor(0, 0, 0);

        style_header.setAlignment(HorizontalAlignment.CENTER);
        style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style_header.setFillForegroundColor(color_ashy);
        style_header.setBorderBottom(BorderStyle.THIN);
        style_header.setBorderTop(BorderStyle.THIN);
        style_header.setBorderRight(BorderStyle.THIN);
        style_header.setBorderLeft(BorderStyle.THIN);

        style_header.setBottomBorderColor(color_black);
        style_header.setTopBorderColor(color_black);
        style_header.setLeftBorderColor(color_black);
        style_header.setRightBorderColor(color_black);


        CellStyle style = excelUtil.createStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBottomBorderColor(color_black);
        style.setTopBorderColor(color_black);
        style.setLeftBorderColor(color_black);
        style.setRightBorderColor(color_black);


        excelUtil.nextSheet(16, 16, 16, 16).nextRow()
                .nextCell("凭证汇总表", 4, 1, style_center).nextRow()
                .nextCell("凭证总张数：12张  附件总张数：11张", 4, 1, style_right).nextRow()
                .nextCell("1102测试账套", 2, 1).nextCell("2018-06-01 至 2018-06-30", 2, 1, style_right).nextRow()
                .setStyle(style_header).nextCell("科目编码", "科目名称", "借方余额", "贷方余额").nextRow()
                .setStyle(style).nextCell("1001", "kemumingcheng", "1111", "22222")
                .fetch(path);

    }


    public static void test1() {


        String str = "id desc nullLast";

        Matcher matcher = p.matcher(str);
        while (matcher.find()) {

            System.out.println(matcher.group(0));
        }


    }


}
