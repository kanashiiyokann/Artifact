package artifact;


import artifact.modules.common.util.ExcelUtil;

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

        excelUtil.prepare()
                .nextSheet()
                .nextRow()
                .nextCell("your", "mother", "will", "die", "this", "afternoon")
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
