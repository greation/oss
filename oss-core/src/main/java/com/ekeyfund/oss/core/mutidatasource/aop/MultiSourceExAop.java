package com.ekeyfund.oss.core.mutidatasource.aop;

import com.ekeyfund.oss.core.constant.DatasourceEnum;
import com.ekeyfund.oss.core.mutidatasource.DataSourceContextHolder;
import com.ekeyfund.oss.core.mutidatasource.annotion.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源切换的aop
 *
 * @author fengshuonan
 * @date 2017年3月5日 上午10:22:16
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "guns", name = "muti-datasource-open", havingValue = "true")
public class MultiSourceExAop implements Ordered {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${guns.defaultDataSourceName}")
    private String defaultDataSourceName = DatasourceEnum.DATA_SOURCE_YQ_OSS.name();

    //@Pointcut(value = "@annotation(com.ekeyfund.oss.core.mutidatasource.annotion.DataSource)")
    //@Pointcut("within( com.ekeyfund.oss.modular.*.service..*)")
    @Pointcut("execution(public * com.baomidou.mybatisplus.service.IService+.*(..))")
    private void cut() {

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;

        Object target = point.getTarget();
        //优先处理类上的DataSource注解
        DataSource dataSourceOnClass = target.getClass().getAnnotation(DataSource.class);
        if(dataSourceOnClass!=null){
            DataSourceContextHolder.setDataSourceType(dataSourceOnClass.value().name());
            log.info("设置数据源为：" + dataSourceOnClass.value().name());
        }
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        //如果方法上有DataSource注解，覆盖类上的DataSource注解
        DataSource datasourceOnMethod = currentMethod.getAnnotation(DataSource.class);
        if (datasourceOnMethod != null) {
            DataSourceContextHolder.setDataSourceType(datasourceOnMethod.value().name());
            log.info("设置数据源为：" + datasourceOnMethod.value().name());
        }
        //如果方法和类上都没有注解，则使用默认的DataSource
        if(dataSourceOnClass==null&&datasourceOnMethod==null){
            DataSourceContextHolder.setDataSourceType(defaultDataSourceName);
            log.info("设置数据源为："+defaultDataSourceName);
        }

        try {
            return point.proceed();
        } finally {
            log.info("清空数据源信息！");
            DataSourceContextHolder.clearDataSourceType();
        }
    }


    /**
     * aop的顺序要早于spring的事务
     */
    @Override
    public int getOrder() {
        return 1;
    }

}
