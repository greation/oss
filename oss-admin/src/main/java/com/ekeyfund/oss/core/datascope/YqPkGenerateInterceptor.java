package com.ekeyfund.oss.core.datascope;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.ekeyfund.oss.generator.mybatisplus.injector.id.ID;
import com.ekeyfund.oss.generator.mybatisplus.injector.id.YqIdGenerator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;

/**
 * 自定义主键值生成拦截器mybatis插件（暂时没有用，要加载到spring容器中即可）
 *
 * 在执行插入操作时
 * 检查是否是使用公司内部自定义生成主键的方式（有注解@TableId(value="id", type= IdType.INPUT)和@YqIdGenerator（18））
 * 如果是，则执行自定义生成固定位数的数字（ID类）作为主键值
 * 否则不进行任何处理
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class,Object.class }) })
public class YqPkGenerateInterceptor implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        doYqGenerateValid(invocation);
        return invocation.proceed();
    }

    /**
     * 拦截操作方法
     * @param invocation Invocation
     */
    private void doYqGenerateValid(Invocation invocation) {
        Object[] args = invocation.getArgs();
        //一般为2个参数，第一个参数是MappedStatement记录执行类型信息，第二个参数是要执行的实体，为了更好的兼容性这里的过滤条件为>=2
        if (args.length >= 2) {
            if (args[0] instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) args[0];
                SqlCommandType sqlCommandType = ms.getSqlCommandType();
                if (sqlCommandType == SqlCommandType.INSERT) {
                    Object obj = args[1];
                    if (obj instanceof Model) {
                        Field[] fields = obj.getClass().getDeclaredFields();
                        Arrays.stream(fields).filter(field -> field.getAnnotation(TableId.class) != null).findFirst()
                                .ifPresent((Field field) -> {
                                    YqIdGenerator idGenerator = field.getAnnotation(YqIdGenerator.class);
                                    if (null != idGenerator) {
                                        try {
                                            BeanUtils.setProperty(obj, field.getName(),ID.getInstanse().getID(idGenerator.value()));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                    }
                }
            }
        }
    }

    @Override public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override public void setProperties(Properties properties) {

    }
}