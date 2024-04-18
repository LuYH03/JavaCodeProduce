package com.easyjava.builder;

import com.easyjava.bean.Constans;
import com.easyjava.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YHan
 * @Date: 2024/4/17 19:49
 * @Description:
 **/
public class BuilderBase {
    private static Logger logger = LoggerFactory.getLogger(BuilderBase.class);

    public static void execute() {
        List<String> headerInfoList = new ArrayList();
        headerInfoList.add("package " + Constans.PACKAGE_ENUM);
        build(headerInfoList,"DateTimePatternEnum", Constans.PATH_ENUM);

        headerInfoList.clear();
        headerInfoList.add("package " + Constans.PACKAGE_UTILS);
        build(headerInfoList, "DateUtil", Constans.PATH_UTILS);

        headerInfoList.clear();
        headerInfoList.add("package " + Constans.PACKAGE_MAPPER);
        build(headerInfoList, "BaseMapper", Constans.PATH_MAPPER);

    }

    private static void build(List<String> headerInfoList, String fileName, String outPutPath) {
        File folder = new File(outPutPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File javaFile = new File(outPutPath, fileName + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        InputStream in = null;
        InputStreamReader inr = null;
        BufferedReader bf = null;
        try {
            out = new FileOutputStream(javaFile);
            outw = new OutputStreamWriter(out, "utf-8");
            bw = new BufferedWriter(outw);

            String templatePath = BuilderBase.class.getClassLoader().getResource("template/" + fileName + ".txt").getPath();
            in = new FileInputStream(templatePath);
            inr = new InputStreamReader(in, "utf-8");
            bf = new BufferedReader(inr);

            for (String head:headerInfoList){
                bw.write(head + ";");
                bw.newLine();
                if (head.contains("package")){
                    bw.newLine();
                }
            }

            String lineInfo = null;
            while ((lineInfo = bf.readLine()) != null) {
                bw.write(lineInfo);
                bw.newLine();
            }
            bw.flush();
        } catch (Exception e) {
            logger.error("生成基础类：{},失败：", fileName, e);
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (inr != null) {
                try {
                    inr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
