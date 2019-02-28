package artifact.util;

import net.dgg.accountingtools.common.annotation.Text;
import net.dgg.accountingtools.common.contant.BillStateConstant;
import org.apache.commons.collections.map.LinkedMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConstantUtil {
    public static LinkedMap getMap(Class<?> clazz) {
        LinkedMap retMap = new LinkedMap();

        try {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Text.class)) {
                    Text annotation = field.getAnnotation(Text.class);
                    String key = String.valueOf(field.get(null));
                    String text = annotation.value();
                    retMap.put(key, text);
                }
            }
        } catch (Exception e) {
        }
        return retMap;
    }
    
    public static List<Map<String, String>> getList(Class<?> clazz) {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Text.class)) {
                    Text annotation = field.getAnnotation(Text.class);
                    Map<String, String> map = new HashMap<>(2);
                    map.put("key", String.valueOf(field.get(null)));
                    map.put("text", annotation.value());
                    list.add(map);
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {

        System.out.println(ConstantUtil.getMap(BillStateConstant.class));
    }
}
