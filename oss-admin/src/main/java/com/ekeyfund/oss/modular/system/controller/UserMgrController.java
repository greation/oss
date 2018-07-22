package com.ekeyfund.oss.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.ekeyfund.oss.config.properties.GunsProperties;
import com.ekeyfund.oss.config.properties.YQProperties;
import com.ekeyfund.oss.core.base.controller.BaseController;
import com.ekeyfund.oss.core.base.tips.Tip;
import com.ekeyfund.oss.core.base.tips.UserTip;
import com.ekeyfund.oss.core.common.annotion.BussinessLog;
import com.ekeyfund.oss.core.common.annotion.DataLog;
import com.ekeyfund.oss.core.common.annotion.Permission;
import com.ekeyfund.oss.core.common.constant.Const;
import com.ekeyfund.oss.core.common.constant.dictmap.UserDict;
import com.ekeyfund.oss.core.common.constant.factory.ConstantFactory;
import com.ekeyfund.oss.core.common.constant.factory.PageFactory;
import com.ekeyfund.oss.core.common.constant.state.ManagerStatus;
import com.ekeyfund.oss.core.common.exception.BizExceptionEnum;
import com.ekeyfund.oss.core.datascope.DataScope;
import com.ekeyfund.oss.core.db.Db;
import com.ekeyfund.oss.core.exception.GunsException;
import com.ekeyfund.oss.core.log.LogObjectHolder;
import com.ekeyfund.oss.core.shiro.ShiroKit;
import com.ekeyfund.oss.core.util.ToolUtil;
import com.ekeyfund.oss.modular.system.dao.UserMapper;
import com.ekeyfund.oss.modular.system.factory.UserFactory;
import com.ekeyfund.oss.modular.system.model.Dept;
import com.ekeyfund.oss.modular.system.model.LoginLog;
import com.ekeyfund.oss.modular.system.model.OperationLog;
import com.ekeyfund.oss.modular.system.model.User;
import com.ekeyfund.oss.modular.system.service.*;
import com.ekeyfund.oss.modular.system.transfer.SmsSendResponse;
import com.ekeyfund.oss.modular.system.transfer.UserDto;
import com.ekeyfund.oss.modular.system.transfer.UserEditDto;
import com.ekeyfund.oss.modular.system.warpper.OperationLogWrapper;
import com.ekeyfund.oss.modular.system.warpper.UserWarpper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 系统管理员控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/mgr")
public class UserMgrController extends BaseController {

    private static final Logger LOGGER=LoggerFactory.getLogger(UserMgrController.class);

    private static String PREFIX = "/system/user/";
    @Autowired
    private IUserService userService;
    @Autowired
    private GunsProperties gunsProperties;
    @Autowired
    private YQProperties yqProperties;
    @Autowired
    private SmsService smsService;

    @Autowired
    private IOperationLogService operationLogService;

    @Autowired
    private ILoginLogService loginLogService;
    @Autowired
    private IDeptService deptService;

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping(value = "/user_add",method = RequestMethod.GET)
    @ApiOperation(value = "用户管理默认页", notes = "跳转到用户管理默认页面", tags={ "系统管理->用户管理", })
    public String toUserAddView() {
        return PREFIX + "user_add.html";
    }

    /**
     * 跳转到员工修改部门的页面
     */
    @RequestMapping(value = "/change_dept_view/{userIds}",method = RequestMethod.GET)
    @ApiOperation(value = "用户修改部门页面", notes = "用户修改部门页面", tags={ "系统管理->用户管理", })
    public String toUserChangeDeptView(@PathVariable String userIds, Model model){
        model.addAttribute("userIds", userIds);
        return PREFIX+"user_chdept.html";

    }



