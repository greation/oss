package com.ekeyfund.oss.generator.mybatisplus.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.ekeyfund.oss.generator.action.annotion.CodeGenerator;

/**
 * <pre>
 * 自增主键
 * </pre>
 * 
 * @author zhaolin
 * @version 1.0, 2018-4-4
 */
public abstract class AbstractAutoIdEntity<T extends Model> extends AbstractEntity<T> {
    private static final long serialVersionUID = 6757814772463743205L;

    @TableId(type= IdType.AUTO)
    @CodeGenerator(addVisiable = false,editEditable = false ,order = Integer.MIN_VALUE)
    protected Long id;


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
