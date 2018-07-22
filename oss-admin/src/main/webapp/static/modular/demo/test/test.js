/**
 * 测试生成管理初始化
 */
var Test = {
    id: "TestTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    permissions:null  //权限标识，默认均无权限，会在首页html中赋值
};

/**
 * 判断是否有权限
 * @param permission YQ中定义的常量，或者自定义的常量key，描述是否有对应权限
 * @returns {boolean}
 */
Test.hasPermission = function(permission){
    return Test.permissions.get(permission);
}

/**
 * 初始化表格的列
 */
Test.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
            {title: '序号', field: 'id', visible: true, sortable:true,align: 'center', valign: 'middle'},
            {title: '名称', field: 'value', visible: true, sortable:true,align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: true, sortable:true,align: 'center', valign: 'middle'},
            {title: '创建人', field: 'creater', visible: true, sortable:false,align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updatetime', visible: true, sortable:true,align: 'center', valign: 'middle'},
            {title: '更新人', field: 'updater', visible: true, sortable:false,align: 'center', valign: 'middle'}
        ];

        if(Test.hasPermission("/test/delete") || Test.hasPermission("/test/update")){
            columns.push({title: '操作', field: 'Button', visible: true, align: 'center', valign: 'middle', formatter:Test.addFunctionAlty});
        }
        if(Test.hasPermission("/test/oplog")){
            columns.push({title: '操作日志', field: 'Button11', visible: false, align: 'center', valign: 'middle', formatter: Test.viewFunction});
        }
        return columns;
};

Test.addFunctionAlty = function (value,row,index) {
    var value = '';
    if(Test.hasPermission("/test/update")){
        value+='<button id="TableEditor" type="button" class="btn btn-link btn-xs text-success" onclick="Test.openEditTest(\''+ row.id +'\')">编辑</button>';
    }
    if(Test.hasPermission("/test/delete")){
        value+='<button id="TableDelete" type="button" class="btn btn-link btn-xs text-success" onclick="Test.delete(\''+ row.id +'\')">删除</button>';
    }
    return value;
}

Test.viewFunction = function (value, row, index) {
    var value = '';
    if(Test.hasPermission("/test/oplog")){
        value+='<button id="TableOplog" type="button" class="btn btn-link btn-xs text-success" onclick="Test.viewOpLog(\''+ row.id +'\')">查看</button>';
    }
    return value;
}

/**
 * 显示测试生成操作日志
 */
Test.viewOpLog = function (id) {
    var url = Feng.ctxPath + '/test/test_oplog/' + ((id==null)?Test.seItem.id:id);
        var index = layer.open({
            type: 2,
            title: '操作日志',
            area: ['900px', '450px'], //宽高
            //fix: false, //不固定
            maxmin: true,
            content: url
            ,btn: ['关闭']
            ,btnAlign: 'c'
            ,cancel: function(index, layero){
            }
        });
        layer.full(index);
        this.layerIndex = index;
};

/**
 * 检查测试生成是否选中
 */
Test.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Test.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加测试生成
 */
Test.openAddTest = function () {
    var index = layer.open({
        type: 2,
        title: '添加测试生成',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/test/test_add'
    });
    this.layerIndex = index;
};

/**
 * 点击编辑测试生成
 */
Test.openEditTest = function (id) {
    if (id!=null||this.check()) {
        var index = layer.open({
            type: 2,
            title: '测试生成详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/test/test_update/' + ((id==null)?Test.seItem.id:id)
        });
        this.layerIndex = index;
    }
};

/**
 * 点击删除测试生成
 */

Test.delete = function (id) {

    if (id!=null||this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/test/delete", function (data) {
                Feng.success("删除成功!");
                Test.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("testId",((id==null)?Test.seItem.id:id));
            ajax.start();
        };
            Feng.confirm("是否刪除测试生成?", operation);
        }
}



/**
 * 查询测试生成列表
 */
Test.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    Test.table.refresh({query: queryData});
};


/**
 * 初始化，必须在html中待权限设置后调用初始化
 */
$(function () {
    var ajax = new $ax(Feng.ctxPath + "/permissions/test", function (data) {
        Test.permissions = new Map();
        for(var p in data){//遍历json对象的每个key/value对,p为key
            //alert(p+' '+typeof(data[p])+' '+data[p]);
            Test.permissions.set(p,(data[p]));
        }
        var defaultColunms = Test.initColumn();
        var table = new BSTable(Test.id, "/test/list", defaultColunms);
        table.setPaginationType("server");
        Test.table = table.init();
    }, function (data) {
        Feng.error("加载权限失败!");
    });
    ajax.start();
});

