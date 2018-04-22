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
    <title><spring:message code="googleAuth"/></title>
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
            <h3 class="global-tit"><spring:message code="googleAuth"/><span>／google Authenticator</span></h3>
            <form class="layui-form" id="data-form" >
                <input type="hidden" name="secret" value="${googleAuth.secret}"/>
                <div class="id-validate">
                    <ul>
                        <li class="clearfix">
                            <div class="layui-form-item">
                                <label class="layui-form-label"><spring:message code="secret"/>：${googleAuth.secret}</label>
                            </div>
                            <div class="layui-form-item">
                                <img src="member/ucenter/qrCode">
                                <%--<img width="132" height="132" src="${googleAuth.qrcodeURL}"/>--%>
                            </div>
                        </li>

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
                        <button class="layui-btn add-data" lay-submit="" lay-filter="submitData"><spring:message code="bind"/></button>
                        <button type="reset" class="layui-btn layui-btn-primary"><spring:message code="cancel"/></button>
                    <%--    &lt;%&ndash;<a href="" class="layui-btn" lay-filter="submitData">绑定</a>&ndash;%&gt;
                        <a href="" class="btn-modify">取消</a>--%>
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
                url: "member/ucenter/googleAuth/bind",
                type: 'POST',
                dataType: "json",
                data: data.field,
                success: function (r) {
                    if (r.code != 0) {
                        layer.alert(r.msg, {
                            skin: 'layui-layer-molv' //样式类名
                            , closeBtn: 0, offset: '50%'
                        });
                    } else {
                        location.href="member/ucenter/index";
                    }
                }
            });
            return false;
        });
    });
</script>
</html>