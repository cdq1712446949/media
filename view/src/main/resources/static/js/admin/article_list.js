$(function () {

    //获取文章地址
    var articleListUrl = 'http://media.com/article/admin/agal';

    getArticleList = function () {
        var article = {};
        article.startTime = $('#startTime').val();
        article.endTime = $('#endTime').val();
        article.articleContent = $('#content').val();
        article.articleStatus = $('#status').val();
        $.ajax({
            url: articleListUrl,
            type: 'GET',
            data: {
                artiStr: JSON.stringify(article)
            },
            dataType: 'JSON',
            success: function (data) {
                if (data.success) {
                    var articleList = data.articleList;
                    var tempHtml = '';
                    $.each(articleList, function (index, item) {
                    });
                    $('#article').html(tempHtml);
                } else {
                    alert("获取文章列表失败，" + data.errMsg);
                }
            }
        })
    };

    $('#status').change(function () {
        alert($('#status').val());
    });

});