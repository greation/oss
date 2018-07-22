/**
 * <pre>
 * Title: 		AbstractEntity.java
 * Author:		linriqing
 * Create:	 	2010-7-6 上午11:40:21
 * Copyright: 	Copyright (c) 2010
 * Company:		Shenzhen Helper
 * <pre>
 */
package com.ekeyfund.oss.generator.mybatisplus.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.ekeyfund.oss.core.support.DateTimeKit;
import com.ekeyfund.oss.generator.action.annotion.CodeGenerator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <pre>
 * 基类
 * </pre>
 * 
 * @author linriqing
 * @version 1.0, 2010-7-6
 */
public abstract class AbstractEntity<T extends Model> extends Model<T> implements AuditedEntity {
    public final static int SEQALLOCATIONSIZE = 1000;
    public final static int SEQINITNUM = 1;

    private static final long serialVersionUID = 6757814772463743205L;
    /**
     * 版本号
     */
    @CodeGenerator(ignore = true)
    private Long version = 0L;
    /**
     * 逻辑删除状态
     */
    @CodeGenerator(ignore = true)
    @TableLogic
    private Integer delflag = 0;

    /**
     * 数据角色id
     */
    @CodeGenerator(ignore = true)
    private Long roleId = 0L;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern= DateTimeKit.NORM_DATETIME_PATTERN)
    @CodeGenerator(editable = false,order = Integer.MAX_VALUE)
    private Date createtime;
    /**
     * 创建人
     */
    @CodeGenerator(editable = false,sortable = false,order = Integer.MAX_VALUE)
    private Integer creater;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern= DateTimeKit.NORM_DATETIME_PATTERN)
    @CodeGenerator(editable = false,sortable = true,order = Integer.MAX_VALUE)
    private Date updatetime;
    /**
     * 更新人
     */
    @CodeGenerator(editable = false,sortable = false,order = Integer.MAX_VALUE)
    private Integer updater;
    @Override
    public abstract Object getId();

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    /**
     * <pre>
     * 通用的枚举类型(是, 否)
     * </pre>
     *
     * @author linriqing
     * @version 1.0, 2010-7-19
     */
    public static enum BoolValue {
        /**
         * 否
         */
        NO("否", false),
        /**
         * 是
         */
        YES("是", true);

        private String text;

        private Boolean value;

        BoolValue(String text, Boolean value) {
            this.text = text;
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public Boolean getValue() {
            return value;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setValue(Boolean value) {
            this.value = value;
        }

        public static BoolValue valueOf(boolean value) {
            if (value) {
                return BoolValue.YES;
            } else {
                return BoolValue.NO;
            }
        }
    }

    /**
     * <pre>
     * 证件类型枚举
     * </pre>
     *
     * @author linriqing
     * @version 1.0, 2010-7-19
     */
    public static enum IdCardType {
        /**
         * 身份证
         */
        IDCARD("身份证"),
        /**
         * 护照
         */
        PASSPORT("护照"),
        /**
         * 军官证
         */
        SOLDIERCARD("军官证"),
        /**
         * 士兵证
         */
        XSOLDIERCARD("士兵证"),
        /**
         * 港澳台胞证
         */
        HK2MACAU2TAICARD("港澳台胞证"),
        /**
         * 其它证件
         */
        OTHERCARD("其它证");
        private String text;

        IdCardType(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
