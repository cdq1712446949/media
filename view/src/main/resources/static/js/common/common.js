/**
 *
 */
var logoutUrl = 'http://media.com/user/logout';

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function changeKaptcha(img) {
    img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}

function getQueryString(name) {
    //解析地址,获取参数json对象
    var href = decodeURI(window.location.href);
    var arr1 = href.split('?');
    if (arr1.length <= 1) {
        return null;
    } else {
        var arr2 = arr1[1].split('&&');
        for (var i = 0; i < arr2.length; i++) {
            var tempArr = arr2[i].split('=');
            if (tempArr.length == 2) {
                if (tempArr[0]==name){
                    return tempArr[1];
                }
            }
        }
    }
    return '';
}


back = function(){
  var activeName  = sessionStorage.getItem('active_name');
  window.location.href='http://media.com/media/'+activeName;
};

/**
 * 退出登录
 */
logout = function () {
    $.ajax({
        url: logoutUrl,
        type: "POST",
        data: {
            userId:JSON.parse(sessionStorage.getItem("media_login_info")).userId
        },
        dataType: "JSON",
        success: function (data) {
            if (data.success) {
                sessionStorage.removeItem('media_login_info');
                sessionStorage.removeItem('media_token');
                $.toast("已退出");
                window.location.reload();
            } else {
                sessionStorage.removeItem('media_login_info');
                sessionStorage.removeItem('media_token');
                $.toast('已退出');
                window.location.reload();
            }
        }
    });
};