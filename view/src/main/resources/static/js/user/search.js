$(function () {

    var lableUrl = 'http://media.com/article/ugall';

    function getLableList() {
        $.ajax({
            url: lableUrl,
            type: 'POST',
            data:{},
            dataType:'JSON',
            success: function (data) {
                if (data.success) {
                    initLable1List(data.articleLableList);
                    initLable2List(data.articleLableList);
                } else {
                    alert('获取话题列表失败：' + data.errMsg);
                }
            }
        });
    }

    getLableList();

    initLable1List = function (list) {
        var tempHtml = '';
        for (var i = 0; i < list.length; i++) {
            var t1 = list[i];
            var t2 = list[i + 1];
            tempHtml += '<div class="row">' +
                '<div class="col-50"><a  class="pull-left user-info-a">' + (i + 1) +'.'+ t1.articleLableName + '</a></div>\n' +
                ' <div class="col-50"><a  class="pull-left user-info-a">' + (i + 1)+'.' + t2.articleLableName + '</a></div>\n' +
                '        </div>';
            i++;
        }
        $('#lable1_content').html(tempHtml);
    }

    initLable2List = function (list) {
        var tempHtml = '';
        for (var i = 0; i < list.length; i++) {
            var t1 = list[i];
            var t2 = list[i + 1];
            tempHtml += '<div class="row">' +
                '<div class="col-50"><a  class="pull-left user-info-a">' + (i + 1) +'.'+ t1.articleLableName + '</a></div>\n' +
                ' <div class="col-50"><a  class="pull-left user-info-a">' + (i + 1)+'.' + t2.articleLableName + '</a></div>\n' +
                '        </div>';
            i++;
        }
        $('#lable2_content').html(tempHtml);
    }

});