package artifact.util;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Convertor {
    private String datePattern = "";
    private Map<String, Integer> typeMap;

    public Convertor() {
        this.typeMap = new HashMap<>();
        this.typeMap.put("java.lang.String", 1);
        this.typeMap.put("int", 2);
        this.typeMap.put("java.lang.Integer", 3);
        this.typeMap.put("double", 4);
        this.typeMap.put("java.lang.Double", 5);
        this.typeMap.put("long", 6);
        this.typeMap.put("java.lang.Long", 7);
        this.typeMap.put("java.util.Date", 8);
    }

    @SuppressWarnings("unchecked")
    public <T> T parse(Map<?, ?> para, Class<?> clazz) {

        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            return null;
        }

        Method[] methods = clazz.getMethods();
        Map<String, Method> setters = new HashMap<>();
        for (int i = 0; i < methods.length; i++) {
            String name = methods[i].getName();
            if (name.startsWith("set"))
                setters.put(name, methods[i]);
        }


        Iterator<?> it = para.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object value = para.get(key);
            String methodName = key.toString();
            methodName = "set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

            if (!setters.containsKey(methodName)) continue;
            Method method = setters.get(methodName);
            Class<?> type = method.getParameterTypes()[0];
            value = specify(value, type);
            try {
                method.invoke(instance, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return (T) instance;
    }

    /**
     * 
     * @param value
     * @param type
     * @return
     */
    private Object specify(Object value, Class<?> type) {
        if (value == null) return value;
        String valStr = value.toString();
        String typeName = type.getName();
        if (!this.typeMap.containsKey(typeName)) {
            try {
                throw new Exception("no support type.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int code = this.typeMap.get(typeName);
        switch (code) {
            case 1:
                value = value.toString();
                break;
            case 2:
                value = Integer.parseInt(valStr);
                break;
            case 3:
                value = Integer.valueOf(valStr);
                break;
            case 4:
                value = Double.parseDouble(valStr);
                break;
            case 5:
                value = Double.valueOf(valStr);
                break;
            case 6:
                value = Long.parseLong(valStr);
                break;
            case 7:
                value = Long.valueOf(valStr);
                break;
            case 8:
                String pattern = this.datePattern;
                if (pattern.length() == 0) {
                    pattern = value.toString();

                }
                SimpleDateFormat sdf =getPattern(pattern);
                try {
                    value = sdf.parse(valStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;


        }

        return value;
    }

    /**
     * 根据日期字符串，生成转换模板
     * 1)最多支持到秒
     * 2)只支持年月日、时分秒的降序
     * @param simpleTimeStr
     * @return
     */
    private static SimpleDateFormat getPattern(String simpleTimeStr) {
        String[] holders = new String[]{"yyyy", "MM", "dd", "HH", "mm", "ss"};
        simpleTimeStr=simpleTimeStr.trim();
        String reg="[0-9]+";
        for(int i=0;i<holders.length;i++){
            simpleTimeStr=simpleTimeStr.replaceFirst(reg,holders[i]);
        }
    return new SimpleDateFormat(simpleTimeStr);
    }

    /**
     * 设置日期转换模板
     * @param pattern
     */
    public void setDatePattern(String pattern){
        this.datePattern=pattern;
    }

}
