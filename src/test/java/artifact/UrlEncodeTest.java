package artifact;

import java.io.UnsupportedEncodingException;

//rfc1738
public class UrlEncodeTest {

    public static void main(String[] args) throws Exception {
        String url = "编码设置";
        System.out.println(url);
        url = encode(url);
        System.out.println(url);
        url = decode(url);
        System.out.println(url);
    }

    private static String encode(String url) throws UnsupportedEncodingException {
        return java.net.URLEncoder.encode(url, "utf-8");
    }

    private static String decode(String url) {
        String ret = url;
        try {
            ret = java.net.URLDecoder.decode(url, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return ret;
    }
}
