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
    <title>钱包添加</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
</head>
<body class="childrenBody">
<form id="data-form" class="layui-form" style="width:80%;padding-top: 20px;">
    <input type="hidden" name="id" value="${wallet.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">钱包类型</label>
        <div class="layui-input-inline">
            <select name="type" lay-verify="required">
                <option value="ETH">ETH</option>
                <option value="BTC">BTC</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">method</label>
        <div class="layui-input-inline eth ">
            <select name="type" lay-verify="required">
                <option value="getaccount">getaccount</option>
                <option value="getaccountaddress">getaccountaddress</option>
                <option value="getbalance">getbalance</option>
                <option value="getnewaddress">getnewaddress</option>
                <option value="listaccounts">listaccounts</option>
                <option value="validateaddress">validateaddress</option>
            </select>
        </div>
        <div class="layui-input-inline btc hide">
            <select name="type" lay-verify="required">
                <option value="getaccount">getaccount</option>
                <option value="getaccountaddress">getaccountaddress</option>
                <option value="getbalance">getbalance</option>
                <option value="getnewaddress">getnewaddress</option>
                <option value="listaccounts">listaccounts</option>
                <option value="validateaddress">validateaddress</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">钱包地址</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="url" value="${wallet.url}" lay-verify="required">
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
        base: "static/js/"
    }).use(['form', 'layer', 'jquery'], function () {
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;

        form.on('submit(submitData)', function (data) {
            $.ajax({
                url: "wallet/saveOrUpdate.do",
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