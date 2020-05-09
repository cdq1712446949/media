$(function () {

    //关注列表获取地址
    var collectionListUrl = 'http://media.com/article/coll/ugcl';
    var cancleCollectionUrl = 'http://media.com/article/coll/udcoll';

    var indexPage = 1;
    var totalPageSize = 0;
    var isLoading = false;

    //取消关注，发送私信动作
    collectionAction = function (that) {
        var cid = that.dataset.cid;
        cancleCollection(cid,that);
    };

    cancleCollection = function (aid, that) {
        $.ajax({
            url: cancleCollectionUrl,
            type: 'POST',
            data: {
                token: sessionStorage.getItem('media_token'),
                collectionId: aid
            },
            dateType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    cancleAttention(aid);
                    return;
                }
                if (data.success) {
                    var eml = document.getElementById('coll'+aid);
                    eml.remove();
                } else {
                    $.toast('取消收藏失败：' + data.errMsg);
                }
            }
        });
    };

    initCollectionList = function (list) {
        var html = '';
        $.each(list, function (index, item) {
            var userInfo = item.user;
            var article = item.article;
            html +='  <li id="coll'+item.collectionId+'"><div class="card facebook-card">\n' +
                '                        <div class="card-header no-border">\n' +
                '                            <div class="facebook-avatar">\n' +
                '                                <img class="yuan-head" src="http://media.com/image/images/'+userInfo.userHeadPhoto+'" style="width: 3rem;"></div>\n' +
                '                            <div class="facebook-name" style="max-width: 85%">'+userInfo.nickName+'\n' +
                '                            <span>' +
                '<a onclick="collectionAction(this)" data-cid="'+item.collectionId+'" ' +
                'class="icon icon-remove pull-right"></a></span></div>\n' +
                '\n' +
                '                        </div>\n' +
                '                        <div class="card-content-inner">'+article.articleContent+'</div>\n' +
                '                    </div></li>';
        });
        $('#ul_collection').append(html);
        $('#tishifu').hide();
    };

    getCollectionList = function () {
        $('#tishifu').show();
        $.ajax({
            url: collectionListUrl,
            type: 'POST',
            data: {
                token: sessionStorage.getItem('media_token')
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    getCollectionList();
                    return;
                }
                totalPageSize = data.totalPage;
                if (data.success) {
                    initCollectionList(data.attentionList);
                } else {
                    $.toast('获取关注列表失败：' + data.errMsg);
                }
            }
        });
    };

    $('#tishifu').hide();

    getCollectionList();

    $(document).on('infinite', '#collection_content', function () {
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
            getCollectionList();
            //容器发生改变,如果是js滚动，需要刷新滚动
            $.refreshScroller();
        }, 1000);
    });

});