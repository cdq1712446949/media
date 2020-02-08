package com.cdq.media;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/1/14 14:54
 * @description：所有测试类的父类,子类中所有方法执行顺序都是按照从上到下
 * @modified By：
 * @version: 1.0.1
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.JVM)
public class BaseTest {

    public void test(){
        System.out.println("test start!");
    }

}
