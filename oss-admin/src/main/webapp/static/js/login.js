/*登录 */
$(function () {
    /*正则*/
    var d = /^1\d{10}$/;

    //用户名
    $('input').eq(0).blur(function () {
        $("#tips").hide();
        var phone = $('#username').val();
        var newPhone = YQ.trimPhone(phone,"g");
        $('#userNameHidden').val(newPhone);
        if (newPhone.length == "") {
            $('#usernameMsg').text("账号没有填写");
        } else if (newPhone.length > 0 && newPhone.length < 11) {
            $('#usernameMsg').text("手机号11位");
        } else if (!newPhone.match(d)) {
            $('#usernameMsg').text("手机号码格式不正确");
        } else {
            $('#usernameMsg').text("");
        }
    })

    //密码
    $('input').eq(1).blur(function () {
        $("#tips").hide();
        if ($(this).val().length == "") {
            $(this).next("span").text("密码没有填写");
        } else if ($(this).val().length > 0 && $(this).val().length < 6) {
            $(this).next("span").text("长度只能在6-18个字符");
        } else {
            $(this).next("span").text("");
        }
    })

    //	验证码验证
    $('input').eq(2).blur(function () {
        $("#tips").hide();
        if ($(this).val().length == "") {
            $(this).next().next("span").text("验证码没有填写");
        } else if ($(this).val().length > 0 && $(this).val().length < 4) {
            $(this).next().next("span").text("4位数字字母组合");
        } else {
            $(this).next().next("span").text("");
        }
    })

    /*提交 btn*/
    $("#sub_check").on('click', function () {
        var username = $("[name='userNameHidden']").val();
        var password = $("[name='password']").val();
        var kaptcha = $("[name='kaptcha']").val();
        var remember = $('input[name="remember"]:checked').val();
        if (username == '') {
            $("[name='username']").next("span").html("账号没有填写");
            $("[name='username']").focus();
            return false;
        }
        if (password == '') {
            $("[name='password']").next("span").html("密码没有填写");
            $("[name='password']").focus();
            return false;
        }
        if (kaptcha == '') {
            $("[name='kaptcha']").next("span").html("验证码没有填写");
            $("[name='kaptcha']").focus();
            return false;
        }
        var result = "";
        $.ajax({
            type:"POST",
            contentType: "application/x-www-form-urlencoded",
            url:Feng.ctxPath + "/loginCheck",
            async:false,
            data:{username:username, password:password, kaptcha:kaptcha},
            success:function (data) {
                result = data;
            },
            error:function (data) {
                result="系统异常，请稍后重试!";
            }
        });
        //当且仅当登录名、密码、验证码同时校验通过时才进行表单提交
        if(result == "ok"){
            $.ajax({
                type:"POST",
                contentType: "application/x-www-form-urlencoded",
                url:Feng.ctxPath + "/login",
                async:false,
                data:{username:username, password:password, kaptcha:kaptcha,remember:remember},
                success:function (data) {

                },
                error:function (data) {
                    result="系统异常，请稍后重试!";
                }
            });
            return true;
        }
        //验证码错误或者超时的情况下刷新验证码
        if(result == "验证码错误" || result == "验证码超时"){
            $("#kaptchaMsg").html(result);
            $("#kaptcha_int").val("");
            $("#kaptcha").attr('src', Feng.ctxPath+'/kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
        }
        if(result==="用户不存在"){
            $("[name='username']").next("span").html("用户不存在");
            $("#kaptcha_int").val("");
            $("#kaptcha").attr('src', Feng.ctxPath+'/kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
        }
        if(result==="账户被冻结"){
            $("[name='username']").next("span").html("账户被冻结");
            $("#kaptcha_int").val("");
            $("#kaptcha").attr('src', Feng.ctxPath+'/kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
        }
        if(result==="账号密码不正确"){
            $("[name='password']").next("span").html("账号密码不正确");
            $("#kaptcha_int").val("");
            $("#kaptcha").attr('src', Feng.ctxPath+'/kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
        }

        return false;
    });

    //回车事件
    document.onkeydown = function(e) {
        if (!e)
            e = window.event;//火狐中是 window.event
        if ((e.keyCode || e.which) == 13) {
            $("#sub_check").click();
        }
    }
});