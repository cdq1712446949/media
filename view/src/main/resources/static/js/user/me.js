$(function () {

    //根据sessionStorge中的存储信息判断用户是否登录
    var userInfo = sessionStorage.getItem("media_login_info");
    if (userInfo==null){
        $('#login-yes').hide();
        $('#login-no').show();
    } else {
        $('#login-no').hide();
        $('#login-yes').show();
    }
    
    chooseFile = function () {
        document.getElementById("head_change").click();
    }

});