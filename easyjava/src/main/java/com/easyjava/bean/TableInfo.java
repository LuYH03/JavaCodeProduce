package com.easyjava.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableInfo {
    /**
     * 表名
     */
    private String tableName;

    /**
     * bean名称
     */
    private String beanName;

    /**
     * 参数名称
     */
    private String beanParamName;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 字段信息
     */
    private List<FieldInfo> fieldInfoList;

    /**
     * 扩展字段信息
     */
    private List<FieldInfo> fieldExtendList;

    /**
     * 唯一索引集合
     */
    private Map<String, List<FieldInfo>> keyIndexMap = new LinkedHashMap();

    /**
     * 是否有date类型
     */
    private Boolean haveDate;

    /**
     * 是否有时间类型
     */
    private Boolean haveDateTime;

    /**
     * 是否有bigdecimal类型
     */
    private Boolean haveBigDecimal;



    public TableInfo() {
    }

    public TableInfo(String tableName, String beanName, String beanParamName, String comment, List<FieldInfo> fieldInfoList,List<FieldInfo> fieldExtendList ,
                     Map<String, List<FieldInfo>> keyIndexMap, Boolean haveDate, Boolean haveDateTime, Boolean haveBigDecimal) {
        this.tableName = tableName;
        this.beanName = beanName;
        this.beanParamName = beanParamName;
        this.comment = comment;
        this.fieldInfoList = fieldInfoList;
        this.fieldExtendList = fieldExtendList;
        this.keyIndexMap = keyIndexMap;
        this.haveDate = haveDate;
        this.haveDateTime = haveDateTime;
        this.haveBigDecimal = haveBigDecimal;
    }

    /**
     * 获取
     * @return tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置
     * @param tableName
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取
     * @return beanName
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * 设置
     * @param beanName
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    /**
     * 获取
     * @return beanParamName
     */
    public String getBeanParamName() {
        return beanParamName;
    }

    /**
     * 设置
     * @param beanParamName
     */
    public void setBeanParamName(String beanParamName) {
        this.beanParamName = beanParamName;
    }

    /**
     * 获取
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取
     * @return fieldInfoList
     */
    public List<FieldInfo> getFieldInfoList() {
        return fieldInfoList;
    }

    /**
     * 设置
     * @param fieldInfoList
     */
    public void setFieldInfoList(List<FieldInfo> fieldInfoList) {
        this.fieldInfoList = fieldInfoList;
    }

    public List<FieldInfo> getFieldExtendList() {
        return fieldExtendList;
    }

    public void setFieldExtendList(List<FieldInfo> fieldExtendList) {
        this.fieldExtendList = fieldExtendList;
    }

    /**
     * 获取
     * @return keyIndexMap
     */
    public Map<String, List<FieldInfo>> getKeyIndexMap() {
        return keyIndexMap;
    }

    /**
     * 设置
     * @param keyIndexMap
     */
    public void setKeyIndexMap(Map<String, List<FieldInfo>> keyIndexMap) {
        this.keyIndexMap = keyIndexMap;
    }

    /**
     * 获取
     * @return haveDate
     */
    public Boolean getHaveDate() {
        return haveDate;
    }

    /**
     * 设置
     * @param haveDate
     */
    public void setHaveDate(Boolean haveDate) {
        this.haveDate = haveDate;
    }

    /**
     * 获取
     * @return haveDateTime
     */
    public Boolean getHaveDateTime() {
        return haveDateTime;
    }

    /**
     * 设置
     * @param haveDateTime
     */
    public void setHaveDateTime(Boolean haveDateTime) {
        this.haveDateTime = haveDateTime;
    }

    /**
     * 获取
     * @return haveBigDecimal
     */
    public Boolean getHaveBigDecimal() {
        return haveBigDecimal;
    }

    /**
     * 设置
     * @param haveBigDecimal
     */
    public void setHaveBigDecimal(Boolean haveBigDecimal) {
        this.haveBigDecimal = haveBigDecimal;
    }

    public String toString() {
        return "TableInfo{tableName = " + tableName + ", beanName = " + beanName + ", beanParamName = " + beanParamName + ", comment = " + comment + ", fieldInfoList = " + fieldInfoList + ", keyIndexMap = " + keyIndexMap + ", haveDate = " + haveDate + ", haveDateTime = " + haveDateTime + ", haveBigDecimal = " + haveBigDecimal + "}";
    }

}
