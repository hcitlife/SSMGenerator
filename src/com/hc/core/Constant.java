package com.hc.core;

/**
 * Created by HC on 2017/12/7.
 */
public interface Constant {
    String databaseName = "db_test";
    String username = "root";
    String password = "root";
    String urlPrefix = "jdbc:mysql://localhost:3306/";
    String driver = "com.mysql.cj.jdbc.Driver";
    String urlSuffix = "?useSSL=false&serverTimezone=GMT&nullCatalogMeansCurrent=true";


    String aimPath= "e:/abc";
//    String aimPath = null;
    String baseUrlStr = "com.wc";
    String utilsStr ="utils";
    String controllerStr ="controller";
    String serviceStr="service";
    String entityStr="bean";
    String implStr="impl";
    String daoStr="dao";


    //各模板文件的路径(千万不要动)
    String entityFile = "resources\\\\ftl\\\\entity.ftl";
    String daoFile = "resources\\\\ftl\\\\dao.ftl";
    String mapperFile = "resources\\\\ftl\\\\mapper.ftl";
    String serviceFile = "resources\\\\ftl\\\\service.ftl";
    String serviceImplFile = "resources\\\\ftl\\\\serviceImpl.ftl";
    String controllerFile = "resources\\\\ftl\\\\controller.ftl";
    String pageBeanFile = "resources\\\\ftl\\\\pageBean.ftl";
    String pageParamFile = "resources\\\\ftl\\\\pageParam.ftl";
    String constFile = "resources\\\\ftl\\\\const.ftl";
}
