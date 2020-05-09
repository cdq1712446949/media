$(function () {

    //查询userInfo地址
    var userInfoUrl = 'http://media.com/user/ugui';
    //修改userInfo地址
    var editUserInfoUrl = 'http://media.com/user/ueui';
    //获取user表信息地址
    var infoUrl = 'http://media.com/user/ufuinfo';

    var artiNum = 0;
    var funsNum = 0;

    //根据sessionStorge中的存储信息判断用户是否登录
    var userInfo = JSON.parse(sessionStorage.getItem("media_login_info"));
    if (userInfo == null) {
        $('#login-yes').hide();
        $('#login-no').show();
    } else {
        $('#login-no').hide();
        $('#login-yes').show();
    }

    chooseFile = function () {
        document.getElementById("head_change").click();
    };

    editNickName = function () {
        $.prompt('新的昵称', function (value) {
            var userInfoStr = {
                nickName: value
            };
            ajaxUserInfo(userInfoStr)
        });
    };

    editDesc = function () {
        $.prompt('简介', function (value) {
            var userInfoStr = {
                userDesc: value
            }
            ajaxUserInfo(userInfoStr)
        });
    };

    editSex = function () {
        var buttons1 = [
            {
                text: '请选择',
                label: true
            }, {
                text: '男',
                bold: true,
                onClick: function () {
                    var userInfoStr = {
                        userSex: '男'
                    };
                    ajaxUserInfo(userInfoStr)
                }
            },
            {
                text: '女',
                bold: true,
                onClick: function () {
                    var userInfoStr = {
                        userSex: '女'
                    };
                    ajaxUserInfo(userInfoStr)
                }
            }

        ];
        var buttons2 = [
            {
                text: '取消',
                bg: 'danger'
            }
        ];
        var groups = [buttons1, buttons2];
        $.actions(groups);
    };

    ajaxUserInfo = function (userInfoStr) {
        $.ajax({
            url: editUserInfoUrl,
            type: 'POST',
            data: {
                token: sessionStorage.getItem('media_token'),
                userStr: JSON.stringify(userInfoStr)
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    ajaxUserInfo(userInfoStr);
                    return;
                }
                if (data.success) {
                    getInfo();
                } else {
                    $.toast('修改信息失败：' + data.errMsg);
                }
            }
        });
    };

    getInfo = function () {
        $.ajax({
            url: infoUrl,
            type: 'POST',
            data: {
                token: sessionStorage.getItem('media_token')
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    getInfo();
                    return;
                }
                if (data.success) {
                    sessionStorage.setItem("media_login_info", JSON.stringify(data.userInfo));
                    userInfo = JSON.parse(sessionStorage.getItem("media_login_info"));
                } else {
                    $.toast('获取用户信息失败：' + data.errMsg);
                }
            }
        });
    };

    //获取用户信息（文章数量，粉丝数量）
    getUserInfo = function () {
        var userId = userInfo.userId;
        $.ajax({
            url: userInfoUrl,
            type: 'POST',
            data: {
                userId: userId,
                token: sessionStorage.getItem('media_token')
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    getUserInfo();
                    return;
                }
                if (data.success) {
                    var userInfo1 = data.userInfo;
                    artiNum = userInfo1.articleNum;
                    funsNum = userInfo1.funsNum;
                    var user_info = new Vue({
                        el: '#user_info',
                        data: {
                            artiNum: artiNum,
                            funsNum: funsNum
                        }
                    });
                } else {
                    $.toast('获取userInfo失败：' + data.errMsg);
                }
            }
        });
    };

    getUserInfo();

    initUserInfo = function () {
        $('#head_photo').attr('src', 'http://media.com/image/images/' + userInfo.userHeadPhoto);
        var userLoginInfo = new Vue({
            el: '#userLoginInfo',
            data: {
                nickName: userInfo.nickName,
                userDesc: userInfo.userDesc,
                userSex: userInfo.userSex
            },
            methods:{
                nameAction:function () {
                    var that = this;
                    $.prompt('新的昵称', function (value) {
                        var userInfoStr = {
                            nickName: value
                        };
                        ajaxUserInfo(userInfoStr);
                        that.nickName=userInfoStr.nickName;
                    });

                },
                descAction:function () {
                    var that = this;
                    $.prompt('简介', function (value) {
                        var userInfoStr = {
                            userDesc: value
                        };
                        ajaxUserInfo(userInfoStr);
                        that.userDesc=userInfoStr.userDesc;
                    });

                },
                sexAction:function(){
                    var that = this;
                    var buttons1 = [
                        {
                            text: '请选择',
                            label: true
                        }, {
                            text: '男',
                            bold: true,
                            onClick: function () {
                                var userInfoStr = {
                                    userSex: '男'
                                };
                                ajaxUserInfo(userInfoStr);
                                that.userSex=userInfoStr.userSex;
                            }
                        },
                        {
                            text: '女',
                            bold: true,
                            onClick: function () {
                                var userInfoStr = {
                                    userSex: '女'
                                };
                                ajaxUserInfo(userInfoStr);
                                that.userSex=userInfoStr.userSex;
                            }
                        }

                    ];
                    var buttons2 = [
                        {
                            text: '取消',
                            bg: 'danger'
                        }
                    ];
                    var groups = [buttons1, buttons2];
                    $.actions(groups);

                }
            }
        });
    };

    initUserInfo();

});