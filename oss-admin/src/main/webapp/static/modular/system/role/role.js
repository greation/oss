/**
 * 角色管理的单例
 */
var Role = {
    id: "roleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Role.initColumn = function () {
    var columns = [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '角色名', field: 'nameFunction', align: 'center', valign: 'middle', sortable: true, events:operateEvents, formatter:Role.nameFunction},
        {title: '员工数', field: 'userCount', align: 'center', valign: 'middle', sortable: true},
        {title: '角色状态', field: 'status', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: 'addFunctionAlty', visible: true, align: 'center', valign: 'middle', events:operateEvents, formatter:Role.addFunctionAlty},
        {title: '操作日志', field: 'viewFunction', visible: false, align: 'center', valign: 'middle', events:operateEvents, formatter:Role.viewFunction}]
    return columns;
};

Role.nameFunction = function (value, row, index) {
    return [
        '<button id="TableName" class="btn btn-link btn-xs text-success"><span  class="text_split"  style="width: 120px;">'+ row.name +'</span></button>'
    ].join("");
};
Role.addFunctionAlty = function (value, row, index) {
    return [
        '<button id="TableEditor" type="button" class="btn btn-link btn-xs text-success">编辑</button>',
        '<button id="TablePower" type="button" class="btn btn-link btn-xs text-success">功能权限</button>'
    ].join("");
};
Role.viewFunction = function (value, row, index) {
    return [
        '<button id="TableView" type="button" class="btn btn-link btn-xs text-success">查看</button>'
    ].join("");
};
//
window.operateEvents = {
    "click #TableName":function (e,value,row,index) {
        var  url =Feng.ctxPath + '/role/view/' + row.id;
        if(YQ.checkPermission(url, YQ.methodGET())){
            var index = layer.open({
                type: 2,
                title: '角色详情',
                area: ['900px', '450px'], //宽高
                //fix: false, //不固定
                maxmin: true,
                content:url
                ,btn: ['关闭']
                ,btnAlign: 'c'
                ,cancel: function(index, layero){
                }
            });
            layer.full(index);//全屏打开
            this.layerIndex = index;
        }else{
            Feng.error("权限异常")
        }
    },
    "click #TableEditor":function (e,value,row,index) {
        Role.openEditRole(row.id)
    },
    "click #TablePower":function (e,value,row,index) {
        var url = Feng.ctxPath + '/role/role_assign/' + row.id;
        if(YQ.checkPermission(url, YQ.methodGET())){
        var index = layer.open({
            type: 2,
            title: '权限配置',
            area: ['1200px', '520px'], //宽高
            // fix: false, //不固定
            maxmin: true,
            content:url
        });
        layer.full(index);
        this.layerIndex = index;
        }else{
            Feng.error("权限异常")
        }
    },
    "click #TableView":function (e,value,row,index) {
        Role.openLogRole(row.id)
    }
};
/**
 * 检查是否选中
 */
Role.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Role.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理员
 */
Role.openAddRole = function () {
    var url = Feng.ctxPath + '/role/role_add'
    if(YQ.checkPermission(url, YQ.methodGET())){
    var index = layer.open({
        type: 2,
        title: '新增角色',
        area: ['600px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: url
    });
    this.layerIndex = index;
}else{
    Feng.error("权限异常")
}
};

/*编辑角色*/
Role.openEditRole = function (id) {
    var url = Feng.ctxPath + '/role/role_edit/' + id
    if(YQ.checkPermission(url, YQ.methodGET())){
    var index = layer.open({
        type: 2,
        title: '编辑角色',
        area: ['600px', '450px'], //宽高
        fix: false, //不固定
        maxmin: false
        ,closeBtn:0
        ,content:url
    });
    this.layerIndex = index;
    }else{
        Feng.error("权限异常")
    }
};

Role.openLogRole = function (id) {
    var url =Feng.ctxPath + '/role/log/' + id;
    if(YQ.checkPermission(url, YQ.methodGET())){
    var index = layer.open({
        type: 2,
        title: '操作日志',
        area: ['900px', '450px'], //宽高
        fix: false, //不固定
        //maxmin: true,
        content: url
        ,btn: ['关闭']
        ,btnAlign: 'c'
        ,cancel: function(index, layero){
        }
    });
    layer.full(index);//全屏打开
    this.layerIndex = index;
}else{
    Feng.error("权限异常")
}
};
/**
 * 权限配置
 */
Role.assign = function () {
    var url =Feng.ctxPath + '/role/role_assign/' + this.seItem.id;
    if(YQ.checkPermission(url, YQ.methodGET())){
        if (this.check()) {
            var index = layer.open({
                type: 2,
                title: '权限配置',
                area: ['300px', '450px'], //宽高
                fix: false, //不固定
                maxmin: true,
                content: url
            });
            this.layerIndex = index;
        }
    }else{
        Feng.error("权限异常")
    }
};
/**
 * 角色详情
 */
Role.openDetailsRole = function (id) {
    var url =Feng.ctxPath + '/role/role_view/' + id;
    if(YQ.checkPermission(url, YQ.methodGET())){
    var index = layer.open({
        type: 2,
        title: '角色详情',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: url
    });
    this.layerIndex = index;
}else{
    Feng.error("权限异常")
}
};
/**
 * 搜索角色
 */
Role.search = function () {
    var queryData = {};
    queryData['roleName'] = YQ.Trim($("#roleName").val());
    queryData['status'] = $('#status').val();

    Role.table.refresh({query: queryData});
}

Role.clear = function () {
    $("#roleName").val('');
    $('#status').val("0");
}

$(function () {
    var defaultColunms = Role.initColumn();
    var table = new BSTable(Role.id, "/role/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Role.table = table;
});
