package com.ekeyfund.oss.modular.demo.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ekeyfund.oss.generator.mybatisplus.entity.AbstractAssign18IdEntity;

/**
 * <p>
 * 模板表
 * </p>
 *
 * @author ekeyfund123
 * @since 2018-05-31
 */
@TableName("demo_test")
public class Test extends AbstractAssign18IdEntity<Test> {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String value;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Test{" +
        "value=" + value +
        "}";
    }
}
