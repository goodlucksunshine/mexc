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
    <title>活动规则列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all"/>
    <link rel="stylesheet" href="static/css/user.css" media="all"/>
</head>
<body class="childrenBody">
<blockquote class="layui-elem-quote news_search">
    <div class="layui-inline">
        <form id="searchForm" name="searchForm" class="layui-form" action="activity/rulePage">
            <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}"/>
            <input type="hidden" id="activityId" name="activityId" value="${searchDto.activityId}"/>
            <%--<div class="layui-input-inline">--%>
            <%--<input type="text" value="${dto.account}" name="account" placeholder="请输入账号"--%>
            <%--class="layui-input search_input"/>--%>
            <%--</div>--%>

            <%--<a class="layui-btn search_btn">查询</a>--%>
        </form>
    </div>

    <div class="layui-inline">
        <a class="layui-btn layui-btn-normal doAdd_btn">添加</a>
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
            <col width="40%">
            <col width="10%">
            <col width="20%">
        </colgroup>
        <thead>
        <tr>
            <th>规则名称</th>
            <th>规则内容</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>执行状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody class="data_content">
        <c:forEach var="item" items="${page.resultList}">
            <tr>
                <td>${item.ruleName}</td>
                <td><fmt:formatDate value="${item.ruleStartTime}" pattern="yyyy-MM-dd"/></td>
                <td><fmt:formatDate value="${item.ruleEndTime}" pattern="yyyy-MM-dd"/></td>
                <td>
                        ${item.sourceName}资产满${item.ruleCondition}
                    <c:if test="${item.type==1}">按${item.sourceName}的<fmt:formatNumber value="${item.percent*100}"
                                                                                       minFractionDigits="2"/> %赠送${item.presentName}
                    </c:if>
                    <c:if test="${item.type==2}">按固定数量赠送${item.presentName}${item.fix}个</c:if>
                </td>
                <td>
                    <c:if test="${item.execStatus==0}">新建</c:if>
                    <c:if test="${item.execStatus==1}">执行中</c:if>
                    <c:if test="${item.execStatus==2}">执行完成</c:if>
                    <c:if test="${item.execStatus==-1}">执行异常</c:if>
                </td>
                <td>
                    <a class="layui-btn layui-btn layui-btn-mini edit" data-id="${item.id}">编辑</a>
                    <a class="layui-btn layui-btn layui-btn-mini executor" data-id="${item.id}"> 执行</a>
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
        //添加
        $(".edit").click(function () {
            var id = $(this).attr("data-id");
            var index = layui.layer.open({
                title: "修改活动细则",
                type: 2,
                content: "activity/ruleDetail?ruleId=" + id,
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


        $(".executor").click(function () {
            var id = $(this).attr("data-id");
            $.ajax({
                url: "activity/execRule.do?ruleId=" + id,
                type: 'POST',
                dataType: "json",
                success: function (r) {
                    if (r.code == 0) {
                        top.layer.msg("任务启动成功");
                        //刷新父页面
                        location.reload();
                    } else {
                        layer.msg(r.msg, {icon: 5});
                    }
                }
            });
        });


        //添加
        $(".doAdd_btn").click(function () {
            var id = $("#activityId").val();
            var index = layui.layer.open({
                title: "添加活动细则",
                type: 2,
                content: "activity/ruleAdd?activityId=" + id,
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
        //查询
        $(".search_btn").click(function () {
            $("#searchForm").submit();
        });
    })
</script>
</body>
</html>
