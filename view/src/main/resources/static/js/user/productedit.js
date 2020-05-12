/**
 *
 */

$(function () {
    //获取地址栏中的articleId
    var articleId = getQueryString("articleId");
    //根据是否有productId选择执行函数
    //文章信息获取地址
    var initPriductUrl = '/article/ugabi?articleId=' + articleId + '&&token=' + sessionStorage.getItem('media_token');
    //文章发布url
    var addArticleUrl = '/article/addArticle';
    //x修改文章url
    var modifyArticleUrl = '/article/modifyArticle';
    //获取文章类别列表
    var getCategoryListUrl = '/article/aflat';
    var mediaToken = 'media_token';
    var isEdit = false;

    getArticleInfo = function () {
        $.getJSON(initPriductUrl, function (data) {
            var result = checkData(data);
            if (result) {
                getArticleInfo();
                return;
            }
            if (data.success) {
                var article = data.article;
                $('#article-content').val(article.articleContent);
            } else {
                $.toast("获取文章信息失败:" + data.errMsg);
            }
        });
    };

    if (articleId) {
        getArticleTypeList();
        getArticleInfo();
        isEdit = true;
        $('#li_first').hide();
        $('#li_two').hide();
        $('#li_image').hide();
        $('#li_vido').hide();
    } else {
        getArticleTypeList();
        isEdit = false;
    }

    initArticleType = function (optionSelected) {
        $.getJSON(getCategoryListUrl, function (data) {
            var result = checkData(data);
            if (result) {
                initArticleType(optionSelected);
                return;
            }
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
                $.toast("获取文章类别失败" + data.errMsg);
            }
        });
    };

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
                $('#product-category').html(optionHtml);
            } else {
                $.toast("获取文章类别失败" + data.errMsg);
            }
        });
    }

    $('.detail-img-div').on('change', '.detail-img:last-child', function () {
        $('#li_vido').hide();
        if ($('.detail-img').length < 3) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    $('#video-art').change(function () {
        $('#li_image').hide();
    });



    $('#submit').click(function () {
        var formData = new FormData();
        var article = {};
        article.articleContent = $('#article-content').val();
        if (!isEdit) {
            article.articleType = {
                articleTypeId: $('#two-category').find('option').not(
                    function () {
                        return !this.selected;
                    }).data('value')
            };
            if ($('#video-art').val() == '' || $('#video-art').val() == null) {
                $('.detail-img').map(function (index, item) {
                    if (index > 2) {
                        $.toast('图片数量不能超过三张,只会选取前三张上传');
                        return;
                    }
                    if ($('.detail-img')[index].files.length > 0) {
                        formData.append('productImg' + index,
                            $('.detail-img')[index].files[0]);
                    }
                });
            } else {
                var tempFile = document.getElementById('video-art')
                if (tempFile.files.length>0) {
                    formData.append('articleVideo', tempFile.files[0]);
                }
            }
        }
        article.articleId = articleId;
        formData.append('productStr', JSON.stringify(article));
        var token = sessionStorage.getItem('media_token');
        $.ajax({
            url: isEdit ? modifyArticleUrl + '?token=' + token : addArticleUrl + '?token=' + token,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                var result = checkData(data);
                if (result) {
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
                }
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