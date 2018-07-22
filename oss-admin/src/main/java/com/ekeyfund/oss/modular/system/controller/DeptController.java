package com.ekeyfund.oss.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ekeyfund.oss.core.base.controller.BaseController;
import com.ekeyfund.oss.core.common.annotion.BussinessLog;
import com.ekeyfund.oss.core.common.annotion.DataLog;
import com.ekeyfund.oss.core.common.annotion.Permission;
import com.ekeyfund.oss.core.common.constant.dictmap.DeptDict;
import com.ekeyfund.oss.core.common.constant.factory.ConstantFactory;
import com.ekeyfund.oss.core.common.constant.factory.PageFactory;
import com.ekeyfund.oss.core.common.exception.BizExceptionEnum;
import com.ekeyfund.oss.core.exception.GunsException;
import com.ekeyfund.oss.core.log.LogObjectHolder;
import com.ekeyfund.oss.core.node.ZTreeNode;
import com.ekeyfund.oss.core.util.ToolUtil;
import com.ekeyfund.oss.modular.system.model.Dept;
import com.ekeyfund.oss.modular.system.model.OperationLog;
import com.ekeyfund.oss.modular.system.service.IDeptService;
import com.ekeyfund.oss.modular.system.service.IOperationLogService;
import com.ekeyfund.oss.modular.system.warpper.DeptWarpper;
import com.ekeyfund.oss.modular.system.warpper.OperationLogWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/system/dept/";

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IOperationLogService operationLogService;

    /**
     * 跳转到部门管理首页
     */
    @ApiOperation(value = "部门管理默认页", notes = "跳转到部门管理默认页面", tags={ "系统管理->部门管理", })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加部门
     */
    @ApiOperation(value = "跳转到添加部门或添加子部门", notes = "跳转到添加部门或添加子部门", tags={ "系统管理->部门管理", })
    @RequestMapping(value = "/dept_add/{deptPid}", method = RequestMethod.GET)
    @ApiImplicitParam(paramType = "path", name = "deptPid", value = "父部门Id(-1表示添加部门，其他存在的部门id表示添加子部门)", required = true, dataType = "String")
    public String deptAdd(@PathVariable Integer deptPid, Model model) {
        if(null != deptPid){
            model.addAttribute("pName", "");
            model.addAttribute("pid", "");
            Dept dept = deptService.selectById(deptPid);
            if(null != dept) {

                model.addAttribute("pName", deptService.getAllLevelDeptNameById(dept.getId()));
                model.addAttribute("pid", deptPid);
            }
        }
        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到修改部门
     */
    @ApiOperation(value = "跳转到修改部门", notes = "跳转到修改部门", tags={ "系统管理->部门管理", })
    @ApiImplicitParams ({
        @ApiImplicitParam(paramType = "path", name = "deptId", value = "部门Id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "select", name = "model", value = "部门信息", required = false)
    })
    @Permission
    @RequestMapping(value = "/dept_update/{deptId}", method = RequestMethod.GET)
    public String deptUpdate(@PathVariable Integer deptId, Model model) {
        Dept dept = deptService.selectById(deptId);
        model.addAttribute(dept);
        model.addAttribute("pName", ConstantFactory.me().getDeptName(dept.getPid()));
        LogObjectHolder.me().set(dept);
        return PREFIX + "dept_edit.html";
    }

    /**
     * 跳转到查看日志
     */
    @ApiOperation(value = "跳转到查看日志", notes = "跳转到查看日志", tags={ "系统管理->部门管理", })
    @ApiImplicitParams ({
            @ApiImplicitParam(paramType = "path", name = "deptId", value = "部门Id", required = true, dataType = "String"),
    })
    @Permission
    @RequestMapping(value = "/dept_log/{deptId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String deptLog(@PathVariable Integer deptId, Model model) {
        model.addAttribute("dataId",deptId);
        return PREFIX + "dept_log.html";
    }

    /**
     * 跳转到查看日志
     */
    @ApiOperation(value = "跳转到编辑排序", notes = "跳转到编辑排序", tags={ "系统管理->部门管理", })
    @ApiImplicitParams ({
            @ApiImplicitParam(paramType = "path", name = "deptId", value = "部门Id", required = true, dataType = "String"),
    })
    @Permission
    @RequestMapping(value = "/dept_modify_num/{deptId}", method = {RequestMethod.GET})
    public String deptModifyNum(@PathVariable Integer deptId, Model model) {
        model.addAttribute("deptId",deptId);
        Dept dept = deptService.selectById(deptId);
        model.addAttribute("num",dept.getNum());
        return PREFIX + "dept_modify_num.html";
    }

    /**
     * 获取部门的tree列表
     */
    @ApiOperation(value = "获取部门的tree列表", notes = "获取部门的tree列表", tags={ "系统管理->部门管理", })
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptService.tree();
        //应产品要求，去掉顶级节点    2018-4-23   CJ
//        tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * 新增部门
     */
    @ApiOperation(value = "新增部门", notes = "新增部门", tags={ "系统管理->部门管理", })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @BussinessLog(value = "添加部门", key = "simplename",dict = DeptDict.class)
    @DataLog(value = "添加部门",keyParam = "",model = Dept.class,dict = DeptDict.class)
    @Permission
    @ResponseBody
    @ApiImplicitParam(paramType = "body", name = "dept", value = "新增部门信息", required = true, dataType = "Dept")
    public Object add(@RequestBody Dept dept) {
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //完善pids,根据pid拿到pid的pids
        deptSetPids(dept);

        //同级名称重复判断
        validSameSimplename(dept.getId(), dept.getPid(), dept.getSimplename());

        dept.setCreatetime(new Date());
        dept.setUpdatetime(dept.getCreatetime());
        boolean result= this.deptService.insert(dept);
        //日志需要
        LogObjectHolder.me().set(dept.getId());
        return result;
    }

    /**
     * 获取所有部门列表
     */
    @ApiOperation(value = "获取所有部门列表", notes = "获取所有部门列表", tags={ "系统管理->部门管理", })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public Object list(@ApiParam(value = "查询部门条件",required = false)@RequestParam(value = "condition", required = false)String condition) {
        condition = StringUtils.trim(condition);
        List<Map<String, Object>> list = this.deptService.list(condition);
        return super.warpObject(new DeptWarpper(list));
    }

    /**
     * 部门详情
     */
    @ApiOperation(value = "查看部门详情", notes = "查看部门详情", tags={ "系统管理->部门管理", })
    @ApiImplicitParam(paramType = "path", name = "deptId", value = "部门Id", required = true, dataType = "String")
    @RequestMapping(value = "/detail/{deptId}", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public Object detail(@PathVariable Integer deptId) {
        return deptService.selectById(deptId);
    }

    /**
     * 修改部门
     */
    @BussinessLog(value = "修改部门", key = "simplename",dict = DeptDict.class)
    @DataLog(value = "修改部门",model = Dept.class, keyParam="id",dict = DeptDict.class )
    @ApiOperation(value = "修改部门", notes = "修改部门", tags={ "系统管理->部门管理", })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    @ApiImplicitParam(paramType = "body", name = "dept", value = "修改部门信息", required = true, dataType = "Dept")
    public Object update(@RequestBody Dept dept) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        if(dept.getPid() != null){
            if(dept.getPid().equals(dept.getId())) {//选择的父部门是当前部门
                throw new GunsException(BizExceptionEnum.DEPT_EDIT_PID_EQ_ID);
            }
            Dept parentDept = deptService.selectById(dept.getPid());
            if(null != parentDept && parentDept.getPids().contains("["+dept.getId()+"]")){//选择的父部门是当前部门的子部门
                throw new GunsException(BizExceptionEnum.DEPT_EDIT_PID_IS_CHILD_ID);
            }
        }

        //完善pids,根据pid拿到pid的pids
        deptSetPids(dept);

        //同级名称重复判断
        validSameSimplename(dept.getId(), dept.getPid(), dept.getSimplename());

        dept.setUpdatetime(new Date());
        deptService.updateById(dept);
        return SUCCESS_TIP;
    }

    /**
     * 同级名称重复判断
     * @param pid 父级部门id
     * @param simplename 部门名称
     */
    private void validSameSimplename(Integer deptId, Integer pid, String simplename){

        EntityWrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper.eq("pid",pid)
                .and().eq("simplename", simplename);
        if(null != deptId){
            wrapper.and().ne("id",deptId);
        }


        List<Dept> deptList = deptService.selectList(wrapper);
        if(null != deptList && deptList.size() > 0){
            throw new GunsException(BizExceptionEnum.DEPT_NAME_REPEAT);
        }
    }

    /**
     * 删除部门
     */
    @ApiOperation(value = "删除部门", notes = "删除部门", tags={ "系统管理->部门管理", })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @BussinessLog(value = "删除部门", key = "deptId",dict = DeptDict.class)
    @DataLog(value = "删除部门",model = Dept.class, keyParam="deptId",dict = DeptDict.class )
    @Permission
    @ResponseBody
    public Object delete(@ApiParam(value = "部门id",required = true)@RequestParam(value = "deptId", required = true) Integer deptId) {

        //缓存被删除的部门名称
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));

        deptService.deleteDept(deptId);

        return SUCCESS_TIP;
    }

    private void deptSetPids(Dept dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0)) {
            dept.setPid(0);
            dept.setPids("[0],");
        } else {
            int pid = dept.getPid();
            Dept temp = deptService.selectById(pid);
            String pids = temp.getPids();
            if(pids.split(",").length > 4){
                throw new GunsException(BizExceptionEnum.DEPT_OUT_FOUR_LEVEL);
            }
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }

    /**
     * 数据日志
     */
    @ApiOperation(value = "查看数据日志", notes = "查看数据日志", tags={ "系统管理->部门管理", })
    @ApiImplicitParam(paramType = "path", name = "deptId", value = "部门Id", required = true, dataType = "Integer")
    @RequestMapping(value = "/operationLogs/{deptId}", method = RequestMethod.POST)
    @Permission
    @ResponseBody
    public Object operationLogs(@PathVariable Integer deptId) {
        Page<OperationLog> page = new PageFactory<OperationLog>().defaultPage();
        OperationLog operationLog = new OperationLog();
        operationLog.setLogtype("业务日志");
        operationLog.setModelclass(Dept.class.getName());
        operationLog.setKeyvalue(deptId + "");
        List list = operationLogService.getOperationLogsByCondition(operationLog);
        return super.warpObject(new OperationLogWrapper(list));
    }

    /**
     * 修改部门排序
     */
    @ApiOperation(value = "修改部门排序", notes = "修改部门排序", tags={ "系统管理->部门管理", })
    @RequestMapping(value = "/do_modify_dept_num", method = RequestMethod.POST)
    @BussinessLog(value = "修改部门排序", key = "deptId",dict = DeptDict.class)
    @DataLog(value = "修改部门排序",model = Dept.class, keyParam="deptId",dict = DeptDict.class )
    @Permission
    @ResponseBody
    public Object doModifyDeptNum(@ApiParam(value = "部门id",required = true)@RequestParam(value = "deptId", required = true) Integer deptId,
                                @ApiParam(value = "部门排序号",required = true)@RequestParam(value = "num", required = true) Integer num
                                ) {
        Dept dept = deptService.selectById(deptId);
        dept.setUpdatetime(new Date());
        dept.setNum(num);
        deptService.updateAllColumnById(dept);
        LogObjectHolder.me().set(dept);
        return SUCCESS_TIP;
    }
}
