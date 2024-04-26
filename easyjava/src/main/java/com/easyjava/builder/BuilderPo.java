package com.easyjava.builder;

import com.easyjava.bean.Constans;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.DateUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class BuilderPo {
    public static final Logger logger = LoggerFactory.getLogger(BuilderPo.class);
    public static void execute(TableInfo tableInfo){
        File folder = new File(Constans.PATH_PO);
        File poFile = new File(folder,tableInfo.getBeanName() + ".java");

        try {
            if (!folder.exists()) {
                folder.mkdirs();
            }
            poFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out,"utf8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constans.PACKAGE_PO + ";");
            bw.newLine();
            bw.newLine();
            bw.write("import java.io.Serializable;");
            bw.newLine();

            if (tableInfo.getHaveDateTime() || tableInfo.getHaveDate()){
                bw.write("import java.util.Date;");
                bw.newLine();
                bw.write(Constans.BEAN_DATE_FORMAT_CLASS + ";");
                bw.newLine();
                bw.write(Constans.BEAN_DATE_UNFFORMAT_CLASS + ";");
                bw.newLine();

                bw.write("import " + Constans.PACKAGE_ENUM + ".DateTimePatternEnum;");
                bw.newLine();
                bw.write("import " + Constans.PACKAGE_UTILS + ".DateUtil;");
                bw.newLine();
            }
            // 开启lombok
            Boolean ignoreLombok = Constans.STR_TRUE.equals(Constans.IGNORE_BEAN_LOMBOK);
            // 属性忽略
            Boolean haveignorefiled = false;
            for (FieldInfo field: tableInfo.getFieldInfoList()){
                if (ArrayUtils.contains(Constans.IGNORE_BEAN_TOJSON_FILED.split(","), field.getPropertyName())){
                    haveignorefiled = true;
                    break;
                }
            }
            if (haveignorefiled){
                bw.write(Constans.IGNORE_BEAN_TOJSON_CLASS + ";");
                bw.newLine();
            }

            if (tableInfo.getHaveBigDecimal()){
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }

            if (ignoreLombok){
                bw.write(Constans.IGNORE_BEAN_LOMBOK_CLASS + ";");
            }

            bw.newLine();

            //构建类注释
            BuilderComment.createClassCommnet(bw, tableInfo.getComment());
            if (ignoreLombok){
                bw.write(Constans.IGNORE_BEAN_LOMBOK_ANNOTATION);
                bw.newLine();
            }
            bw.write("public class " + tableInfo.getBeanName() + " implements Serializable{");
            bw.newLine();

            for (FieldInfo field: tableInfo.getFieldInfoList()){
                BuilderComment.createFiledComment(bw, field.getComment());

                if (ArrayUtils.contains(Constans.SQL_DATE_TIME_TYPES, field.getSqlType())){
                    bw.write("\t" + String.format(Constans.BEAN_DATE_FORMAT_EXPRESSION, DateUtils.YYYY_MM_DD_HH_MM_SS));
                    bw.newLine();
                    bw.write("\t" + String.format(Constans.BEAN_DATE_UNFORMAT_EXPRESSION, DateUtils.YYYY_MM_DD_HH_MM_SS));
                    bw.newLine();
                }
                if (ArrayUtils.contains(Constans.SQL_DATE_TYPES, field.getSqlType())){
                    bw.write("\t" +String.format(Constans.BEAN_DATE_FORMAT_EXPRESSION, DateUtils.YYYY_MM_DD));
                    bw.newLine();
                    bw.write("\t" +String.format(Constans.BEAN_DATE_UNFORMAT_EXPRESSION, DateUtils.YYYY_MM_DD));
                    bw.newLine();
                }

                if (ArrayUtils.contains(Constans.IGNORE_BEAN_TOJSON_FILED.split(","), field.getPropertyName())){
                    bw.write("\t" + Constans.IGNORE_BEAN_TOJSON_EXPRESSION);
                    bw.newLine();
                }

                bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + ";");
                bw.newLine();
                bw.newLine();
            }
            // Get()、Set()
            if (!ignoreLombok){
                for (FieldInfo field: tableInfo.getFieldInfoList()){
                    String tempField = StringUtils.UperCaseFirstLetter(field.getPropertyName());
                    bw.write("\tpublic void set" + tempField + "(" + field.getJavaType() + " " + field.getPropertyName() + ") {");
                    bw.newLine();
                    bw.write("\t\tthis." + field.getPropertyName() + " = " + field.getPropertyName() + ";");
                    bw.newLine();
                    bw.write("\t}");
                    bw.newLine();
                    bw.newLine();

                    bw.write("\tpublic " + field.getJavaType() + " get" + tempField + "() {");
                    bw.newLine();
                    bw.write("\t\treturn this." + field.getPropertyName() + ";");
                    bw.newLine();
                    bw.write("\t}");
                    bw.newLine();
                    bw.newLine();
                }
            }

            // 重写toString()
            StringBuffer toString = new StringBuffer();
            Integer index = 0;
            for (FieldInfo field : tableInfo.getFieldInfoList()){
                index ++;

                String properName = field.getPropertyName();
                if (ArrayUtils.contains(Constans.SQL_DATE_TIME_TYPES, field.getSqlType())){
                    properName = "DateUtil.format(" + properName + ", DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())";
                } else if (ArrayUtils.contains(Constans.SQL_DATE_TYPES, field.getSqlType())) {
                    properName = "DateUtil.format(" + properName + ", DateTimePatternEnum.YYYY_MM_DD.getPattern())";
                }

                toString.append(field.getComment() + ":\" + (" + field.getPropertyName() + " == null ? \"空\" : " + properName + ")");
                if (index < tableInfo.getFieldInfoList().size()){
                    toString.append(" +").append("\",");
                }
            }
            String toStringStr = toString.toString();
            toStringStr = "\"" + toStringStr;
            bw.write("\t@Override");
            bw.newLine();
            bw.write("\tpublic String toString() {");
            bw.newLine();
            bw.write("\t\treturn " + toStringStr + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();


            bw.write("}");
            bw.flush();

        }catch (Exception e){
            logger.error("创建po失败",e);
        }finally {
            if (bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (outw != null){
                try {
                    outw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


}
