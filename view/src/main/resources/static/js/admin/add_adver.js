$(function () {

    var addAdverUrl = "http://media.com/other/addAdver";

    delImg = function(that){
        var id = that.getAttribute('del-id');
        if (id == 'img-1'){
            $('#img-1').remove();
        }

    };

    addAdverList = function () {
        var formData = new FormData();
        var adver = {};
        adver.advertisementHref = $('#adver_href').val();
        adver.advertisementName = $('#adver_name').val();
        formData.append("adverStr",JSON.stringify(adver));
        //广告图片必须切只能上传一张
        formData.append("adverImg",$('#imgs').files[0]);
        $.ajax({
            url:addAdverUrl,
            type:"POST",
            data:formData,
            contentType: false,
            processData: false,
            cache: false,
            success:function (data) {
                if (data.success){
                    $.confirm({
                        title: '成功',
                        content: '获取广告列表成功',
                        type: 'green',
                        buttons: {
                            close: {
                                text: '关闭',
                            }
                        }
                    });
                }else{
                    $.confirm({
                        title: '错误提示',
                        content: '获取广告列表失败',
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
    }

});