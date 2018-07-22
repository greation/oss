package com.ekeyfund.oss.core.mutidatasource.annotion;

import com.ekeyfund.oss.core.constant.DatasourceEnum;

import java.lang.annotation.*;

/**
 * 
 * 多数据源标识
 *
 * @author fengshuonan
 * @date 2017年3月5日 上午9:44:24
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD,ElementType.TYPE })
public @interface DataSource {

	DatasourceEnum value() default DatasourceEnum.DATA_SOURCE_YQ_OSS;
}
