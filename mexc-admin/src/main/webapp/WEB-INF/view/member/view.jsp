<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <title>查看会员</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all" />
</head>
<body class="childrenBody">
<form id="data-form" class="layui-form" style="width:80%;padding-top: 20px;">

    <div class="layui-form-item">
        <label class="layui-form-label">账号</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"  name="marketName" value="${member.account}" readonly>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">账户认证类型</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"  value="${member.secondAuthType}" readonly>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">认证手机</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${member.authAcc}" readonly>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">认证等级</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${member.authLevel}" readonly>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${member.status}" readonly>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">推荐人</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${member.recommender}" readonly>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">IP</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${member.lastLoginIp}"  readonly>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-block">
            <textarea  class="layui-textarea" name="note" readonly>${member.note}</textarea>
        </div>
    </div>
</form>
<script type="text/javascript" src="static/layui/layui.js"></script>

</body>
</html>