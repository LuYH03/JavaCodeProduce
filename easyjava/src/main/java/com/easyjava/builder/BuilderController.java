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
public class BuilderController {
    public static final Logger logger = LoggerFactory.getLogger(BuilderPo.class);

    public static void execute(TableInfo tableInfo) {

        File folder = new File(Constans.PATH_CONTROLLER);
        String className = tableInfo.getBeanName() + "Controller";
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

            bw.write("package " + Constans.PACKAGE_CONTROLLER + ";");
            bw.newLine();

            String serviceName = tableInfo.getBeanName() + "Service";
            String serviceBeanName = StringUtils.lowerCaseFirstLetter(serviceName);
            String beanName = tableInfo.getBeanName();
            String bean = StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName());


            bw.newLine();
            bw.write("import " + Constans.PACKAGE_PO + "." + beanName + ";");
            bw.newLine();
            bw.write("import " + Constans.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");
            bw.newLine();
            bw.write("import " + Constans.PACKAGE_SERVICE + "." + serviceName + ";");
            bw.newLine();
            bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RestController;");
            bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RequestMapping;");
            bw.newLine();
            bw.write("import javax.annotation.Resource;");
            bw.newLine();
            bw.write("import " + Constans.PACKAGE_VO + ".ResponseVo;");
            bw.newLine();

            bw.write("import org.springframework.web.bind.annotation.RequestBody;");
            bw.newLine();
            bw.write("import java.util.List;");
            bw.newLine();
            bw.newLine();

            BuilderComment.createClassCommnet(bw, tableInfo.getTableName() + "Controller");
            bw.write("@RestController");
            bw.newLine();
            bw.write("@RequestMapping(\"/" + bean + "\")");
            bw.newLine();
            bw.write("public class " + className + " extends ABaseController {");
            bw.newLine();
            bw.newLine();

            bw.write("\t@Resource");
            bw.newLine();
            bw.write("\tprivate " + serviceName + " " + serviceBeanName + ";");
            bw.newLine();
            bw.newLine();

            BuilderComment.createFiledComment(bw, "分页查询");
            bw.write("\t@RequestMapping(\"/loadDataList\")");
            bw.newLine();
            bw.write("\tpublic ResponseVo loadDataList(" + tableInfo.getBeanParamName() + " query) {");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVo(" + serviceBeanName + ".findListByPage(query));");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();


            BuilderComment.createFiledComment(bw, "新增");
            bw.write("\t@RequestMapping(\"/add\")");
            bw.newLine();
            bw.write("\tpublic ResponseVo add(" + beanName + " bean) {");
            bw.newLine();
            bw.write("\t\tgetSuccessResponseVo(" + serviceBeanName + ".add(bean));");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVo(null);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuilderComment.createFiledComment(bw, "批量新增");
            bw.write("\t@RequestMapping(\"/addBatch\")");
            bw.newLine();
            bw.write("\tpublic ResponseVo addBatch(@RequestBody List<" + beanName + "> listBean) {");
            bw.newLine();
            bw.write("\t\tgetSuccessResponseVo(" + serviceBeanName + ".addBatch(listBean));");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVo(null);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuilderComment.createFiledComment(bw, "批量新增或修改");
            bw.write("\t@RequestMapping(\"/addOrUpdateBatch\")");
            bw.newLine();
            bw.write("\tpublic ResponseVo addOrUpdateBatch(@RequestBody List<" + beanName + "> listBean) {");
            bw.newLine();
            bw.write("\t\t getSuccessResponseVo(" + serviceBeanName + ".addOrUpdateBatch(listBean));");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVo(null);");
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
                String methodNameStr = "getBy" + methodName;
                BuilderComment.createFiledComment(bw, "根据" + methodName + "查询");
                bw.write("\t@RequestMapping(\"/" + methodNameStr + "\")");
                bw.newLine();
                bw.write("\tpublic ResponseVo " + methodNameStr + "(" + methodParams + ") {");
                bw.newLine();
                bw.write("\t\treturn getSuccessResponseVo(" + serviceBeanName + "" + ".getBy" + methodName + "(" + paramNames + "));");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                methodNameStr = "updateBy" + methodName;
                BuilderComment.createFiledComment(bw, "根据" + methodName + "更新");
                bw.write("\t@RequestMapping(\"/" + methodNameStr + "\")");
                bw.newLine();
                bw.write("\tpublic ResponseVo " + methodNameStr + "(" + beanName + " bean, " + methodParams + ") {");
                bw.newLine();
                bw.write("\t\t getSuccessResponseVo(" + serviceBeanName + ".updateBy" + methodName + "(bean, " + paramNames + "));");
                bw.newLine();
                bw.write("\t\treturn getSuccessResponseVo(null);");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();


                methodNameStr = "deleteBy" + methodName;
                BuilderComment.createFiledComment(bw, "根据" + methodName + "删除");
                bw.write("\t@RequestMapping(\"/" + methodNameStr + "\")");
                bw.newLine();
                bw.write("\tpublic ResponseVo " + methodNameStr + "(" + methodParams + ") {");
                bw.newLine();
                bw.write("\t\tgetSuccessResponseVo(" + serviceBeanName + ".deleteBy" + methodName + "(" + paramNames + "));");
                bw.newLine();
                bw.write("\t\treturn getSuccessResponseVo(null);");
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
