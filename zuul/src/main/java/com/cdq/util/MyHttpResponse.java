package com.cdq.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/10 11:07
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
public class MyHttpResponse implements ClientHttpResponse {

    private HttpStatus status;

    public MyHttpResponse(HttpStatus status){
        this.status=status;
    }

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
        return new ByteArrayInputStream(status.toString().getBytes());
    }

    @Override
    public HttpHeaders getHeaders() {
        //需要对响应报头设置的话可以在此设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
