<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
    <title>活动列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="static/css/user.css" media="all"/>
</head>
<body class="childrenBody">
<blockquote class="layui-elem-quote news_search">
    <div class="layui-inline">
        <form id="searchForm" name="searchForm" class="layui-form" action="activity/actPage">
            <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}"/>
            <div class="layui-input-inline">
                <input type="text" value="${dto.account}" name="account" placeholder="请输入账号"
                       class="layui-input search_input"/>
            </div>
            <a class="layui-btn search_btn">查询</a>
        </form>
    </div>

    <div class="layui-inline">
        <shiro:hasPermission name="role:add">
            <a class="layui-btn layui-btn-normal doAdd_btn">添加</a>
        </shiro:hasPermission>
    </div>
    <div class="layui-inline">
        <div class="layui-form-mid layui-word-aux"></div>
    </div>
</blockquote>
<div class="layui-form data_list">
    <table class="layui-table">
        <colgroup>
            <col width="20%">
            <col width="20%">
            <col width="15%">
            <col width="15%">
            <col width="15%">
            <col width="15%">
        </colgroup>
        <thead>
        <tr>
            <th>活动标题</th>
            <th>类型</th>
            <th>开始日期</th>
            <th>截止日期</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody class="data_content">
        <c:forEach var="item" items="${page.resultList}">
            <tr>
                <td>${item.activityTitle}</td>
                <td><c:if test="${item.activityType==1}">糖果赠送</c:if></td>
                <td><fmt:formatDate value="${item.activityStartTime}" pattern="yyyy-MM-dd"/></td>
                <td><fmt:formatDate value="${item.activityEndTime}" pattern="yyyy-MM-dd"/></td>
                <td>
                    <c:if test="${item.activityStatus==0}">新建</c:if>
                    <c:if test="${item.activityStatus==1}">开始</c:if>
                    <c:if test="${item.activityStatus==-1}">结束</c:if>
                </td>
                <td>
                    <a class="layui-btn layui-btn-mini do_edit" data-id="${item.id}">
                        <i class="iconfont icon-edit"></i> 编辑
                    </a>
                    <a class="layui-btn layui-btn-mini item_list" data-id="${item.id}">
                        <i class="iconfont icon-edit"></i> 活动项目
                    </a>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
<div id="page"></div>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script>
    layui.config({
        base: "static/js/"
    }).use(['form', 'layer', 'jquery', 'laypage'], function () {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : parent.layer,
            laypage = layui.laypage,
            $ = layui.jquery;

        laypage.render({
            elem: 'page'
            , count: ${page.totalResult} //数据总数，从服务端得到
            , curr: ${page.currentPage}
            , layout: ['count', 'prev', 'page', 'next', 'skip']
            , jump: function (obj, first) {
                $("#currentPage").val(obj.curr);
                //首次不执行
                if (!first) {
                    $("#searchForm").submit();
                }
            }
        });
        //查询
        $(".search_btn").click(function () {
            $("#searchForm").submit();
        });
        //编辑
        $("body").on("click", ".do_edit", function () {
            var _this = $(this);
            var id = _this.attr("data-id");
            var index = layui.layer.open({
                title: "编辑市场",
                type: 2,
                content: "activity/actDetail?activityId=" + id,
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function () {
                layui.layer.full(index);
            });
            layui.layer.full(index);
        });


        //编辑
        $("body").on("click", ".item_list", function () {
            var _this = $(this);
            var id = _this.attr("data-id");
            var index = layui.layer.open({
                title: "活动项",
                type: 2,
                content: "activity/rulePage?activityId=" + id,
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function () {
                layui.layer.full(index);
            });
            layui.layer.full(index);
        });


        //添加
        $(".doAdd_btn").click(function () {
            var index = layui.layer.open({
                title: "添加管理员",
                type: 2,
                content: "activity/actAdd",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function () {
                layui.layer.full(index);
            });
            layui.layer.full(index);
        });

    })
</script>
</body>
</html>
