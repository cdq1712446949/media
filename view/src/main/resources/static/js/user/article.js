$(function () {

    //获取所有二级文章类型地址
    var twoLevelUrl = "/article/aflat";
    var fistName = '生活';
    var firstChooser = '';
    var allPicker;
    $(document).on("pageInit", function () {
        var fistLevelList;
        var twoList;

        $.ajaxSettings.async = false;
        $.getJSON(twoLevelUrl + '?parentId=1', function (data) {
            twoList = getTypeName(data.firstLevelList);
        });
        $.ajaxSettings.async = true;
        $.ajax({
            url: twoLevelUrl,
            type: "GET",
            data: {},
            dataType: "JSON",
            async: true,
            success: function (data) {
                if (data.success) {
                    fistLevelList = getTypeName(data.firstLevelList);
                    sessionStorage.setItem("first_level_list", JSON.stringify(data.firstLevelList));
                    $("#article_type").picker({
                        toolbarTemplate: '<header class="bar bar-nav">\
      <button class="button button-link pull-right close-picker" id="type_sure" onclick="type_sure()">确定</button>\
      <h1 class="title">选择文章类型</h1>\
      </header>',
                        cols: [
                            {
                                textAlign: 'center',
                                values: fistLevelList
                            },
                            {
                                textAlign: 'center',
                                values: twoList
                            }
                        ],
                        rotateEffect: true,
                        onOpen: function (picker) {
                            allPicker = picker;
                        }
                    });
                } else {
                    $.alert(data.errMsg);
                }
            }
        });

    });

    $('#article_type').change(function () {
        var parentTypeId;
        var localFirstTypeList = JSON.parse(sessionStorage.getItem("first_level_list"));
        var name1 = this.value.split(" ")[0];
        for (var i = 0; i < localFirstTypeList.length; i++) {
            var tempArticleType = localFirstTypeList[i];
            if (tempArticleType.articleTypeName == name1) {
                parentTypeId = tempArticleType.articleTypeId;
            }
        }
        if (name1 != fistName) {
            $.ajax({
                url: twoLevelUrl,
                type: 'GET',
                dataType: 'JSON',
                data: {
                    parentId: parentTypeId
                },
                success: function (data) {
                    var templist = getTypeName(data.firstLevelList);
                    allPicker.cols[1].replaceValues(templist);
                    fistName = name1;
                }
            });
        }

    });


    //文章类型选择器点击确定后发生改变是触发的逻辑
    type_sure = function () {

    };

    function getTypeName(typeList) {
        var names = new Array();
        for (var i = 0; i < typeList.length; i++) {
            var tempType = typeList[i];
            names[i] = tempType.articleTypeName;
        }
        return names;
    }

    $.init();


});



