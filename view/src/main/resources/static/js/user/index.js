$(function () {
    var firstArticleTypeListUrl = '/article/firstarticletypelist';

    var mediaLoginInfo = 'media_login_info';

    function advert(config) {
    }

    // var viewId = window.location.hash;
    // if (viewId != null && viewId != ''){
    //     var eml = document.getElementById('ifra_content');
    //     eml.getAttribute('src','http://media.com/media/'+viewId.substring(1,viewId.length));
    // }

    //TODO 获取用户所有未读消息数量

    advert.prototype = {
        constructor: advert,
        adsBox: function () {
            var that = this;
            var boxWidth;
            // 与屏幕大小比较
            if (this.width >= screen.width) {
                boxWidth = "95%";
            } else {
                boxWidth = that.width;
            }
            var ad = document.createElement("div");
            ad.setAttribute("style", "z-index:1000000;position: fixed;top:0;left:0;");
            //广告主体内容
            var html1 = `<div class="cover" style="width: 100%;height: 100%;background: rgba(0,0,0,0.3);position: fixed;top: 0;left: 0;"></div>`
                + `<div id="insertBox" style="position: fixed; width: ` + boxWidth + `;height:auto;top: 50%;left: 50%;transform:translate(-50%,-50%);-webkit-transform:translate(-50%,-50%);-ms-transform:translate(-50%,-50%);-o-transform:translate(-50%,-50%);z-index:20;">`
                + `<a href="https://www.baidu.com" style="display:block;width:100%;height:auto"><img src="https://s3m.mediav.com/galileo/2a74d406010b239591bd7bd442422d3f.png" alt="图片" style="width:100%;height:auto;"></a>`
                + `<span id="insert_closeBtn" style="position: absolute;cursor:pointer;top: 10px;right: 10px;width: 32px;height: 32px;border-radius: 16px;background: rgba(0,0,0,0.5);color: #FFF;text-align: center;line-height: 26px;font-size:32px;font-family:'MingLiU';">&times;</span>`
                + `</div>`;
            ad.innerHTML = html1;
            document.body.appendChild(ad);
            var box = document.getElementById("insertBox");
            var closeBtn = document.getElementById("insert_closeBtn")
            //删除
            closeBtn.onclick = function () {
                document.body.removeChild(ad);
            }
        },
        // 插屏广告
        interstitialAd: function () {
            console.log(1);
            var that = this;
            var img = new Image();
            img.src = "https://s3m.mediav.com/galileo/2a74d406010b239591bd7bd442422d3f.png";
            if (img.complete) {
                that.width = img.width;
                that.height = img.height;
                that.adsBox();
            } else {
                // 判断图片加载完成时获取到图片的宽高
                img.onload = function () {
                    that.width = img.width;
                    that.height = img.height;
                    img.onload = null;
                    that.adsBox();
                };
            }
            ;
            return this;
        },
        // banner广告
        banner: function () {
            // 渲染页面
            var html = '<div style="cursor: pointer;z-index: 10000;width: 300px;height: 100px">'
                + '<a href="www.baidu.com" style="display: block;width:100%;height: auto">'
                + '<img src="https://p0.ssl.qhimg.com/t01cc456dbd8c4fdb8d.png" style="width: 300px;height: 100px;" alt="图片信息"></a></div>';
            document.writeln(html);
        }
    }

    if (sessionStorage.getItem("isShow") == null) {
        new advert().interstitialAd();
        sessionStorage.setItem("isShow", true);
    }


});
