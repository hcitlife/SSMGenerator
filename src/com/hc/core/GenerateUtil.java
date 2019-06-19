package com.hc.core;

import com.hc.bean.FkItem;
import com.hc.bean.Item;
import com.hc.bean.TableInfo;
import com.hc.utils.DBUtil;
import com.hc.utils.FileUtil;
import com.hc.utils.FreemarkerUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

public class GenerateUtil extends DBUtil {

    private String base = System.getProperty("user.dir") + "\\src";// user.dir得到的是项目的绝对路径

    private String baseUrlStr = Constant.baseUrlStr;
    private String utilsStr = Constant.utilsStr;
    private String controllerStr = Constant.controllerStr;
    private String serviceStr = Constant.serviceStr;
    private String entityStr = Constant.entityStr;
    private String implStr = Constant.implStr;
    private String daoStr = Constant.daoStr;

    public static void main(String[] args) throws Exception {
        new GenerateUtil().genCode();
    }

    public void genCode() throws Exception {
        //如果用户指定生成代码的位置
        if (Constant.aimPath != null && Constant.aimPath.trim().length() > 0) {
            base = Constant.aimPath;
        }
        System.out.println("generating....");

        List<TableInfo> tableInfoList = readTableMetaData();

        createPackage(); // 创建包

        createPageBeanFile();
        createPageParamFile();
        createConstFile();

        for (TableInfo tableInfo : tableInfoList) {
            createEntityFile(tableInfo); // 创建实体类
            createDaoFile(tableInfo); // 创建Dao接口
            createMapperFile(tableInfo); // 创建Mapper映射文件
            createServiceFile(tableInfo);
            createServiceImplFile(tableInfo);
            createControllerFile(tableInfo);
        }

        System.out.println("generate success!");
    }

    private void createPackage() {
        String[] baseUrls = baseUrlStr.split("[.]");
        String basePackage = base;
        File fileBase = new File(basePackage + "/" + baseUrls[0]);
        if (fileBase.exists()) {
            FileUtil.deleteDirectory(basePackage + "/" + baseUrls[0]);
        }

        for (String b : baseUrls) {
            basePackage += "\\" + b;
            File file = new File(basePackage);
            if (!file.exists()) {
                file.mkdirs();
            }
        }

        new File(basePackage + "\\" + entityStr).mkdir();
        new File(basePackage + "\\" + serviceStr).mkdir();
        new File(basePackage + "\\" + serviceStr + "\\" + implStr).mkdir();
        new File(basePackage + "\\" + controllerStr).mkdir();
        new File(basePackage + "\\" + daoStr).mkdir();
        new File(basePackage + "\\" + daoStr + "\\" + implStr).mkdir();
        new File(basePackage + "\\" + utilsStr).mkdir();
    }

    private void createEntityFile(TableInfo tableInfo) throws Exception {
        Map<String, Object> data = new HashMap<>(); // 创建数据模型
        data.put("clazzName", tableInfo.getClassName());// 生成类所对应的package语句
        data.put("pkg", baseUrlStr + "." + entityStr); // 生成Entity类

        Set<String> importList = new HashSet<>(); // 用来生成import语句

        for (Item item : tableInfo.getItemList()) {
            String javaType = item.getJavaType();
            importList.add(javaType);
        }

        data.put("fkItemList", tableInfo.getFkItemList());
        data.put("itemList", tableInfo.getItemList());
        data.put("importList", importList);

        String fileName = base + "\\" + baseUrlStr.replace(".", "\\") + "\\" + entityStr + "\\" + tableInfo.getClassName() + ".java";
        Writer out = new FileWriter(new File(fileName));
        FreemarkerUtil.execute(Constant.entityFile, data, out);
    }

