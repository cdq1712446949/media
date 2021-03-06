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
                if (tempArr[0] == name) {
                    return tempArr[1];
                }
            }
        }
    }
    return '';
}


back = function () {
    var activeName = sessionStorage.getItem('active_name');
    window.location.href = 'http://media.com/media/' + activeName;
};

/**
 * 处理token过期方法
 */
checkData = function (data) {
    var reSend = false;
    var stateCode = data.stateCode;
    var redirect = data.redirect;
    if (redirect != null && redirect != '') {
        window.location.href = redirect;
    }
    if (stateCode == 201) {
        sessionStorage.setItem('admin_user_token', data.token);
        reSend = true;
    }
    return reSend;
};

/**
 *弹窗
 */
myConfirm = function (content, fun) {
    $.confirm({
        title: '成功',
        content: content,
        type: 'green',
        buttons: {
            close: {
                text: '关闭',
                action: fun
            }
        }
    });
};
