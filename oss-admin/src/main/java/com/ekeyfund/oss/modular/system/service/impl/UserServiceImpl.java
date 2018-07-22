package com.ekeyfund.oss.modular.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ekeyfund.oss.core.datascope.DataScope;
import com.ekeyfund.oss.modular.system.dao.UserMapper;
import com.ekeyfund.oss.modular.system.model.User;
import com.ekeyfund.oss.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-02-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private  UserMapper userMapper;

    @Override
    public int setStatus(Integer userId, int status) {
        return this.baseMapper.setStatus(userId, status);
    }

    @Override
    public int setBatchStatus(List<Integer> userIds, int status) {
        return userMapper.setBatchStatus(userIds,status);
    }

    @Override
    public int changePwd(Integer userId, String pwd) {
        return this.baseMapper.changePwd(userId, pwd);
    }

    @Override
    public List<Map<String, Object>> selectUsers(Page<User> page, DataScope dataScope, String name,String account, String beginTime, String endTime, Integer deptid, String roleName, Integer status, String orderByField, boolean asc) {
        return this.baseMapper.selectUsers(page,dataScope, name,account, beginTime, endTime, deptid,roleName,status,orderByField,asc);
    }

    @Override
    public int setRoles(Integer userId, String roleIds) {
        return this.baseMapper.setRoles(userId, roleIds);
    }

    @Override
    public int changeDept(List<Integer> userIds, String deptId) {
        return this.baseMapper.changeDept(userIds,deptId);
    }

    @Override
    public User getByAccount(String account) {
        return this.baseMapper.getByAccount(account);
    }
}