    private void createDaoFile(TableInfo tableInfo) throws Exception {
        Map<String, Object> data = new HashMap<>(); // 创建数据模型
        // 生成类所对应的package语句
        data.put("pkg", baseUrlStr + "." + daoStr);
        String clazzName = tableInfo.getClassName();
        data.put("clazzNameWithPkg", baseUrlStr + "." + entityStr + "." + clazzName);
        data.put("clazzName", clazzName);

        // 获取主键
        List<String> tablePK = DBUtil.getTablePK(tableInfo.getTableName());
        data.put("pk", tablePK.get(0));

        List<Item> itemList = tableInfo.getItemList();
        for (Item item : itemList) {
            if (item.isPk()) {
                String javaType = item.getJavaType();
                data.put("pkType", javaType.substring(javaType.lastIndexOf(".") + 1));
            }
        }
        data.put("entityPkg", baseUrlStr + "." + entityStr);

        // 获取外键
        List<FkItem> fkItemList = tableInfo.getFkItemList();
        data.put("fkItemList", fkItemList);

        String fileName = base + "\\" + baseUrlStr.replace(".", "\\") + "\\" + daoStr + "\\" + clazzName + "Dao.java";
        Writer out = new FileWriter(new File(fileName));
        FreemarkerUtil.execute(Constant.daoFile, data, out);
    }

    private void createMapperFile(TableInfo tableInfo) throws Exception {
        Map<String, Object> data = new HashMap<>(); // 创建数据模型
        String clazzName = tableInfo.getClassName();
        data.put("DaoInterfaceNameWithPkg", baseUrlStr + "." + daoStr + "." + clazzName + "Dao");
        // 生成类所对应的package语句
        data.put("EntityNameWithPkg", baseUrlStr + "." + entityStr + "." + clazzName);

        List<Item> itemList = tableInfo.getItemList();
        data.put("itemList", itemList);
        data.put("tableName", tableInfo.getTableName());
        data.put("clazzName", tableInfo.getClassName());

        // 获取外键
        List<FkItem> fkItemList = tableInfo.getFkItemList();
        data.put("fkItemList", fkItemList);

        String fileName = base + "\\" + baseUrlStr.replace(".", "\\") + "\\" + daoStr + "\\" + implStr + "\\"
                + clazzName + "Mapper.xml";
        Writer out = new FileWriter(new File(fileName));
        FreemarkerUtil.execute(Constant.mapperFile, data, out);
    }

    private void createServiceFile(TableInfo tableInfo) throws Exception {
        Map<String, Object> data = new HashMap<>(); // 创建数据模型

        String className = tableInfo.getClassName();
        // 生成类所对应的package语句
        data.put("clazzNameWithPkg", baseUrlStr.replace("main.java.", "") + "." + entityStr + "." + className);
        data.put("clazzName", className);
        data.put("pkg", baseUrlStr.replace("main.java.", "") + "." + serviceStr);
        data.put("pageBeanWithPkg", baseUrlStr.replace("main.java.", "") + "." + utilsStr + "." + "PageBean");
        data.put("daoNameWithPkg", baseUrlStr.replace("main.java.", "") + "." + daoStr + "." + className + "Dao");
        data.put("pageParamWithPkg", baseUrlStr.replace("main.java.", "") + "." + utilsStr + "." + "PageParam");
        // 生成主键
        String pk = DBUtil.getTablePK(tableInfo.getTableName()).get(0);
        data.put("pk", pk);
        List<Item> itemList = tableInfo.getItemList();
        for (Item item : itemList) {
            if (item.isPk()) {
                String javaType = item.getJavaType();
                data.put("pkType", javaType.substring(javaType.lastIndexOf(".") + 1));
            }
        }
        data.put("entityPkg", baseUrlStr + "." + entityStr);

        // 获取外键
        List<FkItem> fkItemList = tableInfo.getFkItemList();
        data.put("fkItemList", fkItemList);

        String fileName = base + "\\" + baseUrlStr.replace(".", "\\") + "\\" + serviceStr + "\\" + className
                + "Service.java";
        Writer out = new FileWriter(new File(fileName));
        FreemarkerUtil.execute(Constant.serviceFile, data, out);
    }

