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
    <title>会员等级</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="static/css/user.css" media="all" />
</head>
<body class="childrenBody">


<table class="layui-table" lay-data="{url:'member/level/list', id:'levelTable'}" lay-filter="levelTable">
    <thead>
        <tr>
            <th lay-data="{field:'level', width:80}">等级</th>
            <th lay-data="{field:'levelName', width:120}">等级名称</th>
            <th lay-data="{field:'limitAmount', edit: 'number', width: 300}">提现额度</th>
        </tr>
    </thead>
</table>

<script type="text/javascript" src="static/layui/layui.js"></script>

<script type="text/javascript">
    layui.config({
        base : "static/js/"
    }).use(['form','layer','jquery','table'],function(){
        var layer = layui.layer;
        var table = layui.table;
        var $ = layui.jquery;
        //监听单元格编辑
        table.on('edit(levelTable)', function(obj){
            var value = obj.value //得到修改后的值
                ,data = obj.data //得到所在行所有键值
                ,field = obj.field; //得到字段

            $.ajax({
                url: "member/level/update.do",
                type: 'POST',
                dataType: "json",
                data: {"id":data.id,"limitAmount":value},
                success: function (r) {
                    if(r.code==0) {
                        top.layer.msg("修改成功！");
                    }else {
                        layer.msg(r.msg, {icon: 5});
                    }
                }
            });
        });
    });
</script>
</body>
</html>