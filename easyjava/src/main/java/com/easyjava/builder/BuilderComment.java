package com.easyjava.builder;

import com.easyjava.utils.DateUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: YHan
 * @Date: 2024/4/15 01:42
 * @Description:
 **/
public class BuilderComment {
    public static void createClassCommnet(BufferedWriter bw, String classComment) throws IOException {
        bw.write("/**");
        bw.newLine();
        bw.write(" * @Description: " + classComment);
        bw.newLine();
        bw.write(" * @Date: " + DateUtils.format(new Date(), DateUtils.YYYY_MM_DD));
        bw.newLine();
        bw.write(" */");
        bw.newLine();
    }
    public static void createFiledComment(BufferedWriter bw, String fieldComment) throws IOException {
        bw.write("\t/**");
        bw.newLine();
        bw.write("\t * ");
        bw.write(fieldComment == null ? " " : fieldComment);
        bw.newLine();
        bw.write("\t */");
        bw.newLine();
    }
    public static void createMethodComment(BufferedWriter bw, String methodComment){

    }


}
