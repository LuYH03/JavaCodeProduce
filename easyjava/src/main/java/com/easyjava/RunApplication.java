package com.easyjava;

import com.easyjava.bean.TableInfo;
import com.easyjava.builder.BuilderBase;
import com.easyjava.builder.BuilderPo;
import com.easyjava.builder.BuilderQuery;
import com.easyjava.builder.BuilderTable;

import java.util.List;

public class RunApplication {
    public static void main(String[] args) {

        List<TableInfo> tableInfoList = BuilderTable.getTable();

        BuilderBase.execute();

        for (TableInfo tableInfo : tableInfoList){
            BuilderPo.execute(tableInfo);

            BuilderQuery.execute(tableInfo);
        }

    }
}
