$(function () {

    var articleId = getQueryString('articleId');

    $(document).on("pageInit", function () {
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

});