    /**
     * 跳转到查看用户操作日志页面
     */
    @RequestMapping(value = "/user_log_view/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "查看用户操作日志页面", notes = "查看用户操作日志页面", tags={ "系统管理->用户管理", })
    @Permission
    public String toUserLogView(@PathVariable Integer userId, Model model){
        if(ToolUtil.isEmpty(userId)){
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user =userService.selectById(userId);
        LogObjectHolder.me().set(user);
        model.addAttribute("userId", userId);
        return PREFIX+"user_log.html";

    }

    /**
     * 跳转到角色分配页面
     */
    //@RequiresPermissions("/mgr/role_assign")  //利用shiro自带的权限检查
    @Permission
    @RequestMapping("/role_assign/{userId}")
    public String toRoleAssignView(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = (User) Db.create(UserMapper.class).selectOneByCon("id", userId);
        model.addAttribute("userId", userId);
        model.addAttribute("userAccount", user.getAccount());
        return PREFIX + "user_roleassign.html";
    }

    /**
     * 跳转到编辑管理员页面
     */
    @Permission
    @RequestMapping(value = "/user_edit/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "跳转到编辑用户页面", notes = "跳转到编辑用户页面", tags={ "系统管理->用户管理", })
    public String toUserEditView(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        User user=wrapperUserOnView(userId,model);
        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * 跳转到修改密码界面
     */
    @RequestMapping("/user_chpwd")
    public String toUserChangePwd() {
        return PREFIX + "user_chpwd.html";
    }

    /**
     * 跳转到查看用户详情页面
     */
    @RequestMapping(value = "/user_info",method = RequestMethod.GET)
    @ApiOperation(value = "跳转到查看用户详情页面", notes = "跳转到查看用户详情页面", tags={ "系统管理->用户管理" })
    public String toUserDetailView(Model model) {
        Integer userId = ShiroKit.getUser().getId();
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
       User user=wrapperUserOnView(userId,model);
        LogObjectHolder.me().set(user);
        return PREFIX + "user_view.html";
    }

    @ApiOperation(value = "跳转到根据ID查看用户详情页面", notes = "跳转到根据ID查看用户详情页面", tags={ "系统管理->用户管理" })
    @RequestMapping(value = "/user_detail/{userId}",method = RequestMethod.GET)
    public String userDetailInfo(@PathVariable Integer userId, Model model){

        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = wrapperUserOnView(userId, model);
        LogObjectHolder.me().set(user);
        return PREFIX + "user_detail.html";
    }

    private User wrapperUserOnView( Integer userId, Model model) {
        User user = this.userService.selectById(userId);
        LoginLog loginLog=loginLogService.getLoginLog(user.getId());
        model.addAttribute(user);
        model.addAttribute("sexName", ConstantFactory.me().getSexName(user.getSex()));
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("statusName", ConstantFactory.me().getStatusName(user.getStatus()));
        model.addAttribute("deptName",deptService.getAllLevelDeptNameById(user.getDeptid()));
        //是否此部门主管
        String manager=user.getManager().equals(Integer.valueOf(1))?"是":"否";
        model.addAttribute("manager",manager);
        String employeeNumber=user.getPrefix()+String.format("%04d",user.getId());
        //增加员工编号
        model.addAttribute("employeeNumber",employeeNumber);
        //增加登录日志信息
        if(null!=loginLog){
            model.addAttribute("lastLoginTime",loginLog.getCreatetime());
            model.addAttribute("lastLoginIp",loginLog.getIp());
        }
        return user;
    }


    /**
     * 修改当前用户的密码
     */
    @RequestMapping(value = "/changePwd",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户修改密码",notes = "用户修改密码",tags = "系统管理->用户管理")
    @ApiImplicitParams(
            { @ApiImplicitParam(name = "oldPwd" ,value="旧密码",required = true,dataType = "String",paramType = "query"),
                    @ApiImplicitParam(name = "newPwd" ,value="新密码",required = true,dataType = "String",paramType = "query"),
                    @ApiImplicitParam(name = "rePwd" ,value="确认新密码",required = true,dataType = "String",paramType = "query"),
            }
    )
    @BussinessLog(value = "修改密码", key = "account", dict = UserDict.class)
    @DataLog(value = "修改密码",model =User.class, dict = UserDict.class)
    public Object userChangePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestParam String rePwd) {
        if(StringUtils.isEmpty(oldPwd)||StringUtils.isEmpty(newPwd)||StringUtils.isEmpty(rePwd)){
            throw new GunsException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        if (!newPwd.equals(rePwd)) {
            throw new GunsException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        Integer userId = ShiroKit.getUser().getId();
        User user = userService.selectById(userId);
        String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
            user.setPassword(newMd5);
            //缓存数据更新之前的旧值
            LogObjectHolder.me().set(UserFactory.createUserDto(user));
            user.updateById();
            return SUCCESS_TIP;
        } else {
            throw new GunsException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }

    /**
     * 查询管理员列表
     */
    @RequestMapping(value = "/list",method ={ RequestMethod.POST,RequestMethod.GET})
    @Permission
    @ResponseBody
    @ApiOperation(value = "根据条件获取员工信息列表",notes = "根据条件获取员工信息列表",tags = "系统管理->用户管理")
    @ApiImplicitParams(
            { @ApiImplicitParam(name = "name" ,value="员工姓名",required = false,dataType = "String",paramType = "query"),
                    @ApiImplicitParam(name = "account" ,value="员工账号",required = false,dataType = "String",paramType = "query"),
                    @ApiImplicitParam(name = "beginTime" ,value="注册开始日期",required = false,dataType = "String",paramType = "query"),
                    @ApiImplicitParam(name = "endTime" ,value="注册结束日期",required = false,dataType = "String",paramType = "query"),
                    @ApiImplicitParam(name = "deptid" ,value="部门编号",required = false,dataType = "int",paramType = "query"),
                    @ApiImplicitParam(name = "roleName" ,value="角色名称",required = false,dataType = "String",paramType = "query"),
                    @ApiImplicitParam(name = "status" ,value="状态",required = false,dataType = "int",paramType = "query"),
                    @ApiImplicitParam(name = "limit" ,value="每页多少条数据",required = true,dataType = "int",paramType = "query"),
                    @ApiImplicitParam(name = "offset" ,value="每页的偏移量(本页当前有多少条)",required = true,dataType = "int",paramType = "query")
            }
    )
    public Object userList(@RequestParam(required = false) String name, @RequestParam(required = false) String account,@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid,@RequestParam(required = false) String  roleName,@RequestParam(required = false)Integer status) {
        Page<User> page =new PageFactory<User>().defaultPage();

        if (ShiroKit.isAdmin()) {
            List<Map<String, Object>> users = userService.selectUsers(page,null, name,account, beginTime, endTime, deptid,roleName,status, page.getOrderByField(), page.isAsc());
            page.setRecords((List<User>)new UserWarpper(users).warp());
            return super.packForBT(page);
        } else {
            DataScope dataScope = new DataScope(ShiroKit.getDeptDataScope());
            List<Map<String, Object>> users = userService.selectUsers(page,dataScope, name, account,beginTime, endTime, deptid,roleName,status, page.getOrderByField(), page.isAsc());
            page.setRecords((List<User>)new UserWarpper(users).warp());
            return super.packForBT(page);
        }
    }

    /**
     * 添加管理员
     */
    @RequestMapping(value="/add",method = RequestMethod.POST)
    @BussinessLog(value = "新增员工", key = "account", dict = UserDict.class)
    @DataLog(value = "新增员工",keyParam ="id",model =User.class, dict = UserDict.class)
    @Permission
    @ResponseBody
    @ApiOperation(value = "新增用户",notes = "新增用户",tags = "系统管理->用户管理")
    @ApiImplicitParam(name = "user",value = "新增员工对象" ,required = true,dataType = "UserDto")
    public Tip userAdd(@Valid  @RequestBody  UserDto user, BindingResult result) {
        UserTip userTip=new UserTip();
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        checkUserRoleId(user.getRoleid());
        // 判断账号是否重复
        User theUser = userService.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new GunsException(BizExceptionEnum.USER_ALREADY_REG);
        }
        Dept dept=deptService.selectById(user.getDeptid());

        if(null==dept){
            userTip.setCode(500);
            userTip.setMessage("当前员工没有设置部门");
            return userTip;
        }
        if(null!=dept&&Integer.valueOf(1).equals(dept.getDelflag())){
            userTip.setCode(500);
            userTip.setMessage("用户所在的"+dept.getSimplename()+"被删除，需调整用户所属部门");
            return userTip;
        }
        // 完善账号信息
        user.setSalt(ShiroKit.getRandomSalt(5));
        //生成随机密码
        String randomPassword=ToolUtil.generatorRandomPassword();
        user.setPassword(randomPassword);
        //MD5加密
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setCreatetime(new Date());
        User userEntity = UserFactory.createUser(user);
        LOGGER.info("新增用户信息内容为"+userEntity);
        boolean insertFlag= this.userService.insert(userEntity);
        //日志记录主键id
        LogObjectHolder.me().set(userEntity.getId());
        if(insertFlag==false){
            userTip.setCode(500);
            userTip.setMessage("新增用户失败");
            return userTip;
        }
        else {
            String msg = yqProperties.getSign()+userEntity.getAccount() + "用户您好!，您的密码为" + randomPassword;
            LOGGER.info("新增用户"+userEntity.getAccount()+"的密码为" + randomPassword);
                SmsSendResponse smsSendResponse= smsService.sendSms(msg,userEntity.getAccount());
                if(null!=smsSendResponse){
                    if(StringUtils.isNotEmpty(smsSendResponse.getCode())&&"0".equals(smsSendResponse.getCode())&&insertFlag==true){
                        userTip.setMessage("新增用户成功");
                        userTip.setCode(200);
                        LOGGER.info("新增用户成功");
                    }
                    else {
                        userTip.setMessage("新增用户成功，短信发送失败，原因是" + smsSendResponse.getErrorMsg());
                        userTip.setCode(500);
                        LOGGER.error("新增用户成功，短信发送失败，原因是" +smsSendResponse.getErrorMsg());
                    }
                }

            return userTip;
        }
    }

    /**
     * 修改管理员
     *
     * @throws NoPermissionException
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @BussinessLog(value = "修改员工", key = "account",dict = UserDict.class)
    @DataLog(value = "修改员工",model =User.class,dict = UserDict.class)
    @ResponseBody
    @ApiOperation(value = "修改员工",notes = "修改员工",tags = "系统管理->用户管理")
    @ApiImplicitParam(name = "user" ,value = "修改员工对象",required = true,dataType = "UserEditDto")
    public Tip userEdit(@Valid @RequestBody UserEditDto user, BindingResult result) throws NoPermissionException {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        LOGGER.info("修改用户信息，传递的参数是"+user);

        if(user.getDeptid()==null){
            throw new GunsException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        if(null!=user.getDeptid()){
            Dept dept=deptService.selectById(user.getDeptid());
            if(null==dept){
                UserTip userTip=new UserTip();
                userTip.setCode(500);
                userTip.setMessage("用户编辑的部门不存在");
                return userTip;
            }
        }

        checkUserRoleId(user.getRoleid());
//        if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
            //缓存数据更新之前的旧值
            LogObjectHolder.me().set(this.userService.selectById(user.getId()));
            this.userService.updateById(UserFactory.createUser(user));
//            return SUCCESS_TIP;
//        } else {
            assertAuth(user.getId());
            //ShiroUser shiroUser = ShiroKit.getUser();
            //if (shiroUser.getId().equals(user.getId())) {
                this.userService.updateById(UserFactory.createUser(user));
                return SUCCESS_TIP;
//            } else {
//                throw new GunsException(BizExceptionEnum.NO_PERMITION);
//            }
//        }
    }

    /**
     * 校验角色ID的合法性
     * @param roleId
     */
    private void checkUserRoleId(String roleId) {
        if(org.apache.commons.lang.StringUtils.isNotEmpty(roleId)){
            String[]roleIds=roleId.split(",");
            if(roleIds.length==1){
                boolean flag=roleIds[0].matches("^-?[1-9]\\d*$");
                if(flag==false){
                    throw new GunsException(BizExceptionEnum.REQUEST_INVALIDATE);
                }
            }
            //多个角色
            else if(null!=roleIds&&roleIds.length>1){
                for(int i=0;i<roleIds.length;i++){
                    boolean flag=roleIds[i].matches("^-?[1-9]\\d*$");
                    if(flag==false){
                        throw new GunsException(BizExceptionEnum.REQUEST_INVALIDATE);
                    }
                }
            }
        }
        else{
            throw new GunsException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
    }

    /**
     * 删除管理员（逻辑删除）
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @BussinessLog(value = "删除管理员", key = "id",dict = UserDict.class)
    @DataLog(value = "删除管理员",model =User.class, keyParam = "id",dict = UserDict.class)
    @Permission
    @ResponseBody
    @ApiOperation(value = "删除用户",notes = "删除用户",tags = "系统管理->用户管理")
    @ApiImplicitParam(name = "userId" ,value = "用户id",required = true,paramType = "query",dataType = "int")
    public Tip userDelete(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.DELETED.getCode());
        return SUCCESS_TIP;
    }



    /**
     * 重置管理员的密码
     */
    @RequestMapping(value="/reset",method = RequestMethod.POST)
    @BussinessLog(value = "重置管理员密码", key = "account",dict = UserDict.class)
    @DataLog(value = "重置管理员密码",model =User.class, keyParam = "id",dict = UserDict.class)
    @Permission
    @ResponseBody
    @ApiOperation(value = "重置密码",notes = "重置密码",tags = "系统管理->用户管理")
    @ApiImplicitParam(name = "userId" ,value="用户编号",required = true,dataType = "int",paramType = "query")
    public Tip userResetPassword(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        UserTip userTip=new UserTip();
        User user = this.userService.selectById(userId);
        if(user.getStatus().equals(Integer.valueOf(2))){
            userTip.setMessage("冻结用户不支持重置密码");
            userTip.setCode(500);
            return userTip;
        }
        if(user.getStatus().equals(Integer.valueOf(3))){
            userTip.setMessage("已删除用户不支持重置密码");
            userTip.setCode(500);
            return userTip;
        }
        user.setSalt(ShiroKit.getRandomSalt(5));
        String randomPassword=ToolUtil.generatorRandomPassword();
        user.setPassword(ShiroKit.md5(randomPassword, user.getSalt()));
        LogObjectHolder.me().set(user);

        boolean flag= this.userService.updateById(user);

        if(flag==false){
            userTip.setCode(500);
            userTip.setMessage("密码重置入库失败");
            return userTip;
        }
        else{
            String msg=yqProperties.getSign()+user.getName()+" 您好！您的密码已重置成功，新密码为 "+randomPassword;
            LOGGER.info("用户为"+user.getAccount()+"重置密码后密码为" + randomPassword);
            SmsSendResponse smsSendResponse= smsService.sendSms(msg,user.getAccount());
            if(null!=smsSendResponse){
                if(StringUtils.isNotEmpty(smsSendResponse.getCode())&&"0".equals(smsSendResponse.getCode())&&flag==true){
                    userTip.setMessage("重置密码成功");
                    userTip.setCode(200);
                    LOGGER.info("重置密码成功");
                }
                else {
                    userTip.setMessage("重置密码发送短信失败，原因是" + smsSendResponse.getErrorMsg());
                    userTip.setCode(500);
                    LOGGER.error("重置密码发送短信失败，原因是" +smsSendResponse.getErrorMsg());
                }
            }

        }
        return userTip;

    }

    /**
     * 冻结用户
     */
    @RequestMapping(value="/freeze",method = RequestMethod.POST)
    @BussinessLog(value = "冻结用户", key = "account",dict = UserDict.class)
    @DataLog(value = "冻结用户",model =User.class, keyParam = "id",dict = UserDict.class)
    @Permission
    @ResponseBody
    @ApiOperation(value = "冻结用户",notes = "冻结用户",tags = "系统管理->用户管理")
    @ApiImplicitParam(name = "userId" ,value="用户编号",required = true,dataType = "int",paramType = "query")
    public Tip userFreeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能冻结超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        assertAuth(userId);
        //缓存数据更新之前的旧值
        User user =userService.selectById(userId);
        LogObjectHolder.me().set(UserFactory.createUserDto(user));
        this.userService.setStatus(userId, ManagerStatus.FREEZED.getCode());

        return SUCCESS_TIP;
    }


    /**
     * 批量冻结用户
     */
    @RequestMapping(value="/batchFreeze",method = RequestMethod.POST)
    @BussinessLog(value = "批量冻结用户", key = "account",dict = UserDict.class)
    @DataLog(value = "批量冻结用户",model =User.class, keyParam = "id",dict = UserDict.class)
    @Permission
    @ResponseBody
    @ApiOperation(value = "批量冻结用户",notes = "批量冻结用户",tags = "系统管理->用户管理")
    @ApiImplicitParam(name = "userIds" ,value="用户编号列表",required = true,dataType = "List")
    public Tip userBatchFreeze(@RequestParam List<Integer> userIds) {
        if (ToolUtil.isEmpty(userIds)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        for(int i=0;i<userIds.size();i++){
            //不能冻结超级管理员
            if (userIds.get(i).equals(Const.ADMIN_ID)) {
                throw new GunsException(BizExceptionEnum.CANT_FREEZE_ADMIN);
            }
        }
        //已经冻结的用户不能冻结
        List<User> userList=userService.selectBatchIds(userIds);
        if(CollectionUtils.isNotEmpty(userList)){
            for(int i=0;i<userList.size();i++){
                User user =userList.get(i);
                if(user.getStatus().equals(ManagerStatus.FREEZED.getCode())){
                    UserTip userTip=new UserTip();
                    userTip.setCode(500);
                    userTip.setMessage("当前用户"+user.getAccount()+"已经冻结,不能再次冻结");
                    return userTip;
                }
            }
        }
        assertAuth(userIds);
       int batchUpdateResult= this.userService.setBatchStatus(userIds, ManagerStatus.FREEZED.getCode());
       if(batchUpdateResult>0){
           return SUCCESS_TIP;
       }
       UserTip userTip=new UserTip();
       userTip.setCode(500);
       userTip.setMessage("批量冻结用户失败");
       return userTip;
    }

    /**
     * 取消冻结
     */
    @RequestMapping(value="/unfreeze",method = RequestMethod.POST)
    @BussinessLog(value = "解除冻结用户", key = "account", dict = UserDict.class)
    @DataLog(value = "解除冻结用户",model =User.class, keyParam = "id",dict = UserDict.class)
    @Permission
    @ResponseBody
    @ApiOperation(value = "解除冻结用户",notes = "解除冻结用户",tags = "系统管理->用户管理")
    @ApiImplicitParam(name = "userId" ,value="用户编号",required = true,dataType = "int",paramType = "query")
    public Tip userUnfreeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        User user = this.userService.selectById(userId);
        UserTip userTip =new UserTip();
        if(null!=user&&user.getStatus().equals(Integer.valueOf(3))){
         userTip.setMessage("已删除的用户不支持解冻");
         userTip.setCode(500);
         return userTip;
        }
        Dept dept=deptService.selectById(user.getDeptid());

        if(null==dept){
            userTip.setMessage("用户所在的部门被删除，需调整用户所属部门");
            userTip.setCode(500);
            return userTip;
        }
        //缓存数据更新之前的旧值
        LogObjectHolder.me().set(UserFactory.createUserDto(user));
        this.userService.setStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 分配角色
     */
    @RequestMapping("/setRole")
    @BussinessLog(value = "分配角色", key = "userId,roleIds", dict = UserDict.class)
    @DataLog(value = "分配角色",model =User.class, keyParam = "userId",dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip userSetRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        assertAuth(userId);
        this.userService.setRoles(userId, roleIds);
        return SUCCESS_TIP;
    }

    /**
     * 修改批量用户的部门
     */
    @RequestMapping(value = "/changeDept",method = RequestMethod.POST)
    @Permission
    @ResponseBody
    @ApiOperation(value = "用户批量变更部门",notes = "用户批量变更部门",tags = "系统管理->用户管理")

    @ApiImplicitParams({
            @ApiImplicitParam (name = "deptId" ,value="部门编号",required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "userIds" ,value="用户编号列表",required = true,dataType = "List")
    })
    public Tip userChangeDept(@RequestParam List<Integer> userIds, @RequestParam("deptId") String deptId) {

        if (ToolUtil.isOneEmpty(userIds, deptId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userIds.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        UserTip userTip =new UserTip();

        List<User> userList =userService.selectBatchIds(userIds);
        if(CollectionUtils.isEmpty(userList)){
            userTip.setCode(500);
            userTip.setMessage("变更部门的用户不存在");
            return userTip;
        }
        Dept dept =deptService.selectById(deptId);
        if(dept==null){
            userTip.setCode(500);
            userTip.setMessage("变更的部门不存在");
            return userTip;
        }
        assertAuth(userIds);
        this.userService.changeDept(userIds, deptId);
        return SUCCESS_TIP;
    }


    /**
     * 上传图片(上传到项目的webapp/static/img)
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        try {
            String fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }

    /**
     * 判断当前登录的用户是否有操作这个用户的权限
     */
    private void assertAuth(Integer userId) {
//        if (ShiroKit.isAdmin()) {
//            return;
//        }
//        List<Integer> deptDataScope = ShiroKit.getDeptDataScope();
//        User user = this.userService.selectById(userId);
//        Integer deptid = user.getDeptid();
//        if (deptDataScope.contains(deptid)) {
//            return;
//        } else {
//            throw new GunsException(BizExceptionEnum.NO_PERMITION);
//        }
        //暂时不启用数据权限
        return;
    }

    /**
     * 判断当前用户是否有操作这个用户的权限
     */
    private void assertAuth(List<Integer> userIds) {

        return ;
//        if (ShiroKit.isAdmin()) {
//            return;
//        }
//        List<Integer> deptDataScope = ShiroKit.getDeptDataScope();
//        List<User> userList = this.userService.selectBatchIds(userIds);
//        for(int i=0;i<userList.size();i++){
//            Integer deptid = userList.get(i).getDeptid();
//            if (deptDataScope.contains(deptid)) {
//                return;
//            } else {
//                throw new GunsException(BizExceptionEnum.NO_PERMITION);
//            }
//        }
    }

    /**
     * 数据日志
     */
    @ApiOperation(value = "查看用户数据日志", notes = "查看用户数据日志", tags={ "系统管理->用户管理" })
    @ApiImplicitParam(paramType = "path", name = "userId", value = "用户Id", required = true, dataType = "int")
    @RequestMapping(value = "/operationLogs/{userId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @Permission
    public Object operationLogs(@PathVariable Integer userId) {
        OperationLog operationLog = new OperationLog();
        operationLog.setLogtype("业务日志");
        operationLog.setModelclass(User.class.getName());
        operationLog.setKeyvalue(userId + "");
        List list = operationLogService.getOperationLogsByCondition(operationLog);
        return super.warpObject(new OperationLogWrapper(list));
    }
}