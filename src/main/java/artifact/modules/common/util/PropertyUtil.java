package artifact.modules.common.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static Properties prop = new Properties();

    /**
     * @param path like "/application.properties" .
     */
    public static void load(String path) {

        InputStream in = Object.class.getResourceAsStream(path);
        try {
            prop.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key, String def) {
        return prop.getProperty(key, def).trim();
    }

    public static String get(String key) {
        return get(key, "");
    }
}
