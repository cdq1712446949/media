$(function () {

    var messageNumUrl = 'http://media.com/user/sm/ugmn';

    //根据sessionStorge中的存储信息判断用户是否登录
    var userInfo = sessionStorage.getItem("media_login_info");
    var isLogin = false;
    if (userInfo == null) {
        isLogin = false;
    } else {
        isLogin = true;
    }

    initMessageNumList = function (list) {
        var tempHtml = '';
        $.each(list, function (index, item) {
            var userInfo = item.fromUser;
            var messageNum = item.messageNum;
            if (messageNum >= 99) {
                messageNum = '99+'
            }
            tempHtml += '<li>\n' +
                '                    <a onclick="jumpToChat('+"'" + userInfo.userId+"'" +','+"'"+userInfo.nickName+ "'"+')" class="item-link item-content">\n' +
                '                        <div class="item-media">\n' +
                '                            <img class="yuan-head" src="http://media.com/image/images/' + userInfo.userHeadPhoto + '" style=\'width: 2.2rem;\'>\n' +
                '                        </div>\n' +
                '                        <div class="item-inner">\n' +
                '                            <div class="item-title-row">\n' +
                '                                <div class="item-title">' + userInfo.nickName + '</div>\n' +
                '                            </div>\n' +
                '                            <div class="item-subtitle">..........</div>\n' +
                '                        </div><span class="badge" style="background-color: red; color: white">' + messageNum + '</span>\n' +
                '                    </a>\n' +
                '                </li>';
        });
        $('#message_content').html(tempHtml);
    };

    jumpToChat = function (fromUserId,nickName) {
        window.parent.location.href = 'http://media.com/media/chat?fromUserId=' + fromUserId+'&&nickName='+nickName;
    };

    getMessageNumber = function () {
        if (!isLogin) {
            window.parent.location.href = 'http://media.com/media/login';
        }
        $.ajax({
            url: messageNumUrl,
            type: "POST",
            data: {
                token: sessionStorage.getItem("media_token")
            },
            dataType: "JSON",
            success: function (data) {
                if (data.success) {
                    initMessageNumList(data.messageNumberList);
                } else {
                    alert("获取未读消息列表失败:" + data.errMsg);
                }
            }
        });
    };

    getMessageNumber();

    setInterval('getMessageNumber()','1000');

});