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
    <title><spring:message code="loginIn"/></title>
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
                    <div class="title"><span><spring:message code="loginIn"/></span></div>
                </div>
                <div class="alert-box-con">
                    <ul>
                        <li>
                            <input type="text" placeholder="<spring:message code="accountInputTips"/>" name="loginAccount" class="login-input"/>
                            <div class="error-box"><span class="error-tip"></span></div>
                        </li>
                        <li>
                            <input type="password" placeholder="<spring:message code="passwordInputTips"/>"  name="loginPassword"
                                   class="login-input"/>
                            <div class="error-box"><span class="error-tip"></span></div>
                        </li>
                        <li>
                            <input type="text" placeholder="<spring:message code="captchaCodeInputTips"/>" name="loginCaptchaCode" class="login-input code-input"/>
                            <img src="code.do" alt="点击更换" class="code-img">
                            <div class="error-box"><span class="error-tip hide"></span></div>
                        </li>
                    </ul>
                </div>
                <div class="alert-box-bottom">
                    <a href="javascript:void(0);" class="btn-submit login" id="login"><spring:message code="loginIn"/></a>
                </div>
                <div class="login-info">
                    <a href="findPasswordPage"><spring:message code="forgetPassword"/></a><span>|</span><a href="to_reg"><spring:message code="registerName"/></a>
                </div>
            </div>
        </div>
    </div>
</form>
<jsp:include page="common/footer.jsp"/>
</body>
<script>
 /*   layui.config({base: 'static/js/'}).extend({
        jquery_cookie: 'jquery.cookie',
        jquery_validate: 'jquery.validate.min',
        // 默认寻找base也就是js/index.js模块导入
    });*/
    layui.use(['layer', 'jquery', "jquery_cookie", 'jquery_validate'], function () {
        var layer = layui.layer
            , $ = layui.$;
        $(".login").click(function () {
            var account = $("input[name='loginAccount']");
            var password = $("input[name='loginPassword']");
            var captchaCode = $("input[name='loginCaptchaCode']");
            if (!account.val()) {
                account.addClass("error-input");
                account.parent().find(".error-tip").text($.i18n.prop('accountInputTips'));
                return;
            } else {
                account.removeClass("error-input");
                account.parent().find(".error-tip").text("");
            }
            if (!password.val()) {
                password.addClass("error-input");
                password.parent().find(".error-tip").text($.i18n.prop('passwordInputTips'));
                return;
            } else {
                password.removeClass("error-input");
                password.parent().find(".error-tip").text("");
            }
            if (!captchaCode.val()) {
                captchaCode.addClass("error-input");
                captchaCode.parent().find(".error-tip").text($.i18n.prop('captchaCodeInputTips'));
                return;
            } else {
                captchaCode.removeClass("error-input");
                captchaCode.parent().find(".error-tip").text("");
            }
            $.ajax({
                url: basePath + "login",
                data: {account: account.val(), password: hex_md5(password.val()),captchaCode: captchaCode.val()},
                cache: false,
                dataType: 'json',
                type: "POST",
                success: function (data) {
                    if (data.code != 0) {
                        layer.msg(data.msg, {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                        changeCode();
                    } else {
                        if (data.status == 0) {
                            layer.confirm($.i18n.prop('noRegisterTips')+'？', {
                                btn: [$.i18n.prop('hasRegisterTips'), $.i18n.prop('resendEmailTips')], //按钮
                                offset: '40%'
                            }, function () {
                                window.location.reload();
                            }, function () {
                                $.ajax({
                                    url: basePath + "sendMail",
                                    data: {account: account.val()},
                                    cache: false,
                                    dataType: 'json',
                                    type: "POST",
                                    success: function (data) {
                                        if (data.code != 0) {
                                            layer.msg($.i18n.prop('sendFailTips'), {
                                                offset: '40%',
                                                shade: [0.8, '#393D49'],
                                                skin: 'layui-layer-molv',
                                                time: 5000
                                            });
                                        } else {
                                            layer.msg($.i18n.prop('finishSendEmailTips') + data.account + $.i18n.prop('toRegisterTips'), {
                                                offset: '40%',
                                                shade: [0.8, '#393D49'],
                                                skin: 'layui-layer-molv',
                                                time: 5000
                                            });
                                        }
                                    }
                                });
                            });
                        } else {
                            if (data.secondAuthType == 2) {
                                window.location.href = basePath + "login/check";
                            } else {
                                window.location.href = basePath + "index";
                            }
                        }
                    }
                },
                error: function () {
                    $("#login-box").addClass("hide");
                    layer.msg($.i18n.prop('loadFailTips'), {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                }
            });
        });
        $(".top-country").hover(function () {
            $(".country-con").removeClass("hide");
        }, function () {
            $(".country-con").addClass("hide");
        });
    });

 function changeCode() {
     $(".code-img").attr("src","code.do?t="+ genTimestamp());
 }

 function genTimestamp() {
     var time = new Date();
     return time.getTime();
 }
</script>
</html>