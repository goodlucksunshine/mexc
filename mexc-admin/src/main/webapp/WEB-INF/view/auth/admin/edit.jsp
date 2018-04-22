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
    <link rel="stylesheet" href="static/btree/css/bootstrapStyle/bootstrapStyle.css" type="text/css">
</head>
<body class="childrenBody">
<form id="data-form" class="layui-form">
    <input type="hidden" name="id" value="${admin.id}"/>
    <div class="user_left">
        <div class="layui-form-item">
            <label class="layui-form-label">用户姓名</label>
            <div class="layui-input-block">
                <input type="text" name="realName" value="${admin.realName}" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">用户角色</label>
            <div class="layui-input-block">
                <select name="roleId" lay-filter="roleId">
                    <c:forEach items="${roleList}" var="item" >
                        <option value="${item.id}">${item.roleName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">登陆名</label>
            <div class="layui-input-block">
                <input type="text" name="adminName" value="${admin.adminName}" disabled class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">手机号码</label>
            <div class="layui-input-block">
                <input type="tel" name="mobile" value="${admin.mobile}" placeholder="请输入手机号码" lay-verify="required|phone" class="layui-input userPhone">
            </div>
        </div>


        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
            <textarea placeholder="请输入备注" class="layui-textarea" lay-verify="remark" name="remark">${admin.remark}</textarea>
            </div>
        </div>
    </div>

    <div class="user_right">
        <div class="layui-input-block">
            <input type="file" name="file" style="display: none;"  lay-ext="jpg|png|gif" onchange="previewImage(this)" id="previewImg" />
        </div>

        <div class="layui-input-block">
            上传头像
            <div id="preview">
                <img id="pic" src="file/download/${admin.headPic}" border="0" src="static/images/photo_icon.jpg" width="100" height="100" onclick="$('#previewImg').click();">
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

<script type="text/javascript" src="static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="static/btree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="static/btree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="static/btree/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript">
    var $;
    layui.config({
        base : "static/js/"
    }).use(['form','layer','jquery'],function(){
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;


        form.on('submit(addUser)',function(data){
            var form = new FormData($("#data-form")[0]);
            $.ajax({
                url: "admin/saveOrUpdate.do",
                type: 'POST',
                dataType: "json",
                data: form,
                cache: false,
                processData: false,
                contentType: false,
                success: function (r) {
                    if(r.code==0) {
                        top.layer.msg("保存成功！");
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

        //图片预览
        $("#picFile").on("change",function(){
            var objUrl = getObjectURL(this.files[0]) ; //获取图片的路径，该路径不是图片在本地的路径
            if (objUrl) {
                $("#pic").attr("src", objUrl) ; //将图片路径存入src中，显示出图片
            }
        });
    });
</script>

</body>
</html>