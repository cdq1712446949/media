package com.cdq.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/20 19:00
 * @description：Object工具累
 * @modified By：
 * @version: 1.0.1
 */
public class ObjectUtil {

    public static Object toPojo(String str , Class clazz){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(str,clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
