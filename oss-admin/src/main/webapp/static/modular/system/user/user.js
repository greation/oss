/**
 * 系统管理--用户管理的单例对象
 */
var MgrUser = {
    id: "managerTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid: 0
};

/**
 * 初始化表格的列
 */
MgrUser.initColumn = function () {
    var columns = [
        {field: 'selectItem',align: 'center', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '登录账号', field: 'account', align: 'center', valign: 'middle',events: operateEvents,
            formatter: MgrUser.showDetail},
        {title: '员工姓名', field: 'name', align: 'center', valign: 'middle'},
        {title: '所属部门', field: 'deptName', align: 'center', valign: 'middle'},
        {title: '角色名称', field: 'roleName', visible: false},
        {title: '是否此部门主管', field: 'managerName', align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle'},
        {
            title: '操作',
            field: 'Button',
            visible: true,
            align: 'center',
            valign: 'middle',
            events: operateEvents,
            formatter: MgrUser.addFunctionAlty
        },
        {
            title: '操作日志',
            field: 'Button11',
            visible: false,
            align: 'center',
            valign: 'middle',
            events: operateEvents,
            formatter: MgrUser.viewFunction
        }
    ];
    return columns;
};
MgrUser.dptSelect = function (value, row, index) {
    var dpt = [];
    dpt = row.deptName;
};
MgrUser.addFunctionAlty = function (value, row, index) {
    if (row.statusName == "冻结") {
        return [
            '<button id="TableEditor" type="button" class="btn btn-link btn-xs text-success">编辑</button>',
            '<button id="TableResetpwd" type="button" class="btn btn-link btn-xs text-success">重置密码</button>',
            '<button id="TableunFreenze" type="button" class="btn btn-link btn-xs text-success">取消冻结</button>'
        ].join("");
    } else {
        return [
            '<button id="TableEditor" type="button" class="btn btn-link btn-xs text-success">编辑</button>',
            '<button id="TableResetpwd" type="button" class="btn btn-link btn-xs text-success">重置密码</button>',
            '<button id="TableFreenze" type="button" class="btn btn-link btn-xs text-success">冻结账号</button>'
        ].join("");
    }

}
MgrUser.viewFunction = function (value, row, index) {
    return [
        '<button id="MgrUserView" type="button" class="btn btn-link btn-xs text-success">查看</button>'
    ]
};
MgrUser.showDetail = function (value, row, index) {
    row.account =YQ.phoneSeparated(row.account);
        return [
            '<button  type="button" class="btn btn-link btn-xs text-success" onclick="MgrUser.roleDetail('+ row.id+')">'+ row.account +'</button>'
        ]
};
window.operateEvents = {
    "click #TableEditor": function (e, value, row, index) {
        MgrUser.openChangeUser(row.id);
    },
    "click #TableResetpwd": function (e, value, row, index) {
        MgrUser.resetPwd(row.id);
    },
    "click #TableFreenze": function (e, value, row, index) {
        MgrUser.freezeThisAccount(row.id);
    },
    "click #TableunFreenze": function (e, value, row, index) {
        MgrUser.unfreeze(row.id);
    },
    "click #MgrUserView": function (e, value, row, index) {
        MgrUser.ViewDept(row.id);
    }
}
/**
 * 检查是否选中
 */
MgrUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        MgrUser.seItem = selected[0];
        return true;
    }
};


/**
 *
 *
 * */
