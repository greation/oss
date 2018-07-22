package com.ekeyfund.oss.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ekeyfund.oss.core.base.controller.BaseController;
import com.ekeyfund.oss.core.base.tips.Tip;
import com.ekeyfund.oss.core.common.annotion.BussinessLog;
import com.ekeyfund.oss.core.common.annotion.DataLog;
import com.ekeyfund.oss.core.common.annotion.Permission;
import com.ekeyfund.oss.core.common.constant.Const;
import com.ekeyfund.oss.core.common.constant.dictmap.MenuDict;
import com.ekeyfund.oss.core.common.constant.factory.ConstantFactory;
import com.ekeyfund.oss.core.common.constant.state.MenuStatus;
import com.ekeyfund.oss.core.common.exception.BizExceptionEnum;
import com.ekeyfund.oss.core.exception.GunsException;
import com.ekeyfund.oss.core.log.LogObjectHolder;
import com.ekeyfund.oss.core.node.ZTreeNode;
import com.ekeyfund.oss.core.support.BeanKit;
import com.ekeyfund.oss.core.util.ToolUtil;
import com.ekeyfund.oss.modular.system.model.Menu;
import com.ekeyfund.oss.modular.system.service.IMenuService;
import com.ekeyfund.oss.modular.system.warpper.MenuWarpper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static String PREFIX = "/system/menu/";

    @Autowired
    private IMenuService menuService;

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "菜单列表页面", notes = "跳转至菜单列表页面", tags = {"系统管理->菜单管理",})
    public String index() {
        return PREFIX + "menu.html";
    }

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping(value = "/menu_add", method = RequestMethod.GET)
    @ApiOperation(value = "菜单添加页面", notes = "跳转至菜单添加页面", tags = {"系统管理->菜单管理",})
    public String menuAdd() {
        return PREFIX + "menu_add.html";
    }

    /**
     * 跳转到菜单详情列表页面
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/menu_edit/{menuId}", method = RequestMethod.GET)
    @ApiOperation(value = "菜单编辑页面", notes = "跳转至菜单编辑页面", tags = {"系统管理->菜单管理",})
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "path", name="menuId", value = "菜单ID", required = true, dataType = "String"),
        @ApiImplicitParam(paramType = "body", name = "model", value = "数据项", dataType = "Model")}
    )
    public String menuEdit(@PathVariable Long menuId, Model model) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        Menu menu = this.menuService.selectById(menuId);

        //获取父级菜单的id
        Menu temp = new Menu();
        temp.setCode(menu.getPcode());
        Menu pMenu = this.menuService.selectOne(new EntityWrapper<>(temp));

        //如果父级是顶级菜单
        if (pMenu == null) {
            menu.setPcode("0");
        } else {
            //设置父级菜单的code为父级菜单的id
            menu.setPcode(String.valueOf(pMenu.getId()));
        }

        Map<String, Object> menuMap = BeanKit.beanToMap(menu);
        menuMap.put("pcodeName", ConstantFactory.me().getMenuNameByCode(temp.getCode()));
        model.addAttribute("menu", menuMap);
        LogObjectHolder.me().set(menu);
        return PREFIX + "menu_edit.html";
    }

    /**
     * 修该菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @BussinessLog(value = "修改菜单", key = "name",dict = MenuDict.class)
    @DataLog(value = "修改菜单",keyParam = "id",model = Menu.class,dict = MenuDict.class)
    @ResponseBody
    @ApiOperation(value = "修改菜单", tags = {"系统管理->菜单管理",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "menu", value = "菜单信息", required = true, dataType = "Menu"),
            @ApiImplicitParam(paramType = "body", name = "result", value = "绑定结果", dataType = "BindingResult")})
    public Tip edit(@Valid @RequestBody Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //设置父级菜单编号
        menuSetPcode(menu);

        this.menuService.updateById(menu);
        return SUCCESS_TIP;
    }

    /**
     * 获取菜单列表
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "获取菜单列表", tags = {"系统管理->菜单管理",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "menuName", value = "菜单名称", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "level", value = "菜单层级", dataType = "String")})
    public Object list(@RequestParam(required = false) String menuName, @RequestParam(required = false) String level) {
        List<Map<String, Object>> menus = this.menuService.selectMenus(menuName, level);
        return super.warpObject(new MenuWarpper(menus));
    }

    /**
     * 新增菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @BussinessLog(value = "菜单新增", key = "name", dict = MenuDict.class)
    @DataLog(value = "菜单新增",model = Menu.class,dict = MenuDict.class)
    @ResponseBody
    @ApiOperation(value = "添加菜单", tags = {"系统管理->菜单管理",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "menu", value = "菜单信息", required = true, dataType = "Menu"),
            @ApiImplicitParam(paramType = "body", name = "result", value = "绑定结果", dataType = "BindingResult")})
    public Tip add(@Valid @RequestBody Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        //判断是否存在该编号
        String existedMenuName = ConstantFactory.me().getMenuNameByCode(menu.getCode());
        if (ToolUtil.isNotEmpty(existedMenuName)) {
            throw new GunsException(BizExceptionEnum.EXISTED_THE_MENU);
        }

        //设置父级菜单编号
        menuSetPcode(menu);

        menu.setStatus(MenuStatus.ENABLE.getCode());
        this.menuService.insert(menu);
        //日志记录主键id
        LogObjectHolder.me().set(menu.getId());
        return SUCCESS_TIP;
    }

    /**
     * 删除菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @BussinessLog(value = "删除菜单", key = "menuId", dict = MenuDict.class)
    @DataLog(value = "删除菜单",model = Menu.class,keyParam = "menuId",dict = MenuDict.class)
    @ResponseBody
    @ApiOperation(value = "删除菜单", tags = {"系统管理->菜单管理",})
    @ApiImplicitParam(paramType = "query", name = "menuId", value = "菜单ID", required = true, dataType = "int")
    public Tip remove(@RequestParam Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        //缓存菜单的名称
        LogObjectHolder.me().set(ConstantFactory.me().getMenuName(menuId));

        this.menuService.delMenuContainSubMenus(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 查看菜单
     */
    @RequestMapping(value = "/view/{menuId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看菜单", tags = {"系统管理->菜单管理",})
    @ApiImplicitParam(paramType = "path", name = "menuId", value = "菜单ID", required = true, dataType = "int")
    public Tip view(@PathVariable Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        this.menuService.selectById(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 获取菜单列表(首页用)
     */
    @RequestMapping(value = "/menuTreeList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "获取菜单列表(首页用)", tags = {"系统管理->菜单管理",})
    public List<ZTreeNode> menuTreeList() {
        List<ZTreeNode> roleTreeList = this.menuService.menuTreeList();
        return roleTreeList;
    }

    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "获取菜单列表(选择父级菜单用)", tags = {"系统管理->菜单管理",})
    public List<ZTreeNode> selectMenuTreeList() {
        List<ZTreeNode> roleTreeList = this.menuService.menuTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/menuTreeListByRoleId/{roleId}", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "根据角色ID获取菜单列表", tags = {"系统管理->菜单管理",})
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "int")
    public List<ZTreeNode> menuTreeListByRoleId(@PathVariable Integer roleId) {
        List<Long> menuIds = this.menuService.getMenuIdsByRoleId(roleId);
        if (ToolUtil.isEmpty(menuIds)) {
            List<ZTreeNode> roleTreeList = this.menuService.menuTreeList();
            return roleTreeList;
        } else {
            List<ZTreeNode> roleTreeListByUserId = this.menuService.menuTreeListByMenuIds(menuIds);
            return roleTreeListByUserId;
        }
    }


    /**
     * 根据角色ID获取已选中菜单列表
     */
    @RequestMapping(value = "/checkedMenuTreeListByRoleId/{roleId}", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "根据角色ID获取菜单列表", tags = {"系统管理->菜单管理",})
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "int")
    public List<ZTreeNode> checkedMenuTreeListByRoleId(@PathVariable Integer roleId) {
        List<Long> menuIds = this.menuService.getMenuIdsByRoleId(roleId);
        List<ZTreeNode> selectMenuTreeNodeList = new ArrayList<>(16);
        List<ZTreeNode> returnMenuTreeNodeList = new ArrayList<>(16);
        if (ToolUtil.isEmpty(menuIds)) {
            selectMenuTreeNodeList = this.menuService.menuTreeList();
        } else {
            selectMenuTreeNodeList = this.menuService.menuTreeListByMenuIds(menuIds);
        }
        if(selectMenuTreeNodeList != null && selectMenuTreeNodeList.size()>0){
            for(ZTreeNode zTreeNode : selectMenuTreeNodeList){
                if(zTreeNode.getChecked()){
                    returnMenuTreeNodeList.add(zTreeNode);
                }
            }
        }
        return returnMenuTreeNodeList;
    }



    /**
     * 根据请求的父级菜单编号设置pcode和层级
     */
    private void menuSetPcode(@Valid @RequestBody Menu menu) {
        if (ToolUtil.isEmpty(menu.getPcode()) || menu.getPcode().equals("0")) {
            menu.setPcode("0");
            menu.setPcodes("[0],");
            menu.setLevels(1);
        } else {
            long code = Long.parseLong(menu.getPcode());
            Menu pMenu = menuService.selectById(code);
            Integer pLevels = pMenu.getLevels();
            menu.setPcode(pMenu.getCode());

            //如果编号和父编号一致会导致无限递归
            if (menu.getCode().equals(menu.getPcode())) {
                throw new GunsException(BizExceptionEnum.MENU_PCODE_COINCIDENCE);
            }

            menu.setLevels(pLevels + 1);
            menu.setPcodes(pMenu.getPcodes() + "[" + pMenu.getCode() + "],");
        }
    }

}
