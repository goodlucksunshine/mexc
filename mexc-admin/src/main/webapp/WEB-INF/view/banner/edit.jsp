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
    <title>banner编辑</title>
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
    <input type="hidden" name="id" value="${banner.id}"/>

    <div class="layui-form-item">
        <label class="layui-form-label">名称</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${banner.name}"  name="name" lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">标题</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${banner.title}" name="title"  lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">图片</label>
        <input type="file" name="file" style="display: none;" onchange="previewImage(this)" id="previewImg"/>
        <div class="layui-input-block">
            <div id="preview">
                <img id="pic" border="0" src="file/download/${banner.imageUrl}" width="100" height="100"
                     onclick="$('#previewImg').click();">
            </div>
        </div>
    </div>

   <%-- <div class="layui-form-item">
        <label class="layui-form-label">链接</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" value="${banner.url}" name="url"  lay-verify="required">
        </div>
    </div>--%>

    <div class="layui-form-item">
        <label class="layui-form-label">排序</label>
        <div class="layui-input-block">
            <input type="number" class="layui-input" value="${banner.sort}" name="sort"  lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <c:if test="${banner.status==1}">
                <input type="checkbox" name="status" value="1" checked lay-filter="status" lay-skin="switch" lay-text="开启|关闭">
            </c:if>
            <c:if test="${banner.status==0}">
                <input type="checkbox" name="status" value="0" lay-filter="status" lay-skin="switch" lay-text="开启|关闭">
            </c:if>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入描述内容" class="layui-textarea"  name="description">${banner.description}</textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn add-data" lay-submit="" lay-filter="submitData">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script type="text/javascript" src="static/js/common.js"></script>
<script type="text/javascript" src="static/js/jquery-1.10.2.min.js"></script>

<script type="text/javascript">
    var $;
    layui.config({
        base : "static/js/"
    }).use(['form','layer','jquery'],function(){
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;

        form.on('switch(status)', function(data){
            if(data.elem.checked) {
                $(this).val("1");
            }else {
                $(this).val("0");
            }
        });

        form.on('submit(submitData)', function (data) {
            var form = new FormData($("#data-form")[0]);
            $.ajax({
                url: "banner/saveOrUpdate.do",
                type: 'POST',
                dataType: "json",
                data: form,
                cache: false,
                processData: false,
                contentType: false,
                success: function (r) {
                    if (r.code == 0) {
                        top.layer.msg("保存成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    } else {
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