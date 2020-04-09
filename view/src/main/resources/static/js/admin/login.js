$(function () {

    var loginUrl='/media/user/admin/login';

    $('.tishi').hide();

    $('#btn-login').click(function () {
       login();
    });

    $('.user input').focus(function () {
        $('.user input').css("border-style","none");
        $('#tishi-username').hide();
    });
    $('.user-in input').focus(function () {
        $('.user-in input').css("border-style","none");
        $('#tishi-password').hide();
    });

    //根据headr中的token判断是否已经登录,如果已经登录则返回上一层页面
    if (sessionStorage.getItem('media_admin_login_info')!=null){
        window.location.href='/media/index';
    }

    function login() {
        var un=$('.user input').val();
        var pw=$('.user-in input').val();
        if (un==''){
            $('.user input').css({"border-color":"red","border-style":"solid"});
            $('#tishi-username').show();
            return;
        }
        if (pw==''){
            $('.user-in input').css({"border-color":"red","border-style":"solid"});
            $('#tishi-password').show();
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
                    var user=JSON.stringify(data.userInfo)
                    alert("登陆成功");
                    window.location.href='/media/admin/index';
                } else {
                    alert(data.errMsg);
                }
            }
        });
    }

});