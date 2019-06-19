package com.hc.utils;

import org.junit.Test;


public class ToolsTest {

    @Test
    public void first2LowerCase() {
        System.out.println(Tools.First2LowerCase("ABC"));
    }

    @Test
    public void first2UpperCase() {
        System.out.println(Tools.First2UpperCase("abc"));
    }

    @Test
    public void field2Property() {
        System.out.println(Tools.field2Property("locact"));
        System.out.println(Tools.field2Property("loc_act"));
        System.out.println(Tools.field2Property("loc_act_ion"));
    }

    @Test
    public void getEntryName() {
        System.out.println(Tools.getEntryName("dept"));
        System.out.println(Tools.getEntryName("tb_dept"));
        System.out.println(Tools.getEntryName("tb_dept_abc"));
        System.out.println(Tools.getEntryName("tb_dept_abc_fdsa"));
    }

    public static void main(String[] args) {
        String s = "abc*abc*abc";
        String replace = s.replaceFirst("ab", "_");
        System.out.println(replace);
    }
}
