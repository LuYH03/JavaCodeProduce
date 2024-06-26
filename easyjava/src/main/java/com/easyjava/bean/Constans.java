package com.easyjava.bean;

import com.easyjava.builder.BuilderTable;
import com.easyjava.utils.PropertiesUtils;

public class Constans {

    // 开启lombok
    public static String STR_TRUE = "true";
    // 需要忽略的属性
    public static Boolean IGNORE_TABLE_PREFIX;
    public static String IGNORE_BEAN_LOMBOK;
    public static String IGNORE_BEAN_LOMBOK_ANNOTATION;
    public static String IGNORE_BEAN_LOMBOK_CLASS;
    public static String IGNORE_BEAN_TOJSON_FILED;
    public static String IGNORE_BEAN_TOJSON_EXPRESSION;
    public static String IGNORE_BEAN_TOJSON_CLASS;

    public static String SUFFIX_BEAN_QUERY;
    public static String SUFFIX_BEAN_QUERY_FUZZY;
    public static String SUFFIX_BEAN_QUERY_TIME_START;
    public static String SUFFIX_BEAN_QUERY_TIME_END;
    public static String SUFFIX_MAPPER;

    // 日期格式序列化、反序列化
    public static String BEAN_DATE_FORMAT_EXPRESSION;
    public static String BEAN_DATE_FORMAT_CLASS;
    public static String BEAN_DATE_UNFORMAT_EXPRESSION;
    public static String BEAN_DATE_UNFFORMAT_CLASS;


    public static String PACKAGE_SERVICE;
    public static String PACKAGE_SERVICE_IMPL;
    public static String PACKAGE_PO;
    public static String PACKAGE_QUERY;
    public static String PACKAGE_VO;
    public static String PACKAGE_MAPPER;
    public static String PACKAGE_UTILS;
    public static String PACKAGE_ENUM;
    public static String PACKAGE_EXCEPTION;
    public static String PACKAGE_BASE;
    public static String PACKAGE_CONTROLLER;


    public static String PATH_JAVA = "java";
    public static String PATH_RESOURCES = "resources";
    public static String PATH_QUERY;
    public static String PATH_VO;
    public static String PATH_BASE;
    public static String PATH_PO;
    public static String PATH_UTILS;
    public static String PATH_ENUM;
    public static String PATH_EXCEPTION;
    public static String PATH_MAPPER;
    public static String PATH_MAPPER_XMLS;
    public static String PATH_SERVICE;
    public static String PATH_SERVICE_IMPL;
    public static String PATH_CONTROLLER;


    static {
        IGNORE_BEAN_LOMBOK = PropertiesUtils.getString("ignore.bean.lombok");
        IGNORE_BEAN_LOMBOK_ANNOTATION = PropertiesUtils.getString("ignore.bean.lombok.annotation");
        IGNORE_BEAN_LOMBOK_CLASS = PropertiesUtils.getString("ignore.bean.lombok.class");
        IGNORE_BEAN_TOJSON_FILED = PropertiesUtils.getString("ignore.bean.tojson.filed");
        IGNORE_BEAN_TOJSON_EXPRESSION = PropertiesUtils.getString("ignore.bean.tojson.expression");
        IGNORE_BEAN_TOJSON_CLASS = PropertiesUtils.getString("ignore.bean.tojson.class");
        // 日期格式序列化、反序列化
        BEAN_DATE_FORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.format.expression");
        BEAN_DATE_FORMAT_CLASS = PropertiesUtils.getString("bean.date.format.class");
        BEAN_DATE_UNFORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.unformat.expression");
        BEAN_DATE_UNFFORMAT_CLASS = PropertiesUtils.getString("bean.date.unfformat.class");


        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        SUFFIX_BEAN_QUERY = PropertiesUtils.getString("suffix.bean.query");
        SUFFIX_BEAN_QUERY_FUZZY = PropertiesUtils.getString("suffix.bean.query.fuzzy");
        SUFFIX_BEAN_QUERY_TIME_START = PropertiesUtils.getString("suffix.bean.query.time.start");
        SUFFIX_BEAN_QUERY_TIME_END = PropertiesUtils.getString("suffix.bean.query.time.end");
        SUFFIX_MAPPER = PropertiesUtils.getString("suffix.mapper");


        PACKAGE_BASE = PropertiesUtils.getString("package.base");
        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");
        PACKAGE_QUERY = PACKAGE_BASE + "." + PropertiesUtils.getString("package.query");
        PACKAGE_VO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.vo");
        PACKAGE_UTILS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.utils");
        PACKAGE_ENUM = PACKAGE_BASE + "." + PropertiesUtils.getString("package.enum");
        PACKAGE_EXCEPTION = PACKAGE_BASE + "." + PropertiesUtils.getString("package.exception");
        PACKAGE_MAPPER = PACKAGE_BASE + "." + PropertiesUtils.getString("package.mapper");
        PACKAGE_SERVICE = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service");
        PACKAGE_SERVICE_IMPL = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service.impl");
        PACKAGE_CONTROLLER = PACKAGE_BASE + "." + PropertiesUtils.getString("package.controller");

        PATH_BASE = PropertiesUtils.getString("path.base");
        PATH_BASE = PATH_BASE + PATH_JAVA;

        PATH_PO = PATH_BASE + "/" + PACKAGE_PO.replace(".", "/");
        PATH_QUERY = PATH_BASE + "/" + PACKAGE_QUERY.replace(".", "/");
        PATH_VO = PATH_BASE + "/" + PACKAGE_VO.replace(".", "/");
        PATH_UTILS = PATH_BASE + "/" + PACKAGE_UTILS.replace(".", "/");
        PATH_ENUM = PATH_BASE + "/" + PACKAGE_ENUM.replace(".", "/");
        PATH_EXCEPTION = PATH_BASE + "/" + PACKAGE_EXCEPTION.replace(".", "/");
        PATH_MAPPER = PATH_BASE + "/" + PACKAGE_MAPPER.replace(".", "/");
        PATH_CONTROLLER = PATH_BASE + "/" + PACKAGE_CONTROLLER.replace(".", "/");

        PATH_MAPPER_XMLS = PropertiesUtils.getString("path.base") + PATH_RESOURCES + "/" + PACKAGE_MAPPER.replace(".", "/");

        PATH_SERVICE = PATH_BASE + "/" + PACKAGE_SERVICE.replace(".", "/");
        PATH_SERVICE_IMPL = PATH_BASE + "/" + PACKAGE_SERVICE_IMPL.replace(".", "/");
    }

    public static final String[] SQL_DATE_TIME_TYPES = new String[]{"datetime", "timestamp"};
    public static final String[] SQL_DATE_TYPES = new String[]{"date"};
    public static final String[] SQL_DECIMAL_TYPES = new String[]{"decimal", "double", "float"};
    public static final String[] SQL_STARING_TYPES = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};
    public static final String[] SQL_INTEGER_TYPES = new String[]{"int", "tinyint"};
    public static final String[] SQL_LONG_TYPES = new String[]{"bigint"};


}
