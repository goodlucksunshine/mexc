<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>查看会员身份认证资料</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
</head>
<body class="childrenBody">
<form id="data-form" class="layui-form" style="width:80%;padding-top: 20px;" onsubmit="return false;">
    <input type="hidden" name="id" id="authId" value="${auth.id}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">封面</label>
        <div class="site-demo-flow">
            <img src="file/download/${auth.cardHome}" width="500" height="500"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">背面</label>
        <div class="site-demo-flow">
            <img src="file/download/${auth.cardBack}" width="500" height="500"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">手持身份证</label>
        <div class="site-demo-flow">
            <img src="file/download/${auth.cardHand}" width="500" height="500"/>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn audit" status="1" id="auditSuccess">审核通过</button>
            <button class="layui-btn audit" status="2" id="auditFail">驳回</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script>
    var $;
    layui.config({
        base: "static/js/"
    }).use(['form', 'layer', 'jquery'], function () {
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;
        $(".audit").bind("click",function () {
            var status = $(this).attr("status");
            var id = $("#authId").val();
            $.ajax({
                url: "member/auditAuth?authId=" + id + "&status=" + status,
                type: 'POST',
                dataType: "json",
                data: form,
                cache: false,
                processData: false,
                contentType: false,
                success: function (r) {
                    if (r.code == 0) {
                        top.layer.msg("操作成功");
                        layer.closeAll("iframe");

                        //刷新父页面
                        parent.location.reload();
                    } else {
                        layer.msg(r.msg, {icon: 5});
                    }
                }
            });
        });
    });
</script>
</body>
</html>