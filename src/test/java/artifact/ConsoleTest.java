package artifact;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConsoleTest {

  private static  Pattern p=Pattern.compile("\\S+");

    public static void main(String[] args)throws Exception  {

        test();
    }



    public static void test() {


        String str="id desc nullLast";

        Matcher matcher=p.matcher(str);
        while(matcher.find()){

          System.out.println(matcher.group(0));
        }


    }




}