    private void createServiceImplFile(TableInfo tableInfo) throws Exception {
        Map<String, Object> data = new HashMap<>(); // 创建数据模型

        String className = tableInfo.getClassName();
        // 生成类所对应的package语句
        data.put("clazzNameWithPkg", baseUrlStr.replace("main.java.", "") + "." + entityStr + "." + className);
        data.put("serviceNameWithPkg",
                baseUrlStr.replace("main.java.", "") + "." + serviceStr + "." + className + "Service");
        data.put("daoNameWithPkg", baseUrlStr.replace("main.java.", "") + "." + daoStr + "." + className + "Dao");
        data.put("daoImplNameWithPkg",
                baseUrlStr.replace("main.java.", "") + "." + daoStr + "." + implStr + "." + className + "DaoImpl");
        data.put("pageParamWithPkg", baseUrlStr.replace("main.java.", "") + "." + utilsStr + "." + "PageParam");
        data.put("clazzName", className);
        data.put("pkg", baseUrlStr.replace("main.java.", "") + "." + serviceStr + "." + implStr);
        data.put("pageBeanWithPkg", baseUrlStr.replace("main.java.", "") + "." + utilsStr + "." + "PageBean");

        // 生成主键
        String pk = getTablePK(tableInfo.getTableName()).get(0);
        data.put("pk", pk);
        List<Item> itemList = tableInfo.getItemList();
        for (Item item : itemList) {
            if (item.isPk()) {
                String javaType = item.getJavaType();
                data.put("pkType", javaType.substring(javaType.lastIndexOf(".") + 1));
            }
        }
        data.put("entityPkg", baseUrlStr + "." + entityStr);

        // 获取外键
        List<FkItem> fkItemList = tableInfo.getFkItemList();
        data.put("fkItemList", fkItemList);

        String fileName = base + "\\" + baseUrlStr.replace(".", "\\") + "\\" + serviceStr + "\\" + implStr + "\\"
                + className + "ServiceImpl.java";
        Writer out = new FileWriter(new File(fileName));
        FreemarkerUtil.execute(Constant.serviceImplFile, data, out);

    }

    private void createControllerFile(TableInfo tableInfo) throws Exception {
        Map<String, Object> data = new HashMap<>(); // 创建数据模型

        String className = tableInfo.getClassName();

        // 生成类所对应的package语句
        data.put("pkg", baseUrlStr.replace("main.java.", "") + "." + controllerStr);
        data.put("serviceNameWithPkg",
                baseUrlStr.replace("main.java.", "") + "." + serviceStr + "." + className + "Service");
        data.put("clazzName", className);

        String fileName = base + "\\" + baseUrlStr.replace(".", "\\") + "\\" + controllerStr + "\\" + className
                + "Controller.java";
        Writer out = new FileWriter(new File(fileName));
        FreemarkerUtil.execute(Constant.controllerFile, data, out);
    }

    private void createPageBeanFile() throws Exception {
        Map<String, Object> data = new HashMap<>(); // 创建数据模型
        data.put("pkg", baseUrlStr.replace("main.java.", "") + "." + utilsStr);
        data.put("pageParamWithPkg", baseUrlStr.replace("main.java.", "") + "." + utilsStr + "." + "PageParam");

        String fileName = base + "\\" + baseUrlStr.replace(".", "\\") + "\\" + utilsStr + "\\" + "PageBean.java";

        Writer out = new FileWriter(new File(fileName));
        FreemarkerUtil.execute(Constant.pageBeanFile, data, out);
    }

    private void createPageParamFile() throws Exception {
        Map<String, Object> data = new HashMap<>(); // 创建数据模型
        data.put("pkg", baseUrlStr.replace("main.java.", "") + "." + utilsStr);

        String fileName = base + "\\" + baseUrlStr.replace(".", "\\") + "\\" + utilsStr + "\\" + "PageParam.java";

        Writer out = new FileWriter(new File(fileName));
        FreemarkerUtil.execute(Constant.pageParamFile, data, out);
    }

    private void createConstFile() throws Exception {
        Map<String, Object> data = new HashMap<>(); // 创建数据模型
        data.put("pkg", baseUrlStr.replace("main.java.", "") + "." + utilsStr);

        String fileName = base + "\\" + baseUrlStr.replace(".", "\\") + "\\" + utilsStr + "\\" + "Const.java";
        Writer out = new FileWriter(new File(fileName));
        FreemarkerUtil.execute(Constant.constFile, data, out);
    }
}