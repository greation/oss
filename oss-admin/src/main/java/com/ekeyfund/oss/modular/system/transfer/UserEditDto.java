package com.ekeyfund.oss.modular.system.transfer;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 编辑管理员的请求
 *
 * @author fengshuonan
 * @Date 2017年1月15日 下午10:29:16
 */
public class UserEditDto {

    private Integer id;
    private String account;
    @ApiModelProperty(hidden = true)
    private String password;
    @ApiModelProperty(hidden = true)
    private String salt;
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Integer sex;
    private String email;
    private String phone;
    private String roleid;
    private Integer deptid;
    private Integer status;
    private Date createtime;
    private Integer version;
    @ApiModelProperty(hidden = true)
    private String avatar;
    /**
     * 	岗位名称
     */

    private String jobs;
    /**
     * 是否此部门主管
     */
    private Integer manager;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("account", account)
                .append("password", password)
                .append("salt", salt)
                .append("name", name)
                .append("birthday", birthday)
                .append("sex", sex)
                .append("email", email)
                .append("phone", phone)
                .append("roleid", roleid)
                .append("deptid", deptid)
                .append("status", status)
                .append("createtime", createtime)
                .append("version", version)
                .append("avatar", avatar)
                .append("jobs", jobs)
                .append("manager", manager)
                .toString();
    }

}
