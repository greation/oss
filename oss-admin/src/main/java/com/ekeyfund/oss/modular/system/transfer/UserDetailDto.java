package com.ekeyfund.oss.modular.system.transfer;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Create by guanglei on 2018/4/9
 */
public class UserDetailDto  {

    /**
     * 员工编号
     */
    private String employeeNumber;
    /**
     * 登陆账号
     */
    private String account;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 岗位名称
     */
    private String jobs;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 是否为部门主管
     */
    private String manager;
    /**
     * 状态名称
     */
    private String statusName;
    /**
     * 角色名称
     */
    private String roleNames;
    /**
     * 最后登陆时间
     */
    private String lastLoginTime;
    /**
     * 最后登陆IP
     */
    private String lastLoginIp;


    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("employeeNumber", employeeNumber)
                .append("account", account)
                .append("departmentName", departmentName)
                .append("jobs", jobs)
                .append("email", email)
                .append("manager", manager)
                .append("statusName", statusName)
                .append("roleNames", roleNames)
                .append("lastLoginTime", lastLoginTime)
                .append("lastLoginIp", lastLoginIp)
                .toString();
    }
}
