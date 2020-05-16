$(function () {

    var messageNumUrl = 'http://media.com/user/sm/ugmn';
    var systemStatusUrl = 'http://media.com/user/sm/ucss';
    var systemList = null;
    var sysIdArr = new Array();
    //根据sessionStorge中的存储信息判断用户是否登录
    var userInfo = sessionStorage.getItem("media_login_info");
    sessionStorage.setItem('message_number', '');
    var isLogin = false;
    if (userInfo == null) {
        isLogin = false;
    } else {
        isLogin = true;
    }

    initMessageNumList = function (list, list2) {
        var tempHtml = '';
        if (list2 != null && list2.length > 0) {
            systemList=list2;
            tempHtml += '<li>\n' +
                '                    <a onclick="showSystemMessage()" class="item-link item-content">\n' +
                '                        <div class="item-media">\n' +
                '                            <img class="yuan-head" src="http://media.com/image/images/system/logo-ico.png" style=\'width: 2.2rem;\'>\n' +
                '                        </div>\n' +
                '                        <div class="item-inner">\n' +
                '                            <div class="item-title-row">\n' +
                '                                <div class="item-title" style="color: #8A360F">' + '系统通知' + '</div>\n' +
                '                            </div>\n' +
                '                            <div class="item-subtitle">..........</div>\n </div>' ;
                tempHtml+='<span class="badge" style="background-color: red; color: white">' + list2.length + '</span>\n' +
                ' </a>\n' + '   </li>'
        }
        $.each(list, function (index, item) {
            var userInfo = item.fromUser;
            var messageNum = item.messageNum;
            if (messageNum >= 99) {
                messageNum = '99+'
            }
            tempHtml += '<li>\n' +
                '                    <a onclick="jumpToChat(' + "'" + userInfo.userId + "'" + ',' + "'" + userInfo.nickName + "'" + ')" class="item-link item-content">\n' +
                '                        <div class="item-media">\n' +
                '                            <img class="yuan-head" src="http://media.com/image/images/' + userInfo.userHeadPhoto + '" style=\'width: 2.2rem;\'>\n' +
                '                        </div>\n' +
                '                        <div class="item-inner">\n' +
                '                            <div class="item-title-row">\n' +
                '                                <div class="item-title">' + userInfo.nickName + '</div>\n' +
                '                            </div>\n' +
                '                            <div class="item-subtitle">..........</div>\n </div>';
            if (messageNum >= 1) {
                tempHtml += '<span class="badge" style="background-color: red; color: white">' + messageNum + '</span>\n';
            }
            tempHtml += ' </a>\n' +
                '   </li>';
        });
        //添加系统通知界面

        $('#message_content').html(tempHtml);
    };

    jumpToChat = function (fromUserId, nickName) {
        window.location.href = 'http://media.com/media/chat?fromUserId=' + fromUserId + '&&nickName=' + nickName;
    };

    getMessageNumber = function () {
        if (!isLogin) {
            window.location.href = 'http://media.com/media/login';
        }
        $.ajax({
            url: messageNumUrl,
            type: "POST",
            data: {
                token: sessionStorage.getItem("media_token")
            },
            dataType: "JSON",
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    getMessageNumber();
                    return;
                }
                if (data.success) {
                    // if (JSON.stringify(data.messageNumberList) != sessionStorage.getItem('message_number')) {
                    initMessageNumList(data.messageNumberList, data.systemMessageList);
                    // sessionStorage.setItem('message_number',JSON.stringify(data.messageNumberList));
                    // }
                } else {
                    alert("获取未读消息列表失败:" + data.errMsg);
                }
            }
        });
    };

    getMessageNumber();

    setInterval('getMessageNumber()', '1000');

    searchChange = function (that) {
        var str = that.value;
        $.each($('.item-title'), function (index, item) {
            var itemStr = item.innerHTML;

        });
    };

    showSystemMessage = function () {
        var text = '';
        $.each(systemList,function (index,item) {
            sysIdArr.push(item.systemMessageId);
            text+=item.messageContent+'\n';
        });
        $.modal({
            title:  '消息内容',
            text:text,
            buttons: [
                {
                    text: '已读',
                    onClick: function() {
                        changeSystemStatus();
                    }
                }
            ]
        })
    };

    changeSystemStatus = function () {
        $.ajax({
            url:systemStatusUrl,
            type:'POST',
            data:{
                listStr:JSON.stringify(sysIdArr),
                token:sessionStorage.getItem('media_token')
            },
            dataType:'JSON',
            success:function (data) {
                var result = checkData(data);
                if (result){
                    changeSystemStatus();
                    return;
                }
                if (data.success){
                    sysIdArr.length=0;
                }

            }
        });
    }

});