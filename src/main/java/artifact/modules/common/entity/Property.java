package artifact.modules.common.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author DGG-S27-D-20
 */
public class Property extends HashMap<String, Object> {

    public String getString(String key, String def) {
        String ret = def;
        if (this.containsKey(key) && null != this.get(key)) {
            ret = String.valueOf(this.get(key));
        }
        return ret;
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public Integer getInteger(String key, Integer def) {
        Integer ret = def;
        if (this.containsKey(key) && null != this.get(key)) {
            try {
                ret = Integer.parseInt(String.valueOf(this.get(key)));
            } catch (Exception e) {
                throw new RuntimeException(String.format("integer parse error！\r%nmessage:%s", e.getMessage()));
            }
        }
        return ret;
    }

    public Integer getInteger(String key) {
        return getInteger(key, null);
    }

    public Date getDate(String key, String pattern) {
        Date ret = null;
        if (this.containsKey(key) && null != this.get(key)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                ret = sdf.parse(String.valueOf(this.get(key)));
            } catch (Exception e) {
                throw new RuntimeException(String.format("date parse error！\r%nmessage:%s", e.getMessage()));
            }
        }
        return ret;
    }

    public Date getDate(String key) {

        return getDate(key, "yyyy-MM-dd");
    }

    public <T> T get(String key, Class<T> clazz) {

        if (this.containsKey(key) && null != this.get(key)) {
            Object obj = this.get(key);
            if (obj.getClass().equals(clazz)) {
                return (T) obj;
            }
        }
        return null;
    }

}
