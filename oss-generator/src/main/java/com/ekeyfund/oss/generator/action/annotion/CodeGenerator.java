package com.ekeyfund.oss.generator.action.annotion;

import java.lang.annotation.*;

/**
 * 代码自动生成注解
 * Created by zhaolin on 4/3/2018.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CodeGenerator {
    boolean addVisiable() default  true;
    boolean editVisiable() default  true;
    boolean listVisiable() default  true;

    boolean addEditable() default  true;
    boolean editEditable() default  true;
    boolean listEditable() default  true;

    //是否隐藏，关联所有visiable
    boolean visible() default true;
    //是否可编辑，关联所有editable
    boolean editable() default true;
    /**
     * 是否忽略
     * @return
     */
    boolean ignore() default false;
    /**
     * 字段显示排序
     * @return
     */
    int order() default 0;

    /**
     * 显示表格是否可排序
     * @return
     */
    boolean sortable() default true;


}
