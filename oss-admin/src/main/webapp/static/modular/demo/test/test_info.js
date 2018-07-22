/**
 * 初始化测试生成详情对话框
 */
var TestInfoDlg = {
    testInfoData : {}
};

/**
 * 清除数据
 */
TestInfoDlg.clearData = function() {
    this.testInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestInfoDlg.set = function(key, val) {
    this.testInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TestInfoDlg.close = function() {
    parent.layer.close(window.parent.Test.layerIndex);
}

/**
 * 收集新增数据
 */
TestInfoDlg.collectAddData = function() {
    this
    .set('value')
    .set('createtime')
    .set('creater')
    .set('updatetime')
    .set('updater');
}

/**
 * 收集编辑数据
 */
TestInfoDlg.collectEditData = function() {
    this
    .set('id')
    .set('value')
    .set('createtime')
    .set('creater')
    .set('updatetime')
    .set('updater');
}


/**
 * 提交添加
 */
TestInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectAddData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/test/add", function(data){
        Feng.success("添加成功!");
        window.parent.Test.table.refresh();
        TestInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.setContentType(Feng.fullContentType("JSON"));
    ajax.set(this.testInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TestInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectEditData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/test/update", function(data){
        Feng.success("修改成功!");
        window.parent.Test.table.refresh();
        TestInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.setContentType(Feng.fullContentType("JSON"));
    ajax.set(this.testInfoData);
    ajax.start();
}

$(function() {

});
