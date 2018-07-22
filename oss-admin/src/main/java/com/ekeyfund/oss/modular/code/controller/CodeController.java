package com.ekeyfund.oss.modular.code.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.ekeyfund.oss.core.base.controller.BaseController;
import com.ekeyfund.oss.core.constant.DatasourceEnum;
import com.ekeyfund.oss.generator.action.config.WebGeneratorConfig;
import com.ekeyfund.oss.generator.action.model.GenQo;
import com.ekeyfund.oss.modular.code.factory.DefaultTemplateFactory;
import com.ekeyfund.oss.modular.code.service.TableService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 代码生成控制器
 *
 * @author fengshuonan
 * @Date 2017年11月30日16:39:19
 */
@Controller
@RequestMapping("/code")
public class CodeController extends BaseController {

    private static String PREFIX = "/code";

    @Autowired
    private TableService tableService;


    /**
     * 跳转到代码生成主页
     */
    @RequestMapping("")
    public String blackboard(@RequestParam(required=false) DatasourceEnum datasourceEnum, Model model) {
        List<Map<String, Object>> tables = null;
        if(datasourceEnum == null){
            datasourceEnum = tableService.getDefaultDataSourceName();
        }
        model.addAttribute("datasourceEnum", datasourceEnum);
        tables = tableService.getAllTables(datasourceEnum);

        //model.addAttribute("dbNames", tableService.getDataSourceNames());
        model.addAttribute("datasourceEnums", DatasourceEnum.values());
        model.addAttribute("tables", tables);
        model.addAttribute("params", DefaultTemplateFactory.getDefaultParams());
        model.addAttribute("templates", DefaultTemplateFactory.getDefaultTemplates());
        return PREFIX + "/code.html";
    }

    /**
     * 生成代码
     */
    @ApiOperation("生成代码")
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    public Object generate(GenQo genQo) {
        //设置数据源
        DruidDataSource dataSource = (DruidDataSource)tableService.getMutiDataSource().getDataSource(genQo.getDatasourceEnum());
        if(dataSource != null){
            genQo.setUrl(dataSource.getUrl());
            genQo.setUserName(dataSource.getUsername());
            genQo.setPassword(dataSource.getPassword());
       }
// else{
//            dataSource = (DruidDataSource)tableService.getMutiDataSource().getDataSource(tableService.getDefaultDataSourceName());
//            genQo.setUrl(dataSource.getUrl());
//            genQo.setUserName(dataSource.getUsername());
//            genQo.setPassword(dataSource.getPassword());
//        }

        WebGeneratorConfig webGeneratorConfig = new WebGeneratorConfig(genQo);
        webGeneratorConfig.doMpGeneration();
        webGeneratorConfig.doGunsGeneration();
        return SUCCESS_TIP;
    }


}
