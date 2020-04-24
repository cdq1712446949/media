$(function () {

    $.init();

    //获取关注文章列表地址
    var annUrl = 'http://media.com/article/ugaal';

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
    //总数
    var totalNum = 0;

    getAnnList = function(){
        $.ajax({
            url: annUrl,
            type: 'POST',
            data: {
                indexPage:indexPage,
                token:sessionStorage.getItem("media_token")
            },
            dataType: 'JSON',
            success: function (data) {
                if (data.success) {
                    var articleList = data.articleList;
                    var tempHtml = '';
                    $.each(articleList, function (index, item) {
                        var uinfo = item.user;
                        var t1 = ' <div class="card facebook-card" style="width: 90%; " data-aid="'+item.articleId+'">\n' +
                            '                <div class="card-header no-border">\n' +
                            '                    <div class="facebook-avatar"><img\n' +
                            '                            src="http://media.com/image/images/'+uinfo.userHeadPhoto+'"\n' +
                            '                            width="34" height="34"></div>\n' +
                            '                    <div class="facebook-name">'+uinfo.nickName+'</div>\n' +
                            '                    <div class="facebook-date">'+item.articleCreateTime+'</div>\n' +
                            '                </div>';
                        var t4 = '<div class="card-content"><div class="card-content-inner">\n' +
                            '        <p>'+item.articleContent+'</p>\n' +
                            '      </div></div>'
                        var t2='';
                        $.each(item.photoList,function (ind,ite) {
                            if (ind == item.photoList.length-1){
                                t2+='  <div class="card-content"><img\n' +
                                    '                        src="http://media.com/image/images/'+ite.photoAddr+'"\n' +
                                    '                        width="33%" style="float: left;></div>';
                            } else {
                                t2+='  <div class="card-content"><img\n' +
                                    '                        src="http://media.com/image/images/'+ite.photoAddr+'"\n' +
                                    '                        width="33%" "></div>'
                            }
                        });
                        t2 = '<div class="row" style="padding:20px">'+t2+'</div>';
                        var t3 = ' <div class="card-footer no-border">\n' +
                            '                    <a href="#" data-aid="'+item.articleId+'" class="link">赞('+item.goodNum+')</a>\n' +
                            '                    <a href="#" data-aid="'+item.articleId+'" class="link">评论('+item.commentNum+')</a>\n' +
                            '                    <a href="#" data-aid="'+item.articleId+'" class="link">分享</a>\n' +
                            '                </div>  </div>';
                        var t = t1+t4+t2+t3;
                        tempHtml += t;
                    });
                    $('#tab2').append(tempHtml);
                } else {
                    alert("获取文章列表失败，" + data.errMsg);
                }
            }
        })
    };


    function addItems(number, lastIndex) {
        getAnnList();
    }

    //预先加载10条
    addItems(itemsPerLoad, 0);

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
            if (lastIndex >= maxItems) {
                // 加载完毕，则注销无限加载事件，以防不必要的加载
                $.detachInfiniteScroll($('.infinite-scroll'));
                // 删除加载提示符
                $('.infinite-scroll-preloader').remove();
                return;
            }
            // 添加新条目
            addItems(itemsPerLoad, lastIndex);
            // 更新最后加载的序号
            lastIndex = $('.list-container li').length;
            //容器发生改变,如果是js滚动，需要刷新滚动
            $.refreshScroller();
        }, 1000);
    });
});