package com.easyjava.builder;

import com.easyjava.bean.Constans;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.JsonUtils;
import com.easyjava.utils.PropertiesUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class BuilderTable {
    public static final Logger logger = LoggerFactory.getLogger(BuilderTable.class);
    public static Connection conn = null;

    public static String SQL_SHOW_TABLE_STATUS = "show table status";
    public static String SQL_SHOW_TABLE_FIELDS = "show full fields from %s";
    public static String SQL_SHOW_TABLE_INDEX = "show index from %s";

    static {
        String driverName = PropertiesUtils.getString("db.driver.name");
        String url = PropertiesUtils.getString("db.url");
        String username = PropertiesUtils.getString("db.username");
        String password = PropertiesUtils.getString("db.password");

        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            logger.error("数据库连接失败", e);
        }
    }

    public static List<TableInfo> getTable() {
        PreparedStatement ps = null;
        ResultSet tableResult = null;

        ArrayList<TableInfo> tableInfoList = new ArrayList();
        try {
            ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
            tableResult = ps.executeQuery();
            while (tableResult.next()) {
                String tableName = tableResult.getString("Name");
                String comment = tableResult.getString("Comment");


                String beanName = tableName;
                if (Constans.IGNORE_TABLE_PREFIX) {
                    beanName = tableName.substring(beanName.indexOf("_") + 1);
                }
                beanName = processFiled(beanName, true);
                TableInfo tableInfo = new TableInfo();
                tableInfo.setTableName(tableName);
                tableInfo.setBeanName(beanName);
                tableInfo.setComment(comment);
                tableInfo.setBeanParamName(beanName + Constans.SUFFIX_BEAN_QUERY);

                /*logger.info("表:{},备注:{},javabean:{},javaParamBean:{}",tableInfo.getTableName(),tableInfo.getComment(),
                        tableInfo.getBeanName(),tableInfo.getBeanParamName());*/
                readFiledInfo(tableInfo);

                getKeyIndexInfo(tableInfo);

                tableInfoList.add(tableInfo);

            }
        } catch (Exception e) {
            logger.error("读取表失败", e);
        } finally {
            if (tableResult != null) {
                try {
                    tableResult.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return tableInfoList;
    }

    private static void readFiledInfo(TableInfo tableInfo) {
        PreparedStatement ps = null;
        ResultSet fieldResult = null;

        ArrayList<FieldInfo> fileInfoList = new ArrayList();
        try {
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_FIELDS, tableInfo.getTableName()));
            fieldResult = ps.executeQuery();

            Boolean haveDateTime = false;
            Boolean haveDate = false;
            Boolean haveBigDecimal = false;


            while (fieldResult.next()) {
                String field = fieldResult.getString("field");
                String type = fieldResult.getString("type");
                String extra = fieldResult.getString("extra");
                String comment = fieldResult.getString("comment");
                if (type.indexOf("(") > 0) {
                    type = type.substring(0, type.indexOf("("));
                }
                String propertyName = processFiled(field, false);

                FieldInfo fieldInfo = new FieldInfo();
                fileInfoList.add(fieldInfo);


                fieldInfo.setFieldName(field);
                fieldInfo.setSqlType(type);
                fieldInfo.setComment(comment);
                fieldInfo.setIsAutoIncrement("auto_increment".equalsIgnoreCase(extra) ? true : false);
                fieldInfo.setPropertyName(propertyName);
                fieldInfo.setJavaType(processJavaType(type));

                if (ArrayUtils.contains(Constans.SQL_DATE_TIME_TYPES, type)) {
                    haveDateTime = true;
                }
                if ((ArrayUtils.contains(Constans.SQL_DATE_TYPES, type))) {
                    haveDate = true;
                }
                if ((ArrayUtils.contains(Constans.SQL_DECIMAL_TYPES, type))) {
                    haveBigDecimal = true;
                }
            }

            tableInfo.setHaveDateTime(haveDateTime);
            tableInfo.setHaveDate(haveDate);
            tableInfo.setHaveBigDecimal(haveBigDecimal);
            tableInfo.setFieldInfoList(fileInfoList);
        } catch (Exception e) {
            logger.error("读取表字段失败");
        } finally {
            if (fieldResult != null) {
                try {
                    fieldResult.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private static List<FieldInfo> getKeyIndexInfo(TableInfo tableInfo) {
        PreparedStatement ps = null;
        ResultSet fieldResult = null;

        ArrayList<FieldInfo> fileInfoList = new ArrayList();
        try {
            Map<String,FieldInfo> tempMap = new HashMap<>();
            for (FieldInfo fieldInfo: tableInfo.getFieldInfoList()){
                tempMap.put(fieldInfo.getFieldName(),fieldInfo);
            }
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_INDEX, tableInfo.getTableName()));
            fieldResult = ps.executeQuery();
            while (fieldResult.next()) {
                String keyName = fieldResult.getString("key_name");
                Integer nonUnique = fieldResult.getInt("non_unique");
                String columnName = fieldResult.getString("column_name");

                if (nonUnique == 1){
                    continue;
                }
                List<FieldInfo> keyFiledList = tableInfo.getKeyIndexMap().get(keyName);
                if (null == keyFiledList){
                    keyFiledList = new ArrayList<>();
                    tableInfo.getKeyIndexMap().put(keyName,keyFiledList);
                }
                keyFiledList.add(tempMap.get(columnName));
            }
        } catch (Exception e) {
            logger.error("读取索引失败");
        } finally {
            if (fieldResult != null) {
                try {
                    fieldResult.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return fileInfoList;
    }


    /**
     * 表名、字段名驼峰命名转换方法  true:表名转换 false:字段名转换
     *
     * @param field
     * @param uperCaseFirstLetter
     * @return
     */
    private static String processFiled(String field, Boolean uperCaseFirstLetter) {
        StringBuffer sb = new StringBuffer();
        String[] fields = field.split("_");
        sb.append(uperCaseFirstLetter ? StringUtils.UperCaseFirstLetter(fields[0]) : fields[0]);
        for (int i = 1, len = fields.length; i < len; i++) {
            sb.append(StringUtils.UperCaseFirstLetter(fields[i]));
        }
        return sb.toString();
    }

    private static String processJavaType(String type) {
        if (ArrayUtils.contains(Constans.SQL_INTEGER_TYPES, type)) {
            return "Integer";
        } else if (ArrayUtils.contains(Constans.SQL_LONG_TYPES, type)) {
            return "Long";
        } else if (ArrayUtils.contains(Constans.SQL_STARING_TYPES, type)) {
            return "String";
        } else if (ArrayUtils.contains(Constans.SQL_DATE_TIME_TYPES, type)) {
            return "Date";
        } else if (ArrayUtils.contains(Constans.SQL_DECIMAL_TYPES, type)) {
            return "BigDecimal";
        } else {
            throw new RuntimeException("无法识别的类型" + type);
        }
    }

}
