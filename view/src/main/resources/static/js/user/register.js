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
        user.userName = $('#username input').val();
        user.passWord = $('#password input').val();
        user.nickName = $('#nick_name input').val();
        user.userSex = $('#sex input').val();
        user.userDesc = $('#intr input').val();
        user.userBirthday = $('#birth input').val();
        user.userEmail = $('#user_email input').val();
        if (user.userName==null || user.passWord==null){
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