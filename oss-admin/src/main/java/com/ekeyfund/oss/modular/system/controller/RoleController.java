package com.ekeyfund.oss.modular.system.controller;

import com.ekeyfund.oss.core.base.controller.BaseController;
import com.ekeyfund.oss.core.base.tips.Tip;
import com.ekeyfund.oss.core.cache.CacheKit;
import com.ekeyfund.oss.core.common.annotion.BussinessLog;
import com.ekeyfund.oss.core.common.annotion.DataLog;
import com.ekeyfund.oss.core.common.annotion.Permission;
import com.ekeyfund.oss.core.common.constant.Const;
import com.ekeyfund.oss.core.common.constant.cache.Cache;
import com.ekeyfund.oss.core.common.constant.dictmap.RoleDict;
import com.ekeyfund.oss.core.common.constant.factory.ConstantFactory;
import com.ekeyfund.oss.core.common.exception.BizExceptionEnum;
import com.ekeyfund.oss.core.exception.GunsException;
import com.ekeyfund.oss.core.log.LogObjectHolder;
import com.ekeyfund.oss.core.node.ZTreeNode;
import com.ekeyfund.oss.core.shiro.ShiroKit;
import com.ekeyfund.oss.core.util.Convert;
import com.ekeyfund.oss.core.util.ToolUtil;
import com.ekeyfund.oss.modular.system.model.OperationLog;
import com.ekeyfund.oss.modular.system.model.Role;
import com.ekeyfund.oss.modular.system.model.User;
import com.ekeyfund.oss.modular.system.service.IOperationLogService;
import com.ekeyfund.oss.modular.system.service.IRoleService;
import com.ekeyfund.oss.modular.system.service.IUserService;
import com.ekeyfund.oss.modular.system.warpper.OperationLogWrapper;
import com.ekeyfund.oss.modular.system.warpper.RoleWarpper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/role")
@Api(value = "角色管理")
public class RoleController extends BaseController {

    private static String PREFIX = "/system/role";

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IOperationLogService operationLogService;

    /**
     * 跳转到角色列表页面
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "角色列表页面", notes = "跳转至角色列表页面", tags = {"系统管理->角色管理",})
    public String index() {
        return PREFIX + "/role.html";
    }

    /**
     * 跳转到添加角色
     */
    @RequestMapping(value = "/role_add", method = RequestMethod.GET)
    @ApiOperation(value = "角色添加页面", notes = "跳转至角色添加页面", tags = {"系统管理->角色管理",})
    public String roleAdd() {
        return PREFIX + "/role_add.html";
    }

