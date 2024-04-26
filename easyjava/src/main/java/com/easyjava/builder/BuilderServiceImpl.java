package com.easyjava.builder;

import com.easyjava.bean.Constans;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @Author: YHan
 * @Date: 2024/4/26 19:08
 * @Description:
 **/
public class BuilderServiceImpl {
    public static final Logger logger = LoggerFactory.getLogger(BuilderPo.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constans.PATH_SERVICE_IMPL);

        String interfaceName = tableInfo.getBeanName() + "Service";
        String className = tableInfo.getBeanName() + "ServiceImpl";
        File poFile = new File(folder, className + ".java");

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
            outw = new OutputStreamWriter(out, "utf8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constans.PACKAGE_SERVICE_IMPL + ";");
            bw.newLine();
            bw.newLine();

            String mapperName = tableInfo.getBeanName() + Constans.SUFFIX_MAPPER;
            String mapperBeanName = StringUtils.lowerCaseFirstLetter(mapperName);


            bw.write("import " + Constans.PACKAGE_QUERY + ".SimplePage;");
            bw.newLine();
            bw.write("import " + Constans.PACKAGE_ENUM + ".PageSize;");
            bw.newLine();
            bw.write("import " + Constans.PACKAGE_VO + ".PaginationResultVo;");
            bw.newLine();
            bw.write("import " + Constans.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");
            bw.newLine();
            bw.write("import " + Constans.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");
            bw.newLine();
            bw.write("import " + Constans.PACKAGE_MAPPER + "." + mapperName + ";");
            bw.newLine();
            bw.write("import " + Constans.PACKAGE_SERVICE + "." + interfaceName + ";");
            bw.newLine();
            bw.write("import org.springframework.stereotype.Service;");
            bw.newLine();

            bw.write("import javax.annotation.Resource;");
            bw.newLine();

            bw.write("import java.util.List;");
            bw.newLine();
            bw.newLine();

            BuilderComment.createClassCommnet(bw, tableInfo.getTableName() + "Service");
            bw.write("@Service(\"" + mapperBeanName + "\")");
            bw.newLine();
            bw.write("public class " + className + " implements " + interfaceName + " {");
            bw.newLine();
            bw.newLine();

            bw.write("\t@Resource");
            bw.newLine();

            bw.write("\tprivate " + mapperName + "<" + tableInfo.getBeanName() + ", " + tableInfo.getBeanParamName() + "> " + mapperBeanName + ";");
            bw.newLine();
            bw.newLine();

            BuilderComment.createFiledComment(bw, "根据条件查询列表");
            bw.write("\tpublic List<" + tableInfo.getBeanName() + "> findListByParam(" + tableInfo.getBeanParamName() + " query) {");
            bw.newLine();
            bw.write("\t\treturn this." + mapperBeanName + ".selectList(query);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuilderComment.createFiledComment(bw, "根据条件查询数量");
            bw.write("\tpublic Integer findCountByParam(" + tableInfo.getBeanParamName() + " query) {");
            bw.newLine();
            bw.write("\t\treturn this." + mapperBeanName + ".selectCount(query);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuilderComment.createFiledComment(bw, "分页查询");
            bw.write("\tpublic PaginationResultVo<" + tableInfo.getBeanName() + "> findListByPage(" + tableInfo.getBeanParamName() + " query) {");
            bw.newLine();
            bw.write("\t\tInteger count = this.findCountByParam(query);");
            bw.newLine();
            bw.write("\t\tInteger pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();");

            bw.newLine();
            bw.write("\t\tSimplePage page = new SimplePage(query.getPageNo(), count, pageSize);");
            bw.newLine();
            bw.write("\t\tquery.setSimplePage(page);");
            bw.newLine();
            bw.write("\t\tList<" + tableInfo.getBeanName() + "> list = this.findListByParam(query);");
            bw.newLine();
            bw.write("\t\tPaginationResultVo<" + tableInfo.getBeanName() + "> result = new PaginationResultVo<>(count, page.getPageSize(), page.getPageNo(),page.getPageTotal(), list);");
            bw.newLine();
            bw.write("\t\treturn result;");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuilderComment.createFiledComment(bw, "新增");
            bw.write("\tpublic Integer add(" + tableInfo.getBeanName() + " bean) {");
            bw.newLine();
            bw.write("\t\treturn this." + mapperBeanName + ".insert(bean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuilderComment.createFiledComment(bw, "批量新增");
            bw.write("\tpublic Integer addBatch(List<" + tableInfo.getBeanName() + "> listBean) {");
            bw.newLine();
            bw.write("\t\tif (listBean == null || listBean.isEmpty()) {");
            bw.newLine();
            bw.write("\t\t\treturn 0;");
            bw.newLine();
            bw.write("\t\t}");
            bw.newLine();
            bw.write("\t\treturn this." + mapperBeanName + ".insertBatch(listBean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuilderComment.createFiledComment(bw, "批量新增或修改");
            bw.write("\tpublic Integer addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> listBean) {");
            bw.newLine();
            bw.write("\t\tif (listBean == null || listBean.isEmpty()) {");
            bw.newLine();
            bw.write("\t\t\treturn 0;");
            bw.newLine();
            bw.write("\t\t}");
            bw.newLine();
            bw.write("\t\treturn this." + mapperBeanName + ".insertOrUpdateBatch(listBean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            for (Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();

                Integer index = 0;
                StringBuilder methodName = new StringBuilder();
                StringBuilder methodParams = new StringBuilder();
                StringBuilder paramNames = new StringBuilder();
                for (FieldInfo fieldInfo : keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtils.UperCaseFirstLetter(fieldInfo.getPropertyName()));
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                    }

                    methodParams.append(fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                    paramNames.append(fieldInfo.getPropertyName());
                    if (index < keyFieldInfoList.size()) {
                        methodParams.append(", ");
                        paramNames.append(", ");
                    }
                }
                BuilderComment.createFiledComment(bw, "根据" + methodName + "查询");
                bw.write("\tpublic " + tableInfo.getBeanName() + " getBy" + methodName + "(" + methodParams + ") {");
                bw.newLine();
                bw.write("\t\treturn this." + mapperBeanName + "" + ".selectBy" + methodName + "(" + paramNames + ");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                BuilderComment.createFiledComment(bw, "根据" + methodName + "更新");
                bw.write("\tpublic Integer updateBy" + methodName + "(" + tableInfo.getBeanName() + " bean, " + methodParams + ") {");
                bw.newLine();
                bw.write("\t\treturn this." + mapperBeanName + ".updateBy" + methodName + "(bean, " + paramNames + ");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                BuilderComment.createFiledComment(bw, "根据" + methodName + "删除");
                bw.write("\tpublic Integer deleteBy" + methodName + "(" + methodParams + ") {");
                bw.newLine();
                bw.write("\t\treturn this." + mapperBeanName + ".deleteBy" + methodName + "(" + paramNames + ");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();
            }


            bw.newLine();
            bw.write("}");


            bw.flush();

        } catch (Exception e) {
            logger.error("创建Service Impl失败", e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (outw != null) {
                try {
                    outw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
