package cn.yayi.start;

import cn.yayi.util.Regex;
import cn.yayi.util.SimpleHttpClient;

import java.util.*;

/**
 * Created by Administrator on 2018/2/27 0027.
 */
public class Program {
    public static void main(String[] args) {
        String url = "www.bilibili.com";
        SimpleHttpClient httpClient = new SimpleHttpClient();
        httpClient.setConnection(url);
        String html = httpClient.getResponseString();

      List<String> imgUrlList=  Regex.matches(html,Regex.IMG_TARGET);
        Set<String> srcSet=getSrcSet(imgUrlList);

        Iterator<String> it=srcSet.iterator();
        while(it.hasNext()){
            String  imgurl=it.next();
            String fileName=Regex.match(imgurl,"[^/]*.(gif|png|jpg)");
            httpClient.setConnection(imgurl);
            httpClient.getResponseFile("F:\\BaiduNetdiskDownload\\"+fileName);
        }

    }

    public static Set<String> getSrcSet(List<String> dataList) {
        Set<String> result =new HashSet<String>();
        String str="かきくけこ　さしすえそ";
        for (int i=0;i<dataList.size();i++){
            String src=Regex.match(dataList.get(i),"(?<=src=\")[\\S]*(?=\")");
            System.out.println(src);
            if(src.length()>0 && src.startsWith("http://")) {
                result.add(src);
            }

        }
        return result;
    }
}
