$(function () {

    var addAdverUrl = "http://media.com/other/addAdver";

    $('#img-1').hide();

    delImg = function (that) {
        var id = that.getAttribute('del-id');
        if (id == 'img-1') {
            $('#img-1').remove();
            var eml = document.getElementById("img1");
            eml.outerHTML = eml.outerHTML;
        }

    };

    /**
     * 添加广告
     * 1.检查名称、地址是否为空
     * 2.检查图片文件是否是一张
     */
    addAdverList = function () {
        var formData = new FormData();
        var adver = {};
        var isReturn = false;
        adver.advertisementHref = $('#adver_href').val();
        adver.advertisementName = $('#adver_name').val();
        if (adver.advertisementHref == null || adver.advertisementHref == '') {
            lightyear.notify('请填写地址', 'danger', 100);
            isReturn = true;
        }
        if (adver.advertisementName == null || adver.advertisementName == '') {
            lightyear.notify('请填写名称', 'danger', 100);
            isReturn = true;
        }
        if (isReturn) {
            return
        }
        formData.append("adverStr", JSON.stringify(adver));
        //广告图片必须切只能上传一张
        var eml = document.getElementById("input-img");
        formData.append("adverImg",eml.files[0]);
        $.ajax({
            url: addAdverUrl,
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.confirm({
                        title: '成功',
                        content: '获取广告列表成功',
                        type: 'green',
                        buttons: {
                            close: {
                                text: '关闭',
                                action:function () {
                                    window.location.href = 'http://media.com/media/admin/addAdver';
                                }
                            }
                        }
                    });
                } else {
                    $.confirm({
                        title: '错误提示',
                        content: '获取广告列表失败'+data.errMsg,
                        type: 'red',
                        typeAnimated: true,
                        buttons: {
                            close: {
                                text: '关闭'
                            }
                        }
                    });
                }
            }
        })
    };

    chooseFile = function (that) {
        var url = window.URL.createObjectURL(that.files[0]);
        var eml = document.getElementById('img1');
        eml.src = url;
        $('#img-1').show();
        // $('#img1').setAttribute("src",fielSrc);
    };

});