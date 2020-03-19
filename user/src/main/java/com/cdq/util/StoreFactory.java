package com.cdq.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class StoreFactory {

    @Autowired
    MySqlStore sqlStore;

    @Autowired
    RedisStore redisStore;

    @Value("${cdq.storeType}")
    String storeType;


    public  StoreInterface getStore(){
        switch (storeType){
            case "redis":
                return  redisStore;
            case "mysql":
                return  sqlStore;
        }
        return redisStore;

    }




}
