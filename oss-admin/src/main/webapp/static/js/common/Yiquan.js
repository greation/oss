var YQ = {

    checkPermission: function(url,methodType){//是否有访问接口权限
        var hasPermission = true;
        var ajax = new $ax(url, function(data){
            hasPermission = true;
        },function (data) {
            if(data.responseJSON && data.responseJSON.code && data.responseJSON.code == "405"){
                hasPermission = false;
            }
        });

        if(methodType){//默认Post方法
            ajax.setMethodType(methodType);
        }
        ajax.start();
        return hasPermission;
    },

    methodPost : function () {//获取 post请求method字符串
        return "post";
    },

    methodGET : function () {//获取 get请求method字符串
        return "get";
    },
    Trim:function (str) {//去掉字符串前后所有空格：
         return str.replace(/(^\s*)|(\s*$)/g, "");
    },
    // 去掉字符串中所有空格(包括中间空格,需要设置第2个参数为:g)
    trimPhone:function (str,is_global) {
        var result;
        result = str.replace(/(^\s+)|(\s+$)/g, "");
        if (is_global && is_global.toLowerCase() == "g") {
            result = result.replace(/\s/g, "");
        }
        return result;
    },
    // 判断是否是手机号码格式
    isPhone:function (str) {
        if(str == null || str == undefined){
            return false;
        }
        var reg = /^1(3|4|5|7|8)\d{9}$/;
        return reg.test(this.trimPhone(str, 'g'));
    },
    // 手机号码格式转化为 344 格式
    phoneSeparated:function (phone) {
        var tel = phone;
        if(this.isPhone(tel)){
            tel =this.trimPhone(phone,'g');
            tel =tel.substring(0,3)+' '+tel.substring(3,7) + ' '+tel.substring(7,11);
        }
        return tel;
    }

};
