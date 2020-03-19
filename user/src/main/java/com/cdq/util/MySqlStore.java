package com.cdq.util;

import org.springframework.stereotype.Component;

@Component
public class MySqlStore implements StoreInterface {
    @Override
    public Object get(String key) {
        System.out.println("吧信息存入数据库");
        return null;
    }

    @Override
    public void set(String key, Object obj) {
        System.out.println("从数据库读取信息");
    }
}
