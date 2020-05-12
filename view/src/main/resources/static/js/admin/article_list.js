$(function () {

    //获取文章地址
    var articleListUrl = 'http://media.com/article/admin/agal';

    var indexPage = 1;
    var totalPageSize = 0;

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
                artiStr: JSON.stringify(article),
                token: sessionStorage.getItem('admin_user_token'),
                indexPage:indexPage
            },
            async:false,
            dataType: 'JSON',
            success: function (data) {
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
                            '                                                <input type="checkbox" name="ids[]" value="1"><span></span>\n' +
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

    getArticleList();

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


    var pageContent = new Vue({
        el: '#pageContent',
        data: {
            pageNumber: 1
        },
        methods: {
            toFirst: function () {
                this.pageNumber = 1;
                indexPage = 1;
                getArticleList();
            },
            toBottom: function () {
                this.pageNumber = totalPageSize;
                indexPage = totalPageSize;
                getArticleList();
            },
            toNext: function () {
                indexPage += 1;
                this.pageNumber = indexPage;
                getArticleList();
            },
            toUp: function () {
                indexPage -= 1;
                this.pageNumber = indexPage;
                getArticleList();
            }
        }
    });

    checkPageNum();

});
