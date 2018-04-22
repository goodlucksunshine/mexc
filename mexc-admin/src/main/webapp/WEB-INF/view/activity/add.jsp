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
    <title>添加活动</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="static/css/common.css" media="all"/>
    <link rel="stylesheet" href="static/css/user.css" media="all"/>
</head>
<body class="childrenBody">
<form id="data-form" class="layui-form" style="padding-top: 20px;">
    <input type="hidden" name="id" id="activityId" value="${activity.id}"/>

    <div class="user_left">
        <div class="layui-form-item">
            <label class="layui-form-label">活动标题</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" name="activityTitle" value="${activity.activityTitle}"
                       lay-verify="required">
            </div>

            <label class="layui-form-label">活动类型</label>
            <div class="layui-input-inline">
                <select name="activityType">
                    <option value="1"
                            <c:if test="${activity.activityType==1}">selected</c:if> >糖果赠送
                    </option>
                </select>
            </div>
        </div>


        <div class="layui-form-item">
            <div class="layui-form-item">
                <label class="layui-form-label">开始时间</label>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input" name="activityStartTime"
                           value="<fmt:formatDate value="${activity.activityStartTime}" pattern="yyyy-MM-dd"/>"
                           lay-verify="required" id="startTime">
                </div>
                <label class="layui-form-label">结束时间</label>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input"
                           value="<fmt:formatDate value="${activity.activityEndTime}" pattern="yyyy-MM-dd"/>"
                           name="activityEndTime" lay-verify="required" id="endTime">
                </div>
            </div>
        </div>


        <div class="layui-form-item">
            <label class="layui-form-label">活动说明</label>
            <div class="layui-input-block">
                <textarea name="activityMemo" placeholder="活动说明"
                          class="layui-textarea">${activity.activityMemo}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn add-data" lay-submit="" lay-filter="addActivity">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script type="text/javascript" src="static/js/common.js"></script>

<script type="text/javascript">
    var $;
    layui.config({
        base: "static/js/"
    }).use(['laydate', 'form', 'layer', 'jquery'], function () {
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;
        var laydate = layui.laydate;
        form.on('submit(addActivity)',function(data){
            $.ajax({
                url: "activity/saveActivity.do",
                type: 'POST',
                dataType: "json",
                data: data.field,
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
        laydate.render({
            elem: '#startTime'
        });
        laydate.render({
            elem: '#endTime'
        });
    });
</script>
</body>
</html>