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
    <title><spring:message code="closeGoogleAuth"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
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
            <h3 class="global-tit"><spring:message code="closeGoogleAuth"/><span>／close google Authenticator</span></h3>
            <form class="layui-form" id="data-form" >
                <div class="id-validate">
                    <ul>

                        <li class="clearfix">
                            <div class="layui-form-item">
                                <label class="layui-form-label"><spring:message code="loginPassword"/>：</label>
                                <div class="layui-input-inline">
                                    <input type="password" class="layui-input"  name="password" lay-verify="required">
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label"><spring:message code="googleAuthCode"/>：</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input"  name="validationCode" lay-verify="required">
                                </div>
                            </div>
                        </li>
                    </ul>
                    <div class="id-uload-btn">
                        <button class="layui-btn add-data" lay-submit="" lay-filter="submitData"><spring:message code="unbind"/></button>
                        <button type="reset" class="layui-btn layui-btn-primary"><spring:message code="cancel"/></button>
                    </div>
                </div>
            </form>
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
        base : "static/js/"
    }).use(['form','layer','jquery'],function() {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;

        form.on('submit(submitData)',function(data){
            data.field.password = hex_md5(data.field.password);
            $.ajax({
                url: "member/ucenter/googleAuth/unbind",
                type: 'POST',
                dataType: "json",
                data: data.field,
                success: function (r) {
                    if (r.code != 0) {
                        layer.msg(r.msg, {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    } else {
                        window.location.href="member/ucenter/index";
                    }
                }
            });
            return false;
        });
    });
</script>
</html>