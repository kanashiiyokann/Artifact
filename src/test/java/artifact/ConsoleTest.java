package artifact;


import artifact.modules.common.util.ExcelUtil;
import artifact.modules.common.util.ExcelUtil.Style;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConsoleTest {

    private static Pattern p = Pattern.compile("\\S+");

    public static void main(String[] args) throws Exception {

        test();
    }


    public static void test() throws Exception {
        String path = "C:\\Users\\DGG-S27-D-20\\Desktop\\text1.xls";

        ExcelUtil excelUtil = new ExcelUtil();


        excelUtil.nextSheet(16, 16, 16, 16).nextRow()
                .nextCell("凭证汇总表", 1, 3).nextRow()
                .nextCell("凭证总张数",2,2).nextRow()
                .nextCell("1102测试账套", 2, 1).nextCell("2018-06-01 至 2018-06-30", 2, 1).nextRow()
                .fetch(path);

    }

    public static void test2() throws Exception {
        String path = "C:\\Users\\DGG-S27-D-20\\Desktop\\text1.xls";

        ExcelUtil excelUtil = new ExcelUtil();

        Style style_center = excelUtil.createStyle();
        style_center.setHorizontalAlign(Style.ALIGNMENT_CENTER);

        Style style_right = excelUtil.createStyle();
        style_right.setHorizontalAlign(Style.ALIGNMENT_RIGHT);

        Style style_header = excelUtil.createStyle();


        style_header.setHorizontalAlign(Style.ALIGNMENT_CENTER);
        style_header.setBackgroundColor(192, 192, 192);
        style_header.setBorder(Style.BORDER_THIN);
        style_header.setBorderColor(0, 0, 0);


        Style style = excelUtil.createStyle();
        style.setBorder(Style.BORDER_THIN);
        style.setBorderColor(0, 0, 0);


        excelUtil.nextSheet(16, 16, 16, 16).nextRow()
                .setStyle(style_center).nextCell("凭证汇总表", 4, 1).nextRow()
                .setStyle(style_right).nextCell("凭证总张数：12张  附件总张数：11张", 4, 1).nextRow()
                .setStyle(style_right).nextCell("1102测试账套", 2, 1).nextCell("2018-06-01 至 2018-06-30", 2, 1).nextRow()
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