MgrUser.roleDetail =function (id) {
    var url =Feng.ctxPath + '/mgr/user_detail/' + id;
    if(YQ.checkPermission(url, YQ.methodGET())){
    var index = layer.open({
        type: 2,
        title: '用户详情',
        area: ['80%', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content:url
        ,btn: ['关闭']
        ,btnAlign: 'c'
        ,cancel: function(index, layero){
        }
    });
    this.layerIndex = index;
    }else{
        Feng.error("权限异常")
    }
}
/**
 * 点击添加管理员
 */
MgrUser.openAddMgr = function () {
    var url =Feng.ctxPath + '/mgr/user_add';
    if(YQ.checkPermission(url)){
    var index = layer.open({
        type: 2,
        title: '新增员工',
        area: ['70%', '550px'], //宽高
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
 * 点击修改按钮时
 * @param userId 管理员id
 */
MgrUser.openChangeUser = function (id) {
    //提交信息
    var url = Feng.ctxPath + "/mgr/user_edit/" + id;
    if(YQ.checkPermission(url, YQ.methodGET())){
        var index = layer.open({
            type: 2,
            title: '编辑员工',
            area: ['80%', '600px'], //宽高
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
 * 点击调整部门
 * @param
 */
MgrUser.deptAssign = function () {
    if (this.check()) {
        var selectRows = $("#managerTable").bootstrapTable('getSelections', function (row) {
            return row;
        });
        var userIds = '';
        for(index in selectRows){
            userIds += selectRows[index].id + ",";
        }
        userIds = userIds.substring(0,userIds.length - 1);
        var url = Feng.ctxPath + '/mgr/change_dept_view/'+userIds;
        if(YQ.checkPermission(url)){
        var index = layer.open({
            type: 2,
            title: '选择部门',
            area: ['500px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: url
        });
        this.layerIndex = index;
        }else{
            Feng.error("权限异常")
        }
    }
};
/**
 * 点击角色分配
 * @param
 */
MgrUser.roleAssign = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '角色分配',
            area: ['300px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mgr/role_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除用户
 */
MgrUser.delMgrUser = function () {
    if (this.check()) {

        var operation = function () {
            var userId = MgrUser.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/mgr/delete", function () {
                if(data.code=="500"){
                    Feng.error(data.message);
                }else {
                    Feng.success(data.message);
                }
                MgrUser.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("userId", userId);
            ajax.start();
        };

        Feng.confirm("是否删除用户" + MgrUser.seItem.account + "?", operation);
    }
};
/**
 *
 * 操作日志
 *
 */
MgrUser.showDept = function () {
    $("#deptTree").toggle();
}


/****
 *判断是否有权限
 * @param url
 * @returns {boolean}
 */

MgrUser.ViewDept = function (id) {
    var url = Feng.ctxPath + '/mgr/user_log_view/' + id;
    if(YQ.checkPermission(url, YQ.methodGET())){
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
    }else{
        Feng.error("权限异常")
    }
};

/**
 * 冻结用户账户
 * @param userId
 */
MgrUser.freezeAccount = function () {
    if (this.check()) {
        var selectRows = $("#managerTable").bootstrapTable('getSelections', function (row) {
            return row;
        });
        var userIds = '';
        for(index in selectRows){
            userIds += selectRows[index].id + ",";
        }
        userIds = userIds.substring(0,userIds.length - 1);
        var ajax = new $ax(Feng.ctxPath + "/mgr/batchFreeze", function (data) {
            if(data.code=="500"){
                Feng.error(data.message);
            }else {
                Feng.success(data.message);
            }
            MgrUser.table.refresh();
        }, function (data) {
            Feng.error("冻结失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userIds", userIds);
        ajax.start();
    }
};
MgrUser.freezeThisAccount = function (id) {
    var userId = id;
    var ajax = new $ax(Feng.ctxPath + "/mgr/freeze", function (data) {
        if(data.code=="500"){
            Feng.error(data.message);
        }else {
            Feng.success(data.message);
        }
        MgrUser.table.refresh();
    }, function (data) {
        Feng.error("冻结失败!" + data.responseJSON.message + "!");
    });
    ajax.set("userId", userId);
    ajax.start();
};
/**
 * 解除冻结用户账户
 * @param userId
 */
MgrUser.unfreeze = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/mgr/unfreeze", function (data) {
        if(data.code=="500"){
            Feng.error(data.message);
        }else {
            Feng.success(data.message);
        }
        MgrUser.table.refresh();
    }, function (data) {
        Feng.error(data.responseJSON.message||data.responseText.message||data.message);
    });
    ajax.set("userId", id);
    ajax.start();
}

/**
 * 重置密码
 */
MgrUser.resetPwd = function (id) {
    var userId = id;
    parent.layer.confirm('确认重置密码嘛？', {
        btn: ['确定', '取消'],
        shade: false //不显示遮罩
    }, function () {
        var ajax = new $ax(Feng.ctxPath + "/mgr/reset", function (data) {
            Feng.success(data.message);
        }, function (data) {
            Feng.error(data.responseJSON.message);
        });
        ajax.set("userId", userId);
        ajax.start();
    });

};

MgrUser.resetSearch = function () {
    MgrUser.deptid = 0;
    $("#roleFamilyName").val("");
    $("#account").val("");
    $("#roleName").val("");
    $("#citySel").attr("value","");
    $("#status option:first").prop("selected", 'selected');
}

MgrUser.search = function () {
    var queryData = {};
    queryData['deptid'] = MgrUser.deptid;
    queryData['name'] = YQ.Trim($("#roleFamilyName").val());
    queryData['status'] = $("#status option:selected").val();
    queryData['account'] = YQ.Trim($("#account").val());
    queryData['roleName'] = YQ.Trim($("#roleName").val());
    MgrUser.table.refresh({query: queryData});
}

MgrUser.onClickDept = function (e, treeId, treeNode) {
    MgrUser.deptid = treeNode.id;
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#deptid").attr("value", treeNode.id);
    $("#deptTree").hide();
    // MgrUser.search();
};

$(function () {
    var defaultColunms = MgrUser.initColumn();
    var table = new BSTable(MgrUser.id, "/mgr/list", defaultColunms);
    table.setPaginationType("server");
    table.init();
    MgrUser.table = table;
    var ztree = new $ZTree("deptTree", "/dept/tree");
    ztree.bindOnClick(MgrUser.onClickDept);
    ztree.init();
    instance = ztree;
});
