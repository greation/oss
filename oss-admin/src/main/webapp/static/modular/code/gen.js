/**
 * 初始化
 */
var Code = {
    ztreeInstance: null,
    tableName: "",
    submitData: {},
    switchs: {}
};

/**
 * 选择table的事件
 */
Code.selectTable = function (tableName) {

    SelectList.clearSelect("templateList");
    Code.switchs = {};

    if (SelectList.singelSelect("tableList", "tableName", tableName) == true) {
        Code.tableName = tableName;
        Code.setTableName(tableName);
    } else {
        Code.tableName = "";
    }
};

/**
 * 选择模板的事件
 */
Code.selectTemplate = function (templateKey) {
    if (Code.tableName != "") {
        if (SelectList.mutiSelect("templateList", "key", templateKey) == true) {
            Code.switchs[templateKey] = true;
        } else {
            Code.switchs[templateKey] = false;
        }
    } else {
        Feng.info("请先选择表");
    }
};

/**
 * 点击生成
 */
Code.generate = function () {
    Code.submitData = {};
    Code.submitData.tableName = Code.tableName;
    this.set('projectPath').set('author').set('projectPackage').set('corePackage').set('ignoreTabelPrefix').set('bizName').set('moduleName').set('parentMenuName').set('superEntityClass').set('datasourceEnum');
    var baseAjax = Feng.baseAjax("/code/generate", "生成代码");

    for (var item in Code.switchs) {
        Code.submitData[item] = Code.switchs[item];
    }

    baseAjax.setData(Code.submitData);
    baseAjax.start();
};

Code.loadTable = function () {
    var urlo =window.location.href;
    var datasourceEnum =$("#datasourceEnum option:selected").val();
    urlo = urlo.substr(0,urlo.indexOf('?'));
    var url =urlo +"?datasourceEnum="+datasourceEnum;
    window.location.href=url;
};

/**
 *获取url参数
 */
Code.getRequest = function (url, name) {
    var url = window.location.href;
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

/**
 * 设置表名称
 */
Code.setTableName = function (tableName) {
    var preSize = $("#ignoreTabelPrefix").val().length;
    $("#tableName").val(tableName);
    $("#className").val(Feng.underLineToCamel(tableName.substring(preSize)));
};

/**
 * 点击父级编号input框时
 */
Code.onClickDept = function (e, treeId, treeNode) {
    $("#parentMenuName").attr("value", Code.ztreeInstance.getSelectedVal());
};

/**
 * 显示父级菜单选择的树
 */
Code.showMenuSelectTree = function () {
    Feng.showInputTree("parentMenuName", "pcodeTreeDiv", 15, 34);
};

$(function () {
    var ztree = new $ZTree("pcodeTree", "/menu/selectMenuTreeList");
    ztree.bindOnClick(Code.onClickDept);
    ztree.init();
    Code.ztreeInstance = ztree;
    $("#pcodeTree").css('width',$("#parentMenuName").css('width'));
});

Code.set = function (key, val) {
    Code.submitData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};