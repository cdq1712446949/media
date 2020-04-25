$(function () {

    //注册接口
    var registerUrl = 'http://media.com/user/register';

    $('#btn-cancle').click(function () {
        fallBackView();
    });

    var fallBackView = function () {
        window.history.back(-1);
    };

    regiter = function () {
        var user = {};
        user.userName = $('#username').val();
        user.password = $('#password').val();
        user.nickName = $('#nick_name').val();
        user.userSex = $('#sex').val();
        user.userDesc = $('#intr').val();
        user.userBirthday = $('#birth').val();
        user.userEmail = $('#user_email').val();
        if (user.userName==null || user.password==null){
            $('#uname-tishi').show();
            $('#password-tishi').show();
            return
        } else{
            $('#uname-tishi').hide();
            $('#password-tishi').hide();
        }
        $.ajax({
            url:registerUrl,
            type:"POST",
            data:{
                userStr:JSON.stringify(user)
            },
            dataType:"JSON",
            success:function (data) {
                if (data.success){
                    alert('注册成功');
                    window.location.href = 'http://media.com/media/login'
                } else{
                    alert(data.errMsg)
                }
            }
        })
    }

});