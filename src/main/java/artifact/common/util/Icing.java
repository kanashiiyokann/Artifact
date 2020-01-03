package artifact.common.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Icing extends HashMap {

    public String stripToString(String path, String def) {

        Object obj = baseStrip(path, def);

        return Optional.ofNullable(obj).map(Object::toString).orElse(null);

    }


    private Object baseStrip(String path, Object def) {
        String[] fields = path.split("\\.");
        Object target = this;
        int index = 0;
        while (target instanceof HashMap && index < fields.length) {
            target = ((HashMap) target).get(fields[index++]);
        }
        return index == fields.length ? target : def;
    }

    public <T> T strip(String path, Class<T> clazz){

        Object ret= baseStrip(path,null);
        if(!clazz.isInstance(ret)){
            return null;
        }
        return (T) ret;
    }


    public Object baseStrip(String path) {
        return baseStrip(path, null);
    }


    public String stripToString(String path) {
        return stripToString(path, null);
    }

    public static Icing of(Map map) {

        Icing icing = Optional.ofNullable(map).map(m -> {
            Icing i = new Icing();
            i.putAll(map);
            return i;
        }).orElse(null);

        return icing;
    }

    public BigDecimal stripToBigDecimal(String path, BigDecimal def) {
        return Optional.ofNullable(stripToString(path, null)).map(BigDecimal::new).orElse(def);
    }

    public BigDecimal stripToBigDecimal(String path) {
        return Optional.ofNullable(stripToString(path, null)).map(BigDecimal::new).orElse(null);
    }

    public boolean stuff(String path, Object value) {

        String[] fields = path.split("\\.");
        Object target = this;
        int index = 0;
        while (target instanceof HashMap && index < fields.length - 1) {
            target = ((HashMap) target).get(fields[index++]);
        }
        if (target instanceof HashMap) {
            ((HashMap) target).put(fields[index], value);
            return true;
        }
        return false;

    }
}
