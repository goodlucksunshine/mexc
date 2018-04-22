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
    <title>管理员添加</title>
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
<form id="data-form" class="layui-form" style="width:80%;">
    <input type="hidden" name="id" value="${admin.id}"/>
    <div class="user_left">
        <div class="layui-form-item">
            <label class="layui-form-label">用户姓名</label>
            <div class="layui-input-block">
                <input type="text" name="realName" value="${admin.realName}" lay-verify="required|realName" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">用户角色</label>
            <div class="layui-input-block">
                <select name="roleId" lay-filter="roleId" lay-verify="required">
                    <option value=""></option>
                    <c:forEach items="${roleList}" var="item" >
                        <option value="${item.id}">${item.roleName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">登陆名</label>
            <div class="layui-input-block">
                <input type="text" name="adminName" lay-verify="required|adminName" value="${admin.adminName}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">登陆密码</label>
            <div class="layui-input-block">
                <input type="password" name="password" value="${admin.password}"  lay-verify="required|password" class="layui-input realName">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">手机号码</label>
            <div class="layui-input-block">
                <input type="text" name="mobile" value="${admin.mobile}" placeholder="请输入手机号码" lay-verify="required|phone" class="layui-input userPhone">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
            <textarea placeholder="请输入备注" class="layui-textarea" name="remark">${admin.remark}</textarea>
            </div>
        </div>
    </div>

    <div class="user_right">
        <div class="layui-input-block">
            <input type="file" name="file" style="display: none;" lay-verify="required" onchange="previewImage(this)" id="previewImg" />
        </div>

        <div class="layui-input-block">
            上传头像
            <div id="preview">
                <img id="pic" border="0" src="static/images/photo_icon.jpg" width="100" height="100" onclick="$('#previewImg').click();">
            </div>
        </div>
    </div>

    <div class="layui-form-item" style="margin-left: 5%;">
        <div class="layui-input-block">
            <button class="layui-btn add-data" lay-submit="" lay-filter="addUser">立即提交</button>
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
    }).use(['form','layer','upload','jquery'],function(){
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;


        //自定义验证规则
        form.verify({
            adminName: function(value){
                if(value.length > 20) {
                    return '用户名不能超过20个字符';
                }
            }, realName: function(value){
                if(value.length > 5) {
                    return '真实姓名不能超过5个字符';
                }
            }
            ,password: [/(.+){6,12}$/, '密码必须6到12位']
            ,phone: [/^1[3|4|5|7|8]\d{9}$/, '手机必须11位，只能是数字！']
        });

        form.on('submit(addUser)',function(data){
            var form = new FormData($("#data-form")[0]);
            $.ajax({
                url: 'admin/saveOrUpdate.do',
                type: 'POST',
                dataType: "json",
                data: form,
                cache: false,
                processData: false,
                contentType: false,
                success: function (r) {
                    if(r.code==0) {
                        top.layer.msg("添加成功！");
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