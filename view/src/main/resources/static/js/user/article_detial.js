$(function () {

    // fallBackView = function () {
    //     window.history.back(-1);
    // };

    //举报页面url
    var reportUrl = 'http://media.com/media/report?articleId='

    var articleId = getQueryString('articleId');
    var userId = getQueryString('userId');

    var commListUrl = 'http://media.com/';

    //初始化界面
    initView = function () {
        //获取文章信息
        getArticleById();
        //获取评论列表
        getCommListByAid();
    };

    getArticleById = function () {

    };

    getCommListByAid = function () {

    };

    getChildComment = function () {

    }

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
                    window.location.href = reportUrl+articleId+'&&userId='+userId;
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


});