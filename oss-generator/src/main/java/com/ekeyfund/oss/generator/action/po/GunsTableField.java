package com.ekeyfund.oss.generator.action.po;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;

/**
 * 包装mybatisplus的默认 * TableField，扩展更多和模板生成有关的信息
 ，扩展更多和模板生成有关的信息
 * Created by zhaolin on 4/3/2018.
 */
public class GunsTableField extends TableField implements Comparable<GunsTableField>{
    private TableField tableField;
    /**
     * 是否在添加模板显示
     */
    private boolean addVisiable = true;
    /**
     * 是否在编辑模板显示
     */
    private boolean editVisiable = true;
    /**
     * 是否在列表模板显示
     */
    private boolean listVisiable = true;

    /**
     * 是否可编辑
     */
    private boolean addEditable = true;
    /**
     * 是否可编辑
     */
    private boolean editEditable = true;
    /**
     * 是否可编辑
     */
    private boolean listEditable = true;

    /**
     * 是否是忽略列，忽略列不推送到前台
     */
    private boolean ignore = false;
    /*
     显示排序
     */
    private int order = 0;
    /**
     * 表格排序
     */
    private boolean sortable = true;

    public GunsTableField(TableField tableField){
        this.tableField = tableField;
    }

    public boolean isAddVisiable() {
        return addVisiable;
    }

    public void setAddVisiable(boolean addVisiable) {
        this.addVisiable = addVisiable;
    }

    public boolean isEditVisiable() {
        return editVisiable;
    }

    public void setEditVisiable(boolean editVisiable) {
        this.editVisiable = editVisiable;
    }

    public boolean isListVisiable() {
        return listVisiable;
    }

    public void setListVisiable(boolean listVisiable) {
        this.listVisiable = listVisiable;
    }

    public boolean isAddEditable() {
        return addEditable;
    }

    public void setAddEditable(boolean addEditable) {
        this.addEditable = addEditable;
    }

    public boolean isEditEditable() {
        return editEditable;
    }

    public void setEditEditable(boolean editEditable) {
        this.editEditable = editEditable;
    }

    public boolean isListEditable() {
        return listEditable;
    }

    public void setListEditable(boolean listEditable) {
        this.listEditable = listEditable;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean isConvert() {
        return tableField.isConvert();
    }
    @Override
    public void setConvert(boolean convert) {
        tableField.setConvert(convert);
    }
    @Override
    public boolean isKeyFlag() {
        return tableField.isKeyFlag();
    }
    @Override
    public void setKeyFlag(boolean keyFlag) {
        tableField.setKeyFlag(keyFlag);
    }
    @Override
    public boolean isKeyIdentityFlag() {
        return tableField.isKeyIdentityFlag();
    }
    @Override
    public void setKeyIdentityFlag(boolean keyIdentityFlag) {
        tableField.setKeyIdentityFlag(keyIdentityFlag);
    }
    @Override
    public String getName() {
        return tableField.getName();
    }
    @Override
    public void setName(String name) {
        tableField.setName(name);
    }
    @Override
    public String getType() {
        return tableField.getType();
    }
    @Override
    public void setType(String type) {
        tableField.setType(type);
    }
    @Override
    public String getPropertyName() {
        return tableField.getPropertyName();
    }
    @Override
    public void setPropertyName(StrategyConfig strategyConfig, String propertyName) {
        tableField.setPropertyName(strategyConfig,propertyName);
    }
    @Override
    public DbColumnType getColumnType() {
        return tableField.getColumnType();
    }
    @Override
    public void setColumnType(DbColumnType columnType) {
        tableField.setColumnType(columnType);
    }
    @Override
    public String getPropertyType() {
        return tableField.getPropertyType();
    }
    @Override
    public String getComment() {
        return tableField.getComment();
    }
    @Override
    public void setComment(String comment) {
        tableField.setComment(comment);
    }

    /**
     * 按JavaBean规则来生成get和set方法
     */
    @Override
    public String getCapitalName() {
       return tableField.getCapitalName();
    }
    @Override
    public String getFill() {
        return tableField.getFill();
    }
    @Override
    public void setFill(String fill) {
        tableField.setFill(fill);
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    @Override
    public int compareTo(GunsTableField o) {
        return getOrder() - o.getOrder();
    }
}
