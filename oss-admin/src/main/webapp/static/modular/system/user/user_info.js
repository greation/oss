/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var UserInfoDlg = {
    roleTree: null,
    treeDemo: null,
    userInfoData: {},
    validateFields: {
        account: {
            validators: {
                notEmpty: {
                    message: '账户不能为空'
                },
                regexp:{
                    regexp: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
                    message: '手机号格式错误'
                },
                stringLength: {//检测长度
                    max: 11
                }

            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                },
                stringLength: {//检测长度
                    max: 20
                },
                regexp:{
                    regexp: /^[\u4e00-\u9fa5]+$/,
                    message: '姓名只能为中文'
                }
            }
        },
        citySel: {
            validators: {
                notEmpty: {
                    message: '部门不能为空'
                }
            }
        },
        email: {
            validators: {
                notEmpty: {
                    message: '邮箱不能为空'
                },
                stringLength: {//检测长度
                    max: 50
                }
            }
        },
        roleid: {
            validators: {
                notEmpty: {
                    message: '' +
                    '角色名称不能为空'
                }

            }
        },
        jobs: {
            validators: {
                notEmpty: {
                    message: '' +
                    '岗位名称不能为空'
                },
                stringLength: {//检测长度
                    max: 20
                },
                regexp:{
                    regexp: /^[\u4e00-\u9fa5]+$/,
                    message: '只能为中文'
                }
            }
        },
        password: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'rePassword',
                    message: '两次密码不一致'
                }
            }
        },
        rePassword: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'password',
                    message: '两次密码不一致'
                }
            }
        },
        oldPwd: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                }
            }
        },
        newPwd: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'rePassword',
                    message: '两次密码不一致'
                },
                regexp:{
                    regexp: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/,
                    message: '新密码6-18位字母数字组合'
                }
            }
        },
        rePwd: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'rePassword',
                    message: '两次密码不一致'
                },
                regexp:{
                    regexp: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/,
                    message: '新密码6-18位字母数字组合'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
UserInfoDlg.clearData = function () {
    this.userInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserInfoDlg.set = function (key, val) {
    this.userInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
UserInfoDlg.close = function () {
    parent.layer.close(window.parent.MgrUser.layerIndex);
};

/**
 * 点击部门input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
UserInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#deptid").attr("value", treeNode.id);
};
/**
 *
 */

UserInfoDlg.onClickRole = function (e, treeId, treeNode) {
    var txt = UserInfoDlg.roleTree.getCheckedVals();
    $("#roleid").attr("value", txt);
    // $("#deptid").attr("value", treeNode.id);
};
UserInfoDlg.onClickAddRole = function (e, treeId, treeNode) {
    var txt = UserInfoDlg.roleTree.getCheckedValadd();
    $("#roleid").attr("value", txt);
    // $("#deptid").attr("value", treeNode.id);
};

/**
 * 显示部门选择的树
 *
 * @returns
 */
UserInfoDlg.showDeptSelectTree = function () {
    var cityObj = $("#citySel");
    var cityOffset = $("#citySel").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};



/**
 * 隐藏部门选择的树
 */
UserInfoDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};
UserInfoDlg.hideRoleSelectTree = function () {
    $("#menuContentRole").fadeOut("fast");
    $("body").unbind("mousedown", onBodyRoleDown);// mousedown当鼠标按下就可以触发，不用弹起
};


/**
 * 收集数据
 */
UserInfoDlg.collectData = function () {
    this.set('id').set('account').set('sex').set('password').set('avatar').set('roleName').set('manager').set('deptName')
        .set('email').set('name').set('birthday').set('rePassword').set('deptid').set('phone').set('statusName').set('roleIds');
};

/**
 * 验证两个密码是否一致
 */
UserInfoDlg.validatePwd = function () {
    var password = this.get("password");
    var rePassword = this.get("rePassword");
    if (password == rePassword) {
        return true;
    } else {
        return false;
    }
};

/**
 * 验证数据是否为空
 */
UserInfoDlg.validate = function () {
    $('#userInfoForm').data("bootstrapValidator").resetForm();
    $('#userInfoForm').bootstrapValidator('validate');
    return $("#userInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加用户
 */
UserInfoDlg.addSubmit = function () {
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    if (!this.validatePwd()) {
        Feng.error("两次密码输入不一致");
        return;
    }
    var roleIds =$("#roleIds").val();
    var status =$("#add_status option:selected").val();
    var jobs =$("#jobs").val();
    var manager =$("#manager option:selected").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/add", function (data) {
        Feng.success(data.message);
        window.parent.MgrUser.table.refresh();
        UserInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.setContentType(Feng.fullContentType("JSON"));
    ajax.set(this.userInfoData);
    ajax.set("roleid",roleIds);
    ajax.set("status",status);
    ajax.set("jobs",jobs);
    ajax.set("manager",manager);
    ajax.set("status",status);
    ajax.start();
};
/**
 *
 * 角色
 * */
UserInfoDlg.roleAssign = function () {
    var index = layer.open({
        type: 2,
        title: '角色分配',
        area: ['300px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mgr/role_assign/' + this.seItem.id
    });
    this.layerIndex = index;

};
/**
 *
 * 调整部门
 * */
UserInfoDlg.exChange = function () {
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    var deptId = $("#deptid").val();
    var userIds = $("#userIds").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/changeDept", function (data) {
        Feng.success("调整成功!");
        window.parent.MgrUser.table.refresh();
        UserInfoDlg.close();
    }, function (data) {
        Feng.error("调整失败!" + data.responseJSON.message + "!");
    });
    ajax.set("deptId",deptId);
    ajax.set("userIds",userIds);
    ajax.start();
};
/**
 * 提交修改
 */
UserInfoDlg.editSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    var birthday = $("#birthday").val();
    var roleIds =$("#roleIds").val();
    var jobs =$("#jobs").val();
    var phone =$("#phone").val();
    var account =$("#account").val();
    var manager =$("#manager option:selected").val();
    var status =$("#edite_status option:selected").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/edit", function (data) {
        Feng.success(data.message);
        if (window.parent.MgrUser != undefined) {
             window.parent.MgrUser.table.refresh();
            UserInfoDlg.close();
        }
         // window.parent.location.reload();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.setContentType(Feng.fullContentType("JSON"));
    ajax.set(this.userInfoData);
    ajax.set("birthday",birthday);
    ajax.set("roleid",roleIds);
    ajax.set("jobs",jobs);
    ajax.set("phone",phone);
    ajax.set("manager",manager);
    ajax.set("account",account);
    ajax.set("status",status);
    ajax.start();
};
UserInfoDlg.showRoleSelectTree=function () {
    UserInfoDlg.getRoleTree();
    var cityObj = $("#roleid");
    var cityOffset = $("#roleid").offset();
    $("#menuContentRole").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyRoleDown);
}

/***
 * 添加账户
 */
UserInfoDlg.addRoleSelectTree=function () {
    UserInfoDlg.addRoleTree();
    var cityObj = $("#roleid");
    var cityOffset = $("#roleid").offset();
    $("#menuContentRole").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyRoleDown);
}
/**
 * 修改密码
 */
