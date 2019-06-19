package com.hc.utils;

import com.hc.bean.TableInfo;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class DBUtilsTest {

    @Test
    public void getConnection() throws SQLException {
        Connection conn = DBUtil.getConnection();
        System.out.println(conn);
    }

    @Test
    public void getAllTableNamesByDatabase() throws SQLException {
        List<String> dbList = DBUtil.getAllTableNamesByDatabase("test");
        for (String s : dbList) {
            System.out.println(s);
        }
    }
    @Test
    public void dbType2JavaType(){
        System.out.println(DBUtil.dbType2JavaType("decimal"));
    }

    @Test
    public void getTablePK() throws SQLException {
        System.out.println(DBUtil.getTablePK("aaa"));
    }

    @Test
    public void getTableFkEntityName() throws SQLException {
        Map<String, String> tb_emp = DBUtil.getTableFkEntityName("tb_course");
        tb_emp.forEach((k,v)-> System.out.println(k+" : "+v));
    }

    @Test
    public void getTableComment() throws Exception {
        System.out.println(DBUtil.getTableComment("tb_dept"));
    }

    @Test
    public void readTableMetaData() throws Exception {
        List<TableInfo> tableInfoList = DBUtil.readTableMetaData();
        tableInfoList.forEach(System.out::println);
    }
}
