$(function () {

        // fallBackView = function () {
        //     window.history.back(-1);
        // };

        //举报页面url
        var reportUrl = 'http://media.com/media/report?articleId=';
        var articleUrl = 'http://media.com/article/ugabi';
        var commentListUrl = 'http://media.com/article/ugucl';

        var articleId = getQueryString('articleId');
        var userId = getQueryString('userId');

        var commListUrl = 'http://media.com/';


        getArticleById = function () {
            $.ajax({
                url: articleUrl,
                type: 'POST',
                data: {
                    articleId: articleId
                },
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        initArticleView(data.article);
                    } else {
                        $.toast('获取文章失败：' + data.errMsg);
                    }
                }
            });
        };

        initArticleView = function (article) {
            var userInfo = article.user;
            var videoSrc = article.videoSrc;
            var html = '';
            var h1 = '<div class="card-header no-border">\n' +
                '            <div class="facebook-avatar"><img src="http://media.com/image/images/\\head\\2.jpg" width="34"\n' +
                '                                              height="34">\n' +
                '            </div>\n' +
                '            <div class="facebook-name">' + userInfo.nickName + '</div>\n' +
                '            <div class="facebook-date">' + article.articleCreateTime + '</div>\n' +
                '        </div>\n' +
                '        <div class="card-content">\n' +
                '            <div class="card-content-inner">\n' +
                '                <p>' + article.articleContent + '</p>\n' +
                '            </div>\n' +
                '        </div>\n';
            var h2 = '        <div class="row" style="padding:20px">\n';
            if (videoSrc == null || videoSrc == '') {
                $.each(article.photoList, function (index, item) {
                    if (index == article.photoList.length - 1) {
                        h2 += '  <div class="card-content"><img\n' +
                            '                        src="http://media.com/image/images/' + item.photoAddr + '"\n' +
                            '                        width="33%"></div>';
                    } else {
                        h2 += '  <div class="card-content"><img\n' +
                            '                        src="http://media.com/image/images/' + item.photoAddr + '"\n' +
                            '                        width="33%" style="float: left;"></div>'
                    }
                });

            } else {
                h2 = '<div class="card-content">     <video id="my-video" class="video-js" controls="" preload="auto" width="350" height="264" poster="http://gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i3/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg" data-setup="{}">\n' +
                    '    <source src="http://media.com/video/videos/' + videoSrc + '" type="video/mp4">\n' +
                    '    <source src="MY_VIDEO.webm" type="video/webm">\n' +
                    '    <p class="vjs-no-js">\n' +
                    '      To view this video please enable JavaScript, and consider upgrading to a\n' +
                    '      web browser that\n' +
                    '      <a href="https://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>\n' +
                    '    </p>\n' +
                    '  </video> </div>';
            }
            h2 += '</div>';
            var h3 = '<div class="card-footer no-border">\n' +
                '     <a href="#" data-aid="' + article.articleId + '" class="link">赞(' + article.goodNum + ')</a>\n' +
                '        <a onclick="addComment()" data-uid="' + userInfo.userId + '" data-aid="' + article.articleId + '" class="link">评论(' + article.commentNum + ')</a>\n' +
                '        <a href="#" data-aid="' + article.articleId + '" class="link">分享</a>\n' +
                '     </div>  ';
            html = h1 + h2 + h3;
            $('#article_content').html(html);
        };

        getCommListByAid = function () {
            $.ajax({
                url: commentListUrl,
                type: 'POST',
                data: {
                    articleId: articleId
                },
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        initCommentList(data.commentList);
                    } else {
                        $.toast('获取评论列表失败:' + data.errMsg);
                    }
                }
            });
        };

        initCommentList = function (list) {
            var html = ' <div style="height:60%;width:100%;overflow:auto;background:white;">\n' +
                '            <ul  style="list-style-type: none;margin: 10px 5px; padding: 1px">\n';
            $.each(list, function (index, item) {
                var childList = item.userCommentList;
                var fromUser = item.fromUser;
                html += '                <li>\n' +
                    '                    <div class="card comment">\n' +
                    '                        <div class="card-header no-border">\n' +
                    '                            <div class="facebook-avatar">\n' +
                    '                                <img class="yuan-head" src="http://media.com/image/images/'+fromUser.userHeadPhoto+'"\n' +
                    '                                    >\n' +
                    '                            </div>\n' +
                    '                            <div class="nick_name" style="float: left">'+fromUser.nickName+'</div>\n' +
                    '                        </div>\n' +
                    '                        <div class="card-content">\n' +
                    '                            <div class="card-content-inner comment_content">'+item.userCommentContent+'</div>\n' +
                    '                        </div>\n' +
                    '                    </div>\n';
                if (childList.length>0){
                    html+='<ul style="list-style-type: none">';
                    $.each(childList,function (ind,ite) {
                        var fUser = ite.fromUser;
                        html += '                <li>\n' +
                            '                    <div class="card comment">\n' +
                            '                        <div class="card-header no-border">\n' +
                            '                            <div class="facebook-avatar">\n' +
                            '                                <img class="yuan-head" src="http://media.com/image/images/'+fUser.userHeadPhoto+'"\n' +
                            '                                     >\n' +
                            '                            </div>\n' +
                            '                            <div class="nick_name" style="float: left">'+fUser.nickName+'</div>\n' +
                            '                        </div>\n' +
                            '                        <div class="card-content">\n' +
                            '                            <div class="card-content-inner comment_content">'+ite.userCommentContent+'</div>\n' +
                            '                        </div>\n' +
                            '                    </div> </li>';
                    });
                    html+='</ul>';
                }
                html += '                </li>\n';
            });
            html += '        </div>        </ul>';
            $('#article_content').append(html);
        };

        addComment = function (that) {
            $('#footer_comment').show();
            $('#btn_send').show();
        };

        $(document).on('click', '#action_icon', function () {
            var buttons1 = [
                {
                    text: '请选择',
                    label: true
                },
                {
                    text: '举报',
                    bold: true,
                    color: 'danger',
                    onClick: function () {
                        window.location.href = reportUrl + articleId + '&&userId=' + userId;
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
        });

    $(document).on('click', '.comment', function () {
        var buttons1 = [
            {
                text: '请选择',
                label: true
            },
            {
                text: '回复',
                bold: true,
                onClick: function () {
                    window.location.href = reportUrl + articleId + '&&userId=' + userId;
                }
            },
            {
                text: '举报',
                bold: true,
                color: 'danger',
                onClick: function () {
                    window.location.href = reportUrl + articleId + '&&userId=' + userId;
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
    });


    //初始化界面
        initView = function () {
            //获取文章信息
            getArticleById();
            //获取评论列表
            getCommListByAid();
        };

        initView();
    }
);