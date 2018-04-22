<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <title>虚拟币转入</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
    <link rel="stylesheet" href="static/css/user.css" media="all" />
</head>
<body class="childrenBody">
<blockquote class="layui-elem-quote news_search">
    <div class="layui-inline">
        <form id="searchForm" name="searchForm" class="layui-form" action="asset/recharge/list">
            <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}"/>
            <div class="layui-input-inline">
                <input type="text" value="${queryDto.account}" name="account" placeholder="请输入用户名" class="layui-input search_input"/>
            </div>
            <div class="layui-input-inline">
                <input type="text" value="${queryDto.txToken}" name="account" placeholder="请输入币种名称" class="layui-input search_input"/>
            </div>

            <div class="layui-input-inline">
                <input type="text" placeholder="开始时间" class="layui-input" id="startTime" value="${queryDto.startTime}" name="startTime">
            </div>

            <div class="layui-input-inline">
                <input type="text" placeholder="结束时间" class="layui-input" id="endTime" value="${queryDto.endTime}" name="endTime">
            </div>

            <a class="layui-btn search_btn">查询</a>
        </form>
    </div>

    <div class="layui-inline">
        <div class="layui-form-mid layui-word-aux"></div>
    </div>
</blockquote>
<div class="layui-form data_list">
    <table class="layui-table">
        <colgroup>
            <col width="10%">
            <col width="10%">
            <col width="10%">
            <col width="10%">
            <col width="10%">
            <col width="10%">
        </colgroup>
        <thead>
            <tr>
                <th>用户名</th>
                <th>币种</th>
                <th>转入时间</th>
                <th>转入地址</th>
                <th>转入数量</th>
                <th>状态</th>
            </tr>
        </thead>
        <tbody class="data_content">
            <c:forEach var="item" items="${page.resultList}">
                <tr>
                    <td>${item.account}</td>
                    <td>${item.txToken}</td>
                    <td><fmt:formatDate value="${item.syncTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>${item.rechargeAddress}</td>
                    <td>${item.rechargeValue}</td>
                    <td>成功</td>
                </tr>
            </c:forEach>

        </tbody>
    </table>
</div>
<div id="page"></div>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script>
    layui.config({
        base : "static/js/"
    }).use(['form','layer','jquery','laypage','laydate'],function() {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : parent.layer,
            laypage = layui.laypage,
            laydate = layui.laydate,
            $ = layui.jquery;

        laypage.render({
            elem: 'page'
            ,count: ${page.totalResult} //数据总数，从服务端得到
            ,curr: ${page.currentPage}
            ,layout: ['count', 'prev', 'page', 'next', 'skip']
            ,jump: function(obj, first){
                $("#currentPage").val(obj.curr);
                //首次不执行
                if(!first){
                    $("#searchForm").submit();
                }
            }
        });

        //日期
        laydate.render({
            elem: '#startTime'
            ,type: 'datetime'
        });

        //日期
        laydate.render({
            elem: '#endTime'
            ,type: 'datetime'
        });

        //查询
        $(".search_btn").click(function(){
            $("#searchForm").submit();
        });

    })
</script>
</body>
</html>
