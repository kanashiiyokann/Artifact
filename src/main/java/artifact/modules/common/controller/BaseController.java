package artifact.modules.common.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {

    public Date getDate(String dateStr, String format, Date def) {
        Date ret = def;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            ret = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public Date getDate(String dateStr, String format) {

        return getDate(dateStr, format, null);
    }
}
