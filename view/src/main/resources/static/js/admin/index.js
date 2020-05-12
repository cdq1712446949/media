$(function () {

    var logoutUrl = 'http://media.com/user/admin/logout';
    var indexNumUrl = 'http://media.com/article/admin/agin';
    var i, j;

    var articleNum = 0;
    var userNum = 0;
    var imageNum = 0;
    var reportNum = 0;

    /**
     * 检查sessionStorge中是否有用户信息
     */
    initInfo = function () {
        var info = sessionStorage.getItem('admin_user_info');
        if (info != null && info != '') {
            var userJson = JSON.parse(sessionStorage.getItem("admin_user_info"));
            //右上角展示用户信息
            var tempHtml = ' <a href="javascript:void(0)" data-toggle="dropdown">\n' +
                '                                <img class="img-avatar img-avatar-48 m-r-10"\n' +
                '                                     src="'
                + 'http://media.com/image/images/' + userJson.userHeadPhoto
                + '" alt="' + userJson.nickName
                + '"/>\n' +
                '                                <span>'
                + userJson.nickName
                + ' <span class="caret"></span></span>\n' +
                '                            </a>\n' +
                '                            <ul class="dropdown-menu dropdown-menu-right">\n' +
                '                                <li><a onclick="logout()"><i class="mdi mdi-logout-variant"></i> 退出登录</a>\n' +
                '                                </li>\n' +
                '                            </ul>';
            $('#info').html(tempHtml);
        } else {
            window.location.href = 'http://media.com/media/admin/login';
        }
    };
    initInfo();
    /**
     * 检查sessionStorge中是否有用户信息
     */
    initInfo = function () {
        var info = sessionStorage.getItem('admin_user_info');
        if (info != null && info != '') {
            var userJson = JSON.parse(sessionStorage.getItem("admin_user_info"));
            //右上角展示用户信息
            var tempHtml = ' <a href="javascript:void(0)" data-toggle="dropdown">\n' +
                '   <img class="img-avatar img-avatar-48 m-r-10"\n' + ' src="'
                + 'http://media.com/image/images/' + userJson.userHeadPhoto
                + '" alt="' + userJson.nickName
                + '"/>\n' + ' <span>' + userJson.nickName
                + ' <span class="caret"></span></span>\n' + ' </a>\n' +
                '  <ul class="dropdown-menu dropdown-menu-right">\n' +
                '  <li><a onclick="logout()"><i class="mdi mdi-logout-variant"></i> 退出登录</a>\n' +
                '   </li>\n' + '</ul>';
            $('#info').html(tempHtml);
        } else {
            window.location.href = 'http://media.com/media/admin/login';
        }
    };
    initInfo();

    getIndexNum = function () {
        $.ajax({
            url: indexNumUrl,
            type: 'POST',
            data: {
                token: sessionStorage.getItem('admin_user_token')
            },
            dataType:'JSON',
            success:function (data) {
                var result = checkData(data);
                if (result){
                    getIndexNum();
                    return;
                }
                if (data.success){
                    var indexNumber = data.indexNumber;
                    var indexNum = new Vue({
                        el: '#indexNum',
                        data: {
                            articleNum: indexNumber.newArticleNumber,
                            userNum: indexNumber.newUserNumber,
                            imageNum: indexNumber.newImageNumber,
                            reportNum: indexNumber.newReportNumber
                        },
                        methods: {}
                    });
                }else{

                }
            }
        });
    };

    getIndexNum();

  initIndexNum = function(){
      var indexNum = new Vue({
          el: '#indexNum',
          data: {
              articleNum: articleNum,
              userNum: userNum,
              imageNum: imageNum,
              reportNum: reportNum
          },
          methods: {}
      });
  };

    loadInner = function (sId) {
        var sId = window.location.hash;
        var pathn;
        //取消高亮
        var temp = '#' + i + '-' + j;
        switch (temp) {
            case "#1-1":
                var clazz1 = $('#1-1').attr('class', '');
                break;
            case "#1-2":
                var clazz1 = $('#1-2').attr('class', '');
                break;
            case "#2-1":
                var clazz1 = $('#2-1').attr('class', '');
                break;
            case "#2-2":
                var clazz1 = $('#2-2').attr('class', '');
                break;
            case "#2-3":
                var clazz1 = $('#2-3').attr('class', '');
                break;
            case "#3-1":
                var clazz1 = $('#3-1').attr('class', '');
                break;
            case "#3-2":
                var clazz1 = $('#3-2').attr('class', '');
                break;
            case "#4-1":
                var clazz1 = $('#4-1').attr('class', '');
                break;
            case "#4-2":
                var clazz1 = $('#4-2').attr('class', '');
                break;
            case "#4-3":
                var clazz1 = $('#4-3').attr('class', '');
                break;
            case "#4-4":
                var clazz1 = $('#4-4').attr('class', '');
                break;
        }
        switch (sId) {
            case "#1-1":
                pathn = "http://media.com/media/admin/articleList";
                var clazz1 = $('#1-1').attr('class');
                clazz1 += ' active';
                $('#1-1').attr('class', clazz1);
                i = 1;
                j = 1;
                break;
            case "#1-2":
                pathn = "http://media.com/media/admin/artiTypeList";
                var clazz1 = $('#1-2').attr('class');
                clazz1 += ' active';
                $('#1-2').attr('class', clazz1);
                i = 1;
                j = 2;
                break;
            case "#2-1":
                pathn = "http://media.com/media/admin/userData";
                var clazz1 = $('#2-1').attr('class');
                clazz1 += ' active';
                $('#2-1').attr('class', clazz1);
                i = 2;
                j = 1;
                break;
            case "#2-2":
                pathn = "http://media.com/media/admin/articleData";
                var clazz1 = $('#2-2').attr('class');
                clazz1 += ' active';
                $('#2-2').attr('class', clazz1);
                i = 2;
                j = 2;
                break;
            case "#2-3":
                pathn = "http://media.com/media/admin/imgData";
                var clazz1 = $('#2-3').attr('class');
                clazz1 += ' active';
                $('#2-3').attr('class', clazz1);
                i = 2;
                j = 3;
                break;
            case "#3-1":
                pathn = "http://media.com/media/admin/reportList";
                var clazz1 = $('#3-1').attr('class');
                clazz1 += ' active';
                $('#3-1').attr('class', clazz1);
                i = 3;
                j = 1;
                break;
            case "#3-2":
                pathn = "http://media.com/media/admin/reasonList";
                var clazz1 = $('#3-2').attr('class');
                clazz1 += ' active';
                $('#3-2').attr('class', clazz1);
                i = 3;
                j = 2;
                break;
            case "#4-1":
                pathn = "http://media.com/media/admin/adverList";
                var clazz1 = $('#4-1').attr('class');
                clazz1 += ' active';
                $('#4-1').attr('class', clazz1);
                i = 4;
                j = 1;
                break;
            case "#4-2":
                pathn = "http://media.com/media/admin/noticeList";
                var clazz1 = $('#4-2').attr('class');
                clazz1 += ' active';
                $('#4-2').attr('class', clazz1);
                i = 4;
                j = 2;
                break;
            case "#4-3":
                pathn = "http://media.com/media/admin/addAdver";
                var clazz1 = $('#4-3').attr('class');
                clazz1 += ' active';
                $('#4-3').attr('class', clazz1);
                j = 4;
                j = 3;
                break;
            case "#4-4":
                pathn = "http://media.com/media/admin/addNotice";
                var clazz1 = $('#4-4').attr('class');
                clazz1 += ' active';
                $('#4-4').attr('class', clazz1);
                i = 4;
                i = 4
                break;
        }
        $(".lyear-layout-content").load(pathn); //加载相对应的内容
        //$(".userMenu li").eq(i).addClass("current").siblings().removeClass("current"); //当前列表高亮
    };

    if (window.location.hash != null && window.location.hash != '') {
        var sId = window.location.hash;
        loadInner(sId);
    }


    $(".li_click").click(function () {
        var that = this;
        var sId = this.getAttribute('id'); //获取data-id的值
        if (sId != null && sId != '') {
            window.location.hash = sId; //设置锚点
            loadInner(sId);
        }
    });


    /**
     * 退出登录
     */
    logout = function () {
        $.ajax({
            url: logoutUrl,
            type: "POST",
            data: {
                userId: JSON.parse(sessionStorage.getItem("admin_user_info")).userId,
                token: sessionStorage.getItem('admin_user_token')
            },
            dataType: "JSON",
            success: function (data) {
                if (data.success) {
                    sessionStorage.removeItem('admin_user_info');
                    sessionStorage.removeItem('admin_user_token');
                    alert("已退出");
                    window.location.reload();
                } else {
                    sessionStorage.removeItem('admin_user_info');
                    sessionStorage.removeItem('admin_user_token');
                    alert('已退出');
                    window.location.reload();
                }
            }
        });
    };


});