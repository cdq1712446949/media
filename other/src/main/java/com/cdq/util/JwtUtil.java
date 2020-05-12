package com.cdq.util;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/3/18 9:28
 * @description：JWT工具类，局域网下jwt容易被拦截
 * 问题原因：http采用明文传输
 * 解决办法：采用https或者代码方面添加验证，验证ip地址或者mac地址
 * @modified By：
 * @version: 1.0.1
 */
@Component
public class JwtUtil {

    //解密的密钥 加密的时候传入密钥 解密的时候使用密钥来解析
    private static final String JWT_KEY = "cdq-key";

    public static final String HEAD_KEY = "media_header";
    public static final String TOKEN = "token:";

    //返回给前端的状态码，401表示需要重新登陆
    public static final int RELOGIN=401;
    //201表示重新发送当前请求
    public static final int RESEND=201;

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKeySpec generalKey() {
        String stringKey = JWT_KEY;
        //本地的密码解码[B@152f6e2
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。
        SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static Map<String, Object> generalKeyMap() {

        String stringKey = JWT_KEY;
        //本地的密码解码[B@152f6e2
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。
        SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        Map<String, Object> map = new HashMap();
        map.put("arg1", encodedKey);
        map.put("arg2", encodedKey.length);
        map.put("arg3", "AES");
        return map;
    }

    /**
     * 创建jwt
     *
     * @param id
     * @param subject
     * @param ttlMillis 过期的时间长度
     * @return
     * @throws Exception
     */
    public static String createJWT(String id, String subject, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。
        // 它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
        return builder.compact();
    }


    public static void main(String[] args) throws Exception {
        Map<String, Object> user = new HashMap<>();
        user.put("username", "123");
        user.put("password", "321");
        //把数据转换为JWT token,设置过期时间为二十四小时
        String jwt = createJWT(UUID.randomUUID().toString(), JSON.toJSONString(user), 3600 * 24);
        System.out.println("加密后的" + jwt);
        //解密
        Claims claims = parseJWT(jwt);
        System.out.println("解密后的" + claims.toString());
    }


    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey();
        //得到DefaultJwtParser
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt).getBody();
            return claims;
        }catch (ExpiredJwtException e){
            return null;
        }
    }


}