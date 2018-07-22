package com.ekeyfund.oss.generator.action.po;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import com.ekeyfund.oss.generator.action.annotion.CodeGenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 包装mybatisplus的默认TableInfo，扩展更多和模板生成有关的信息
 * Created by zhaolin on 4/3/2018.
 */
public class GunsTableInfo extends TableInfo {
    private TableInfo tableInfo;
    private StrategyConfig strategyConfig;
    private TableFieldComparator tableFieldComparator = new TableFieldComparator();
    /**
     * 添加模板需要显示的列
     */
    private List<TableField> addFields = new ArrayList<>();
    /**
     * 编辑模板需要显示的列
     */
    private List<TableField> editFields = new ArrayList<>();
    /**
     * 列表模板需要显示的列
     */
    private List<TableField> listFields = new ArrayList<>();
    /**
     * 隐藏列
     */
    private List<TableField> addHiddenFields = new ArrayList<>();
    /**
     * 隐藏列
     */
    private List<TableField> editHiddenFields = new ArrayList<>();
    /**
     * 隐藏列
     */
    private List<TableField> listHiddenFields = new ArrayList<>();

    public GunsTableInfo(TableInfo tableInfo,StrategyConfig strategyConfig){
        this.tableInfo = tableInfo;
        this.strategyConfig = strategyConfig;
        init();
    }

