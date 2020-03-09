package artifact.modules.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelFactory {
    Workbook workbook;

    private ExcelFactory() {
    }

    public static ExcelFactory newInstance() {
        return new ExcelFactory();
    }

    public void select(String path, String sheetName) throws IOException {
        workbook = getWorkbook(path);
        int numOfSheet = workbook.getNumberOfSheets();
        List<String> list = new ArrayList<>(numOfSheet);
        for (int i = 0; i < numOfSheet; i++) {
            String name = workbook.getSheetName(i);
            if (!name.equals(sheetName)) {
                list.add(name);
            }
        }
        list.forEach(name -> workbook.removeSheetAt(workbook.getSheetIndex(name)));
    }


    public void fetch(String path) throws IOException {
        OutputStream fos = new BufferedOutputStream(new FileOutputStream(path));
        workbook.write(fos);
        fos.close();
    }


    private Workbook getWorkbook(String path) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(path));
        Workbook ret = null;
        if (path.endsWith(".xlsx")) {
            ret = new XSSFWorkbook(is);
        } else {
            ret = new HSSFWorkbook(is);
        }
        is.close();
        return ret;
    }
}
