$(function () {

    var myArticleUrl = 'http://media.com/article/ugmal';
    //删除文章接口地址
    var delArticleUrl = 'http://media.com/article/delArticle';

    var loading = false;
    //页数
    var indexPage = 1;
    //总页数
    var totalPageSize = 0;

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

    //删除文章方法
    delArticle = function(aid){
        $.ajax({
            url:delArticleUrl,
            type:'POST',
            data:{
                articleId:aid,
                token:sessionStorage.getItem('media_token')
            },
            dataType:'JSON',
            success:function (data) {
                if (data.success){
                    $.toast('删除成功');
                } else {
                    $.toast('删除失败：'+data.errMsg);
                }
            }
        });
    };

    clickAction = function (that) {
        var aid = that.dataset.aid;
        var buttons1 = [
            {
                text: '请选择',
                label: true
            }, {
                text: '修改',
                bold: true,
                onClick: function () {
                    window.location.href = 'http://media.com/media/articleEdit?articleId='+aid;
                }
            },
            {
                text: '删除',
                bold: true,
                color: 'danger',
                onClick: function () {
                    $.confirm('确定要删除该文章吗?', function () {
                        //删除文章
                        delArticle(aid);
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

    getMyArticle = function (isAppend) {
        $.ajax({
            url: myArticleUrl,
            type: 'POST',
            data: {
                token: sessionStorage.getItem('media_token'),
                indexPage: indexPage
            },
            dataType: 'JSON',
            success: function (data) {
                if (data.success) {
                    initArticleList(data, isAppend);
                } else {
                    alert("获取文章列表失败，" + data.errMsg);
                }
            }
        })
    };

    getMyArticle();

    initArticleList = function (data, isAppend) {
        var articleList = data.articleList;
        totalPageSize = data.totalPage;
        var tempHtml = '';
        if (articleList.length == 0) {
            $('#tab1_tishifu').hide();
            $.toast('没有更多内容');
        }
        if (articleList.length < 10) {
            $('#tab1_tishifu').hide();
        }
        $.each(articleList, function (index, item) {
            var uinfo = item.user;
            var t1 = ' <div class="card facebook-card" style="width: 90%; " data-aid="' + item.articleId + '">\n' +
                '<a class="icon icon-caret pull-right" data-aid="' + item.articleId + '" onclick="clickAction(this)"></a>' +
                '                <div class="card-header no-border" style="margin-right: 20px">\n' +
                '                    <div class="facebook-avatar"><img\n' +
                '                            src="http://media.com/image/images/' + uinfo.userHeadPhoto + '"\n' +
                '                            width="34" height="34"></div>\n' +
                '                    <div class="facebook-name">' + uinfo.nickName + '</div>\n' +
                '                    <div class="facebook-date">' + item.articleCreateTime + '</div>\n' +
                '                </div>';
            var t4 = '<div class="card-content"><div class="card-content-inner">\n' +
                '        <p>' + item.articleContent + '</p>\n' +
                '      </div></div>'
            var t2 = '';
            $.each(item.photoList, function (ind, ite) {
                if (ind == item.photoList.length - 1) {
                    t2 += '  <div class="card-content"><img\n' +
                        '                        src="http://media.com/image/images/' + ite.photoAddr + '"\n' +
                        '                        width="33%"></div>';
                } else {
                    t2 += '  <div class="card-content"><img\n' +
                        '                        src="http://media.com/image/images/' + ite.photoAddr + '"\n' +
                        '                        width="33%" style="float: left;"></div>'
                }
            });
            t2 = '<div class="row" style="padding:20px">' + t2 + '</div>';
            var t3 = ' <div class="card-footer no-border">\n' +
                '                    <a href="#" data-aid="' + item.articleId + '" class="link">赞(' + item.goodNum + ')</a>\n' +
                '                    <a onclick="addComment(this)" data-uid="' + uinfo.userId + '" data-aid="' + item.articleId + '" class="link">评论(' + item.commentNum + ')</a>\n' +
                '                    <a href="#" data-aid="' + item.articleId + '" class="link">分享</a>\n' +
                '                </div>  </div>';
            var t = '<li>' + t1 + t4 + t2 + t3 + '</li>';
            tempHtml += t;
        });
        if (isAppend) {
            $('#list_article').append(tempHtml);
        } else {
            $('#list_article').html(tempHtml);
        }
    };

    // 注册'infinite'事件处理函数
    $(document).on('infinite', '#tab1_cont', function () {
        if (loading) return;
        loading = true;
        setTimeout(function () {
            loading = false;
            indexPage += 1;
            if (indexPage > totalPageSize) {
                $.toast("没有更多了");
                $('#tishifu').hide();
                return;
            }
            getMyArticle(true);
            lastIndex = $('.list-container li').length;
            $.refreshScroller();
        }, 1000);
    });

    $.init();

});