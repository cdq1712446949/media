package com.cdq.fallback;

import com.alibaba.fastjson.JSONObject;
import com.cdq.util.ConstansUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/1 14:27
 * @description：容错和回退
 * @modified By：
 * @version: 1.0.1
 */
@Component
public class UserFallBack implements FallbackProvider {
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        //这里可以判断根据不同的异常来做不同的处理， 也可以不判断
        //完了之后调用response方法并根据异常类型传入HttpStatus
        if (cause instanceof HystrixTimeoutException) {
            return response(HttpStatus.GATEWAY_TIMEOUT);
        } else {
            return response(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ClientHttpResponse response(final HttpStatus status) {
        //这里返回一个ClientHttpResponse对象 并实现其中的方法，关于回退逻辑的详细，便在下面的方法中
        ClientHttpResponse clientHttpResponse =  new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                //返回一个HttpStatus对象 这个对象是个枚举对象， 里面包含了一个status code 和reasonPhrase信息
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                //返回status的code  比如 404，500等
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                //返回一个HttpStatus对象的reasonPhrase信息
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
                //close的时候调用的方法，讲白了就是当降级信息全部响应完了之后调用的方法
            }

            @Override
            public InputStream getBody() throws IOException {
                //吧降级信息响应回前端
                Map<String,Object> modelMap = new HashMap<>();
                modelMap.put("success",false);
                modelMap.put("errMsg","系统错误"+getRawStatusCode());
                JSONObject jsonObject = new JSONObject(modelMap);
                return new ByteArrayInputStream(jsonObject.toString().getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                //需要对响应报头设置的话可以在此设置
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
        return clientHttpResponse;
    }
}
