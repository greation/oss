package com.ekeyfund.oss.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
@TableName("sys_role")
@ApiModel
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(name = "id", value = "角色ID")
	private Integer id;
    /**
     * 序号
     */
	@ApiModelProperty(name = "num", value = "序号",hidden = true)
	private Integer num;
    /**
     * 父角色id
     */
	@ApiModelProperty(name = "pid", value = "父角色ID",hidden = true)
	private Integer pid;
    /**
     * 角色名称
     */
	@ApiModelProperty(name = "name", value = "角色名称", required = true)
	private String name;
    /**
     * 部门名称
     */
	@ApiModelProperty(name = "deptid", value = "部门ID", hidden = true)
	private Integer deptid;
    /**
     * 部门描述
     */
	@ApiModelProperty(name = "tips", value = "角色描述")
	private String tips;
    /**
     * 保留字段(暂时没用）
     */
	@ApiModelProperty(name = "version", value = "保留字段(暂未使用)", hidden = true)
	private Integer version;

	/**
	 * 逻辑删除标记，必须添加@TableLogic注解
	 */
	@TableLogic
	@ApiModelProperty(name = "delflag", value = "删除标记", hidden = true)
	private Integer delflag;

	/**
	 * 状态(1：正常  2：冻结）
	 */
	@ApiModelProperty(name = "status", value = "角色状态(1：正常  2：冻结）")
	private Integer status;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(name = "createtime", value = "创建时间", hidden = true)
	private Date createtime;

	/**
	 * 创建人
	 */
	@ApiModelProperty(name = "creater", value = "创建人", hidden = true)
	private Integer creater;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(name = "updatetime", value = "修改时间", hidden = true)
	private Date updatetime;

	/**
	 * 更新人
	 */
	@ApiModelProperty(name = "updater", value = "修改人", hidden = true)
	private Integer updater;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getCreater() {
		return creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getUpdater() {
		return updater;
	}

	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Role{" +
			"id=" + id +
			", num=" + num +
			", pid=" + pid +
			", name=" + name +
			", deptid=" + deptid +
			", tips=" + tips +
			", version=" + version +
			"}";
	}
}
