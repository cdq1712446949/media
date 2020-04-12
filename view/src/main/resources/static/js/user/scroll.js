$(function () {

    $.init();
    // 加载flag
    var loading = false;
    // 最多可加载的条目
    var maxItems = 100;
    // 每次加载添加多少条目
    var itemsPerLoad = 10;

    function addItems(number, lastIndex) {
        // 生成新条目的HTML
        var html = '';
        for (var i = lastIndex + 1; i <= lastIndex + number; i++) {
            html += '<li> <div class="card facebook-card" style="width: 90%; ">'
                +'<div class="card-header no-border">'
                +'<div class="facebook-avatar">'
                +'<imgsrc="http://gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i3/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg"'
                +'width="34" height="34"></div>'
                +'<div class="facebook-name">夜萧</div>'
                +'<div class="facebook-date">星期一 3:47pm</div>'
                +' </div>'
                +' <div class="card-content">' +'<div class="card-content-inner">\n' +
                '        <p>此处是内容...</p>\n' +
                '      </div>'+
                '<img'
                +'  src="http://gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i3/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg"'
                +'  width="33%"  style="float: left"></div>'
                +' <div class="card-content"><img'
                +'  src="http://gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i3/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg"'
                +'  width="33%"  style="float: left"></div>'
                +'  <div class="card-content"><img'
                +' src="http://gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i3/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg"'
                +'width="33%"  style="float: left"></div>'
                +'   <div class="card-content"><img'
                +'  src="http://gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i3/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg"'
                +' width="33%" ></div>'
                +' <div class="card-footer no-border">'
                +' <a href="#" class="link">赞</a>'
                +' <a href="#" class="link">评论</a>'
                +' <a href="#" class="link">分享</a>'
                +' </div>'
                +'</div></li>';
        }
        // 添加新条目
        $('.infinite-scroll-bottom .list-container').append(html);
    }

    //预先加载20条
    addItems(itemsPerLoad, 0);
    // 上次加载的序号
    var lastIndex = 20;

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