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

<html>
<head lang="en">
    <meta charset="UTF-8">
    <title><spring:message code="changePassword"/></title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.js"></script>
</head>
<body>
<!-- header[[[ -->
<jsp:include page="../common/head.jsp"/>
<!-- header]]] -->

<div class="inner-container">
    <!-- inner[[[ -->
    <div class="layout id-validate-box">
        <h3 class="global-tit"><spring:message code="changePassword"/><span>／alter login password</span></h3>
        <div class="center">
            <form class="layui-form" id="data-form">
                <div class="id-validate">
                    <ul>

                        <li class="clearfix">
                            <div class="layui-form-item">
                                <label class="layui-form-label"><spring:message code="oldPassword"/>：</label>
                                <div class="layui-input-inline">
                                    <input type="password" class="layui-input" name="oldPwd"
                                           lay-verify="oldPwd|required">
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label"><spring:message code="newPassword"/>：</label>
                                <div class="layui-input-inline">
                                    <input type="password" class="layui-input" name="newPwd"
                                           lay-verify="newPwd|required">
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label"><spring:message code="confirmNewPassword"/>：</label>
                                <div class="layui-input-inline">
                                    <input type="password" class="layui-input" name="comfirmNewPwd"
                                           lay-verify="comfirmNewPwd|required">
                                </div>
                            </div>
                        </li>
                    </ul>
                    <div class="id-uload-btn">
                        <button class="layui-btn add-data" lay-submit="" lay-filter="submitData"><spring:message code="confirm"/></button>
                        <button type="reset" class="layui-btn layui-btn-primary"><spring:message code="cancel"/></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- inner]]] -->
</div>
<!-- footer[[ -->
<jsp:include page="../common/footer.jsp"/>
<!-- footer]] -->
</body>
<script type="text/javascript" src="static/js/md5.js"></script>
<script>
    layui.config({
        base: "static/js/"
    }).use(['form', 'layer', 'jquery'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;

        //自定义验证规则
        form.verify({
            oldPwd: function (value) {
                var checkPwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
                if (!checkPwdReg.test(value)) {
                    return $.i18n.prop('passwordInputLengthTips');
                }
            },
            newPwd: function (value) {
                var checkPwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
                if (!checkPwdReg.test(value)) {
                    return $.i18n.prop('passwordInputLengthTips');
                }
            },
            comfirmNewPwd: function (value) {
                var newPwd = $("input[name='newPwd']").val();
                var checkPwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
                if (!checkPwdReg.test(value)) {
                    return $.i18n.prop('passwordInputLengthTips');
                }
                if (value != newPwd) {
                    return $.i18n.prop('passwordInputSameTips');
                }
            }
        });

        form.on('submit(submitData)', function (data) {
            data.field.oldPwd = hex_md5(data.field.oldPwd);
            data.field.newPwd = hex_md5(data.field.newPwd);
            data.field.comfirmNewPwd = hex_md5(data.field.comfirmNewPwd);
            $.ajax({
                url: "member/ucenter/changePwd",
                type: 'POST',
                dataType: "json",
                data: data.field,
                success: function (r) {
                    if (r.code == 0) {
                        $("input").reset();
                    }
                    layer.msg(r.msg, {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                }
            });
            return false;
        });
    });
</script>
</html>