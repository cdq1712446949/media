/**
 *
 */

$(function () {
    //获取地址栏中的articleId
    var articleId = getQueryString("articleId");
    //根据是否有productId选择执行函数
    //文章信息获取地址
    var initPriductUrl = '/article/ugabi?articleId=' + articleId;
    //文章发布url
    var addArticleUrl = '/article/addArticle';
    //x修改文章url
    var modifyArticleUrl = '/article/modifyArticle';
    //获取文章类别列表
    var getCategoryListUrl = '/article/aflat';
    var mediaToken = 'media_token';
    var isEdit = false;
    if (articleId) {
        getArticleTypeList();
        getArticleInfo();
        isEdit = true;
    } else {
        getArticleTypeList();
        isEdit = false;
    }

    function getArticleInfo() {
        $.getJSON(initPriductUrl, function (data) {
            if (data.success) {
                var article = data.article;
                $('#article-content').val(article.articleContent);
            } else {
                $.toast("获取文章信息失败:" + data.errMsg);
            }
        });
    }

    function initArticleType(optionSelected) {
        $.getJSON(getCategoryListUrl, function (data) {
            if (data.success) {
                var productCategoryList = data.firstLevelList;
                var optionHtml = '';
                var isSelected = 'selected';
                for (var i = 0; i < productCategoryList.length; i++) {
                    var item = productCategoryList[i];
                    if (optionSelected == item.productCategoryId) {
                        optionHtml += '<option data-value="'
                            + item.productCategoryId
                            + '"'
                            + isSelected
                            + '>'
                            + item.articleTypeName
                            + '</option>';
                    } else {
                        optionHtml += '<option data-value="'
                            + item.productCategoryId + '">'
                            + item.productCategoryName + '</option>';
                    }
                }
                $('#product-category').html(optionHtml);
            } else {
                $.toast("获取商品类别失败" + data.errMsg);
            }
        });
    }

    $('#product-category').change(function () {
        var cId = $('#product-category').find('option').not(
            function () {
                return !this.selected;
            }).data('value');
        $.ajax({
            url: getCategoryListUrl,
            type: "GET",
            data: {
                parentId: cId
            },
            dataType: "JSON",
            success: function (data) {
                data = JSON.parse(data);
                if (data.success) {
                    var productCategoryList = data.firstLevelList;
                    var optionHtml = '';
                    for (var i = 0; i < productCategoryList.length; i++) {
                        var item = productCategoryList[i];
                        optionHtml += '<option data-value="'
                            + item.articleTypeId + '">'
                            + item.articleTypeName + '</option>';
                    }
                    ;
                    $('#two-category').html(optionHtml);
                } else {
                    alert(data.errMsg);
                }
            }
        })
    });

    function getArticleTypeList() {
        $.getJSON(getCategoryListUrl, function (data) {
            if (data.success) {
                var productCategoryList = data.firstLevelList;
                var optionHtml = '';
                for (var i = 0; i < productCategoryList.length; i++) {
                    var item = productCategoryList[i];
                    optionHtml += '<option data-value="'
                        + item.articleTypeId + '">'
                        + item.articleTypeName + '</option>';
                }
                ;
                $('#product-category').html(optionHtml);
            } else {
                $.toast("获取商品类别失败" + data.errMsg);
            }
        });
    }

    $('.detail-img-div').on('change', '.detail-img:last-child', function () {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    $('#submit').click(
        function () {
            var formData = new FormData();
            var article = {};
            article.articleContent = $('#article-content').val();
            article.articleType = {
                articleTypeId: $('#two-category').find('option').not(
                    function () {
                        return !this.selected;
                    }).data('value')
            };
            article.articleId = articleId;

            $('.detail-img').map(
                function (index, item) {
                    if ($('.detail-img')[index].files.length > 0) {
                        formData.append('productImg' + index,
                            $('.detail-img')[index].files[0]);
                    }
                });
            formData.append('productStr', JSON.stringify(article));
            formData.append('token', sessionStorage.getItem(mediaToken));
            $.ajax({
                url: isEdit ? modifyArticleUrl : addArticleUrl,
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        window.history.back(-1);
                        $.toast('发布成功！');
                    } else {
                        window.history.back(-1);
                        $.toast('发布失败！' + data.errMsg);
                    }
                },
                erro: function (code) {
                    $.toast(code);
                }
            });
        });
});