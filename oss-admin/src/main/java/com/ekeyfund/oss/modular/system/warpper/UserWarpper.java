package com.ekeyfund.oss.modular.system.warpper;

import com.ekeyfund.oss.core.base.warpper.BaseControllerWarpper;
import com.ekeyfund.oss.core.common.constant.factory.ConstantFactory;
import com.ekeyfund.oss.core.util.SpringContextHolder;
import com.ekeyfund.oss.modular.system.service.IDeptService;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class UserWarpper extends BaseControllerWarpper {

    public UserWarpper(List<Map<String, Object>> list) {
        super(list);
    }



    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("sexName", ConstantFactory.me().getSexName((Integer) map.get("sex")));
        map.put("roleName", ConstantFactory.me().getRoleName((String) map.get("roleid")));
        map.put("deptName", SpringContextHolder.getBean(IDeptService.class).getAllLevelDeptNameById((Integer) map.get("deptid")));
        map.put("statusName", ConstantFactory.me().getStatusName((Integer) map.get("status")));
        map.put("managerName",ConstantFactory.me().getManagerName((Integer)map.get("manager")));

    }

}
