package com.ekeyfund.oss.modular.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ekeyfund.oss.core.datascope.DataScope;
import com.ekeyfund.oss.modular.system.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-02-22
 */
public interface IUserService extends IService<User> {

    /**
     * 修改用户状态
     */
    int setStatus(@Param("userId") Integer userId, @Param("status") int status);

    /**
     * 批量冻结用户
     * @param userIds
     * @param status
     * @return
     */
    int setBatchStatus(@Param("userIds") List<Integer> userIds, @Param("status") int status);

    /**
     * 修改密码
     */
    int changePwd(@Param("userId") Integer userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     */
    List<Map<String, Object>> selectUsers(Page<User> page, @Param("dataScope") DataScope dataScope, @Param("name") String name,@Param("account") String account, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptid") Integer deptid, @Param("name") String roleName, Integer status,String orderByField, boolean asc);

    /**
     * 设置用户的角色
     */
    int setRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds);


    /**
     * 修改批量用户的部门
     */
    int changeDept(@Param("userIds")  List<Integer> userIds, @Param("deptId") String deptId);

    /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);

}
