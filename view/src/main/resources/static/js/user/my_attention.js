$(function () {

    //关注列表获取地址
    var attentionListUrl = 'http://media.com/article/atten/ugalbu';
    var cancleAttentionUrl = 'http://media.com/article/atten/udelatten';

    var indexPage = 1;
    var totalPageSize = 0;
    var isLoading = false;

    //取消关注，发送私信动作
    attentionAction = function (that) {
        var uid = that.dataset.uid;
        var nickName = that.dataset.nm;
        var aid = that.dataset.aid;
        var buttons1 = [
            {
                text: '请选择',
                label: true
            }, {
                text: '发送私信',
                bold: true,
                onClick: function () {
                    window.location.href = 'http://media.com/media/chat?fromUserId=' + uid + '&&nickName=' + nickName;
                }
            },
            {
                text: '取消关注',
                bold: true,
                color: 'danger',
                onClick: function () {
                    $.confirm('确定要取消对该用户的关注吗?', function () {
                        //删除文章
                        cancleAttention(aid, that);
                    });
                }
            }

        ];
        var buttons2 = [
            {
                text: '取消',
                bg: 'danger'
            }
        ];
        var groups = [buttons1, buttons2];
        $.actions(groups);
    };

    cancleAttention = function (aid, that) {
        $.ajax({
            url: cancleAttentionUrl,
            type: 'POST',
            data: {
                token: sessionStorage.getItem('media_token'),
                attentionId: aid
            },
            dateType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    cancleAttention(aid);
                    return;
                }
                if (data.success) {
                    var eml = document.getElementById('atten'+aid);
                    eml.remove();
                } else {
                    $.toast('取消关注失败：' + data.errMsg);
                }
            }
        });
    };

    initAttentionList = function (list) {
        var html = '';
        $.each(list, function (index, item) {
            var userInfo = item.attentedUser;
            html +=
                '            <li>\n' +
                ' <a onclick="attentionAction(this)" class="item-link item-content" id="atten'+item.attentionId+'" ' +
                'data-nm="' + userInfo.nickName + '" data-uid="' + userInfo.userId + '" data-aid="' + item.attentionId + '">\n' +
                '                    <div class="item-media">\n' +
                '                        <img class="yuan-head" src="http://media.com/image/images/' + userInfo.userHeadPhoto + '" style=\'width: 3rem;\'>\n' +
                '                    </div>\n' +
                '                    <div class="item-inner">\n' +
                '                        <div class="item-subtitle">' + userInfo.nickName + '</div>\n' +
                '                        <div class="item-text" style="font-size: 11px">' + userInfo.userDesc + '</div>\n' +
                '                    </div>\n' +
                '                </a>\n' +
                '            </li>\n';
        });
        $('#ul_atten').append(html);
        $('#tishifu').hide();
    };

    getAttentionList = function () {
        $('#tishifu').show();
        $.ajax({
            url: attentionListUrl,
            type: 'POST',
            data: {
                token: sessionStorage.getItem('media_token')
            },
            dataType: 'JSON',
            success: function (data) {
                // TODO 添加数量查询
                var result = checkData(data);
                if (result) {
                    getAttentionList();
                    return;
                }
                totalPageSize = data.totalPage;
                if (data.success) {
                    initAttentionList(data.attentionList);
                } else {
                    $.toast('获取关注列表失败：' + data.errMsg);
                }
            }
        });
    };

    $('#tishifu').hide();

    getAttentionList();

    $(document).on('infinite', '#atten_content', function () {
        // 如果正在加载，则退出
        if (isLoading) return;
        // 设置flag
        isLoading = true;
        // 模拟1s的加载过程
        setTimeout(function () {
            // 重置加载flag
            isLoading = false;
            // 添加新条目
            indexPage += 1;
            if (indexPage > totalPageSize) {
                $.toast("没有更多了");
                return;
            }
            getAttentionList();
            //容器发生改变,如果是js滚动，需要刷新滚动
            $.refreshScroller();
        }, 1000);
    });

});