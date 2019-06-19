package cn.nwnu.dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeptDaoTest {

//    private SqlSession session;
//    private  DeptDao deptDao;
//    @Before
//    public void fun(){
//          session = MyBatisUtil.getSession();
//         deptDao = session.getMapper(DeptDao.class);
//    }
//    @After
//    public  void  sth(){
//        session.commit();
//    }
//
//    @Test
//    public void insertDept() throws SQLException {
//        Dept dept = new Dept((byte)2,"a","aaa");
//        int i = deptDao.insertDept(dept);
//        session.commit();
//        System.out.println(i);
//    }
//
//    @Test
//    public void insertDeptSelective() {
//        Dept dept = new Dept();
//        dept.setLoc("dfd");
//        int i = deptDao.insertDeptSelective(dept);
//        session.commit();
//        System.out.println(i);
//    }
//
//    @Test
//    public void batchInsertDept() {
//        List<Dept> deptList = new ArrayList<>();
//        deptList.add(new Dept((byte)4,"4","44"));
//        deptList.add(new Dept((byte)5,"4","44"));
//        deptList.add(new Dept((byte)6,"4","4444444"));
//        deptList.add(new Dept((byte)7,"4","44"));
//        int i = deptDao.batchInsertDept(deptList);
//        session.commit();
//        System.out.println(i);
//    }
//
//    @Test
//    public void deleteDeptByDeptno() {
//        System.out.println(deptDao.deleteDeptByDeptno((byte) 4));
//        session.commit();
//    }
//
//    @Test
//    public void deleteDeptByCondition() {
//        Dept dept = new Dept();
//        dept.setLoc("%a%");
//        System.out.println(deptDao.deleteDeptByCondition(dept));
//        session.commit();
//    }
//
//    @Test
//    public void batchDeleteDeptByDeptnoList() {
//        List<Byte> deptnoList = Arrays.asList((byte)4,(byte)5,(byte)6);
//        System.out.println(deptDao.batchDeleteDeptByDeptnoList(deptnoList));
//        session.commit();
//    }
//
//    @Test
//    public void updateDept() {
//        Dept dept = new Dept();
//        dept.setDeptno((byte)7);
//        dept.setDname("aaa");
//        System.out.println(deptDao.updateDept(dept));
//    }
//
//    @Test
//    public void updateDeptByCondition() {
//        Dept dept = new Dept( );
//        dept.setDeptno((byte)6);
//        dept.setDname("aaa");
//        System.out.println(deptDao.updateDeptSelective(dept));
//    }
//
//    @Test
//    public void selectCount() {
//        System.out.println(deptDao.selectCount());
//    }
//
//    @Test
//    public void selectCountByCondition() {
//        Dept dept = new Dept( );
//        dept.setDname("aaa");
//        System.out.println(deptDao.selectCountByCondition(dept));
//    }
//
//    @Test
//    public void selectDeptByDeptno() {
//        System.out.println(deptDao.selectDeptByDeptno((byte) 4));
//    }
//
//    @Test
//    public void selectAllDept() {
//        deptDao.selectAllDept().forEach(System.out::println);
//    }
//
//    @Test
//    public void selectDeptByCondition() {
//        Dept dept = new Dept( );
//        dept.setDname("sales");
//        deptDao.selectDeptByCondition(dept).forEach(System.out::println);
//    }

}
