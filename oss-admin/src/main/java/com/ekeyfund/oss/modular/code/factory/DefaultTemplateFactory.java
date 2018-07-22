package com.ekeyfund.oss.modular.code.factory;


import com.ekeyfund.oss.OssApplication;
import com.ekeyfund.oss.core.CoreFlag;
import com.ekeyfund.oss.core.util.ToolUtil;
import com.ekeyfund.oss.generator.action.model.GenQo;
import com.ekeyfund.oss.generator.mybatisplus.entity.AbstractAssign18IdEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板种类构建器
 *
 * @author fengshuonan
 * @date 2017-12-04-下午2:59
 */
public class DefaultTemplateFactory {

    /**
     * 获取所有的模板种类
     */
    public static List<Map<String,Object>> getDefaultTemplates(){
        ArrayList<Map<String, Object>> templates = new ArrayList<>();
        templates.add(create("controllerSwitch","controller-控制器模板"));
        templates.add(create("entitySwitch","entity-实体模板"));
        templates.add(create("serviceSwitch","service-service模板"));
        templates.add(create("daoSwitch","dao-dao模板"));
        templates.add(create("indexPageSwitch","indexPage-首页模板"));
        templates.add(create("addPageSwitch","addPage-添加页面模板"));
        templates.add(create("oplogPageSwitch","oplogPage-操作日志模板"));
        templates.add(create("editPageSwitch","editPage-编辑页面模板"));
        templates.add(create("jsSwitch","indexJs-主页js模板"));
        templates.add(create("infoJsSwitch","infoJs-详情页js模板"));
        templates.add(create("sqlSwitch","sql-sql语句模板"));
        return templates;
    }

    /**
     * 获取默认的参数
     */
    public static GenQo getDefaultParams(){
        GenQo genQo = new GenQo();
        genQo.setProjectPath(ToolUtil.getWebRootPath(null));
        genQo.setAuthor("ekeyfund");
        genQo.setProjectPackage(OssApplication.class.getPackage().getName());
        genQo.setCorePackage(CoreFlag.class.getPackage().getName());
        genQo.setIgnoreTabelPrefix("sys_");
        genQo.setModuleName("system");
        genQo.setParentMenuName("系统管理");
        genQo.setSuperEntityClass(AbstractAssign18IdEntity.class.getName());
        return genQo;
    }

    private static Map<String,Object> create(String key,String desc){
        HashMap<String, Object> template = new HashMap<>();
        template.put("key",key);
        template.put("desc",desc);
        return template;
    }
}
