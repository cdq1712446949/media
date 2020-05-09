$(function () {

    //获取账户设置地址
    var personInfoUrl = 'http://media.com/user/perInfo/ugpi';
    //修改账户设置地址
    var editPersonInfoUrl = 'http://media.com/user/perInfo/udpi';

    var secretSM = '0';
    var isEdit = true;

    $('#div_sm').click(function () {
        if (isEdit) {
            if (secretSM == '0') {
                secretSM = '-1';
            } else {
                secretSM = '0';
            }
            var personInfoStr = {
                secretStatus: secretSM
            };
            editPersonInfo(personInfoStr);
        }
        isEdit = true;
    });

    editPersonInfo = function (personInfoStr) {
        $.ajax({
            url: editPersonInfoUrl,
            type: 'POST',
            data: {
                token: sessionStorage.getItem('media_token'),
                personInfoStr: JSON.stringify(personInfoStr)
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    editPersonInfo(personInfoStr);
                    return;
                }
                if (data.success) {
                    $.toast('修改私信设置成功');
                } else {
                    $.toast('修改私信设置失败：' + data.errMsg);
                    isEdit = false;
                    $('#div_sm').click();
                }
            }
        });
    };

    //渲染数据
    initPersonInfo = function (personInfo) {
        secretSM = personInfo.secretStatus;
        if (secretSM == '0') {
            isEdit = false;
            $('#div_sm').click();
        }
    };

    //获取账户设置信息
    getPersonInfo = function () {
        $.ajax({
            url: personInfoUrl,
            type: 'POST',
            data: {
                token: sessionStorage.getItem('media_token')
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    getPersonInfo();
                    return;
                }
                if (data.success) {
                    initPersonInfo(data.personInfo);
                }
            }
        });
    };

    getPersonInfo();

});