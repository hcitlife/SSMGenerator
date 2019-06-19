package com.hc.bean;

import java.util.List;
import java.util.Map;

public class TableInfo {
    private String tableName;
    private String tableComment;
    private List<Item> itemList;
    private String className;

    public TableInfo() {
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return this.tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<Item> getItemList() {
        return this.itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "tableName='" + tableName + '\'' +
                ", tableComment='" + tableComment + '\'' +
                ", className='" + className + '\'' +
                ", itemList=" + itemList +
                ", fkItemList=" + fkItemList +
                '}';
    }

    ////////////////////////////////////////////////////
    /*
    *当前表的外键
     */
    private List<FkItem> fkItemList;

    public List<FkItem> getFkItemList() {
        return fkItemList;
    }

    public void setFkItemList(List<FkItem> fkItemList) {
        this.fkItemList = fkItemList;
    }

}
