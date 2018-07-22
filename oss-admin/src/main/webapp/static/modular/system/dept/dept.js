/**
 * 部门管理初始化
 */
var Dept = {
    id: "DeptTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Dept.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true, visible:false},
        {title: 'id', field: 'id',visible: false, align: 'center', valign: 'middle',width:'10%'},
        {title: '部门名称', field: 'simplename', align: 'center', valign: 'middle', sortable: true},
        {title: '展示顺序', field: 'num', align: 'center', valign: 'middle', sortable: true,formatter:Dept.rankNum},
        {title: '员工数', field: 'usercount', align: 'center', valign: 'middle', sortable: true},
        {title: '更新时间', field: 'updatetime', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: 'operate', visible: true, align: 'center', valign: 'middle',formatter:Dept.addFunctionAlty},
        {title: '操作日志', field: 'operatelog', visible: true, align: 'center', valign: 'middle',formatter:Dept.viewFunction}
    ];
    return columns;
};

Dept.addFunctionAlty = function (value,row,index) {
    var pids = row.pids;
    if(pids.split(',').length > 5){
        return [
            '<button id="TableEditorDept" type="button" class="btn btn-link btn-xs text-success" onclick="Dept.EditeDeptDetail('+ row.id +')">编辑</button>'
            ,'<button id="TableDeletDept" type="button" class="btn btn-link btn-xs text-success" onclick="Dept.delete('+ row.id +')">删除</button>'
        ].join("");
    }else{
        return [
            '<button id="TableEditorDept" type="button" class="btn btn-link btn-xs text-success" onclick="Dept.EditeDeptDetail('+ row.id +')">编辑</button>'
            ,'<button id="TableDeletDept" type="button" class="btn btn-link btn-xs text-success" onclick="Dept.delete('+ row.id +')">删除</button>'
            ,'<button id="TableAddDept" type="button" class="btn btn-link btn-xs text-success" onclick="Dept.openAddDownDept('+ row.id +')">增加下级部门</button>'
        ].join("");
    }

}
Dept.viewFunction = function (value, row, index) {
    return [
        '<button id="DeptView" type="button" class="btn btn-link btn-xs text-success" onclick="ViewDept('+ row.id +')">查看</button>'
    ]
};
Dept.rankNum =function (value,row,index) {
    return [
        '<button id="DeptRanking" type="button" class="btn btn-link btn-xs text-success" onclick="Dept.DeptRank('+ row.id +')">' + row.num +'</button>'
    ]
}
Dept.EditeDeptDetail = function (id) {
    var url = Feng.ctxPath + '/dept/dept_update/' + id;
    if(YQ.checkPermission(url, YQ.methodGET())){
        var index = layer.open({
            type: 2,
            title: '部门编辑',
            area: ['500px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: url
        });
        this.layerIndex = index;
    }else{
        Feng.error("权限异常")
    }
};
function DeletDept(id) {
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/dept/delete", function () {
            Feng.success("删除成功!");
            Dept.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("deptId",id);
        ajax.start();
    };

    Feng.confirm("是否刪除该部门?", operation);
}

Dept.DeptRank = function(id) {
    var url =Feng.ctxPath + '/dept/dept_modify_num/' + id;
    if(YQ.checkPermission(url, YQ.methodGET())){
        var index = layer.open({
            type: 2,
            title: '调序',
            area: ['600px', '300px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: url
        });
        this.layerIndex = index;
    }else{
        Feng.error("权限异常")
    }
}


Dept.openAddDownDept = function (id) {
    var url =Feng.ctxPath + '/dept/dept_add/'+ id;
    if(YQ.checkPermission(url)){
    var index = layer.open({
        type: 2,
        title: '添加部门',
        area: ['80%', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: url
    });
    this.layerIndex = index;
}else{
    Feng.error("权限异常")
}
};
function ViewDept(id) {
//提交信息
    var url = Feng.ctxPath + "/dept/dept_log/" + id;
    if(YQ.checkPermission(url)){
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
}
/**
 * 检查是否选中
 */
Dept.check = function () {
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Dept.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加部门
 */
Dept.openAddDept = function () {
    var index = layer.open({
        type: 2,
        title: '添加部门',
        area: ['500px', '430px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dept/dept_add/-1'
    });
    this.layerIndex = index;
};

/**
 * 打开查看部门详情
 */
Dept.openDeptDetail = function () {
        var index = layer.open({
            type: 2,
            title: '部门详情',
            area: ['80%', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dept/dept_update/' + Dept.seItem.id
        });
        this.layerIndex = index;
};

/**
 * 删除部门
 */
Dept.delete = function (id) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/dept/delete", function () {
                Feng.success("删除成功!");
                Dept.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("deptId",id);
            ajax.start();
        };
        Feng.confirm("是否刪除该部门?", operation);
};

/**
 * 查询部门列表
 */
Dept.search = function () {
    var queryData = {};
    queryData['condition'] = YQ.Trim($("#condition").val());
    Dept.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Dept.initColumn();
    var table = new BSTreeTable(Dept.id, "/dept/list", defaultColunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(true);
    table.init();
    Dept.table = table;
});
