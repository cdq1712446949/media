$(function () {

    var userInfoUrl = 'http://media.com/user/ugui';

    var artiNum = 0;
    var funsNum = 0;

    //根据sessionStorge中的存储信息判断用户是否登录
    var userInfo = JSON.parse(sessionStorage.getItem("media_login_info"));
    if (userInfo == null) {
        $('#login-yes').hide();
        $('#login-no').show();
    } else {
        $('#login-no').hide();
        $('#login-yes').show();
    }

    chooseFile = function () {
        document.getElementById("head_change").click();
    };

    //获取用户信息（文章数量，粉丝数量）
    getUserInfo = function () {
        var userId = userInfo.userId;
        $.ajax({
            url: userInfoUrl,
            type: 'POST',
            data: {
                userId: userId
            },
            dataType: 'JSON',
            success: function (data) {
                if (data.success) {
                    var userInfo1 = data.userInfo;
                    artiNum = userInfo1.articleNum;
                    funsNum = userInfo1.funsNum;
                    var user_info = new Vue({
                        el: '#user_info',
                        data: {
                            artiNum: artiNum,
                            funsNum: funsNum
                        }
                    });
                } else {
                    $.toast('获取userInfo失败：' + data.errMsg);
                }
            }
        });
    };

    getUserInfo()

    initUserInfo = function(){
        $('#head_photo').attr('src','http://media.com/image/images/'+userInfo.userHeadPhoto);
        var userLoginInfo = new Vue({
            el:'#userLoginInfo',
            data:{
                nickName:userInfo.nickName,
                userDesc:userInfo.userDesc,
                userSex:userInfo.userSex
            }
        });
    };

    initUserInfo();

});