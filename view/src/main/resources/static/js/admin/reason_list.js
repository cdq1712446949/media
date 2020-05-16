$(function () {

    var reasonListUrl = 'http://media.com/other/admin/agrrl';
    var delReasonUrl = 'http://media.com/other/admin/adrrea';
    var addReasonUrl = 'http://media.com/other/admin/aarrea';
    var parentReasonUrl = 'http://media.com/other/ugprl';
    var ridArr = new Array();

    getReasonList = function () {
        $.ajax({
            url: reasonListUrl,
            type: 'POST',
            data: {
                token: sessionStorage.getItem('admin_user_token')
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result){
                    getReasonList();
                    return;
                }
                if (data.success) {
                    var reasonList = data.reasonList;
                    var tempHtml = '';
                    $.each(reasonList, function (index, item) {
                        var parentReason = item.parentReason;
                        var parentReasonName = '';
                        if (parentReason!=null){
                            parentReasonName = parentReason.reportReasonName;
                        }
                        tempHtml += '<tr>\n' +
                            '                                        <td>\n' +
                            '                                            <label class="lyear-checkbox checkbox-primary">\n' +
                            '   <input type="checkbox" name="ids[]" value="1"><span data-rid="' + item.reportReasonId + '" class="box_rid" onclick="addReasonId(this)"></span>\n' +
                            '                                            </label>\n' +
                            '                                        </td>\n' +
                            '                                        <td>' + item.reportReasonId + '</td>\n' +
                            '                                        <td>' + parentReasonName + '</td>\n' +
                            '                                        <td>' + item.reportReasonName + '</td>\n' +
                            '                                        <td>' + item.reportReasonCreateTime + '</td>\n' +
                            '                                        <td>\n' +
                            '                                            <div class="btn-group">\n' +
                            ' <a onclick="delReason(this)" data-rid="' + item.reportReasonId + '" class="btn btn-xs btn-default" title="删除"\n' +
                            '                                                   data-toggle="tooltip"><i class="mdi mdi-window-close"></i></a>\n' +
                            '  </div>\n' + ' </td>\n' + '</tr>';
                    });
                    $('#tbody_reason').html(tempHtml);
                } else {
                }
            }
        })
    };

    delReason = function(that){
        var rid = that.dataset.rid;
        $.ajax({
            url:delReasonUrl,
            type:'POST',
            data:{
                reasonId:rid,
                token:sessionStorage.getItem('admin_user_token')
            },
            dataType:'JSON',
            success:function (data) {
                var result = checkData(data);
                if (result){
                    delReason(that);
                    return;
                }
                if (data.success){
                    getReasonList();
                    lightyear.notify('删除成功', 'success', 100);
                }else{
                    lightyear.notify('删除失败'+data.errMsg, 'danger', 100);
                }
            }
        })
    };

    addReasonId = function (that) {
        var rid = that.dataset.rid;
        var index = ridArr.indexOf(rid);
        if (index > -1) {
            ridArr.splice(index, 1);
        } else {
            ridArr.push(rid);
        }
    };

    allSelect = function () {
        $.map($('.box_rid'), function (that, index) {
            that.click();
        });
    };

    getAllParentList = function(){
        $.ajax({
            url:parentReasonUrl,
            type:'POST',
            data:{
                token:sessionStorage.getItem('admin_user_token')
            },
            dataType:'JSON',
            success:function (data) {
                var result = checkData(data);
                if (result){
                    getAllParentList();
                    return;
                }
                if (data.success){
                    sessionStorage.setItem('parent_reason_list',JSON.stringify(data.reasonList));
                }
            }
        });
    };

    getAllParentList();

    getReasonList();

    addReason = function () {
        var parentList = JSON.parse(sessionStorage.getItem('parent_reason_list'));
        var html = '<form action="" class="formName">' +
            '<div class="form-group">' +
            '<label>请输入类型属性</label>'+
            ' <select name="type"  class="form-control" id="type">\n' ;
        $.each(parentList,function (index,item) {
            html+= '<option value="'+item.reportReasonId+'">'+item.reportReasonName+'</option>\n' ;
        });
        html+= '                      </select>' +
            '<input id="type_name_in" type="text" placeholder="名称" class="name form-control" required />' +
            '</div>' + '</form>';
        $.confirm({
            title: '提示',
            content: html,
            buttons: {
                formSubmit: {
                    text: '提交',
                    btnClass: 'btn-blue',
                    action: function () {
                        var rid = this.$content.find('.form-control').val();
                        var name = this.$content.find('.name').val();
                        if(!name){
                            $.alert('请您输入名称');
                            return false;
                        }
                        sendReason(rid,name);
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

    sendReason = function (reasonId,reasonName) {
        $.ajax({
            url:addReasonUrl,
            type:'POST',
            data:{
                parentId:reasonId,
                reasonName:reasonName,
                token:sessionStorage.getItem('admin_user_token')
            },
            dataType:'JSON',
            success:function (data) {
                var result = checkData(data);
                if (result){
                    sendReason(reasonId,reasonName);
                    return;
                }
                if (data.success){
                    getReasonList();
                    $.alert('添加成功')
                } else {
                    $.alert('添加失败:'+data.errMsg);
                }
            }
        });
    }

});
