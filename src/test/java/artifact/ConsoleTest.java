package artifact;


import artifact.modules.common.util.PoiUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConsoleTest {

  private static  Pattern p=Pattern.compile("\\S+");

    public static void main(String[] args)throws Exception  {

        test();
    }


    public static void test() throws Exception {
        String path="C:\\Users\\DGG-S27-D-20\\Documents\\text1.xls";

        PoiUtil poiUtil=new PoiUtil();
        poiUtil.test1(path);

    }


    public static void test1() {


        String str="id desc nullLast";

        Matcher matcher=p.matcher(str);
        while(matcher.find()){

          System.out.println(matcher.group(0));
        }


    }




}
