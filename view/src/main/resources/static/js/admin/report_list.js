$(function () {

    var getReportUrl = 'http://media.com/other/admin/agupl';
    var addResultUrl = 'http://media.com/other/admin/aarr';
    var indexPage = 1;
    var totalPage = 1;
    var articleDetailUrl= 'http://media.com/media/artideti?articleId=';

    var isTrue = false;
    var reportId = '';
    var remark = '';

    var userStatus = 0;
    var userId = '';

    getReportList = function (isReCheck) {
        if (isReCheck) {
            rootPage();
        }
        $.ajax({
            url: getReportUrl,
            type: 'POST',
            data: {
                reportStr: '',
                token: sessionStorage.getItem('admin_user_token'),
                indexPage: indexPage
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    getReportList();
                    return;
                }
                if (data.success) {
                    totalPageSize = data.totalPage;
                    checkPageNum();
                    var reportList = data.reportList;
                    totalPage = data.totalPage;
                    initReportList(reportList);
                } else {
                    alert('获取举报列表失败:' + data.errMsg);
                }
            }
        });
    };

    checkPageNum = function () {
        if (indexPage == 1) {
            $('#up_index ').hide();
        } else {
            $('#up_index').show();
        }
        if (indexPage == totalPageSize) {
            $('#down_index').hide();
        } else {
            $('#down_index').show();
        }
    };

    rootPage = function (that) {
        $('#rtoOne').click();
        alert('sss')
    };


    var pageContent = new Vue({
        el: '#pageContent',
        data: {
            pageNumber: 1
        },
        methods: {
            toFirst: function () {
                this.pageNumber = 1;
                indexPage = 1;
                getReportList(false);
            },
            toBottom: function () {
                this.pageNumber = totalPageSize;
                indexPage = totalPageSize;
                getReportList(false);
            },
            toNext: function () {
                indexPage += 1;
                this.pageNumber = indexPage;
                getReportList(false);
            },
            toUp: function () {
                indexPage -= 1;
                this.pageNumber = indexPage;
                getReportList(false);
            },
            toOne: function () {
                this.pageNumber = 1;
                indexPage = 1;
            }
        }
    });

    initReportList = function (list) {
        var html = '';

        $.each(list, function (index, item) {
            var reporterUser = item.reportUser;
            var status = '未知';
            var color = 'saddlebrown';
            if (item.userReportStatus == 0) {
                status = '未处理';
                color = 'red';
            }
            if (item.userReportStatus == 1) {
                status = '已处理';
                color = 'green';
            }
            var reportedUser = item.reprotedUser;
            var userComment = item.userComment;
            var commentContent = '';
            if (userComment) {
                commentContent = userComment.userCommentContent;
            }
            var article = item.article;
            var articleId = '';
            if (article) {
                articleId = article.articleId;
            }
            var reason = item.reportReason;
            html += '<tr>\n' +
                '                        <td>\n' +
                '                          <label class="lyear-checkbox checkbox-primary">\n' +
                '                            <input type="checkbox" name="ids[]"><span></span>\n' +
                '                          </label>\n' +
                '                        </td>\n' +
                '                        <td>' + item.userReportId + '</td>\n' +
                '                        <td>' + reporterUser.nickName + '</td>\n' +
                '                        <td>' + reportedUser.nickName + '</td>\n' +
                '                        <td>' + reason.reportReasonName + '</td>\n' +
                '                        <td>' + item.userReportContent + '</td>\n' +
                '                        <td><a style="color:  ' + color + '">' + status + '</a></td>\n' +
                '                        <td><a target="_blank" ' +
                'href="'+articleDetailUrl+articleId+'&&userId='+reportedUser.userId+'">' + articleId + '</a></td>\n' +
                '                        <td>' + commentContent + '</td>\n' +
                '<script> var list'+index+' = '+JSON.stringify(item.photoList)+';</script>'+
                '                        <td><a onclick="showPhoto(list'+index+')">' + '查看图片' + '</a></td>\n' +
                '                        <td>\n' +
                '                          <div class="btn-group">\n' +
                '                            <a onclick="reportAction(this)"' +
                'data-reportid="'+item.userReportId+'" data-uid="'+reportedUser.userId+'" class="btn btn-xs btn-default" title="操作" data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>\n' +
                '                          </div>\n' +
                '                        </td>\n' +
                '                      </tr>';
        });
        $('#tbody_report').html(html);
    };

    reportAction = function(that){
        var reportId = that.dataset.reportid;
        console.log('reportId'+reportId);
        var uid = that.dataset.uid;
        $.confirm({
            title: '对话框',
            content: '情况是否属实',
            buttons: {
                confirm: {
                    text: '是',
                    action: function(){
                        addData(reportId,uid);
                        getReportList(false);
                    }
                },
                cancel: {
                    text: '否',
                    action: function(){
                        noResult(reportId);
                        getReportList(false)
                    }
                },
                somethingElse: {
                    text: '其他',
                    btnClass: 'btn-blue',
                    keys: ['enter', 'shift'],
                    action: function(){

                    }
                }
            }
        });
    };

    addData = function(reportId,uid){
        $.confirm({
            title: '数据',
            content: '' +
            '<form action="" class="formName">' +
            '<div class="form-group">' +
            ' <select name="type"  class="form-control" id="type">\n' +
            '                        <option value="-1">冻结账户</option>\n' +
            '                        <option value="0">正常</option>\n' +
            '                        <option value="1">禁止评论</option>\n' +
            '                        <option value="2">禁止发布文章</option>\n' +
            '                        <option value="3">屏蔽所有文章</option>\n' +
            '                      </select>' +
            '<input type="text" placeholder="备注" class="name form-control" required />' +
            '</div>' +
            '</form>',
            buttons: {
                formSubmit: {
                    text: '提交',
                    btnClass: 'btn-blue',
                    action: function () {
                        var remark = this.$content.find('.name').val();
                        var status = this.$content.find('.form-control').val();
                        yesResult(reportId,remark,status,uid)
                    }
                },
                cancel: {
                    text: '取消'
                },
            },
            onContentReady: function () {
                var jc = this;
                this.$content.find('form').on('submit', function (e) {
                    e.preventDefault();
                    jc.$$formSubmit.trigger('click');
                });
            }
        });
    };

    yesResult = function(reportId,remark,status,userId){
        $.ajax({
            url:addResultUrl,
            type:'POST',
            data:{
                isTrue:false,
                reportId:reportId,
                remark:remark,
                userStatus:status,
                userId:userId,
                token:sessionStorage.getItem('admin_user_token')
            },
            dataType:'JSON',
            success:function (data) {
                var result = checkData(data);
                if (result) {
                    noResult(reportId);
                    return;
                }
                if (data.success){
                    alert('处理成功')
                } else{
                    alert('处理失败：'+data.errMsg);
                }
            }
        });
    };
    noResult = function(reportId){
        $.ajax({
            url:addResultUrl,
            type:'POST',
            data:{
                isTrue:false,
                reportId:reportId,
                remark:'',
                token:sessionStorage.getItem('admin_user_token')
            },
            dataType:'JSON',
            success:function (data) {
                var result = checkData(data);
                if (result) {
                    noResult(reportId);
                    return;
                }
                if (data.success){
                    alert('处理成功')
                } else{
                    alert('处理失败：'+data.errMsg);
                }
            }
        });
    };

    getReportList();

    showPhoto = function (list) {
        // var listStr = that.dataset.list;
        if (list!=null&&list.length>0){
            var html = '<form action="" class="formName">' +
                '<div class="form-group">';
            $.each(list,function (index,item) {
                html+=
                    '<img src="http://media.com/image/images/'+item+'" style=" width: auto;  \n' +
                    '    height: auto;  \n' +
                    '    max-width: 100%;  \n' +
                    '    max-height: 100%;    ">';
            });
            html+= '</div> </form>';
            $.confirm({
                title: '提示',
                content:html,
                buttons: {
                    formSubmit: {

                    },
                    cancel: {
                        text: '取消'
                    },
                },
                onContentReady: function () {
                    var jc = this;
                    this.$content.find('form').on('submit', function (e) {
                        e.preventDefault();
                        jc.$$formSubmit.trigger('click');
                    });
                }
            });
        } else{
            alert('没有图片');
        }
    };

});