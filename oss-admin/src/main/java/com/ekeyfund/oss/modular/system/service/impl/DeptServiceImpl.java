package com.ekeyfund.oss.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ekeyfund.oss.core.common.exception.BizExceptionEnum;
import com.ekeyfund.oss.core.exception.GunsException;
import com.ekeyfund.oss.core.node.ZTreeNode;
import com.ekeyfund.oss.modular.system.dao.DeptMapper;
import com.ekeyfund.oss.modular.system.dao.UserMapper;
import com.ekeyfund.oss.modular.system.model.Dept;
import com.ekeyfund.oss.modular.system.model.User;
import com.ekeyfund.oss.modular.system.service.IDeptService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public void deleteDept(Integer deptId) {
        Dept dept = deptMapper.selectById(deptId);


        Wrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pids", "%[" + dept.getId() + "]%");
        Map<String,Object> eqMap = new HashMap<>();
        eqMap.put("delflag",0);
        wrapper.and().allEq(eqMap);

        List<Dept> subDepts = deptMapper.selectList(wrapper);
        if(CollectionUtils.isNotEmpty(subDepts)){
            throw new GunsException(BizExceptionEnum.DEPT_DEL_HAS_SUB_DEPT);
        }

        Map<String,Object> userMap = new HashMap<>();
        userMap.put("deptid",deptId);
        userMap.put("status",1);
        List<User> userList = userMapper.selectByMap(userMap);
        if(CollectionUtils.isNotEmpty(userList)){
            throw new GunsException(BizExceptionEnum.DEPT_DEL_HAS_USER);
        }

        dept.deleteById();
    }



    @Override
    public List<ZTreeNode> tree() {
        return this.baseMapper.tree();
    }

    @Override
    public List<Map<String, Object>> list(String condition) {
        List<Map<String, Object>> deptMapList =  this.baseMapper.list(condition);
        for(Map deptMap : deptMapList){
            Integer deptId = (Integer)deptMap.get("id");
            Long userCount = userMapper.getCountByDeptId(deptId);
            deptMap.put("usercount", userCount);
        }
        return deptMapList;
    }

    @Override
    public String getAllLevelDeptNameById(Integer deptId) {
        Dept dept = this.baseMapper.selectById(deptId);
        if(null == dept){
            return "";
        }

        String pids = dept.getPids();
        pids = pids.replaceAll("\\[","").replaceAll("]","");
        pids = pids.substring(0, pids.length()-1);
        List<Integer> pidList = Arrays.asList(pids.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Dept> deptList = this.baseMapper.selectDeptByIds(pidList);
        if(CollectionUtils.isEmpty(deptList)){
            return dept.getSimplename();
        }
        else {
            StringBuffer fullDeptName=new StringBuffer();
            for(int i=0;i<deptList.size();i++){
                Dept deptTmp=deptList.get(i);
                fullDeptName.append(deptTmp.getSimplename()+"/");
            }
            fullDeptName.append(dept.getSimplename());
            return fullDeptName.toString();
        }
    }


}