    /**
     * 跳转到修改角色
     */
    @Permission
    @RequestMapping(value = "/role_edit/{roleId}", method = RequestMethod.GET)
    @ApiOperation(value = "角色修改页面", notes = "跳转至角色修改页面", tags = {"系统管理->角色管理",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "body", name = "model", value = "数据项", dataType = "Model")})
    public String roleEdit(@PathVariable Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleService.selectById(roleId);
        model.addAttribute(role);
        model.addAttribute("pName", ConstantFactory.me().getSingleRoleName(role.getPid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(role.getDeptid()));
        LogObjectHolder.me().set(role);
        return PREFIX + "/role_edit.html";
    }

    /**
     * 跳转到角色分配
     */
    @Permission
    @RequestMapping(value = "/role_assign/{roleId}", method = RequestMethod.GET)
    @ApiOperation(value = "角色权限分配页面", notes = "跳转至角色权限分配页面", tags = {"系统管理->角色管理",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "body", name = "model", value = "数据项", dataType = "Model")})
    public String roleAssign(@PathVariable("roleId") Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleName", ConstantFactory.me().getSingleRoleName(roleId));
        return PREFIX + "/role_assign.html";
    }

    /**
     * 跳转到查看角色页面
     */
    @Permission
    @RequestMapping(value = "/view/{roleId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "角色详情页面", notes = "跳转至角色详情页面", tags = {"系统管理->角色管理",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "body", name = "model", value = "数据项", dataType = "Model")})
    public String roleView(@PathVariable Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleService.selectById(roleId);
        model.addAttribute(role);
        LogObjectHolder.me().set(role);
        return PREFIX + "/role_view.html";
    }

    /**
     * 跳转到查看角色日志页面
     */
    @Permission
    @RequestMapping(value = "/log/{roleId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "角色日志页面", notes = "跳转至角色日志页面", tags = {"系统管理->角色管理",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "body", name = "model", value = "数据项", dataType = "Model")})
    public String roleLog(@PathVariable Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleService.selectById(roleId);
        model.addAttribute(role);
        LogObjectHolder.me().set(role);
        return PREFIX + "/role_log.html";
    }
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^以上为页面路由^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    //————————————————以下为数据交互—————————————————————

    /**
     * 获取角色列表
     */
    @Permission
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "获取角色列表", notes = "根绝角色名称返回json格式角色列表数据", tags = {"系统管理->角色管理",})
    @ApiImplicitParam(paramType = "query", name = "roleName", value = "角色名称", dataType = "String")
    public Object list(@RequestParam(required = false) String roleName, @RequestParam(required = false) String status) {
        List<Map<String, Object>> roles = this.roleService.selectRoles(super.getPara("roleName"), super.getPara("status")!=null?super.getPara("status").equals("0")?null:super.getPara("status"):null);
        return super.warpObject(new RoleWarpper(roles));
    }

    /**
     * 角色新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @BussinessLog(value = "添加角色", key = "name", dict = RoleDict.class)
    @DataLog(value = "添加角色",model = Role.class,dict = RoleDict.class)
    @Permission
    @ResponseBody
    @ApiOperation(value = "添加角色", tags = {"系统管理->角色管理",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "role", value = "角色信息", required = true, dataType = "Role"),
            @ApiImplicitParam(paramType = "body", name = "result", value = "绑定结果", dataType = "BindingResult")})
    public Tip add(@Valid @RequestBody Role role, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //校验角色名称，不能与未删除角色名称重复
        Role tempRole = this.roleService.selectRoleByRoleName(role.getName());
        if(tempRole != null){
            throw new GunsException(BizExceptionEnum.ROLE_NAME_REPEAT);
        }
        //置空ID，由后端自动生成
        role.setId(null);
        //原型图没有父角色概念，暂时父ID统归于超级管理员同一级的父ID，即 0
        role.setPid(0);
        // 1目前对应的部门名称为总公司，角色暂定全挂在该部门下
        // 由于角色在本系统目前还没有归属于某部门的概念，为满足现有逻辑以及后续可能的变动，所以先全部挂在根部门下
        role.setDeptid(1);
        //添加新建时间、新建人、更新时间、更新人
        Integer userId = ShiroKit.getUser().getId();
        role.setCreatetime(new Date());
        role.setCreater(userId);
        role.setUpdatetime(new Date());
        role.setUpdater(userId);
        this.roleService.insert(role);
        //日志记录主键id
        LogObjectHolder.me().set(role.getId());
        return SUCCESS_TIP;
    }

    /**
     * 角色修改
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @BussinessLog(value = "修改角色", key = "name",dict = RoleDict.class)
    @DataLog(value = "修改角色",model = Role.class, keyParam = "id",dict = RoleDict.class)
    @Permission
    @ResponseBody
    @ApiOperation(value = "修改角色", tags = {"系统管理->角色管理",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "role", value = "角色信息", required = true, dataType = "Role"),
            @ApiImplicitParam(paramType = "body", name = "result", value = "绑定结果", dataType = "BindingResult")})
    public Tip edit(@Valid @RequestBody Role role, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //校验角色名称，不能与未删除角色名称重复
        Role tempRole = this.roleService.selectRoleByRoleName(role.getName());
        if(tempRole != null && !tempRole.getId().equals(role.getId())){
            throw new GunsException(BizExceptionEnum.ROLE_NAME_REPEAT);
        }
        //添加修改时间与更新人
        Integer userId = ShiroKit.getUser().getId();
        role.setUpdatetime(new Date());
        role.setUpdater(userId);
        this.roleService.updateById(role);

        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @BussinessLog(value = "删除角色", key = "roleId",dict = RoleDict.class)
    @DataLog(value = "删除角色",model = Role.class, keyParam = "roleId",dict = RoleDict.class)
    @Permission
    @ResponseBody
    @ApiOperation(value = "删除角色", tags = {"系统管理->角色管理",})
    @ApiImplicitParam(paramType = "query", name = "roleId", value = "角色ID", required = true, dataType = "int")
    public Tip remove(@RequestParam Integer roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        //不能删除超级管理员角色
        if (roleId.equals(Const.ADMIN_ROLE_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }

        //缓存被删除的角色名称
        LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));

        this.roleService.delRoleById(roleId);

        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 查看角色
     *//*
    @RequestMapping(value = "/view/{roleId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "查看角色信息", tags = {"系统管理->角色管理",})
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "int")
    public Tip view(@PathVariable Integer roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        this.roleService.selectById(roleId);
        return SUCCESS_TIP;
    }*/

    /**
     * 配置权限
     */
    @RequestMapping(value = "/setAuthority", method = {RequestMethod.GET, RequestMethod.POST})
    @BussinessLog(value = "配置权限", key = "roleId,ids",dict = RoleDict.class)
    @DataLog(value = "配置权限",model = Role.class, keyParam = "roleId",dict = RoleDict.class)
    @Permission
    @ResponseBody
    @ApiOperation(value = "配置角色权限", tags = {"系统管理->角色管理",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "roleId", value = "角色ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "ids", value = "权限ID组成的字符串", required = true, dataType = "String")})
    public Tip setAuthority(@RequestParam("roleId") Integer roleId, @RequestParam("ids") String ids) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        this.roleService.setAuthority(roleId, ids);
        return SUCCESS_TIP;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "获取角色树列表", tags = {"系统管理->角色管理",})
    public List<ZTreeNode> roleTreeList() {
        List<ZTreeNode> roleTreeList = this.roleService.roleTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeListByUserId/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "根据用户ID获取角色树列表", tags = {"系统管理->角色管理",})
    @ApiImplicitParam(paramType = "path", name = "userId", value = "用户ID", required = true, dataType = "String")
    public List<ZTreeNode> roleTreeListByUserId(@PathVariable Integer userId) {
        User theUser = this.userService.selectById(userId);
        String roleid = theUser.getRoleid();
        if (ToolUtil.isEmpty(roleid)) {
            List<ZTreeNode> roleTreeList = this.roleService.roleTreeList();
            return roleTreeList;
        } else {
            String[] strArray = Convert.toStrArray(",", roleid);
            List<ZTreeNode> roleTreeListByUserId = this.roleService.roleTreeListByRoleId(strArray);
            return roleTreeListByUserId;
        }
    }

    /**
     * 数据日志
     */
    @ApiOperation(value = "查看数据日志", notes = "查看数据日志", tags={ "系统管理->角色管理", })
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色Id", required = true, dataType = "int")
    @RequestMapping(value = "/operationLogs/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public Object operationLogs(@PathVariable Integer roleId) {
        OperationLog operationLog = new OperationLog();
        operationLog.setLogtype("业务日志");
        operationLog.setModelclass(Role.class.getName());
        operationLog.setKeyvalue(roleId + "");
        List list = operationLogService.getOperationLogsByCondition(operationLog);
        return super.warpObject(new OperationLogWrapper(list));
    }
}