    private void init() {
        //添加上父实体类的列
        //tableInfo.getFields().addAll(tableInfo.getCommonFields());
        try {
            Class entity = Class.forName(strategyConfig.getSuperEntityClass());
            Map<String,Field> fieldMap = ReflectionKit.getFieldMap(entity);
            buildListInfo(getFields(),fieldMap);
            buildListInfo(getCommonFields(),fieldMap);
            //显示排序
            this.addFields.sort(tableFieldComparator);
            this.editFields.sort(tableFieldComparator);
            this.listFields.sort(tableFieldComparator);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static class TableFieldComparator<TableField> implements Comparator<TableField>{
        @Override
        public int compare(TableField o1, TableField o2) {
            if(o1 instanceof GunsTableField && o2 instanceof GunsTableField){
                return (((GunsTableField)o1).getOrder() < ((GunsTableField)o2).getOrder())?-1:1;
            }else {
                return 0;
            }
        }
    }
    private void buildListInfo(List<TableField> fields,Map<String,Field> fieldMap){
        for(TableField tableField:fields){
            GunsTableField newTableField = new GunsTableField(tableField);
            Field field = fieldMap.get(tableField.getName());
            if(field != null){
                CodeGenerator codeGenerator = field.getAnnotation(CodeGenerator.class);
                if(codeGenerator != null){
                    //排序标识
                    newTableField.setOrder(codeGenerator.order());
                    newTableField.setSortable(codeGenerator.sortable());
                    //忽略的字段无需处理
                    if(!codeGenerator.ignore()){
                        //全部隐藏列
                        if(!codeGenerator.visible()){
                            newTableField.setAddVisiable(false);
                            newTableField.setEditVisiable(false);
                            newTableField.setListVisiable(false);
                            //隐藏列忽略编辑
                            addHiddenFields.add(newTableField);
                            editHiddenFields.add(newTableField);
                            listHiddenFields.add(newTableField);
                        }else{
                            //添加不可见
                            if(!codeGenerator.addVisiable()){
                                newTableField.setAddVisiable(false);
                                addHiddenFields.add(newTableField);
                            }else{
                                //添加可见,处理是否可以编辑
                                if(!codeGenerator.addEditable()|| !codeGenerator.editable()){
                                    newTableField.setAddEditable(false);
                                }
                                addFields.add(newTableField);
                            }

                            //编辑不可见
                            if(!codeGenerator.editVisiable()){
                                newTableField.setEditVisiable(false);
                                editHiddenFields.add(newTableField);
                            }else{
                                //编辑可见,处理是否可以编辑
                                if(!codeGenerator.editEditable()|| !codeGenerator.editable()){
                                    newTableField.setEditEditable(false);
                                }
                                editFields.add(newTableField);
                            }

                            //列表不可见
                            if(!codeGenerator.listVisiable()){
                                newTableField.setListVisiable(false);
                                listHiddenFields.add(newTableField);
                            }else{
                                //编辑可见,处理是否可以编辑
                                if(!codeGenerator.listEditable()|| !codeGenerator.editable()){
                                    newTableField.setListEditable(false);
                                }
                                listFields.add(newTableField);
                            }
                        }
                    }else{
                        //忽略
                        newTableField.setIgnore(true);
                    }
                }else{
                    //无注解，默认处理
                    addFields.add(newTableField);
                    editFields.add(newTableField);
                    listFields.add(newTableField);
                }

            }else{
                //自定义属性，默认处理
                addFields.add(newTableField);
                editFields.add(newTableField);
                listFields.add(newTableField);
            }
        }
    }
    @Override
    public boolean isConvert() {
        return tableInfo.isConvert();
    }

    @Override
    public void setConvert(boolean convert) {
        tableInfo.setConvert(convert);
    }
    @Override
    public String getName() {
        return tableInfo.getName();
    }
    @Override
    public void setName(String name) {
        tableInfo.setName(name);
    }
    @Override
    public String getComment() {
        return tableInfo.getComment();
    }
    @Override
    public void setComment(String comment) {
        tableInfo.setComment(comment);
    }
    @Override
    public String getEntityPath() {
        return tableInfo.getEntityPath();
    }
    @Override
    public String getEntityName() {
        return tableInfo.getEntityName();
    }
    @Override
    public void setEntityName(StrategyConfig strategyConfig, String entityName) {
        tableInfo.setEntityName(strategyConfig,entityName);
    }
    @Override
    public String getMapperName() {
        return tableInfo.getMapperName();
    }
    @Override
    public void setMapperName(String mapperName) {
        tableInfo.setMapperName(mapperName);
    }
    @Override
    public String getXmlName() {
        return tableInfo.getXmlName();
    }
    @Override
    public void setXmlName(String xmlName) {
        tableInfo.setXmlName(xmlName);
    }
    @Override
    public String getServiceName() {
        return tableInfo.getServiceName();
    }
    @Override
    public void setServiceName(String serviceName) {
        tableInfo.setServiceName(serviceName);
    }
    @Override
    public String getServiceImplName() {
        return tableInfo.getServiceImplName();
    }
    @Override
    public void setServiceImplName(String serviceImplName) {
        tableInfo.setServiceImplName(serviceImplName);
    }
    @Override
    public String getControllerName() {
        return tableInfo.getControllerName();
    }
    @Override
    public void setControllerName(String controllerName) {
        tableInfo.setControllerName(controllerName);
    }
    @Override
    public List<TableField> getFields() {
        return tableInfo.getFields();
    }
    @Override
    public void setFields(List<TableField> fields) {
        tableInfo.setFields(fields);
    }
    @Override
    public List<TableField> getCommonFields() {
        return tableInfo.getCommonFields();
    }
    @Override
    public void setCommonFields(List<TableField> commonFields) {
        tableInfo.setCommonFields(commonFields);
    }
    @Override
    public List<String> getImportPackages() {
        return tableInfo.getImportPackages();
    }
    @Override
    public void setImportPackages(String pkg) {
        tableInfo.setImportPackages(pkg);
    }

    /**
     * 逻辑删除
     */
    @Override
    public boolean isLogicDelete(String logicDeletePropertyName) {
        return tableInfo.isLogicDelete(logicDeletePropertyName);
    }

    /**
     * 转换filed实体为xmlmapper中的basecolumn字符串信息
     *
     * @return
     */
    @Override
    public String getFieldNames() {
        return tableInfo.getFieldNames();
    }

    public List<TableField> getAddFields() {
        return addFields;
    }

    public void setAddFields(List<TableField> addFields) {
        this.addFields = addFields;
    }

    public List<TableField> getEditFields() {
        return editFields;
    }

    public void setEditFields(List<TableField> editFields) {
        this.editFields = editFields;
    }

    public List<TableField> getListFields() {
        return listFields;
    }

    public void setListFields(List<TableField> listFields) {
        this.listFields = listFields;
    }

    public List<TableField> getAddHiddenFields() {
        return addHiddenFields;
    }

    public void setAddHiddenFields(List<TableField> addHiddenFields) {
        this.addHiddenFields = addHiddenFields;
    }

    public List<TableField> getEditHiddenFields() {
        return editHiddenFields;
    }

    public void setEditHiddenFields(List<TableField> editHiddenFields) {
        this.editHiddenFields = editHiddenFields;
    }

    public List<TableField> getListHiddenFields() {
        return listHiddenFields;
    }

    public void setListHiddenFields(List<TableField> listHiddenFields) {
        this.listHiddenFields = listHiddenFields;
    }
}
