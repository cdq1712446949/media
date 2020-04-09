$(function () {

    var loginUrl='/user/login';



    $('#btn-login').click(function () {
        login();
    });
    $('#btn-cancle').click(function () {
        fallBackView();
    });
    $('.user input').focus(function () {
        $('.user input').css("border-style","none");
        $('#tishi-username').hide();
    });
    $('.user-in input').focus(function () {
        $('.user-in input').css("border-style","none");
        $('#tishi-password').hide();
    });

    if (sessionStorage.getItem('media_login_info')!=null){
        window.location.href='/media/index';
    }

    var fallBackView = function () {
        window.history.back(-1);
    };

    function login() {
        var un=$('#username input').val();
        var pw=$('#password input').val();
        //表示内容检查结果
        var isReturn = false;
        if (un==''){
            // $('.user input').css({"border-color":"red","border-style":"solid"});
            $('#uname-tishi').show();
        }else{
            isReturn = true;
        }
        if (pw==''){
            $('#password-tishi').show();
        }else {
            isReturn = true;
        }
        if (!isReturn){
            return;
        }
        // loginUrl.append('username='+)
        // loginUrl=loginUrl+'username='+un+'&&'+'password='+pw;
        $.ajax({
            url:loginUrl,
            type:'POST',
            data:{
                username:un,
                password:pw
            },
            dataType:'JSON',
            success:function (data) {
                if (data.success){
                   // var user=JSON.stringify(data.userInfo)
                    sessionStorage.setItem("media_token",data.token);
                    sessionStorage.setItem("media_login_info",data.userInfo);
                    alert("登陆成功");
                    window.location.href='/media/index';
                } else {
                    alert(data.errMsg);
                }
            }
        });
    }

});