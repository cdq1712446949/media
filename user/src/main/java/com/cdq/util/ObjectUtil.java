package com.cdq.util;

import com.cdq.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/20 19:00
 * @description：Object工具累
 * @modified By：
 * @version: 1.0.1
 */
public class ObjectUtil {

    /**
     * 字符串转POJO
     *
     * @param str
     * @param clazz
     * @return
     */
    public static Object toPojo(String str, Class clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取token中的userId
     *
     * @param request
     * @return
     */
    public static User getUserId(HttpServletRequest request) {
        String token = HttpServletRequestUtil.getString(request, ConstansUtil.TOKEN);
        User user = new User();
        try {
            if (JwtUtil.parseJWT(token) == null) {
                user.setUserId(ConstansUtil.EMPTY);
            } else {
                user.setUserId(JwtUtil.parseJWT(token).getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 获取fromUserId
     *
     * @param request
     * @return
     */
    public static User getFromUserId(HttpServletRequest request) {
        String fromUserId = HttpServletRequestUtil.getString(request, ConstansUtil.FROM_USER_ID);
        User user = new User();
        if (fromUserId==null||ConstansUtil.EMPTY.equals(fromUserId)){
            user.setUserId(ConstansUtil.EMPTY);
        }else {
            user.setUserId(fromUserId);
        }
        return user;
    }

}
