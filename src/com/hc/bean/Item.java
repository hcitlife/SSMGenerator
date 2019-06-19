package com.hc.bean;

public class Item {
    private String fieldName;
    private String fieldComment;
    private String sqlType;
    private boolean pk;
    private String propertyName;
    private String javaType;

    public Item() {
    }

    public Item(String javaType, String propertyName) {
        this.propertyName = propertyName;
        this.javaType = javaType;
    }

    public Item(String javaType, String propertyName, String fieldComment) {
        this.javaType = javaType;
        this.propertyName = propertyName;
        this.fieldComment = fieldComment;
    }

    public Item(String propertyName, String fieldComment, String javaType, String fieldName, String sqlType) {
        this.propertyName = propertyName;
        this.fieldComment = fieldComment;
        this.javaType = javaType;
        this.fieldName = fieldName;
        this.sqlType = sqlType;
    }

    public Item(String propertyName, String javaType, String fieldName, String sqlType, boolean pk) {
        this.propertyName = propertyName;
        this.javaType = javaType;
        this.fieldName = fieldName;
        this.sqlType = sqlType;
        this.pk = pk;
    }

    public boolean isPk() {
        return this.pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public String getFieldComment() {
        return this.fieldComment;
    }

    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }

    public String getJavaType() {
        return this.javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getSqlType() {
        return this.sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String toString() {
        return "Item{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldComment='" + fieldComment + '\'' +
                ", sqlType='" + sqlType + '\'' +
                ", pk=" + pk +
                ", propertyName='" + propertyName + '\'' +
                ", javaType='" + javaType + '\'' +
                '}';
    }
}
