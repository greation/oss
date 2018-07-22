package com.ekeyfund.oss.modular.system.warpper;

import com.ekeyfund.oss.core.base.warpper.BaseControllerWarpper;
import com.ekeyfund.oss.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 角色列表的包装类
 *
 * @author fengshuonan
 * @date 2017年2月19日10:59:02
 */
public class RoleWarpper extends BaseControllerWarpper {

    public RoleWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        //移除父节点名称转换与部门名称转换
/*        map.put("pName", ConstantFactory.me().getSingleRoleName((Integer) map.get("pid")));
        map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("deptid")));*/
        map.put("status", (Integer)map.get("status") == 1 ? "正常":"冻结");
    }

}
