package artifact.modules.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelFactory {
    HSSFWorkbook workbook;

    private ExcelFactory() {
    }

    public static ExcelFactory newInstance() {
        return new ExcelFactory();
    }

    public void select(String path, String sheetName) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(path)) ;
        workbook = new HSSFWorkbook(is, true);
        is.close();
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
}
