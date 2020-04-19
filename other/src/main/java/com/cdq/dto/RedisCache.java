package com.cdq.dto;

import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/18 16:07
 * @description：Redis缓存注解
 * @modified By：
 * @version: 1.0.1
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RedisCacheCons.class)
public @interface RedisCache {

    /**
     * 访问修饰符必须为public，不写默认为public；
     * 该元素的类型只能是基本数据类型、String、Class、枚举类型、注解类型（体现了注解的嵌套效果）以及上述类型的一位数组；
     * 该元素的名称一般定义为名词，如果注解中只有一个元素，请把名字起为value（后面使用会带来便利操作）；
     * ()不是定义方法参数的地方，也不能在括号中定义任何参数，仅仅只是一个特殊的语法；
     * default代表默认值，值必须和第2点定义的类型一致；
     * 如果没有默认值，代表后续使用注解时必须给该类型元素赋值。
     * @return
     */
    @NotNull
    String value();

}
