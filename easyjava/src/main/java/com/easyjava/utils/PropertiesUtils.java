package com.easyjava.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 读取配置文件类
 */
public class PropertiesUtils {
    public static Properties prop = new Properties();
    public static Map<String,String> PROP_MAP = new ConcurrentHashMap<>();

    static {
        InputStream is = null;
        try {
            is = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
            prop.load(new InputStreamReader(is,"utf8"));

            Iterator<Object> iterator = prop.keySet().iterator();
            while (iterator.hasNext()){
                String key = (String) iterator.next();
                PROP_MAP.put(key, prop.getProperty(key));
            }

        }catch (Exception e){

        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static String getString(String key){
        return PROP_MAP.get(key);
    }

    public static void main(String[] args) {
        System.out.println(getString("db.driver.name"));
    }



}
