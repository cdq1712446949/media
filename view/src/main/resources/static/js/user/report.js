$(function () {

    var addReportUrl = 'http://media.com/other/uaup';
    var parentReasonUrl = 'http://media.com/other/ugprl';
    var childReasonUrl = 'http://media.com/other/ugcrl';

    //TODO 参数校验
    var articleId = getQueryString('articleId');
    var commentId = getQueryString('commentId');
    var userId = getQueryString('userId');

    var parentId = 0;
    var childId = 0;

    getParentList = function () {
        $.ajax({
            url:parentReasonUrl,
            type:'POST',
            data:{
                token:sessionStorage.getItem('media_token')
            },
            dataType:'JSON',
            success:function (data) {
                data = JSON.parse(data);
                if (data.success){
                    initParentList(data.reasonList);
                }else{
                    $.toast('获取举报原因父类型失败：'+data.errMsg);
                }
            }
        });
    };

    getParentList();

    getChildList = function (pid) {
        $.ajax({
            url:childReasonUrl,
            type:'POST',
            data:{
                token:sessionStorage.getItem('media_token'),
                parentId:pid
            },
            dataType:'JSON',
            success:function (data) {
                data = JSON.parse(data);
                if (data.success){
                    initChildList(data.reasonList);
                }else{
                    $.toast('获取举报原因子类型失败：'+data.errMsg);
                }
            }
        });
    };

    getChildList(1);

    initParentList = function (list) {
        var html = '';
        $.each(list,function (index,item) {
            html += '<option data-value="'
                + item.reportReasonId + '">'
                + item.reportReasonName + '</option>';
        });
        $('#report_reson').html(html);
    };

    initChildList = function (list) {
        var html = '';
        $.each(list,function (index,item) {
            html += '<option data-value="'
                + item.reportReasonId + '">'
                + item.reportReasonName + '</option>';
        });
        $('#two-type').html(html);
    };

    $('#report_reson').change(function () {
        var pid = $('#report_reson').find('option').not(
            function () {
                return !this.selected;
            }).data('value');
        getChildList(pid);
    });

    //添加图片输入框
    $('.detail-img-div').on('change', '.detail-img:last-child', function () {
        $('#li_vido').hide();
        if ($('.detail-img').length < 3) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    $('#submit').click(function () {
        var formData = new FormData();
        var userReport = {};
        userReport.userReportContent = $('#report_content').val();
        $('.detail-img').map(function (index, item) {
            if (index > 2) {
                $.toast('图片数量不能超过三张,只会选取前三张上传');
                return;
            }
            if ($('.detail-img')[index].files.length > 0) {
                formData.append('reportImg' + index,
                    $('.detail-img')[index].files[0]);
            }
        });
        var reportReason = {};
        reportReason.reportReasonId=$('#two-type').find('option').not(
            function () {
                return !this.selected;
            }).data('value');
        userReport.reportReason=reportReason;
        if (commentId){
            var comment = {
                userCommentId:commentId
            };
            userReport.userComment=comment;
        }
        if (articleId){
            var article={
                articleId:articleId
            };
            userReport.article=article;
        }
        var reportedUser = {
            userId:userId
        };
        userReport.reprotedUser=reportedUser;
        formData.append('reportStr', JSON.stringify(userReport));
        addReport(formData);
    });

    addReport = function (formData) {
        $.ajax({
            url: addReportUrl+'?token='+sessionStorage.getItem('media_token'),
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                var result = checkData(data);
                if (result){
                    addReport(formData)
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
    }

});