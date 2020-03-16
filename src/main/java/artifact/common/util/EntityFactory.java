package artifact.common.util;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class EntityFactory {
    private SqlConnector sqlConnector;
    private static EntityFactory instance;
    private static Map<String, String> typeAliasMap;
    private static Map<String, String> classPackageMap;

    static {
        typeAliasMap = new HashMap<>();
        typeAliasMap.put("varchar", "String");
        typeAliasMap.put("bigint", "Long");
        typeAliasMap.put("text", "String");
        typeAliasMap.put("datetime", "LocalDateTime");
        typeAliasMap.put("decimal", "BigDecimal");

        classPackageMap = new HashMap<>();
        classPackageMap.put("String", "java.lang");
        classPackageMap.put("Long", "java.lang");
        classPackageMap.put("LocalDateTime", "java.time");

    }


    private EntityFactory() {
    }

    public static EntityFactory newInstance() {
        return new EntityFactory();
    }

    public void generate(String table, String clazz) throws SQLException, ClassNotFoundException {
        List<Map> retList = sqlConnector.getTableInfo(table);
        // StringBuilder sBuilder=new StringBuilder();

        String fields = retList.stream().map(e -> String.format("\t/**\n\t*%s\n\t*/\n\tprivate %s %s", e.get("comment"), Optional.ofNullable(typeAliasMap.get(String.valueOf(e.get("type")))).orElse("Object"), camelCase(e.get("name")))).collect(Collectors.joining("\r\n\r\n"));

        System.out.println(fields);
    }


    public void setConnection(String host, int port, String dbName, String user, String pwd) throws Exception {
        sqlConnector = SqlConnector.of(host, port, dbName, user, pwd);
    }

    private static String camelCase(String str) {
        String[] arr = Optional.ofNullable(str).orElse("").split("_");
        if (arr.length == 1) {
            return str;
        } else {
            for (int i = 1; i < arr.length;i++) {
                arr[i] = arr[i].substring(0, 1).toUpperCase().concat(arr[i].substring(1));
            }
            return Arrays.stream(arr).collect(Collectors.joining());
        }

    }

    private static String camelCase(Object str) {
        if (str == null) {
            return "";
        }
        return camelCase(str.toString());
    }
}


class SqlConnector {
    private String CLAZZ_NAME = "com.mysql.cj.jdbc.Driver";
    private Connection con;
    private String DESC_TABLE = "select COLUMN_NAME AS 'name',DATA_TYPE AS 'type', COLUMN_COMMENT AS 'comment' from information_schema.COLUMNS where table_name='%s'";

    private SqlConnector() throws ClassNotFoundException {
        Class.forName(CLAZZ_NAME);
    }

    public static SqlConnector of(String host, int port, String dbName, String user, String pwd) throws SQLException, ClassNotFoundException {
        SqlConnector connector = new SqlConnector();
        connector.con = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s", host, port, dbName), user, pwd);
        return connector;
    }

    List<Map> getTableInfo(String table) throws SQLException {

        Statement query = con.createStatement();
        ResultSet result = query.executeQuery(String.format(DESC_TABLE, table));
        List<Map> retList = new ArrayList<>(result.getFetchSize());

        ResultSetMetaData meta = result.getMetaData();
        while (result.next()) {
            Map item = new HashMap<String, String>(meta.getColumnCount());
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String key = meta.getColumnLabel(i);
                item.put(key, result.getString(key));
            }
            retList.add(item);
        }
        con.close();
        return retList;
    }
}
