package com.hc.bean;

public class FkItem {
    private String fk;
    private  String fkType;
    /**
     * 外键所关联的表所对应的实体类的类名
     */
    private String clazzName;

    public FkItem() {
    }

    public FkItem(String fk, String fkType, String clazzName) {
        this.fk = fk;
        this.fkType = fkType;
        this.clazzName = clazzName;
    }

    public String getFk() {
        return fk;
    }

    public void setFk(String fk) {
        this.fk = fk;
    }

    public String getFkType() {
        return fkType;
    }

    public void setFkType(String fkType) {
        this.fkType = fkType;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    @Override
    public String toString() {
        return "FkItem{" +
                "fk='" + fk + '\'' +
                ", fkType='" + fkType + '\'' +
                ", clazzName='" + clazzName + '\'' +
                '}';
    }
}
