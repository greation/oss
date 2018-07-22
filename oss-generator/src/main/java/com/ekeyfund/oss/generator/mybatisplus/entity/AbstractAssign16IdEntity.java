package com.ekeyfund.oss.generator.mybatisplus.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.ekeyfund.oss.generator.mybatisplus.injector.id.YqIdGenerator;

/**
 * <pre>
 * 包含自定义分配主键的实体类型抽象类,如果不要自定义分配,可以不用继承改基类
 * </pre>
 * 
 * @author linriqing
 * @version 1.0, 2010-7-6
 */
public abstract class AbstractAssign16IdEntity<T extends Model> extends AbstractEntity<T> {
    private static final long serialVersionUID = 6757814772463743205L;

    @TableId
    @YqIdGenerator(16)
    protected Long id;


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
