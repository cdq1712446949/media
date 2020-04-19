$(function () {

    var loginUrl='http://media.com/user/admin/login';
    var role;

    $('.tishi').hide();

    //根据headr中的token判断是否已经登录,如果已经登录则返回上一层页面
    if (sessionStorage.getItem('media_admin_login_info')!=null){
        window.location.href='/media/index';
    }

     login = function(url) {
        var un=$('#username').val();
        var pw=$('#password').val();
         //表示内容检查结果
         var isReturn = false;
         if (un == '') {
             $('#username').css("border-color","red");
         } else {
             isReturn = true;
         }
         if (pw == '') {
             $('#password').css("border-color","red");
         } else {
             isReturn = true;
         }
         if (!isReturn) {
             return;
         }
        $.ajax({
           url:url,
           type:'POST',
            data:{
               username:un,
                password:pw,
                role:role
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