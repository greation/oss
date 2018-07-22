package com.ekeyfund.oss.modular.demo.controller;

import com.ekeyfund.oss.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.ekeyfund.oss.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import com.ekeyfund.oss.core.common.annotion.BussinessLog;
import com.ekeyfund.oss.core.common.annotion.DataLog;
import com.ekeyfund.oss.core.util.ToolUtil;
import com.ekeyfund.oss.core.common.annotion.Permission;
import java.util.Map;
import java.util.HashMap;
import com.baomidou.mybatisplus.plugins.Page;
import com.ekeyfund.oss.core.common.constant.factory.PageFactory;
import com.ekeyfund.oss.core.common.constant.factory.ConstantFactory;
import com.ekeyfund.oss.core.shiro.ShiroKit;
import com.ekeyfund.oss.modular.system.model.OperationLog;
import com.ekeyfund.oss.modular.system.service.IOperationLogService;
import com.ekeyfund.oss.modular.system.warpper.OperationLogWrapper;
import com.ekeyfund.oss.core.exception.GunsException;
import com.ekeyfund.oss.core.exception.GunsExceptionEnum;
import com.ekeyfund.oss.core.log.LogObjectHolder;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.ekeyfund.oss.core.base.warpper.BaseControllerWarpper;
import com.ekeyfund.oss.modular.demo.model.Test;
import com.ekeyfund.oss.modular.demo.service.ITestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 测试生成控制器
 *
 * @author fengshuonan
 * @Date 2018-05-31 17:31:09
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    private String PREFIX = "/demo/test/";

    @Autowired
    private ITestService testService;
    /**
     * 操作日志service
     */
    @Autowired
    private IOperationLogService operationLogService;

    /**
     * 跳转到测试生成首页
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "测试生成列表页面", notes = "跳转至测试生成列表页面", tags = {"顶级->测试生成",})
    public String index() {
        return PREFIX + "test.html";
    }

    /**
     * 跳转到添加测试生成
     */
    @RequestMapping(value = "/test_add", method = RequestMethod.GET)
    @ApiOperation(value = "测试生成添加页面", notes = "跳转至测试生成添加页面", tags = {"顶级->测试生成",})
    public String testAdd() {
        return PREFIX + "test_add.html";
    }

    /**
     * 跳转到修改测试生成
     */
    @RequestMapping(value = "/test_update/{testId}", method = RequestMethod.GET)
    @ApiOperation(value = "测试生成修改页面", notes = "跳转至测试生成修改页面", tags = {"顶级->测试生成",})
    @ApiImplicitParam(paramType = "path", name = "testId", value = "测试生成ID", required = true, dataType = "long")
    public String testUpdate(@PathVariable Long testId, Model model) {
        Test test = testService.selectById(testId);
        model.addAttribute("item",test);
        LogObjectHolder.me().set(test);
        return PREFIX + "test_edit.html";
    }

    /**
     * 跳转到测试生成操作日志
     */
    @RequestMapping(value = "/test_oplog/{testId}" , method = RequestMethod.GET)
    @ApiOperation(value = "测试生成日志页面", notes = "跳转至测试生成日志页面", tags = {"顶级->测试生成",})
    @ApiImplicitParam(paramType = "path", name = "testId", value = "测试生成ID", required = true, dataType = "long")
    public String testOpLog(@PathVariable Long testId, Model model) {
        if (ToolUtil.isEmpty(testId)) {
            throw new GunsException(GunsExceptionEnum.REQUEST_NULL);
        }
        Test test = testService.selectById(testId);
        model.addAttribute(test);
        LogObjectHolder.me().set(test);
        return PREFIX + "/test_oplog.html";
    }


    /**
     * 获取测试生成列表
     */
    @RequestMapping(value = "/list",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @Permission
    @ApiOperation(value = "测试生成列表数据", notes = "获取测试生成列表数据", tags = {"顶级->测试生成",})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", value = "开始时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "condition", value = "查询条件", dataType = "String")})
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String condition) {
        Page<Test> page = new PageFactory<Test>().defaultPage();
        List<Map<String, Object>> result = testService.getLists(page, beginTime, endTime, condition, page.getOrderByField(), page.isAsc());
        page.setRecords((List<Test>) new TestWrapper(result).warp());
        return super.packForBT(page);

        //Map<String,Object> whereMap = new HashMap<>();
        //whereMap.put("delflag", 0);
        //return testService.selectByMap(whereMap);
    }
    /**
     * 测试生成结果包装器
     */
    private class TestWrapper extends BaseControllerWarpper{
        public TestWrapper(Object obj){
            super(obj);
        }
        @Override
        protected void warpTheMap(Map<String, Object> map) {
            Object creater = map.get("creater");
            if(creater == null || !(creater instanceof Integer)){
                map.put("createrName", "--");
            }else{
                map.put("createrName", ConstantFactory.me().getUserNameById((Integer)creater));
            }

            Object updater = map.get("updater");
            if(updater == null || !(updater instanceof Integer)){
                map.put("updaterName", "--");
            }else{
                map.put("updaterName", ConstantFactory.me().getUserNameById((Integer)updater));
            }
        }
    }

    /**
     * 新增测试生成
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog(value = "新增测试生成")
    @DataLog(value = "新增测试生成",model = Test.class)
    @Permission
    @ApiOperation(value = "新增测试生成数据", notes = "新增测试生成数据", tags = {"顶级->测试生成",})
    @ApiImplicitParam(paramType = "body", name = "test", value = "测试生成JSON数据", required = true, dataType = "Test")
    public Object add(@RequestBody Test test) {
        Date currentDate = new Date();
        Integer userId = ShiroKit.getUser().getId();
        test.setCreatetime(currentDate);
        test.setCreater(userId);
        test.setUpdatetime(currentDate);
        test.setUpdater(userId);
        testService.insert(test);
        //日志需要
        LogObjectHolder.me().set(test.getId());
        return SUCCESS_TIP;
    }

    /**
     * 删除测试生成
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog(value = "删除测试生成")
    @DataLog(value = "删除测试生成",keyParam = "testId",model = Test.class)
    @Permission
    @ApiOperation(value = "删除测试生成数据", notes = "删除测试生成数据", tags = {"顶级->测试生成",})
    @ApiImplicitParam(paramType = "query", name = "testId", value = "测试生成ID", required = true, dataType = "long")
    public Object delete(@RequestParam Long testId) {
        testService.deleteById(testId);
        return SUCCESS_TIP;
    }

    /**
     * 修改测试生成
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog(value = "修改测试生成")
    @DataLog(value = "修改测试生成",model = Test.class)
    @Permission
    @ApiOperation(value = "修改测试生成数据", notes = "修改测试生成数据", tags = {"顶级->测试生成",})
    @ApiImplicitParam(paramType = "body", name = "test", value = "测试生成JSON数据", required = true, dataType = "Test")
    public Object update(@RequestBody Test test) {
        test.setUpdatetime(new Date());
        test.setUpdater(ShiroKit.getUser().getId());
        testService.updateById(test);
        return SUCCESS_TIP;
    }

    /**
     * 测试生成详情
     */
    @RequestMapping(value = "/detail/{testId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @Permission
    @ApiOperation(value = "测试生成详情数据", notes = "获取测试生成详情数据", tags = {"顶级->测试生成",})
    @ApiImplicitParam(paramType = "path", name = "testId", value = "测试生成ID", required = true, dataType = "long")
    public Object detail(@PathVariable("testId") Long testId) {
        return testService.selectById(testId);
    }

    /**
     * 测试生成数据日志
     */
    @RequestMapping(value = "/oplog/{testId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @Permission
    @ApiOperation(value = "测试生成日志数据", notes = "获取测试生成日志数据", tags = {"顶级->测试生成",})
    @ApiImplicitParam(paramType = "path", name = "testId", value = "测试生成ID", required = true, dataType = "long")
    public Object opLog(@PathVariable Long testId) {
        OperationLog operationLog = new OperationLog();
        operationLog.setLogtype("业务日志");
        operationLog.setModelclass(Test.class.getName());
        operationLog.setKeyvalue(testId + "");
        List list = operationLogService.getOperationLogsByCondition(operationLog);
        return super.warpObject(new OperationLogWrapper(list));
    }
}
