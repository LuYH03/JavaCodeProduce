package com.easyjava;

import com.easyjava.bean.TableInfo;
import com.easyjava.builder.*;

import java.util.List;

public class RunApplication {
    public static void main(String[] args) {

        List<TableInfo> tableInfoList = BuilderTable.getTable();

        BuilderBase.execute();

        for (TableInfo tableInfo : tableInfoList){
            BuilderPo.execute(tableInfo);

            BuilderQuery.execute(tableInfo);

            BuilderMapper.execute(tableInfo);
        }

    }
}
