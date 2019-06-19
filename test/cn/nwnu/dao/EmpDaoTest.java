package cn.nwnu.dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EmpDaoTest {

//    private SqlSession session;
//    private  EmpDao empDao;
//    @Before
//    public void fun(){
//        session = MyBatisUtil.getSession();
//        empDao = session.getMapper(EmpDao.class);
//    }
//    @After
//    public  void  sth(){
//        session.commit();
//    }
//
//    @Test
//    public void insertEmp() {
//    }
//
//    @Test
//    public void insertEmpSelective() {
//    }
//
//    @Test
//    public void batchInsertEmp() {
//    }
//
//    @Test
//    public void deleteEmpByEmpno() {
//    }
//
//    @Test
//    public void deleteEmpByCondition() {
//    }
//
//    @Test
//    public void batchDeleteEmpByEmpnoList() {
//    }
//
//    @Test
//    public void updateEmp() {
//    }
//
//    @Test
//    public void updateEmpSelective() {
//    }
//
//    @Test
//    public void selectCount() {
//    }
//
//    @Test
//    public void selectCountByCondition() {
//    }
//
//    @Test
//    public void selectEmpByEmpno() {
//    }
//
//    @Test
//    public void selectAllEmp() {
//        List<Emp> empList = empDao.selectAllEmp();
//        empList.forEach(System.out::println);
//    }
//
//    @Test
//    public void selectEmpByCondition() {
//    }
//
//    @Test
//    public void selectEmpByMgr() {
//        List<Emp> empList = empDao.selectEmpByMgr(7698);
//        empList.forEach(System.out::println);
//    }
//
//    @Test
//    public void selectEmpByDeptno() {
//        List<Emp> empList = empDao.selectEmpByDeptno((byte)10);
//        empList.forEach(System.out::println);
//    }
}