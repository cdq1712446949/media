$(function () {

    //获取文章地址
    var articleListUrl = 'http://media.com/article/admin/agal';
    //批量屏蔽文章地址
    var banArticlesUrl = 'http://media.com/article/admin/abas';
    //批量屏蔽文章地址
    var delArticlesUrl = 'http://media.com/article/admin/adas';
    var indexPage = 1;
    var totalPageSize = 1;
    var aidArr = new Array();

    getArticleList = function (isReCheck) {
        if (isReCheck) {
            rootPage();
        }
        // console.log(indexPage)
        var article = {};
        article.startTime = $('#startTime').val();
        article.endTime = $('#endTime').val();
        article.articleContent = $('#content').val();
        article.articleStatus = $('#status').val();
        $.ajax({
            url: articleListUrl,
            type: 'GET',
            data: {
                artiStr: JSON.stringify(article),
                token: sessionStorage.getItem('admin_user_token'),
                indexPage: indexPage
            },
            async: false,
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result){
                    getArticleList(isReCheck);
                    return;
                }
                if (data.success) {
                    totalPageSize = data.totalPage;
                    checkPageNum();
                    var articleList = data.articleList;
                    var tempHtml = '';
                    $.each(articleList, function (index, item) {
                        var userInfo = item.user;
                        var articleType = item.articleType;
                        var parentType = articleType.parentArticleType;
                        var status = '正常';
                        if (item.articleStatus == -1) {
                            status = '屏蔽'
                        }
                        tempHtml += '<tr>\n' +
                            '                                        <td>\n' +
                            '                                            <label class="lyear-checkbox checkbox-primary">\n' +
                            '   <input type="checkbox" name="ids[]" value="1"><span data-aid="' + item.articleId + '" class="box_aid" onclick="addArticleId(this)"></span>\n' +
                            '                                            </label>\n' +
                            '                                        </td>\n' +
                            '                                        <td>' + item.articleId + '</td>\n' +
                            '                                        <td>' + userInfo.nickName + '</td>\n' +
                            '                                        <td>' + parentType.articleTypeId + '</td>\n' +
                            '                                        <td>' + articleType.articleTypeName + '</td>\n' +
                            '                                        <td>' + item.lookNum + '</td>\n' +
                            '                                        <td><font class="text-success">' + status + '</font></td>\n' +
                            '                                        <td>\n' +
                            '                                            <div class="btn-group">\n' +
                            '                                                <a class="btn btn-xs btn-default" href="#!" title="编辑"\n' +
                            '                                                   data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>\n' +
                            '                                                <a class="btn btn-xs btn-default" href="#!" title="屏蔽"\n' +
                            '                                                   data-toggle="tooltip"><i class="mdi mdi-window-close"></i></a>\n' +
                            '  </div>\n' + ' </td>\n' + '</tr>';
                    });
                    $('#tbody_article').html(tempHtml);
                } else {
                }
            }
        })
    };


    checkPageNum = function () {
        if (indexPage == 1) {
            $('#up_index ').hide();
        } else {
            $('#up_index').show();
        }
        if (indexPage == totalPageSize) {
            $('#down_index').hide();
        } else {
            $('#down_index').show();
        }
    };

    rootPage = function (that) {
        $('#toOne').click();
    };

    var pageContent = new Vue({
        el: '#pageContent',
        data: {
            pageNumber: 1
        },
        methods: {
            toFirst: function () {
                this.pageNumber = 1;
                indexPage = 1;
                getArticleList(false);
            },
            toBottom: function () {
                this.pageNumber = totalPageSize;
                indexPage = totalPageSize;
                getArticleList(false);
            },
            toNext: function () {
                indexPage += 1;
                this.pageNumber = indexPage;
                getArticleList(false);
            },
            toUp: function () {
                indexPage -= 1;
                this.pageNumber = indexPage;
                getArticleList(false);
            },
            toOne: function () {
                this.pageNumber = 1;
                indexPage = 1;
            }
        }
    });


    addArticleId = function (that) {
        var aid = that.dataset.aid;
        var index = aidArr.indexOf(aid);
        if (index > -1) {
            aidArr.splice(index, 1);
        } else {
            aidArr.push(aid);
        }
    };

    allSelect = function () {
        $.map($('.box_aid'), function (that, index) {
            that.click();
        });
    };

    //批量屏蔽文章
    banArticles = function (status) {
        $.ajax({
            url: banArticlesUrl,
            type: 'POST',
            data: {
                articleIds: JSON.stringify(aidArr),
                token: sessionStorage.getItem('admin_user_token'),
                status: status
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    banArticles();
                    return;
                }
                if (data.success) {
                    aidArr.length = 0;
                    getArticleList()
                }
            }
        });
    };

    //批量删除文章
    delArticles = function () {
        $.ajax({
            url: delArticlesUrl,
            type: 'POST',
            data: {
                articleIds: JSON.stringify(aidArr),
                token: sessionStorage.getItem('admin_user_token')
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    delArticlesUrl();
                    return;
                }
                if (data.success) {
                    aidArr.length = 0;
                    getArticleList()
                } else {

                }
            }
        });
    };


});
