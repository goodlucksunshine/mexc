/*获取cookie*/
function getcookie(c_name) {
    if (document.cookie.length > 0) {
        var c_start = document.cookie.indexOf(c_name + "=");//这里因为传进来的的参数就是带引号的字符串，所以c_name可以不用加引号
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            var c_end = document.cookie.indexOf(";", c_start);//当indexOf()带2个参数时，第二个代表其实位置，参数是数字，这个数字可以加引号也可以不加（最好还是别加吧）
            if (c_end == -1) c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
}

//设置cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

//清除cookie
function clearCookie(name) {
    setCookie(name, "", -1);
}

/** 初始化国际化 */
var lang = getcookie("lang");

if(lang == undefined) {
    var browserLang = jQuery.i18n.browserLang();
    if(browserLang == 'zh-CN') {
        lang = "zh_CN";
    }else if(browserLang == 'en-US'){
        lang = "en_US";
    }else {
        lang = "zh_CN";
    }
}

/!*国际化*!/;
$.i18n.properties( {
    name : 'messages', // Resource name
    path : 'static/js/i18n/', //Resource path
    cache : true,
    mode : 'map',
    encoding: 'UTF-8',
    language : lang,
});