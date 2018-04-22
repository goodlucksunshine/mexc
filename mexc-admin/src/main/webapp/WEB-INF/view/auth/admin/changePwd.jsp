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
    <title>管理员编辑</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="static/css/common.css" media="all" />
    <link rel="stylesheet" href="static/css/user.css" media="all" />
</head>
<body class="childrenBody">
<form class="layui-form changePwd" id="data-form" onsubmit="return false;">
    <input type="hidden" name="id" value="${admin.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-block">
            <input type="text" value="${admin.adminName}" disabled class="layui-input layui-disabled">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">旧密码</label>
        <div class="layui-input-block">
            <input type="password" name="password" placeholder="请输入旧密码" lay-verify="required|oldPwd" class="layui-input pwd">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">新密码</label>
        <div class="layui-input-block">
            <input type="password" name="newPassword" placeholder="请输入新密码" lay-verify="required|newPwd"  class="layui-input pwd">
            <span id="tips"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">确认密码</label>
        <div class="layui-input-block">
            <input type="password"  placeholder="请确认密码" lay-verify="required|confirmPwd" class="layui-input pwd">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn add-data" lay-submit="add-data" lay-filter="changePwd">立即修改</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script type="text/javascript" src="static/js/common.js"></script>
<script type="text/javascript">
    var $;
    layui.config({
        base : "static/js/"
    }).use(['form','layer','jquery'],function(){
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;

        form.on('submit(changePwd)',function(data){
            var form = new FormData($("#data-form")[0]);
            $.ajax({
                url: "admin/changePwd.do",
                type: 'POST',
                dataType: "json",
                data: form,
                cache: false,
                processData: false,
                contentType: false,
                success: function (r) {
                    if(r.code==0) {
                        top.layer.msg("修改成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    }else {
                        layer.msg(r.msg, {icon: 5});
                    }
                }
            });
            return false;
        });

    });
</script>

</body>
</html>