$(function () {

    //广告列表url
    var advertisementListUrl = "http://media.com/article/";

    //获取广告列表
    function getAdvertisement() {
        $.getJSON(advertisementListUrl, function (data) {
            if (data.success) {
                var advertisementList = data.advertisementList;
                var tempHtml = '';
                var tempUlHtml = '';
                $.each(advertisementList, function (index, item) {
                    // <a href="#"><img src="http://www.jq22.com/img/cs/500x300a.png"></a>
                    tempHtml += '<a href=" '
                        + item.advertisementHref
                        + '" target="_blank">'
                        + '<img src="'
                        + item.advertisementPhoto
                        + '">'
                        + '</a>';
                    tempUlHtml += '<li></li>';
                });
                $('.lunbo').html(tempHtml);
                size = $('.lunbo a').length;
                images = $('.lunbo a');
                // $('#lunbo-ul').html(tempUlHtml);
            } else {
                alert("获取广告列表失败," + data.errMsg);
            }
        })
    }

});