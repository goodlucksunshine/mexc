<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>首页</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.all.js"></script>
    <script type="text/javascript" src="static/js/md5.js"></script>
</head>
<body style="height: 100%">
<!-- header[ -->
<jsp:include page="common/head.jsp"/>
<!-- header] -->
<form>
    <div class="inner-container login-page">
        <!-- inner[[[ -->
        <div class="layout login-page-bd">
            <div class="alert-login">
                <div class="alert-box-head">
                    <i class="logo-login"></i>
                    <div class="title"><span></span></div>
                </div>
                <div class="alert-box-con">
                    <ul>
                        <li>
                            <input type="text" placeholder="<spring:message code="googleAuthTips"/>" id="googlecode" name="googlecode"
                                   class="login-input"/>
                            <div class="error-box"><span class="error-tip"></span></div>
                        </li>
                    </ul>
                </div>
                <div class="alert-box-bottom">
                    <a href="javascript:void(0);" class="btn-submit" id="login"><spring:message code="loginIn"/></a>
                </div>
            </div>
        </div>
    </div>
</form>
<jsp:include page="common/footer.jsp"/>
</body>
<script>
    layui.config({base: 'static/js/'}).extend({
        jquery_cookie: 'jquery.cookie',
        jquery_validate: 'jquery.validate.min',
        // 默认寻找base也就是js/index.js模块导入
    });
    layui.use(['layer', 'jquery', "jquery_cookie", 'jquery_validate'], function () {
        var layer = layui.layer
            , $ = layui.$;
        $(".btn-submit").click(function () {
            var googleCode = $("#googlecode").val();
            $.ajax({
                url: basePath + "login/google_auth?code=" + googleCode,
                cache: false,
                dataType: 'json',
                type: "POST",
                success: function (data) {
                    if (data.code != 0) {
                        layer.alert($.i18n.prop('googleAuthFail'));

                    } else {
                        layer.msg($.i18n.prop('googleAuthSuccess'));
                        window.location.href = basePath + "index";
                    }
                }
            });
        });
    });
</script>
</html>