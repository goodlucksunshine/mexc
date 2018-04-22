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
    <title>MEXC后台登陆</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="static/layui/css/layui.css" media="all" >
    <link rel="stylesheet" href="static/css/login.css" media="all">
</head>
<body>
<div class="login">
    <h1>MEXC后台登陆</h1>
    <form class="layui-form" action="loginIn.do" method="post">
        <div class="layui-form-item">
            <input class="layui-input" name="adminName" placeholder="用户名" lay-verify="required" type="text" autocomplete="off">
        </div>
        <div class="layui-form-item">
            <input class="layui-input" name="password" placeholder="密码" lay-verify="required" type="password" autocomplete="off">
        </div>
        <div class="layui-form-item form_code">
            <input class="layui-input" name="captchaCode" placeholder="验证码" lay-verify="required" type="text" autocomplete="off">
            <div class="code">
                <img id="capCode" src="code.do" width="116" alt="点击更换" title="点击更换" height="36" onclick="changeCode()">
            </div>
        </div>
        <button class="layui-btn login_btn" lay-submit="" lay-filter="login">登录</button>
    </form>
</div>
</body>
<script type="text/javascript" src="static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script>
    function changeCode() {
        $("#capCode").attr("src","code.do?t="+ genTimestamp());
    }

    function genTimestamp() {
        var time = new Date();
        return time.getTime();
    }

    layui.config({
        base : "static/js/"
    }).use(['form','layer'],function(){
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;

        //登录按钮事件
        form.on("submit(login)",function(data){
            $.ajax({
                type: "POST",
                url: "loginIn.do",
                data: { "adminName":data.field.adminName,
                        "password":data.field.password,
                        "captchaCode":data.field.captchaCode},
                success: function(r){
                    if(r.code == 0){
                        window.top.location.href = "index";
                    }else{
                        layer.msg(r.msg, {icon: 5});
                        changeCode();
                    }
                },error: function () {
                    layer.msg("未知异常，请联系管理员", {icon: 5});
                    changeCode();
                }
            });
            return false;
        });
    })
</script>
</html>
