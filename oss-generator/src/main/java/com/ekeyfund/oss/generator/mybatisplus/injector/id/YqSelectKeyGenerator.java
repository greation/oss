package com.ekeyfund.oss.generator.mybatisplus.injector.id;

import com.ekeyfund.oss.generator.mybatisplus.injector.incrementer.YqKeyGenerator;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

import java.sql.Statement;

/**
 *
 * Created by zhaolin on 3/29/2018.
 */
public class YqSelectKeyGenerator implements KeyGenerator {
    private final boolean executeBefore;
    private final YqKeyGenerator keyGenerator;
    private final String keyProperty;
    public YqSelectKeyGenerator(String keyProperty,YqKeyGenerator keyGenerator, boolean executeBefore) {
        this.keyProperty = keyProperty;
        this.executeBefore = executeBefore;
        this.keyGenerator = keyGenerator;
    }

    @Override
    public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        if(this.executeBefore) {
            this.processGeneratedKeys(executor, ms, parameter);
        }

    }
    @Override
    public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        if(!this.executeBefore) {
            this.processGeneratedKeys(executor, ms, parameter);
        }

    }

    private void processGeneratedKeys(Executor executor, MappedStatement ms, Object parameter) {
        try {
            if(keyProperty != null) {
                Configuration configuration = ms.getConfiguration();
                MetaObject metaParam = configuration.newMetaObject(parameter);
                Long value = keyGenerator.newKey();
                MetaObject metaResult = configuration.newMetaObject(value);
                if(metaResult.hasGetter(keyProperty)) {
                    this.setValue(metaParam, keyProperty, metaResult.getValue(keyProperty));
                } else {
                    this.setValue(metaParam, keyProperty, value);
                }
            }
        } catch (ExecutorException var10) {
            throw var10;
        } catch (Exception var11) {
            throw new ExecutorException("Error selecting key or setting result to parameter object. Cause: " + var11, var11);
        }
    }


    private void setValue(MetaObject metaParam, String property, Object value) {
        if(metaParam.hasSetter(property)) {
            metaParam.setValue(property, value);
        } else {
            throw new ExecutorException("No setter found for the keyProperty \'" + property + "\' in " + metaParam.getOriginalObject().getClass().getName() + ".");
        }
    }
}
