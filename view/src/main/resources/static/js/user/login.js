$(function () {

    var loginUrl = 'http://media.com/user/login';

    var isRem = false;
    var localUserInfo = JSON.parse(localStorage.getItem("user_info_arr"));
    if (localUserInfo == null) {
        localStorage.setItem("user_info_arr", "");
    } else {
        $('#username input').val(localUserInfo.userName);
        $('#password input').val(localUserInfo.passWord);
    }


    $('#btn-login').click(function () {
        login();
    });
    $('#btn-cancle').click(function () {
        fallBackView();
    });
    $('.user input').focus(function () {
        $('.user input').css("border-style", "none");
        $('#tishi-username').hide();
    });
    $('.user-in input').focus(function () {
        $('.user-in input').css("border-style", "none");
        $('#tishi-password').hide();
    });

    if (sessionStorage.getItem('media_login_info') != null) {
        window.location.href = '/media/index';
    }

    var fallBackView = function () {
        window.history.back(-1);
    };

    function login() {
        var un = $('#username input').val();
        var pw = $('#password input').val();
        //表示内容检查结果
        var isReturn = false;
        if (un == '') {
            // $('.user input').css({"border-color":"red","border-style":"solid"});
            $('#uname-tishi').show();
        } else {
            isReturn = true;
        }
        if (pw == '') {
            $('#password-tishi').show();
        } else {
            isReturn = true;
        }
        if (!isReturn) {
            return;
        }
        // loginUrl.append('username='+)
        // loginUrl=loginUrl+'username='+un+'&&'+'password='+pw;
        $.ajax({
            url: loginUrl,
            type: 'POST',
            data: {
                username: un,
                password: pw
            },
            dataType: 'JSON',
            success: function (data) {
                if (data.success) {
                    // var user=JSON.stringify(data.userInfo)
                    sessionStorage.setItem("media_token", data.token);
                    sessionStorage.setItem("media_login_info", data.userInfo);
                    if (isRem) {
                        var user = {
                            userName: un,
                            passWord: pw
                        };
                        localStorage.setItem("user_info_arr", JSON.stringify(user));
                    }
                    alert("登录成功 ");
                    window.location.href = 'http://media.com/media/index';
                } else {
                    alert(data.errMsg);
                }
            }
        });
    }

    $('#rem-pd').change(function () {
        if (isRem) {
            isRem = false;
        } else {
            isRem = true;
        }
    })

});