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
    <title><spring:message code="index"/></title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.js"></script>
    <script type="text/javascript" src="static/js/md5.js"></script>
</head>
<body style="height: 100%">
<!-- header[ -->
<jsp:include page="common/head.jsp">
    <jsp:param name="current" value="register"/>
</jsp:include>
<!-- header] -->
<div class="inner-container login-page">
    <!-- inner[[[ -->
    <div class="layout login-page-bd">
        <form id="register-form" name="reg-form">
            <a href="javascript:void(0);" class="i-alert-close"></a>
            <div class="alert-login">
                <div class="alert-box-head">
                    <i class="logo-login"></i>
                    <div class="title"><span><spring:message code="registerName"/></span></div>
                </div>
                <div class="alert-box-con">
                    <ul>
                        <li>
                            <input type="text" id="registerAccount" name="regAccount" placeholder="<spring:message code="accountInputTips"/>"
                                   class="login-input"/>
                            <div class="error-box"></div>
                        </li>
                        <li>
                            <input type="password" id="registerPassword" name="regPassword" placeholder="<spring:message code="passwordInputTips"/>"
                                   class="login-input"/>
                            <div class="error-box"></div>
                        </li>
                        <li>
                            <input type="password" id="registerPassword2" name="regPassword2" placeholder="<spring:message code="passwordInputAgainTips"/>"
                                   class="login-input"/>
                            <div class="error-box"></div>
                        </li>
                        <li>
                            <input type="text" id="registerCaptchaCode" name="regCaptchaCode" placeholder="<spring:message code="captchaCodeInputTips"/>" class="login-input code-input"/>
                            <img src="code.do" alt="" class="code-img">
                            <div class="error-box"><span class="error-tip registerCaptchaCodeTip"></span></div>
                        </li>
                        <input type="hidden" name="hexPassword" id="registerHexPassword"/>
                    </ul>
                    <label name="remember" class="in-check">
                        <input type="checkbox" checked="checked" class="check-box" name="remember" id="registerRemember">
                        <span><spring:message code="agreeServiceTermTips"/>&nbsp;&nbsp;<a href="serviceterm"
                                                    class="">&lt;&lt;<spring:message code="serviceTerm"/>&gt;&gt;</a></span>
                    </label>
                </div>
                <div class="alert-box-bottom">
                    <button href="javascript:void(0);" class="btn-submit register" type="submit"><spring:message code="registerName"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- footer[[ -->
<jsp:include page="common/footer.jsp"/>
<!-- footer]] -->
</body>
<script>
  /*  layui.config({base: 'static/js/'}).extend({
        jquery_cookie: 'jquery.cookie',
        jquery_validate: 'jquery.validate.min',
        // 默认寻找base也就是js/index.js模块导入
    });*/
    layui.use(['layer', 'jquery', "jquery_cookie", 'jquery_validate'], function () {
        var layer = layui.layer
            , $ = layui.$;
        $ = layui.jquery_cookie($);
        $ = layui.jquery_validate($);
        $("#register-form").validate({
            errorPlacement: function (error, element) {
                error.appendTo(element.next(".error-box"));
            },
            errorClass: "error-tip",
            rules: {
                regAccount: {
                    required: true,
                    email: true
                },
                regPassword: {
                    required: true,
                    checkPwd: true,
                    minlength: 8
                },
                regPassword2: {
                    required: true,
                    checkPwd: true,
                    equalTo: "#registerPassword"
                }
            },
            messages: {
                regAccount: {
                    required: $.i18n.prop('accountInputTips'),
                    email: $.i18n.prop('accountInputError'),
                },
                regPassword: {
                    required: $.i18n.prop('passwordInputTips'),
                    password: $.i18n.prop('passwordInputLengthTips'),
                    minlength: $.i18n.prop('passwordInputFormat')
                },
                regPassword2: {
                    required: $.i18n.prop('passwordInputAgainTips'),
                    password: $.i18n.prop('passwordInputLengthTips'),
                    equalTo: $.i18n.prop('passwordInputNoSame')
                }
            },
            submitHandler: function () {
                if($("#registerCaptchaCode").val() == "") {
                    $(".registerCaptchaCodeTip").text($.i18n.prop('captchaCodeInputTips'));
                    return;
                }
                if (!$("#registerRemember").is(':checked')) {
                    layer.alert($.i18n.prop('agreeServiceTermTips'), {offset: '50%'});
                    return;
                }
                $("#registerHexPassword").val(hex_md5($("#registerPassword").val()));
                $.ajax({
                    url: basePath + "register",
                    data: $("#register-form").serialize(),
                    cache: false,
                    dataType: 'json',
                    type: "POST",
                    success: function (data) {
                        $("#reg-box").addClass("hide");
                        if (data.code != 0) {
                            changeCode();
                            layer.alert(data.msg, {offset: '50%'});
                        } else {
                            layer.alert($.i18n.prop('finishRegisterTips'), {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0, offset: '50%'
                            });
                        }
                    }
                });
            }
        });

        $.validator.addMethod("checkPwd", function (value, element, params) {
            var checkPwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
            return this.optional(element) || (checkPwdReg.test(value));
        }, "*"+$.i18n.prop('passwordInputLengthTips'));

        /!*国际化*!/;
        $.i18n.properties( {
            name : 'messages', // Resource name
            path : '/static/js/i18n/', //Resource path
            cache : true,
            mode : 'map',
            encoding: 'UTF-8',
            language : getcookie("lang")==undefined?'zh_CN':getcookie("lang"),
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