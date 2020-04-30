$(function () {

    var userInfo = sessionStorage.getItem("media_login_info");
    if (userInfo == null) {
        isLogin = false;
    } else {
        isLogin = true;
    }

    addArticle = function () {
        if (isLogin) {
            window.parent.location.href = 'http://media.com/media/articleEdit';
        } else {
            window.parent.location.href = 'http://media.com/media/login';
        }
    };

});