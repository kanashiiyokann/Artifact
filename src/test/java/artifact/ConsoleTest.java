package artifact;


import artifact.modules.common.entity.Property;
import artifact.modules.common.util.Excel2PdfUtil;
import artifact.modules.common.util.ExcelUtil;
import artifact.modules.common.util.ExcelUtil.Style;
import artifact.modules.user.entity.User;
import artifact.modules.user.entity.User.UserType;
import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConsoleTest {

    private static Pattern p = Pattern.compile("\\S+");

    public static void main(String[] args) throws Exception {
        test10();
    }

    public static void test10() throws Exception {

        String number = "2358649013";
        int reduce = 3;

        List<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < number.length(); i++) {
            numberList.add(Integer.parseInt(number.substring(i, i + 1)));
        }

        int index = 0;
        while (reduce > 0) {
            doRemove(numberList, index++, reduce--);
        }
        StringBuilder sb = new StringBuilder();
        numberList.forEach(i -> sb.append(i));

        System.out.println(sb.toString());
    }

    public static void doRemove(List<Integer> list, int index, int range) {
        int max = -1;
        int cursor = index;
        for (int i = index; i <= index + range; i++) {
            if (list.get(i) > max) {
                max = list.get(i);
                cursor = i;
            }
        }
        list.remove(cursor);

    }

    public static void test9() throws Exception {

        User user = new User();
        user.setType(UserType.admin);

        System.out.println(user);

    }


    public static void test8() throws Exception {

        String json = getJson();
        List<Property> objList = JSON.parseArray(json, Property.class);

        System.out.println(objList.get(0));

    }

    public static void test7() throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(calendar.get(Calendar.MONTH) + 1);

    }

    public static String getJson() {
        return "[{\"id\":\"7811404284016828416\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556594123450,\"no\":\"20190412112655\",\"imageUrl\":\"https://testdggoss1.oss-cn-beijing.aliyuncs.com/20190412112655.jpg\",\"info\":{\"id\":\"7811142286765391960\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556531658437,\"type\":\"其他类型\",\"summary\":\"test\",\"used\":true,\"period\":null,\"journals\":[{\"id\":\"7811512006369148928\",\"companyId\":null,\"createrName\":null,\"createTime\":null,\"subjectId\":\"7811142279651852288\",\"subjectName\":\"1001 库存现金\",\"direct\":1,\"type\":\"金额\",\"amount\":900,\"subject\":{\"balanceDirection\":\"DEBIT\",\"whetherAuxiliary\":0,\"whetherEnable\":1,\"code\":\"1001\",\"auxiliaryCategoryList\":[],\"currencyList\":[],\"whetherForeignCurrency\":0,\"subjectDetailCategoryValue\":\"流动资产\",\"subjectSeries\":1,\"createrName\":\"赵有文24379471\",\"parentId\":\"0\",\"whetherCount\":0,\"subjectCategory\":\"ASSETS\",\"companyId\":\"7801976694830727168\",\"unit\":\"\",\"whetherInitSubject\":1,\"createTime\":\"1556531658310\",\"whetherLastSubject\":1,\"name\":\"库存现金\",\"id\":\"7811142279651852288\",\"subjectDetailCategoryKey\":\"CURRENT_ASSETS\",\"whetherFinalAdjustment\":0}}]},\"period\":201901,\"date\":\"2019-01-31\",\"state\":1,\"accounted\":1,\"spaned\":2,\"associatedVoucherId\":null,\"associatedVoucherNo\":null,\"group\":null,\"groupSort\":2147483647,\"groupLevel\":null,\"returnNote\":null,\"spanNote\":null,\"auditState\":\"审核通过\"},{\"id\":\"7811404283983273984\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556594123442,\"no\":\"20190412112220\",\"imageUrl\":\"https://testdggoss1.oss-cn-beijing.aliyuncs.com/20190412112220.jpg\",\"info\":{\"id\":\"7811142286765391959\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556531658437,\"type\":\"人员工资单\",\"summary\":\"test2\",\"used\":true,\"period\":null,\"journals\":[{\"id\":\"7811142286765391936\",\"companyId\":null,\"createrName\":null,\"createTime\":null,\"subjectId\":\"7811142279651852288\",\"subjectName\":\"1001 库存现金\",\"direct\":2,\"type\":\"金额\",\"amount\":450,\"subject\":{\"balanceDirection\":\"DEBIT\",\"whetherAuxiliary\":0,\"whetherEnable\":1,\"code\":\"1001\",\"auxiliaryCategoryList\":[],\"currencyList\":[],\"whetherForeignCurrency\":0,\"subjectDetailCategoryValue\":\"流动资产\",\"subjectSeries\":1,\"createrName\":\"赵有文24379471\",\"parentId\":\"0\",\"whetherCount\":0,\"subjectCategory\":\"ASSETS\",\"companyId\":\"7801976694830727168\",\"unit\":\"\",\"whetherInitSubject\":1,\"createTime\":\"1556531658310\",\"whetherLastSubject\":1,\"name\":\"库存现金\",\"id\":\"7811142279651852288\",\"subjectDetailCategoryKey\":\"CURRENT_ASSETS\",\"whetherFinalAdjustment\":0}}]},\"period\":201901,\"date\":\"2019-01-31\",\"state\":1,\"accounted\":1,\"spaned\":2,\"associatedVoucherId\":null,\"associatedVoucherNo\":null,\"group\":null,\"groupSort\":2147483647,\"groupLevel\":null,\"returnNote\":null,\"spanNote\":null,\"auditState\":\"审核通过\"},{\"id\":\"7811404283949719552\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556594123434,\"no\":\"20190412112211\",\"imageUrl\":\"https://testdggoss1.oss-cn-beijing.aliyuncs.com/20190412112211.jpg\",\"info\":{\"id\":\"7811142286765391937\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556531658437,\"type\":\"增值税专用发票（收入）\",\"summary\":\"收入\",\"used\":null,\"period\":null,\"journals\":[{\"id\":\"7811142286765391872\",\"companyId\":null,\"createrName\":null,\"createTime\":null,\"subjectId\":\"7811142279828013056\",\"subjectName\":\"1122 应收账款\",\"direct\":1,\"type\":\"合计金额\",\"amount\":null,\"subject\":{\"id\":\"7811142279828013056\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556531658310,\"parentId\":\"0\",\"code\":\"1122\",\"name\":\"应收账款\",\"subjectCategory\":\"ASSETS\",\"subjectDetailCategoryKey\":\"CURRENT_ASSETS\",\"subjectDetailCategoryValue\":\"流动资产\",\"balanceDirection\":\"DEBIT\",\"whetherInitSubject\":1,\"whetherLastSubject\":1,\"whetherAuxiliary\":1,\"whetherCount\":0,\"unit\":\"\",\"whetherEnable\":1,\"whetherFinalAdjustment\":0,\"whetherForeignCurrency\":0,\"currencyList\":[],\"subjectSeries\":1,\"auxiliaryCategoryList\":[{\"id\":\"7811142279630880768\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556531656736,\"name\":\"客户\",\"sort\":1,\"whetherSystem\":1}]}},{\"id\":\"7811142286765391873\",\"companyId\":null,\"createrName\":null,\"createTime\":null,\"subjectId\":\"7811142281652535296\",\"subjectName\":\"5001 主营业务收入\",\"direct\":2,\"type\":\"金额\",\"amount\":null,\"subject\":{\"id\":\"7811142281652535296\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556531658310,\"parentId\":\"0\",\"code\":\"5001\",\"name\":\"主营业务收入\",\"subjectCategory\":\"PROFIT_AND_LOSS\",\"subjectDetailCategoryKey\":\"BUSINESS_INCOME\",\"subjectDetailCategoryValue\":\"营业收入\",\"balanceDirection\":\"CREDIT\",\"whetherInitSubject\":1,\"whetherLastSubject\":1,\"whetherAuxiliary\":0,\"whetherCount\":0,\"unit\":\"\",\"whetherEnable\":1,\"whetherFinalAdjustment\":0,\"whetherForeignCurrency\":0,\"currencyList\":[],\"subjectSeries\":1,\"auxiliaryCategoryList\":[]}},{\"id\":\"7811142286765391874\",\"companyId\":null,\"createrName\":null,\"createTime\":null,\"subjectId\":\"7811142280721399808\",\"subjectName\":\"22210106 应交税费_应交增值税_销项税额\",\"direct\":2,\"type\":\"税额\",\"amount\":null,\"subject\":{\"id\":\"7811142280721399808\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556531658310,\"parentId\":\"7811142280608153600\",\"code\":\"22210106\",\"name\":\"销项税额\",\"subjectCategory\":\"LIABILITY\",\"subjectDetailCategoryKey\":\"CURRENT_LIABILITY\",\"subjectDetailCategoryValue\":\"流动负债\",\"balanceDirection\":\"CREDIT\",\"whetherInitSubject\":1,\"whetherLastSubject\":1,\"whetherAuxiliary\":0,\"whetherCount\":0,\"unit\":\"\",\"whetherEnable\":1,\"whetherFinalAdjustment\":0,\"whetherForeignCurrency\":0,\"currencyList\":[],\"subjectSeries\":3,\"auxiliaryCategoryList\":[]}}]},\"period\":201901,\"date\":\"2019-01-31\",\"state\":1,\"accounted\":1,\"spaned\":2,\"associatedVoucherId\":null,\"associatedVoucherNo\":null,\"group\":null,\"groupSort\":2147483647,\"groupLevel\":null,\"returnNote\":null,\"spanNote\":null,\"auditState\":\"审核通过\"},{\"id\":\"7811404283907776512\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556594123424,\"no\":\"201903115\",\"imageUrl\":\"https://testdggoss1.oss-cn-beijing.aliyuncs.com/201903115.jpg\",\"info\":{\"id\":\"7811142286765391937\",\"companyId\":\"7801976694830727168\",\"createrName\":\"赵有文24379471\",\"createTime\":1556531658437,\"type\":\"增值税专用发票（收入）\",\"summary\":\"收入\",\"used\":true,\"period\":null,\"journals\":[{\"id\":\"7811142286765391872\",\"companyId\":null,\"createrName\":null,\"createTime\":null,\"subjectId\":\"7811142279828013056\",\"subjectName\":\"1122 应收账款\",\"direct\":1,\"type\":\"合计金额\",\"amount\":3,\"subject\":{\"balanceDirection\":\"DEBIT\",\"whetherAuxiliary\":1,\"whetherEnable\":1,\"code\":\"1122\",\"auxiliaryCategoryList\":[{\"companyId\":\"7801976694830727168\",\"whetherSystem\":1,\"createTime\":\"1556531656736\",\"name\":\"客户\",\"id\":\"7811142279630880768\",\"sort\":1,\"createrName\":\"赵有文24379471\"}],\"currencyList\":[],\"whetherForeignCurrency\":0,\"subjectDetailCategoryValue\":\"流动资产\",\"subjectSeries\":1,\"createrName\":\"赵有文24379471\",\"parentId\":\"0\",\"whetherCount\":0,\"subjectCategory\":\"ASSETS\",\"companyId\":\"7801976694830727168\",\"unit\":\"\",\"whetherInitSubject\":1,\"createTime\":\"1556531658310\",\"whetherLastSubject\":1,\"name\":\"应收账款\",\"id\":\"7811142279828013056\",\"subjectDetailCategoryKey\":\"CURRENT_ASSETS\",\"whetherFinalAdjustment\":0}},{\"id\":\"7811142286765391873\",\"companyId\":null,\"createrName\":null,\"createTime\":null,\"subjectId\":\"7811142281652535296\",\"subjectName\":\"5001 主营业务收入\",\"direct\":2,\"type\":\"金额\",\"amount\":2,\"subject\":{\"balanceDirection\":\"CREDIT\",\"whetherAuxiliary\":0,\"whetherEnable\":1,\"code\":\"5001\",\"auxiliaryCategoryList\":[],\"currencyList\":[],\"whetherForeignCurrency\":0,\"subjectDetailCategoryValue\":\"营业收入\",\"subjectSeries\":1,\"createrName\":\"赵有文24379471\",\"parentId\":\"0\",\"whetherCount\":0,\"subjectCategory\":\"PROFIT_AND_LOSS\",\"companyId\":\"7801976694830727168\",\"unit\":\"\",\"whetherInitSubject\":1,\"createTime\":\"1556531658310\",\"whetherLastSubject\":1,\"name\":\"主营业务收入\",\"id\":\"7811142281652535296\",\"subjectDetailCategoryKey\":\"BUSINESS_INCOME\",\"whetherFinalAdjustment\":0}},{\"id\":\"7811142286765391874\",\"companyId\":null,\"createrName\":null,\"createTime\":null,\"subjectId\":\"7811142280721399808\",\"subjectName\":\"22210106 应交税费_应交增值税_销项税额\",\"direct\":2,\"type\":\"税额\",\"amount\":1,\"subject\":{\"balanceDirection\":\"CREDIT\",\"whetherAuxiliary\":0,\"whetherEnable\":1,\"code\":\"22210106\",\"auxiliaryCategoryList\":[],\"currencyList\":[],\"whetherForeignCurrency\":0,\"subjectDetailCategoryValue\":\"流动负债\",\"subjectSeries\":3,\"createrName\":\"赵有文24379471\",\"parentId\":\"7811142280608153600\",\"whetherCount\":0,\"subjectCategory\":\"LIABILITY\",\"companyId\":\"7801976694830727168\",\"unit\":\"\",\"whetherInitSubject\":1,\"createTime\":\"1556531658310\",\"whetherLastSubject\":1,\"name\":\"销项税额\",\"id\":\"7811142280721399808\",\"subjectDetailCategoryKey\":\"CURRENT_LIABILITY\",\"whetherFinalAdjustment\":0}}]},\"period\":201901,\"date\":\"2019-01-31\",\"state\":1,\"accounted\":1,\"spaned\":2,\"associatedVoucherId\":null,\"associatedVoucherNo\":null,\"group\":null,\"groupSort\":2147483647,\"groupLevel\":null,\"returnNote\":null,\"spanNote\":null,\"auditState\":\"审核通过\"}]";
    }

    public static void test6() throws Exception {

        Excel2PdfUtil util = new Excel2PdfUtil();
        String path = "src/main/resources/凭证汇总表.xls";
        util.load(path);
        path = "C:\\Users\\DGG-S27-D-20\\Desktop\\凭证汇总表.pdf";
        util.fetch(path, 5);

    }

    public static String test5(int year, int month) throws Exception {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        month--;
        calendar.set(year, month, 1, 0, 0, 0);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.SECOND, -1);

        Date date = calendar.getTime();

        return sdf.format(date);

    }

    public static void test4() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2019-03-18 00:00:00");
        Long localtime = date.getTime();
        if (localtime.equals(1552838400000L)) {
            System.out.println("true");
        }
    }

    public static void test3() throws Exception {
        String path = "C:\\Users\\DGG-S27-D-20\\Desktop\\text1.xls";

        ExcelUtil excelUtil = new ExcelUtil();


        excelUtil.nextSheet(16, 16, 16, 16).nextRow()
                .nextCell("凭证汇总表", 1, 3).nextRow()
                .nextCell("凭证总张数", 2, 2).nextRow()
                .nextCell("1102测试账套", 2, 1).nextCell("2018-06-01 至 2018-06-30", 2, 1).nextRow()
                .fetch(path);

    }

    public static void test2() throws Exception {
        String path = "C:\\Users\\DGG-S27-D-20\\Desktop\\text1.xls";

        ExcelUtil excelUtil = new ExcelUtil();

        Style style_center = excelUtil.createStyle();
        style_center.setHorizontalAlign(Style.ALIGNMENT_CENTER);

        Style style_right = excelUtil.createStyle();
        style_right.setHorizontalAlign(Style.ALIGNMENT_RIGHT);

        Style style_header = excelUtil.createStyle();


        style_header.setHorizontalAlign(Style.ALIGNMENT_CENTER);
        style_header.setBackgroundColor(192, 192, 192);
        style_header.setBorder(Style.BORDER_THIN);
        style_header.setBorderColor(0, 0, 0);


        Style style = excelUtil.createStyle();
        style.setBorder(Style.BORDER_THIN);
        style.setBorderColor(0, 0, 0);


        excelUtil.nextSheet(16, 16, 16, 16).nextRow()
                .setStyle(style_center).nextCell("凭证汇总表", 4, 1).nextRow()
                .setStyle(style_right).nextCell("凭证总张数：12张  附件总张数：11张", 4, 1).nextRow()
                .setStyle(style_right).nextCell("1102测试账套", 2, 1).nextCell("2018-06-01 至 2018-06-30", 2, 1).nextRow()
                .setStyle(style_header).nextCell("科目编码", "科目名称", "借方余额", "贷方余额").nextRow()
                .setStyle(style).nextCell("1001", "kemumingcheng", "1111", "22222")
                .fetch(path);

    }


    public static void test1() {


        String str = "id desc nullLast";

        Matcher matcher = p.matcher(str);
        while (matcher.find()) {

            System.out.println(matcher.group(0));
        }


    }


}
