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
import java.util.ArrayList;
import java.util.List;

public class BuilderQuery {
    public static final Logger logger = LoggerFactory.getLogger(BuilderQuery.class);
    public static void execute(TableInfo tableInfo){
        File folder = new File(Constans.PATH_QUERY);
        if (!folder.exists()){
            folder.mkdir();
        }

        String className = tableInfo.getBeanName() + Constans.SUFFIX_BEAN_QUERY;

        File poFile = new File(folder,  className + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out,"utf8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constans.PACKAGE_QUERY + ";");
            bw.newLine();
            bw.newLine();

            if (tableInfo.getHaveDateTime() || tableInfo.getHaveDate()){
                bw.write("import java.util.Date;");
                bw.newLine();

            }
            if (tableInfo.getHaveBigDecimal()){
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }

            // 开启lombok
            Boolean ignoreLombok = Constans.STR_TRUE.equals(Constans.IGNORE_BEAN_LOMBOK);


            if (ignoreLombok){
                bw.write(Constans.IGNORE_BEAN_LOMBOK_CLASS + ";");
            }

            bw.newLine();

            //构建类注释
            BuilderComment.createClassCommnet(bw, tableInfo.getComment() + "查询对象");
            if (ignoreLombok){
                bw.write(Constans.IGNORE_BEAN_LOMBOK_ANNOTATION);
                bw.newLine();
            }
            bw.write("public class " + className +" extends BaseQuery {");
            bw.newLine();

            for (FieldInfo field: tableInfo.getFieldInfoList()){
                BuilderComment.createFiledComment(bw, field.getComment());
                bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + ";");
                bw.newLine();
                bw.newLine();


                //构建String类型参数
                if (ArrayUtils.contains(Constans.SQL_STARING_TYPES, field.getSqlType())){
                    String propertyName = field.getPropertyName() + Constans.SUFFIX_BEAN_QUERY_FUZZY;
                    bw.write("\tprivate " + field.getJavaType() + " " + propertyName + ";");
                    bw.newLine();
                    bw.newLine();
                }

                if (ArrayUtils.contains(Constans.SQL_DATE_TIME_TYPES, field.getSqlType()) || ArrayUtils.contains(Constans.SQL_DATE_TYPES, field.getSqlType())){
                    bw.write("\tprivate String " + field.getPropertyName() + Constans.SUFFIX_BEAN_QUERY_TIME_START + ";");
                    bw.newLine();
                    bw.newLine();

                    bw.write("\tprivate String " +field.getPropertyName() + Constans.SUFFIX_BEAN_QUERY_TIME_END + ";");
                    bw.newLine();
                    bw.newLine();

                }
            }

            List<FieldInfo> fieldInfoList = tableInfo.getFieldInfoList();
            if (!ignoreLombok){
                buildGetSet(bw, fieldInfoList);
                buildGetSet(bw, tableInfo.getFieldExtendList());
            }

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

    private static void buildGetSet(BufferedWriter bw, List<FieldInfo> fieldInfoList) throws IOException {
        for (FieldInfo field: fieldInfoList){
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


}
