package com.ekeyfund.oss.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.List;

/**
 * Create by guanglei on 2018/4/2
 */
public class FastJSONUtils {

    /**
     * 将传递的对象参数转换为JSONString
     * @author tony
     * @date 2015年11月9日上午11:20:00
     */
    public static String object2JSONString(Object object){
        return JSON.toJSONString(object);
    }


    /**
     * 指定对象的JSON Field转换成JSON字符串
     * @param object
     * @param jsonFields
     * @return
     */
    public static String object2JSONStringFilter(Object object,String...jsonFields){
        PropertyPreFilter filter =new SimplePropertyPreFilter(jsonFields);
        return JSON.toJSONString(object,filter);
    }

    /**
     * 将传递的JSON字符串对象转换为业务对象
     * @author tony
     * @date 2015年11月9日上午11:21:08
     */
    public static <T> T jsonString2Object(String jsonString,Class<T> clazz){
        return JSON.parseObject(jsonString,clazz);
    }


    public static JSONObject jsonString2JSONObject(String jsonString){
        return JSON.parseObject(jsonString);
    }



    public static <T> List<T> jsonString2JSONArray(String jsonString, Class<T> clazz){
        return JSONArray.parseArray(jsonString,clazz);
    }

    public static JSONArray jsonString2JSONArray(String jsonString){
        return JSONArray.parseArray(jsonString);
    }
    public static JSONArray getJSONArrayFromJSONObject(JSONObject jsonObject,String key){
        return jsonObject.getJSONArray(key);
    }

    public  static <T> List<T> parseArray(JSONArray jsonArray,Class<T> clazz){
        return JSON.parseArray(jsonArray.toString(),clazz);
    }


    /**
     * json String中获取指定key的值
     * @param key
     * @param jsonString
     * @return
     */
    public static Object getValueFromJSONString(String jsonString,String key){
        JSONObject jsonObject =jsonString2JSONObject(jsonString);
        return jsonObject.get(key);
    }

}
