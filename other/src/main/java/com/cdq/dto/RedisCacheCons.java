package com.cdq.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/4/19 17:26
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
public class RedisCacheCons implements ConstraintValidator<RedisCache,Object> {

    @Override
    public void initialize(RedisCache constraintAnnotation) {
        System.out.println("RedisCache");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return false;
    }
}
