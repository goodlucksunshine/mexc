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
    <title>币种添加</title>
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
    <input type="hidden" name="id" id="id" value="${rule.id}"/>
    <input type="hidden" name="activityId" id="activityId" value="${rule.activityId}"/>
    <div class="user_left">
        <div class="layui-form-item">
            <label class="layui-form-label">规则名称</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" name="ruleName" value="${rule.ruleName}"
                       lay-verify="required">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">条件币种</label>
            <div class="layui-input-inline">
                <select name="sourceId" lay-verify="required">
                    <c:forEach items="${vcoin}" var="vcoin">
                        <option value="${vcoin.id}"
                                <c:if test="${rule.sourceId==vcoin.id}">selected</c:if>>${vcoin.vcoinNameEn}</option>
                    </c:forEach>
                </select>
            </div>
            <label class="layui-form-label">赠送币种</label>
            <div class="layui-input-inline">
                <select name="percentId">
                    <c:forEach items="${vcoin}" var="vcoin">
                        <option value="${vcoin.id}"
                                <c:if test="${rule.percentId==vcoin.id}">selected</c:if>>${vcoin.vcoinNameEn}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">条件数量</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" name="ruleCondition" value="${rule.ruleCondition}"
                       lay-verify="required">
            </div>
            <label class="layui-form-label">赠送类型</label>
            <div class="layui-input-inline">
                <select name="type">
                    <option value="1" <c:if test="${rule.type==1}">selected</c:if>>按比例</option>
                </select>
            </div>
        </div>


        <div class="layui-form-item">
            <label class="layui-form-label">赠送比例</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" name="percent" value="${rule.percent}"
                       lay-verify="required">
            </div>
            <div class="layui-form-mid layui-word-aux yellow">%</div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn add-data" lay-submit="addRule" lay-filter="addRule">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script type="text/javascript" src="static/js/common.js"></script>
<script type="text/javascript" src="static/js/jquery-1.10.2.min.js"></script>

<script type="text/javascript">
    var $;
    layui.config({
        base: "static/js/"
    }).use(['form', 'layer', 'jquery'], function () {
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;

        form.on('submit(addRule)', function (data) {
            $.ajax({
                url: "activity/saveOrUpdate.do",
                type: 'POST',
                dataType: "json",
                data: data.field,
                success: function (r) {
                    if (r.code == 0) {
                        top.layer.msg("添加成功！");
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