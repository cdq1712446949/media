package com.cdq.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/5 16:52
 * @description：json数据格式转换
 * @modified By：
 * @version: 1.0.1
 */
public class JsonUtil {

    /**
     * json转pojo
     *
     * @param pojo
     * @param tclass
     * @param <T>
     * @return
     */
    public static <T> T getObject(String pojo, Class<T> tclass) {
        try {
            return JSONObject.parseObject(pojo, tclass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *POJO 转 JSON    
     *      
     */
    public static <T> String getJson(T tResponse) {
        String pojo = JSONObject.toJSONString(tResponse);
        return pojo;
    }

    /**
     * List<T> 转 json 
     *      
     */
    public static <T> String listToJson(List<T> ts) {
        String jsons = JSON.toJSONString(ts);
        return jsons;
    }


    /**
     *  json 转 List<T>
     *      
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
        List<T> ts = (List<T>) JSONArray.parseArray(jsonString, clazz);
        return ts;
    }


}
