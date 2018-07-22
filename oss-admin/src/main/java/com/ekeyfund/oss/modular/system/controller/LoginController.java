package com.ekeyfund.oss.modular.system.controller;

import com.ekeyfund.oss.core.base.controller.BaseController;
import com.ekeyfund.oss.core.common.exception.AccountNotFoundException;
import com.ekeyfund.oss.core.common.exception.InvalidKaptchaException;
import com.ekeyfund.oss.core.log.LogManager;
import com.ekeyfund.oss.core.log.factory.LogTaskFactory;
import com.ekeyfund.oss.core.node.MenuNode;
import com.ekeyfund.oss.core.shiro.ShiroKit;
import com.ekeyfund.oss.core.shiro.ShiroUser;
import com.ekeyfund.oss.core.support.DateTimeKit;
import com.ekeyfund.oss.core.support.HttpKit;
import com.ekeyfund.oss.core.util.ApiMenuFilter;
import com.ekeyfund.oss.core.util.KaptchaUtil;
import com.ekeyfund.oss.core.util.ToolUtil;
import com.ekeyfund.oss.modular.system.model.User;
import com.ekeyfund.oss.modular.system.service.IMenuService;
import com.ekeyfund.oss.modular.system.service.IUserService;
import com.google.code.kaptcha.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
@Api(value = "登录管理")
public class LoginController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserService userService;

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        //获取菜单列表
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            model.addAttribute("tipstype", "account");
            return "/login.html";
        }




        List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);

        //获取用户头像
        Integer id = ShiroKit.getUser().getId();
        User user = userService.selectById(id);
        String avatar = user.getAvatar();
        String name =user.getName();
        String account=user.getAccount();
        model.addAttribute("avatar", avatar);
        model.addAttribute("name", name);
        model.addAttribute("account", account);

        return "/index.html";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");

        User userInfo=userService.getByAccount(username);
        if(null==userInfo){
           throw new AccountNotFoundException("500","账户不存在");
        }
        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            Date date = (Date) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_DATE);
            //验证码默认超时时间
            if (ToolUtil.isEmpty(date) || DateTimeKit.diff(date,new Date(),DateTimeKit.MINUTE_MS) > 5) {
                throw new InvalidKaptchaException(InvalidKaptchaException.EXPIRED);
            }
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException(InvalidKaptchaException.INVALID);
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), HttpKit.getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return REDIRECT + "/";
    }


    /**
     * 登录操作前进行校验
     */
    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登录校验", notes = "登录校验", tags = {"系统管理->用户登录",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "用户密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "kaptcha", value = "验证码", dataType = "String")})
    public String loginCheck(String username, String password, String kaptcha) {
        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            Date date = (Date) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_DATE);
            //根据PRD先校验错误与否，再校验失效情况
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                return "验证码错误";
            }
            //验证码默认超时时间
            if (ToolUtil.isEmpty(date) || DateTimeKit.diff(date,new Date(),DateTimeKit.MINUTE_MS) > 5) {
                return "验证码已失效";
            }
        }
        //判断用户是否存在
        User userInfo=userService.getByAccount(username);
        if(null==userInfo){
            return "用户不存在";
        }
        //getByAccount只查询不为3的状态数据
        if(userInfo.getStatus() == 2){
            return "账户被冻结";
        }
        //验证登录密码是否正确
        String loginPwd = ShiroKit.md5(password, userInfo.getSalt());
        String userPwd = userInfo.getPassword();
        if(!loginPwd.equals(userPwd)){
            return "账号密码不正确";
        }
        return "ok";
    }


    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), HttpKit.getIp()));
        ShiroKit.getSubject().logout();
        return REDIRECT + "/login";
    }
}
