package com.cdq.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StoreUtil {

    @Autowired
    StoreFactory factory;


    public Object get(String key){
        return  factory.getStore().get(key);
    }


    public void set(String key,Object object){
        factory.getStore().set(key,object);
    }


}
