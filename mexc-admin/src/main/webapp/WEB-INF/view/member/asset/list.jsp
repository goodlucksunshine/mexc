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
    <title>会员资产列表</title>
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
        <form id="searchForm" name="searchForm" class="layui-form" action="member/assetPage">
            <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}"/>
            <div class="layui-input-inline">
                <input type="text" value="${dto.account}" name="account" placeholder="请输入账号"
                       class="layui-input search_input"/>
            </div>
            <div class="layui-input-inline">
                <input type="text" value="${dto.vcoinName}" name="vcoinName" placeholder="请输入币种名称"
                       class="layui-input search_input"/>
            </div>

            <a class="layui-btn search_btn">查询</a>
        </form>
    </div>

    <div class="layui-inline">
        <%--<shiro:hasPermission name="role:add">--%>
        <%-- <a class="layui-btn layui-btn-normal doAdd_btn">添加</a>--%>
        <%--</shiro:hasPermission>--%>
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
            <col width="20%">
            <col width="20%">
            <col width="10%">
            <col width="10%">
        </colgroup>
        <thead>
        <tr>
            <th>账号</th>
            <th>币种</th>
            <th>可用余额</th>
            <th>锁定资产</th>
            <th>资产地址</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody class="data_content">
        <c:forEach var="item" items="${page.resultList}">
            <tr>
                <td>${item.account}</td>
                <td>${item.vcoinName}</td>
                <td><fmt:formatNumber type="number" maxIntegerDigits="8" value="${item.balanceAmount}" pattern="0.00000000"/></td>
                <td><fmt:formatNumber type="number" maxIntegerDigits="8" value="${item.frozenAmount}" pattern="0.00000000"/></td>
                <td>${item.walletAddress}</td>
                <td>
                    <a class="layui-btn layui-btn layui-btn-mini recharge" data-id="${item.assetId}">
                        <i class="layui-icon">&#xe65e;</i> 充值
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
        $("body").on("click", ".recharge", function () {
            var _this = $(this);
            var assetId = _this.attr("data-id");
            layer.prompt({title: '请输入充值金额', formType: 0}, function (value, index) {
                layer.close(index);
                $.ajax({
                    url: "member/assetRecharge?assetId=" + assetId + "&rechargeValue=" + value,
                    type: 'POST',
                    dataType: "json",
                    success: function (data) {
                        if (data.code != 0) {
                            layer.msg(data.msg);
                        } else {
                            layer.msg("充值成功");
                        }
                        location.reload();
                    }
                });
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
