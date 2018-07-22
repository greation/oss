package com.ekeyfund.oss.core.common.annotion;

import com.baomidou.mybatisplus.activerecord.Model;
import com.ekeyfund.oss.core.common.constant.dictmap.base.AbstractDictMap;
import com.ekeyfund.oss.core.common.constant.dictmap.base.SystemDict;

import java.lang.annotation.*;

/**
 * 标记需要做数据变更日志的方法
 *
 * @author zhaolin
 * @date 2018-04-02 15:35
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataLog {

    /**
     * 数据变更业务的名称,例如:"修改菜单"
     */
    String value() default "";

    /**
     * 被修改数据的主键参数
     */
    String keyParam() default "id";

    /**
     * 字典(用于查找key的中文名称和字段的中文名称)
     */
    Class<? extends AbstractDictMap> dict() default SystemDict.class;

    /**
     * 数据对应的Model，方便记录到数据变更
     * @return
     */
    Class<? extends Model> model();

}
