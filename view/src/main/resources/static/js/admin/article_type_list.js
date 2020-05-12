$(function () {

    var typeListUrl = 'http://media.com/article/admin/agat';
    var delTypesUrl = 'http://media.com/article/admin/adats';
    var addTypeUrl = 'http://media.com/article/admin/aaat';
    var tidArr = new Array();

    getTypeList = function () {
        var typeName= $('#type_name').val();
        $.ajax({
            url: typeListUrl,
            type: 'POST',
            data: {
                typeName: typeName,
                token: sessionStorage.getItem('admin_user_token')
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result){
                    getTypeList();
                    return;
                }
                if (data.success) {
                    var typeList = data.articleTypeList;
                    var tempHtml = '';
                    $.each(typeList, function (index, item) {
                        var parentType = item.parentArticleType;
                        var parentTypeName = '';
                        if (parentType!=null){
                            var parentTypeName = parentType.articleTypeName;
                        }
                        tempHtml += '<tr>\n' +
                            '                                        <td>\n' +
                            '                                            <label class="lyear-checkbox checkbox-primary">\n' +
                            '   <input type="checkbox" name="ids[]" value="1"><span data-tid="' + item.articleTypeId + '" class="box_tid" onclick="addTypeId(this)"></span>\n' +
                            '                                            </label>\n' +
                            '                                        </td>\n' +
                            '                                        <td>' + item.articleTypeId + '</td>\n' +
                            '                                        <td>' + item.articleTypeName + '</td>\n' +
                            '                                        <td>' + parentTypeName + '</td>\n' +
                            '                                        <td>' + item.priority + '</td>\n' +
                            '                                        <td>' + item.articleTypeCreateTime + '</td>\n' +
                            '                                        <td>\n' +
                            '                                            <div class="btn-group">\n' +
                            '                                                <a class="btn btn-xs btn-default" href="#!" title="编辑"\n' +
                            '                                                   data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>\n' +
                            '                                                <a class="btn btn-xs btn-default" href="#!" title="屏蔽"\n' +
                            '                                                   data-toggle="tooltip"><i class="mdi mdi-window-close"></i></a>\n' +
                            '  </div>\n' + ' </td>\n' + '</tr>';
                    });
                    $('#tbody_type').html(tempHtml);
                } else {
                }
            }
        })
    };

    addTypeId = function (that) {
        var tid = that.dataset.tid;
        var index = tidArr.indexOf(tid);
        if (index > -1) {
            tidArr.splice(index, 1);
        } else {
            tidArr.push(tid);
        }
    };

    allSelect = function () {
        $.map($('.box_tid'), function (that, index) {
            that.click();
        });
    };


    //批量删除文章
    delTypes = function () {
        $.ajax({
            url: delTypesUrl,
            type: 'POST',
            data: {
                typeIds: JSON.stringify(tidArr),
                token: sessionStorage.getItem('admin_user_token')
            },
            dataType: 'JSON',
            success: function (data) {
                var result = checkData(data);
                if (result) {
                    delTypesUrl();
                    return;
                }
                if (data.success) {
                    getTypeList()
                } else {
                    alert('删除失败：'+data.errMsg);
                }
            }
        });
    };

    getTypeList();

    addType = function () {
        $.confirm({
            title: '提示',
            content: '' +
            '<form action="" class="formName">' +
            '<div class="form-group">' +
            '<label>请输入类型属性</label>' +
            '<input id="parent_id" type="text" placeholder="父类型id" class="name form-control" required />' +
            '<input id="type_name_in" type="text" placeholder="名称" class="name form-control" required />' +
            '<input id="priority" type="text" placeholder="权重" class="name form-control" required />' +
            '</div>' +
            '</form>',
            buttons: {
                formSubmit: {
                    text: '提交',
                    btnClass: 'btn-blue',
                    action: function () {
                        var pid = this.$content.find('#parent_id').val();
                        var name = this.$content.find('#type_name_in').val();
                        var priority = this.$content.find('#priority').val();
                        if(!name){
                            $.alert('请您输入名称');
                            return false;
                        }
                        var pType = {
                            articleTypeId:pid
                        };
                        var type= {
                            articleTypeName:name,
                            parentArticleType:pType,
                            priority:priority
                        };
                        sendType(type);
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

    sendType = function (typeStr) {
        $.ajax({
            url:addTypeUrl,
            type:'POST',
            data:{
                typeStr:JSON.stringify(typeStr),
                token:sessionStorage.getItem('admin_user_token')
            },
            dataType:'JSON',
            success:function (data) {
                var result = checkData(data);
                if (result){
                    sendType(typeStr);
                    return;
                }
                if (data.success){
                    getTypeList();
                    $.alert('添加成功')
                } else {
                    $.alert('添加失败:'+data.errMsg);
                }
            }
        });
    }

});
