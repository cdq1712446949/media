$(function () {

    $.init();

    var videoArticleUrl = 'http://media.com/article/ugval';

    // 加载flag
    var loading = false;
    // 最多可加载的条目
    var maxItems = 100;
    // 每次加载添加多少条目
    var itemsPerLoad = 10;
    // 上次加载的序号
    var lastIndex = 20;
    //页数
    var indexPage = 1;
    //总页数
    var totalPageSize = 0;

    //获取视频文章列表
    function getVideoArticleList (){
        $.ajax({
            url:videoArticleUrl,
            type:"POST",
            data:{
                indexPage:indexPage
            },
            dataType:"JSON",
            success:function (data) {
                if(data.success){
                    totalPageSize = data.totalPage;
                    addItems(data.articleList);
                }else{
                    alert('获取视频文章列表失败：'+data.errMsg);
                }
            }
        });
    }

    function addItems(articleList) {
        // 生成新条目的HTML
        var html = '';
        $.each(articleList, function (index, item){
            var userInfo = item.user;
            html += '<li> <div class="card facebook-card" style="width: 90%; " data-aid="' + item.articleId + '">\n' +
                '                <div class="card-header no-border">\n' +
                '                    <div class="facebook-avatar"><img\n' +
                '                            src="http://media.com/image/images/' + userInfo.userHeadPhoto + '"\n' +
                '                            width="34" height="34"></div>\n' +
                '                    <div class="facebook-name">' + userInfo.nickName + '</div>\n' +
                '                    <div class="facebook-date">' + item.articleCreateTime + '</div>\n' +
                '                </div>'
                +' <div class="card-content">' +'<div class="card-content-inner">\n' +
                '        <p>'+item.articleContent+'</p>\n' +
                '      </div>'+'  <video\n' +
                '    id="my-video"\n' +
                '    class="video-js"\n' +
                '    controls\n' +
                '    preload="auto"\n' +
                '    width="640"\n' +
                '    height="264"\n' +
                '    poster="http://gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i3/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg"\n' +
                '    data-setup="{}"\n' +
                '  >\n' +
                '    <source src="http://media.com/video/videos/'+item.videoSrc+'" type="video/mp4" />\n' +
                '    <source src="MY_VIDEO.webm" type="video/webm" />\n' +
                '    <p class="vjs-no-js">\n' +
                '      To view this video please enable JavaScript, and consider upgrading to a\n' +
                '      web browser that\n' +
                '      <a href="https://videojs.com/html5-video-support/" target="_blank"\n' +
                '        >supports HTML5 video</a\n' +
                '      >\n' +
                '    </p>\n' +
                '  </video>'

                +' <div class="card-footer no-border">\n' +
                '                    <a href="#" data-aid="' + item.articleId + '" class="link">赞(' + item.goodNum + ')</a>\n' +
                '                    <a onclick="addComment(this)" data-uid="' + userInfo.userId + '" data-aid="' + item.articleId + '" class="link">评论(' + item.commentNum + ')</a>\n' +
                '                    <a href="#" data-aid="' + item.articleId + '" class="link">收藏</a>\n' +
                '                </div>  </div></li>';
        });
        // 添加新条目
        $('.infinite-scroll-bottom .list-container').append(html);
    }

    //预先加载10
    getVideoArticleList();

    // 注册'infinite'事件处理函数
    $(document ).on('infinite','.infinite-scroll-bottom',function() {
        // 如果正在加载，则退出
        if (loading) return;
        // 设置flag
        loading = true;
        // 模拟1s的加载过程
        setTimeout(function() {
            // 重置加载flag
            loading = false;
            indexPage += 1;
            if (indexPage > totalPageSize) {
                $.toast("没有更多了");
                $('#tishifu').hide();
                return;
            }
            // 添加新条目
            getVideoArticleList();
            // 更新最后加载的序号
            lastIndex = $('.list-container li').length;
            //容器发生改变,如果是js滚动，需要刷新滚动
            $.refreshScroller();
        }, 1000);
    });
    
    $('#a_refrash').click(function () {
        $('#tishifu').show();
        $('#video_content').html('');
        indexPage = 1;
        totalPageSize = 0;
        getVideoArticleList();
    });

    var articleDetialUrl = 'http://media.com/media/artideti?articleId=';

    //跳转到文章详情页面
    addComment = function (that) {
        var aid = that.dataset.aid;
        var uid = that.dataset.uid;
        window.location.href= articleDetialUrl+aid+'&&userId='+uid;
    };


});