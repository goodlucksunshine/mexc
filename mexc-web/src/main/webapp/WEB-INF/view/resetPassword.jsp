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
    <title><spring:message code="forgetPassword"/></title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.js"></script>
    <script type="text/javascript" src="static/js/ajax_loading.js"></script>
    <script type="text/javascript" src="static/js/md5.js"></script>
</head>
<body style="height: 100%">
<jsp:include page="common/head.jsp"/>
<form>
    <div class="inner-container login-page">
        <!-- inner[[[ -->
        <div class="layout login-page-bd">
            <div class="alert-login">
                <div class="alert-box-head">
                    <i class="logo-login"></i>
                    <div class="title"><span><spring:message code="findPassword"/></span></div>
                </div>
                <div class="alert-box-con">
                    <input type="hidden" name="findAccount" value="${account}"/>
                    <ul>
                        <li>
                            <input type="password" name="findPassword" placeholder="<spring:message code="passwordInputTips"/>" class="login-input"/>
                            <div class="error-box"><span class="error-tip"></span></div>
                        </li>
                        <li>
                            <input type="text" name="findAuthCode" placeholder="<spring:message code="captchaCodeInputTips"/>" class="login-input"/>
                            <div class="error-box"><span class="error-tip"></span></div>
                        </li>
                    </ul>
                </div>
                <div class="alert-box-bottom">
                    <a class="btn-submit findPassword"><spring:message code="ensure"/></a>
                </div>
            </div>
        </div>
    </div>
</form>
<jsp:include page="common/footer.jsp"/>
</body>
<script>
    layui.use(['layer', 'jquery', "jquery_cookie", 'jquery_validate'], function () {
        var layer = layui.layer
            , $ = layui.$;
        $(".findPassword").click(function () {
            var findAccount = $("input[name='findAccount']");
            var findPassword = $("input[name='findPassword']");
            var findAuthCode = $("input[name='findAuthCode']");
            if (!findPassword.val()) {
                //findPassword.addClass("error-input");
                findPassword.parent().find(".error-tip").text($.i18n.prop('passwordInputTips'));
                return;
            } else {
                if(!checkPwd(findPassword.val())) {
                    findPassword.parent().find(".error-tip").text($.i18n.prop('passwordInputLengthTips'));
                }else {
                    findPassword.parent().find(".error-tip").text("");
                }
            }
            if (!findAuthCode.val()) {
                //findAuthCode.addClass("error-input");
                findAuthCode.parent().find(".error-tip").text($.i18n.prop('captchaCodeInputTips'));
                return;
            } else {
                //findAuthCode.removeClass("error-input");
                findAuthCode.parent().find(".error-tip").text("");
            }

            $.ajax({
                url: basePath + "resetPassword",
                data: {account: findAccount.val(),newPassword:hex_md5(findPassword.val()),authCode:findAuthCode.val()},
                cache: false,
                dataType: 'json',
                type: "POST",
                success: function (data) {
                    if (data.code != 0) {
                        layer.msg(data.msg, {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    }else {
                        layer.msg(data.msg, {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                        location.href = "login";
                    }
                },
                error: function () {
                    layer.msg($.i18n.prop('loadFailTips'), {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                }
            });

            function checkPwd(value) {
                var checkPwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
                if(checkPwdReg.test(value)){
                    return true;
                }
                return false;
            }
        });
    });
</script>
</html>