$(function () {

    var activeName = 'article';

    jumpView =function (viewName) {
        if (sessionStorage.getItem('media_login_info')==null && viewName=='me'){
            window.location.href='http://media.com/media/login';
        }
        //修改页面
        var tempHtml = '<iframe src="http://media.com/media/'
            +viewName
            + '" style="width: 100%; height: 100%; border: none;"></iframe>';
        switch (activeName){
            case 'article':
                $('#article').removeClass('active');
                break;
            case 'video':
                $('#video').removeClass('active');
                break;
            case 'search':
                $('#search').removeClass('active');
                break;
            case 'message':
                $('#message').removeClass('active');
                break;
            case 'me':
                $('#me').removeClass('active');
                break;
        }
        switch (viewName){
            case 'article':
                $('#article').addClass('active');
                activeName="article";
                break;
            case 'video':
                $('#video').addClass('active');
                activeName="video";
                break;
            case 'search':
                $('#search').addClass('active');
                activeName="search";
                break;
            case 'message':
                $('#message').addClass('active');
                activeName="message";
                break;
            case 'me':
                $('#me').addClass('active');
                activeName="me";
                break;
        }
        sessionStorage.setItem('active_name',activeName);
        $('#content').html(tempHtml);
    };


    $('.tab-item').click(function () {
        var viewName = this.id;
        jumpView(viewName);
    });

});