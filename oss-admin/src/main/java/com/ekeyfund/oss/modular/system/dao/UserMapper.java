package com.ekeyfund.oss.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ekeyfund.oss.core.datascope.DataScope;
import com.ekeyfund.oss.modular.system.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 修改用户状态
     */
    int setStatus(@Param("userId") Integer userId, @Param("status") int status);

    int setBatchStatus(@Param("userIds") List<Integer> userIds, @Param("status") int status);


    /**
     * 修改密码
     */
    int changePwd(@Param("userId") Integer userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     */
    List<Map<String, Object>> selectUsers(@Param("page")Page<User> page, @Param("dataScope") DataScope dataScope, @Param("name") String name, @Param("account") String account, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptid") Integer deptid, @Param("roleName") String roleName, @Param("status") Integer status, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

    /**
     * 设置用户的角色
     */
    int setRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds);

    int changeDept(@Param("userIds")  List<Integer> userIds, @Param("deptId") String deptId);

    /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);

    /**
     * 根据部门Id获取用户数量
     * @param deptId Integer
     * @return Long
     */
    Long getCountByDeptId(@Param("deptId")Integer deptId);
}