package com.hc.utils;

import com.hc.bean.FkItem;
import com.hc.bean.Item;
import com.hc.bean.TableInfo;
import com.hc.core.Constant;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DBUtil {

    static {
        try {
            Class.forName(Constant.driver);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public DBUtil() {
    }

    public static Connection getConnection() throws SQLException {
        String url = Constant.urlPrefix + Constant.databaseName + Constant.urlSuffix;
        return DriverManager.getConnection(url, Constant.username, Constant.password);
    }

    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public static List<TableInfo> readTableMetaData() throws Exception {
        List<TableInfo> tableInfoList = new ArrayList();
        List<String> tableNameList = getAllTableNamesByDatabase(Constant.databaseName);
        Map<String, List<String>> map = new HashMap();

        for (String tableName : tableNameList) {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("show full columns from " + tableName);

            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(tableName);
            String entryName = Tools.getEntryName(tableName);//表名转实例名
            tableInfo.setClassName(entryName);
            String tableComment = getTableComment(tableName);
            tableInfo.setTableComment(tableComment);

            List<Item> itemList = new ArrayList();
            while (rs.next()) {
                String fieldName = rs.getString("field");
                String propertyName = Tools.field2Property(fieldName);//字段名转换为属性名
                String comment = rs.getString("Comment");
                String sqlType = rs.getString("type").toUpperCase();
                String javaType = dbType2JavaType(sqlType);
                if (sqlType.contains("(")) {
                    sqlType = sqlType.substring(0, sqlType.indexOf("("));
                }


                if (sqlType.equals("INT"))
                    sqlType = "INTEGER";
                if(sqlType.equals("TEXT"))
                    sqlType = "LONGVARCHAR";
                if(sqlType.equals("MEDIUMTEXT"))
                    sqlType = "VARCHAR";
                if(sqlType.equals("DATETIME"))
                    sqlType="TIMESTAMP";
                if (sqlType.equals("JSON"))
                    sqlType="LONGVARCHAR";


                Item item = new Item(propertyName, comment, javaType, fieldName, sqlType);
                if (getTablePK(tableName).size() > 0 && (getTablePK(tableName).get(0)).equalsIgnoreCase(fieldName)) {
                    item.setPk(true);
                }
                itemList.add(item);
                tableInfo.setItemList(itemList);
            }
            //多对一
            List<FkItem> fkItemList = new ArrayList<>();
            Map<String, String> fkEntityName = getTableFkEntityName(tableName);
            for (Item item : itemList) {
                for (String key : fkEntityName.keySet()) {
                    if (item.getFieldName().equals(key)) {
                        FkItem fkItem = new FkItem(key, item.getJavaType(), fkEntityName.get(key));
                        fkItemList.add(fkItem);
                    }
                }
            }
            Map<String, String> tableFkEntityName = getTableFkEntityName(tableName);
            tableInfo.setFkItemList(fkItemList);

            //一对多
            for (Entry<String, String> entry : tableFkEntityName.entrySet()) {
                if (map.containsKey(entry.getValue())) {
                    (map.get(entry.getValue())).add(entryName);
                } else {
                    ArrayList<String> list = new ArrayList();
                    list.add(entryName);
                    map.put(entry.getValue(), list);
                }
            }
            tableInfoList.add(tableInfo);
            closeAll(conn, stmt, rs);
        }

        for (String key : map.keySet()) {
            for (int i = 0; i < tableInfoList.size(); ++i) {
                TableInfo tableInfo = tableInfoList.get(i);
                if (Tools.getEntryName(tableInfo.getTableName()).equals(key)) {
                    List<String> list = map.get(key);
                    tableInfoList.set(i, tableInfo);
                    break;
                }
            }
        }

        return tableInfoList;
    }

    public static String getTableComment(String tableName) throws SQLException {
        String res = null;
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
        if (rs.next()) {
            String create = rs.getString(2);
            res = parse(create);
        }
        closeAll(conn, stmt, rs);
        return res;
    }

    public static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index > 0) {
            comment = all.substring(index + 9);
            comment = comment.substring(0, comment.length() - 1);
            try {
                comment = new String(comment.getBytes("utf-8"));
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            return comment;
        }
        return "";
    }

    public static List<String> getAllTableNamesByDatabase(String databaseName) throws SQLException {
        List<String> tables = new ArrayList();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement("select table_name from information_schema.TABLES where TABLE_SCHEMA=?");
        ps.setString(1, databaseName);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }
        closeAll(conn, ps, rs);
        return tables;
    }

    public static String dbType2JavaType(String type) {
        type = type.toUpperCase();
        String javaType = null;
        if (type.indexOf("CHAR") > -1 || type.indexOf("TEXT") > -1 || type.indexOf("ENUM") > -1
                || type.indexOf("SET") > -1) {
            javaType = "java.lang.String";
        } else if (type.indexOf("TIME") > -1 || type.indexOf("DATE") > -1 || type.indexOf("YEAR") > -1) {
            javaType = "java.util.Date";
        } else if (type.indexOf("BIGINT") > -1) {
            javaType = "java.lang.Long";
        } else if (type.indexOf("TINYINT") > -1) {
            javaType = "java.lang.Byte";
        } else if (type.indexOf("INT") > -1) {
            javaType = "java.lang.Integer";
        } else if (type.indexOf("BIT") > -1) {
            javaType = "java.lang.Boolean";
        } else if (type.indexOf("FLOAT") > -1 || type.indexOf("REAL") > -1) {
            javaType = "java.lang.Double";
        } else if (type.indexOf("DOUBLE") > -1 || type.indexOf("NUMERIC") > -1) {
            javaType = "java.lang.Double";
        } else if (type.indexOf("BLOB") > -1 || type.indexOf("BINARY") > -1) {
            javaType = "byte[]";
        } else if (type.indexOf("JSON") > -1) {
            javaType = "java.lang.String";
        } else if (type.indexOf("DECIMAL") > -1) {
            javaType = "java.math.BigDecimal";
        } else {
            System.out.println("type:" + type);
        }
        return javaType;
    }

    public static List<String> getTablePK(String table) throws SQLException {
        List<String> res = new ArrayList();
        Connection conn = getConnection();
        String catalog = conn.getCatalog();
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = null;
        rs = metaData.getPrimaryKeys(catalog, null, table);

        while (rs.next()) {
            res.add(rs.getString("COLUMN_NAME"));
        }

        closeAll(conn, null, rs);
        return res;
    }

    public static Map<String, String> getTableFkEntityName(String table) throws SQLException {
        Map<String, String> res = new HashMap();
        Connection conn = getConnection();
        String catalog = conn.getCatalog();
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getImportedKeys(catalog, null, table);

        while (rs.next()) {
            String fkColumnName = rs.getString("FKCOLUMN_NAME");
            String pkTablenName = rs.getString("PKTABLE_NAME");
            String fkType = Tools.getEntryName(pkTablenName);
            res.put(fkColumnName, fkType);
        }

        closeAll(conn, null, rs);
        return res;
    }


}
