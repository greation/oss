package com.ekeyfund.oss.modular.system.controller;

import com.ekeyfund.oss.core.base.controller.BaseController;
import com.ekeyfund.oss.core.shiro.ShiroKit;
import com.ekeyfund.oss.modular.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限相关控制器
 * Created by zhaolin on 5/25/2018.
 */
@Controller
@RequestMapping("/permissions")
public class PermissionController extends BaseController{
    /**
     * 菜单服务
     */
    @Autowired
    private IMenuService menuService;

    /**
     * 从Menu去查询所有的权限列表
     * @return
     */
    @RequestMapping(value = "/{pcode}", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Object permissions(@PathVariable String pcode){
        Map<Object,Boolean> results = new HashMap();
        List<Map<String,Object>> menus = menuService.selectMenusByPcode(pcode);
        for(Map<String,Object> menu:menus){
            results.put(menu.get("url"), ShiroKit.hasPermission(""+menu.get("url")));
        }
        return results;
    }
}
