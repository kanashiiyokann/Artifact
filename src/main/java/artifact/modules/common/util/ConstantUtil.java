package artifact.modules.common.util;



import artifact.modules.common.annotation.Text;
import artifact.modules.user.constant.UserState;

import java.lang.reflect.Field;
import java.util.*;


public class ConstantUtil {
    public static LinkedHashMap getMap(Class<?> clazz) {
        LinkedHashMap retMap = new LinkedHashMap();

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

        LinkedHashMap<String,String> map=getMap(clazz);

        for(String key :map.keySet()){
            Map<String,String> ky=new HashMap(){{
                put(key,map.get(key));
            }};
            list.add(ky);
        }
        return list;
    }

    public static void main(String[] args) {

        System.out.println(ConstantUtil.getMap(UserState.class));
    }
}
