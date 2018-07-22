package com.ekeyfund.oss.generator.mybatisplus.injector.id;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 宜泉主键生成器注解
 * 
 * @author zhaolin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface YqIdGenerator {
    /**
     * <p>
     * id的长度，默认18位
     * </p>
     */
    int value() default 18;
    /**
     * <p>
     * id的类型
     * </p>
     */
    Class clazz() default Long.class;
}