$(function () {

    //获取文章地址
    var articleListUrl = 'http://media.com/article/getArticle';
    //获取所有二级文章类型地址
    var twoLevelUrl = "/article/aflat";
    var fistName = '生活';
    var allPicker;
    var isLogin = false;
    var typeId = '';
    var indexPage = 1;
    var totalPage = 0;

    // 加载flag
    var loading = false;
    // 最多可加载的条目
    var maxItems = 100;
    // 上次加载的序号
    var lastIndex = 20;
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
            window.parent.location.href = 'http://media.com/media/articleEdit';
        } else {
            window.parent.location.href = 'http://media.com/media/login';
        }
    };

    $(document).on("pageInit", function () {
        var fistLevelList;
        var twoList;

        $.ajaxSettings.async = false;
        $.getJSON(twoLevelUrl + '?parentId=1', function (data) {
            twoList = getTypeName(data.firstLevelList);
        });
        $.ajaxSettings.async = true;
        $.ajax({
            url: twoLevelUrl,
            type: "GET",
            data: {},
            dataType: "JSON",
            async: true,
            success: function (data) {
                if (data.success) {
                    fistLevelList = getTypeName(data.firstLevelList);
                    sessionStorage.setItem("first_level_list", JSON.stringify(data.firstLevelList));
                    $("#article_type").picker({
                        toolbarTemplate: '<header class="bar bar-nav">\
      <button class="button button-link pull-right close-picker" id="type_sure" onclick="type_sure()">确定</button>\
      <h1 class="title">选择文章类型</h1>\
      </header>',
                        cols: [
                            {
                                textAlign: 'center',
                                values: fistLevelList
                            },
                            {
                                textAlign: 'center',
                                values: twoList
                            }
                        ],
                        rotateEffect: true,
                        onOpen: function (picker) {
                            allPicker = picker;
                        }
                    });
                } else {
                    $.alert(data.errMsg);
                }
            }
        });

    });

    $('#article_type').change(function () {
        $.init();
        var parentTypeId;
        var localFirstTypeList = JSON.parse(sessionStorage.getItem("first_level_list"));
        var name1 = this.value.split(" ")[0];
        for (var i = 0; i < localFirstTypeList.length; i++) {
            var tempArticleType = localFirstTypeList[i];
            if (tempArticleType.articleTypeName == name1) {
                parentTypeId = tempArticleType.articleTypeId;
            }
        }
        if (name1 != fistName) {
            $.ajax({
                url: twoLevelUrl,
                type: 'GET',
                dataType: 'JSON',
                data: {
                    parentId: parentTypeId
                },
                success: function (data) {
                    sessionStorage.setItem('two_type_list', JSON.stringify(data.firstLevelList));
                    var templist = getTypeName(data.firstLevelList);
                    allPicker.cols[1].replaceValues(templist);
                    allPicker.value[1] = templist[0];
                    allPicker.updateValue();
                    fistName = name1;
                }
            });
        }
    });


    //文章类型选择器点击确定后发生改变是触发的逻辑
    type_sure = function () {
        getTypeId(allPicker.value[1]);
        getArticleList2(false);
    };

    function getTypeName(typeList) {
        var names = new Array();
        for (var i = 0; i < typeList.length; i++) {
            var tempType = typeList[i];
            names[i] = tempType.articleTypeName;
        }
        return names;
    }

    function getTypeId(name) {
        var typeList = JSON.parse(sessionStorage.getItem('two_type_list'));
        for (var i = 0; i < typeList.length; i++) {
            var tempType = typeList[i];
            if (tempType.articleTypeName == name) {
                typeId = tempType.articleTypeId;
            }
        }
    }

    getArticleList1 = function () {

    };

    getArticleList2 = function (isAppend) {
        var article = {};
        console.log('typeId:' + typeId);
        $.ajax({
            url: articleListUrl,
            type: 'GET',
            data: {
                artiStr: JSON.stringify(article),
                typeId: typeId,
                indexPage: indexPage
            },
            dataType: 'JSON',
            success: function (data) {
                if (data.success) {
                    initArticleList(data,isAppend);
                } else {
                    alert("获取文章列表失败，" + data.errMsg);
                }
            }
        })
    };

    getArticleList2();

    initArticleList = function (data,isAppend) {
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
                '                <div class="card-header no-border">\n' +
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
        if (isAppend){
            $('#list_tab1').append(tempHtml);
        } else{
            $('#list_tab1').html(tempHtml);
        }
    };

    // 注册'infinite'事件处理函数
    $(document).on('infinite', '#tab1_cont', function () {
        // 如果正在加载，则退出
        if (loading) return;
        // 设置flag
        loading = true;
        // 模拟1s的加载过程
        setTimeout(function () {
            // 重置加载flag
            loading = false;
            // 添加新条目
            indexPage += 1;
            if (indexPage > totalPageSize) {
                $.toast("没有更多了");
                $('#tab2_tishifu').hide();
                return;
            }
            getArticleList2(true);
            // 更新最后加载的序号
            lastIndex = $('.list-container li').length;
            //容器发生改变,如果是js滚动，需要刷新滚动
            $.refreshScroller();
        }, 1000);
    });

    $.init();

});



