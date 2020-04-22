$(function () {

    var loginUrl = 'http://media.com/user/admin/login';
    var supseLoginUrl = 'http://media.com/user/admin/adminLogin';
    var role;

    $('.tishi').hide();

    //根据headr中的token判断是否已经登录,如果已经登录则返回上一层页面
    if (sessionStorage.getItem('admin_user_info') != null) {
        window.location.href = 'http://media.com/media/admin/index';
    }

    login = function (url) {
        var un = $('#username').val();
        var pw = $('#password').val();
        //表示内容检查结果
        var isReturn = false;
        if (un == '') {
            $('#username').css("border-color", "red");
        } else {
            isReturn = true;
        }
        if (pw == '') {
            $('#password').css("border-color", "red");
        } else {
            isReturn = true;
        }
        if (!isReturn) {
            return;
        }
        var userStr = {};
        userStr.userName = un;
        userStr.passWord = pw;
        $.ajax({
            url: url,
            type: 'POST',
            data: {
                userStr:JSON.stringify(userStr)
            },
            dataType: 'JSON',
            success: function (data) {
                if (data.success) {
                    var user = JSON.stringify(data.userInfo);
                    sessionStorage.setItem("admin_user_info", JSON.parse(user));
                    sessionStorage.setItem("admin_user_token", data.token);
                    alert("登陆成功");
                    window.location.href = 'http://media.com/media/admin/index';
                } else {
                    alert(data.errMsg);
                }
            }
        });
    }

});