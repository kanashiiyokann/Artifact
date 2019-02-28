package artifact.common.util;


import artifact.common.annotation.Text;
import artifact.modules.user.constant.UserState;

import java.util.LinkedHashMap;

public class ConstantUtil {
    public static LinkedHashMap<String,String> getMap(Class<?> clazz) {
        LinkedHashMap<String,String> retMap = new LinkedHashMap();

        try {
            java.lang.reflect.Field[] fields = clazz.getDeclaredFields();

            for (java.lang.reflect.Field field : fields) {
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

    public static void main(String[] args) {

        ConstantUtil.getMap(UserState.class);
    }
}
