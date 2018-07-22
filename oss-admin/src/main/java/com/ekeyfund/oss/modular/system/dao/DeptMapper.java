package com.ekeyfund.oss.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ekeyfund.oss.core.node.ZTreeNode;
import com.ekeyfund.oss.modular.system.model.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 获取所有部门列表
     */
    List<Map<String, Object>> list(@Param("condition") String condition);

    /**
     * 根据多个id批量获取部门列表
     * @param deptIdList
     * @return List<Dept>
     */
    List<Dept> selectDeptByIds(@Param("deptIdList") List<Integer> deptIdList);
}