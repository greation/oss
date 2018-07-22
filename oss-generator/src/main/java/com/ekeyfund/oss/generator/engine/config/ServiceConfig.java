package com.ekeyfund.oss.generator.engine.config;

import com.ekeyfund.oss.core.constant.DatasourceEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Service模板生成的配置
 *
 * @author fengshuonan
 * @date 2017-05-07 22:12
 */
public class ServiceConfig {

    private ContextConfig contextConfig;

    private String servicePathTemplate;
    private String serviceImplPathTemplate;

    private String servicePackageName;
    private String serviceImplPackageName;

    private List<String> serviceInterfaceImports;
    private List<String> serviceImplImports;
    //service默认数据源名称
    private DatasourceEnum dateSource = DatasourceEnum.DATA_SOURCE_YQ_OSS;

    public void init() {
        ArrayList<String> imports = new ArrayList<>();
        imports.add("org.springframework.stereotype.Service");
        imports.add("com.baomidou.mybatisplus.service.impl.ServiceImpl");
        imports.add("com.ekeyfund.oss.generator.mybatisplus.service.impl.BasePageServiceImpl");
        imports.add("com.ekeyfund.oss.core.constant.DatasourceEnum");
        imports.add("com.ekeyfund.oss.core.mutidatasource.annotion.DataSource");
        imports.add(contextConfig.getModelPackageName() + "." + contextConfig.getEntityName());
        imports.add(contextConfig.getModelMapperPackageName() + "." + contextConfig.getEntityName() + "Mapper");
        imports.add(contextConfig.getProPackage() + ".modular." + contextConfig.getModuleName() + ".service.I" + contextConfig.getBizEnBigName() + "Service");
        this.serviceImplImports = imports;

        ArrayList<String> interfaceImports = new ArrayList<>();
        interfaceImports.add("com.baomidou.mybatisplus.service.IService");
        interfaceImports.add("com.ekeyfund.oss.generator.mybatisplus.service.IBasePageService");
        interfaceImports.add(contextConfig.getModelPackageName() + "." + contextConfig.getEntityName());
        this.serviceInterfaceImports = interfaceImports;

        this.servicePathTemplate = "\\src\\main\\java\\" + contextConfig.getProPackage().replaceAll("\\.", "\\\\") + "\\modular\\" + contextConfig.getModuleName() + "\\service\\I{}Service.java";
        this.serviceImplPathTemplate = "\\src\\main\\java\\" + contextConfig.getProPackage().replaceAll("\\.", "\\\\") + "\\modular\\" + contextConfig.getModuleName() + "\\service\\impl\\{}ServiceImpl.java";
        this.servicePackageName = contextConfig.getProPackage() + ".modular." + contextConfig.getModuleName() + ".service";
        this.serviceImplPackageName = contextConfig.getProPackage() + ".modular." + contextConfig.getModuleName() + ".service.impl";
        //设置service用到的数据源
        this.dateSource = contextConfig.getDatasourceEnum();
    }


    public String getServicePathTemplate() {
        return servicePathTemplate;
    }

    public void setServicePathTemplate(String servicePathTemplate) {
        this.servicePathTemplate = servicePathTemplate;
    }

    public String getServicePackageName() {
        return servicePackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }

    public String getServiceImplPackageName() {
        return serviceImplPackageName;
    }

    public void setServiceImplPackageName(String serviceImplPackageName) {
        this.serviceImplPackageName = serviceImplPackageName;
    }

    public String getServiceImplPathTemplate() {
        return serviceImplPathTemplate;
    }

    public void setServiceImplPathTemplate(String serviceImplPathTemplate) {
        this.serviceImplPathTemplate = serviceImplPathTemplate;
    }

    public DatasourceEnum getDateSource() {
        return dateSource;
    }

    public void setDateSource(DatasourceEnum dateSource) {
        this.dateSource = dateSource;
    }

    public List<String> getServiceImplImports() {
        return serviceImplImports;
    }

    public void setServiceImplImports(List<String> serviceImplImports) {
        this.serviceImplImports = serviceImplImports;
    }

    public ContextConfig getContextConfig() {
        return contextConfig;
    }

    public void setContextConfig(ContextConfig contextConfig) {
        this.contextConfig = contextConfig;
    }

    public List<String> getServiceInterfaceImports() {
        return serviceInterfaceImports;
    }

    public void setServiceInterfaceImports(List<String> serviceInterfaceImports) {
        this.serviceInterfaceImports = serviceInterfaceImports;
    }
}
