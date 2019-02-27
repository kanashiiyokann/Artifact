package cn.yayi.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Map;

@SuppressWarnings("unused")
public class Parser {
    public static <T> T generateEntity(Class<T> clazz ,Map para) {
        T entity=null;
        try {
            entity=clazz.newInstance();
            for (Object key : para.keySet()) {

                Field field = clazz.getDeclaredField(key.toString());
                if (field == null) {
                    continue;
                }
                Class type = field.getType();
                Object value = para.get(key);
                value=specify(type,value);
                field.setAccessible(true);
                field.set(entity, value);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * 将value转化成需要的类型，限基础类型：int、long、float、date、string，boolean
     * 其中：date转换需要良好的格式，如2018/03/26 16:23:33，至多支持到秒位，且时间单位从左降序
     *       boolean 转化视null、空串、0、false 被认为false，其余均为true
     * @param clazz
     * @param value
     * @return
     * @throws Exception
     */
    private static Object specify  (Class clazz ,Object value) throws  Exception{
        String  clazzName=clazz.getSimpleName().toLowerCase();
        if(clazzName.equals("boolean")){
            if(value!=null && !value.toString().equals("false") && !value.toString().equals("0")&& !value.toString().equals("")){ value=true;}
            else{ value=false;}
            return value;
        }
        if(value==null) return value;

        String valueStr=value.toString();

        if(clazzName.equals("string")){
            return value.toString();
        }
        else if(clazzName.equals("long")){
            value=Long.valueOf(valueStr);
        }
        else if(clazzName.equals("float")){
            value=Float.valueOf(valueStr);
        }
        else if(clazzName.equals("integer")|| clazzName.equals("int")){
            value=Integer.valueOf(valueStr);
        }

        else if(clazzName.equals("date")){
            SimpleDateFormat sdf=getPattern(value.toString());
            value=sdf.parse(value.toString());
        }
        else{
            throw new Exception(String.format("不支持转换的数据类型：%s。",clazzName));
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

}
