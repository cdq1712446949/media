$(function () {

    var historyUrl = 'http://media.com/user/sm/ughm';
    var unMessageUrl = 'http://media.com/user/sm/ugurm';
    var changeMessageUrl = 'http://media.com/user/sm/ucms';
    var addMessageUrl = 'http://media.com/user/sm/uasm';

    var userInfo = JSON.parse(sessionStorage.getItem('media_login_info'));

    var fromUserId = getQueryString("fromUserId");
    var nickName = getQueryString("nickName");

    $('#nick_name').html(nickName)

    getHistoryMessage = function () {
        $.ajax({
            url: historyUrl,
            type: "POST",
            data: {
                token: sessionStorage.getItem('media_token'),
                fromUserId: fromUserId
            },
            dataType: "JSON",
            success: function (data) {
                var result = checkData(data);
                if (result){
                    getHistoryMessage();
                    return;
                }
                if (data.success) {
                    var secretMessageList = data.secretMessageList;
                    $.each(secretMessageList, function (index, item) {
                        if (fromUserId == item.fromUser.userId) {
                            initLeftMessage(item);
                        } else {
                            initRightMessage(item)
                        }
                    });
                } else {
                    alert('获取消息历史记录失败：' + data.errMsg);
                }
            }
        });
    };

    initLeftMessage = function (sm) {
        var fromUser = sm.fromUser;
        var html = '<li>\n' +
            '                    <div class="row left-10 mess-common" >\n' +
            '                        <div class="pull-left "><img class="yuan-head"\n' +
            '                                                     src="http://media.com/image/images/' + fromUser.userHeadPhoto + '"\n' +
            '                                                     style=\'width: 2.2rem;\'></div>\n' +
            '                        <div class="pull-left " style="max-width: 75%">\n' +
            '                            <div class="item-title-row">\n' +
            '                                <div class="item-title">' + fromUser.nickName + '</div>\n' +
            '                            </div>\n' +
            '                            <div class="message-blue" style="padding: 3px;overflow: paged-x">' + sm.messageContent + '</div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </li>';
        $('#message_ul').append(html);
    };

    initRightMessage = function (sm) {
        var html = ' <li>\n' +
            '                    <div class="row right-10 mess-common">\n' +
            '                        <div class=" pull-right">\n' +
            '                            <img class="yuan-head"\n' +
            '                                 src="http://media.com/image/images/' + userInfo.userHeadPhoto + '"' +
            '                                 style=\'width: 2.2rem;\'></div>\n' +
            '                        <div class="pull-right" style="max-width: 75%">\n' +
            '                            <div class="pull-right">' + userInfo.nickName + '</div>\n' +
            '                            </br>\n' +
            '                            <div class=" message-green" style="padding: 3px;text-align: right">' + sm.messageContent + '</div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </li>';
        $('#message_ul').append(html)
    };

    getHistoryMessage();

    getUnMessageList = function () {
        $.ajax({
            url: unMessageUrl,
            type: "POST",
            data: {
                token: sessionStorage.getItem('media_token'),
                fromUserId: fromUserId
            },
            dataType: "JSON",
            success: function (data) {
                var result = checkData(data);
                if (result){
                    getUnMessageList();
                    return;
                }
                if (data.success) {
                    var secretMessageList = data.secretMessageList;
                    var idList = [];
                    $.each(secretMessageList, function (index, item) {
                        initLeftMessage(item);
                        idList.push(item.secretMessageId);
                    });
                    if (idList.length >= 1) {
                        changeMessage(idList);
                    }
                } else {
                }
            }
        });
    };

    changeMessage = function (idList) {
        $.ajax({
            url: changeMessageUrl,
            type: "POST",
            data: {
                listStr: JSON.stringify(idList),
                token: sessionStorage.getItem('media_token')
            },
            async: false,
            dataType: "JSON",
            success: function (data) {
                var result = checkData(data);
                if (result){
                    changeMessage(idList);
                    return;
                }
                if (data.success) {

                } else {
                }
            }
        })
    };

    initMyMsg = function(content){
        var userInfo = JSON.parse(sessionStorage.getItem('media_login_info'));
        var html = ' <li>\n' +
            '                    <div class="row right-10 mess-common">\n' +
            '                        <div class=" pull-right">\n' +
            '                            <img class="yuan-head"\n' +
            '                                 src="http://media.com/image/images/' + userInfo.userHeadPhoto + '"' +
            '                                 style=\'width: 2.2rem;\'></div>\n' +
            '                        <div class="pull-right" style="max-width: 75%">\n' +
            '                            <div class="pull-right">' + userInfo.nickName + '</div>\n' +
            '                            </br>\n' +
            '                            <div class=" message-green" style="padding: 3px;text-align: right;">' + content + '</div>\n' +
            ' <div class="infinite-scroll-preloader" >\n' +
            '                        <div class="preloader"></div>\n' +
            '                    </div>'+
            '                        </div>\n' +
            '                    </div>\n' +
            '                </li>';
        $('#message_ul').append(html)
    };

    sendMsg = function () {
        var msg = {};
        msg.toUser = {
            userId:fromUserId
        };
        msg.messageContent = $('#msg_content').val();
        //消除input内容，添加到聊天记录界面，然后发送到后台
        initMyMsg($('#msg_content').val());
        $('#msg_content').val('');
        $.ajax({
            url:addMessageUrl,
            type:'POST',
            data:{
                token:sessionStorage.getItem('media_token'),
                smStr:JSON.stringify(msg)
            },
            dataType:'JSON',
            success:function (data) {
                var result = checkData(data);
                if (result){
                    sendMsg();
                    return;
                }
                if (data.success){
                    $('.infinite-scroll-preloader').remove();
                    var scrollHeight = $('#scroll_content').prop("scrollHeight");
                    $('#scroll_content').animate({scrollTop:scrollHeight}, 400);
                } else {
                    $.toast('发送失败:'+data.errMsg);
                }
            }
        });
    };

    //每秒执行一次
    setInterval('getUnMessageList()', '1000');

});