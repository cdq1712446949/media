$(function () {

    //发表评论借口
    var addCommentUrl = 'http://media.com/article/uauc';
    //文章详情页面
    var articleDetialUrl = 'http://media.com/media/artideti?articleId=';

    addComment = function (that) {
        var aid = that.dataset.aid;
        var uid = that.dataset.uid;
        window.location.href= articleDetialUrl+aid+'&&userId='+uid;
    };



});