UserInfoDlg.chPwd = function () {
    if (!this.validate()) {
        return;
    }
    var ajax = new $ax(Feng.ctxPath + "/mgr/changePwd", function (data) {
        Feng.success("修改成功!");
        setInterval(function () {
            var ajax = new $ax(Feng.ctxPath + "/logout", function (data) {
            }, function (data) {
            });
            ajax.start();
            window.location.href = Feng.ctxPath+"/logout";
        },2000);
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("oldPwd");
    ajax.set("newPwd");
    ajax.set("rePwd");
    ajax.start();

};
UserInfoDlg.addRoleTree =function () {
    var setting = {
        check: {
            enable: true,
            autoCheckTrigger:false,
            chkboxType: {
                "Y": "ps",
                "N": "ps"
            }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback:{
            onCheck:UserInfoDlg.onClickAddRole
        }
    };

    var ztreeRole = new $ZTree("roleTree", "/role/roleTreeList/");
    ztreeRole.setSettings(setting);
    ztreeRole.bindOnClick(UserInfoDlg.onClickAddRole);
    ztreeRole.init();
    UserInfoDlg.roleTree = ztreeRole ;
}
/**
 *
 * 根据id选择角色tree
 *
 * */
UserInfoDlg.getRoleTree =function () {
    var userId =$("#id").val();
    var setting = {
        check: {
            enable: true,
            autoCheckTrigger:false,
            chkboxType: {
                "Y": "ps",
                "N": "ps"
            }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback:{
            onCheck:UserInfoDlg.onClickRole
        }
    };

    var ztreeRole = new $ZTree("roleTree", "/role/roleTreeListByUserId/"+ userId);
    ztreeRole.setSettings(setting);
    ztreeRole.bindOnClick(UserInfoDlg.onClickRole);
    ztreeRole.init();
    UserInfoDlg.roleTree = ztreeRole ;
}
function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" ||  event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
        UserInfoDlg.hideDeptSelectTree();
    }
}
function onBodyRoleDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContentRole" || $(
            event.target).parents("#menuContentRole").length > 0)) {
        UserInfoDlg.hideRoleSelectTree();
    }
}


$(function () {
    Feng.initValidator("userInfoForm", UserInfoDlg.validateFields);

    var ztree = new $ZTree("treeDemo", "/dept/tree");
    ztree.bindOnClick(UserInfoDlg.onClickDept);
    ztree.init();
    instance = ztree;
    //初始化性别选项
    $("#sex").val($("#sexValue").val());
    var hiddenStatus = $("#hiddenStatus").val();
    var editeManager = $("#editeManager").val();
    $("#edite_status option:contains("+hiddenStatus+")").attr("selected",true);
    $("#manager option:contains("+editeManager+")").attr("selected",true);
    // 初始化头像上传
    var avatarUp = new $WebUpload("avatar");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();

    //出生日期日历
    laydate.render({
        elem: '#birthday' //指定ID元素
        ,theme: '#2f4050' //颜色
        ,calendar: true //显示公历
        ,format: 'yyyy/MM/dd'
    });
    var userId =$("#id").val();
    $("#account").attr("maxlength","11");
    $("#email").attr("maxlength","50");
    $("#jobs,#name").attr("maxlength","20");
    $("#oldPwd").attr("placeholder","请输入原密码");
    $("#newPwd").attr("placeholder","请输入新密码");
    $("#rePwd").attr("placeholder","请再次输入新密码");
    $("#newPwd,#rePwd").attr("maxlength","18");
    $("#accoutNum").val(YQ.phoneSeparated($("#accoutHidden").val()));

});
