$(function () {

    var userInfo = sessionStorage.getItem("media_login_info");
    if (userInfo == null) {
        isLogin = false;
    } else {
        isLogin = true;
    }

    addArticle = function () {
        if (isLogin) {
            window.location.href = 'http://media.com/media/articleEdit';
        } else {
            window.location.href = 'http://media.com/media/login';
        }
    };

});