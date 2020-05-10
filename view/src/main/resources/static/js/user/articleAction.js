$(function () {

    //添加点赞记录地址
    var thumbsUpUrl = 'http://media.com/article/thumbsupmanage';
    //添加收藏记录地址
    var collectionUrl = 'http://media.com/article/coll/uacoll';

    //点赞
    addThumbsUp = function (articleId, status, that) {
        $.ajax({
            url: thumbsUpUrl,
            type: 'POST',
            data: {
                articleId: articleId,
                status: status,
                token: sessionStorage.getItem('media_token')
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    addThumbsUp(articleId, status,that);
                }
                if (data.success) {
                    that.style.color = 'yellow';
                } else {
                    $.toast('点赞失败：' + data.errMsg);
                }
            }
        });
    };

    //点赞
    addCollection = function (articleId,status, that) {
        $.ajax({
            url: collectionUrl,
            type: 'POST',
            data: {
                articleId: articleId,
                status: status,
                token: sessionStorage.getItem('media_token')
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    addCollection(articleId,status, that);
                }
                if (data.success) {
                    that.style.color = 'yellow';
                } else {
                    $.toast('收藏失败：' + data.errMsg);
                }
            }
        });
    };


    //添加监听事件
    thumbsUpAction = function (that) {
        var aid = that.dataset.aid;
        addThumbsUp(aid, 0, that);
    };

    //点赞按钮监听事件
    collectionAction = function (that) {
        var aid = that.dataset.aid;
        addCollection(aid,0, that);
    }

});