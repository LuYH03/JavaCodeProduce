package com.easyjava.bean;

import lombok.Data;

@Data
public class FieldInfo {
    /**
     * 字段名称
     */
    private String FieldName;

    /**
     * bean属性名称
     */
    private String propertyName;
    private String sqlType;

    /**
     * 备注
     */
    private String comment;

    /**
     * 字段类型
     */
    private String javaType;

    /**
     * 字段备注
     */
    private Boolean isAutoIncrement;

}
