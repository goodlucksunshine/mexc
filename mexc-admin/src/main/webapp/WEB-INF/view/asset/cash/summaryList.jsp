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
    <title>会员提现统计</title>
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
        <form id="searchForm" name="searchForm" class="layui-form" action="asset/cash/summaryList">
            <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}"/>

            <div class="layui-input-inline">
               <div>用户名：</div>
            </div>
            <div class="layui-input-inline">
               <div> <input type="text" name="account" placeholder="请输入账户名" value="${queryDto.account}" class="layui-input search_input"/></div>
            </div>

            <div class="layui-input-inline">
                <div>查询方式：</div>
            </div>
            <div class="layui-input-inline">
                <select name="searchMethod" id="searchMethod">
                    　　<option value="1" ${queryDto.searchMethod=='1'? 'selected':''}>月</option>
                    　　<option value="2" ${queryDto.searchMethod=='2'? 'selected':''}>日</option>
                </select>
            </div>
            <div class="layui-input-inline">
            <select name="month"  size="1">
                    <option value="">--请选择--</option>
                　　<option value="01" ${queryDto.month=='01'? 'selected':''}>1月</option>
                　　<option value="02" ${queryDto.month=='02'? 'selected':''}>2月</option>
                　　<option value="03" ${queryDto.month=='03'? 'selected':''}>3月</option>
                　　<option value="04" ${queryDto.month=='04'? 'selected':''}>4月</option>
                　　<option value="05" ${queryDto.month=='05'? 'selected':''}>5月</option>
                　　<option value="06" ${queryDto.month=='06'? 'selected':''}>6月</option>
                　　<option value="07" ${queryDto.month=='07'? 'selected':''}>7月</option>
                　　<option value="08" ${queryDto.month=='08'? 'selected':''}>8月</option>
                　　<option value="09" ${queryDto.month=='09'? 'selected':''}>9月</option>
                　　<option value="10" ${queryDto.month=='10'? 'selected':''}>10月</option>
                　　<option value="11" ${queryDto.month=='11'? 'selected':''}>11月</option>
                　　<option value="12" ${queryDto.month=='12'? 'selected':''}>12月</option>
            </select>
            </div>

            <div class="layui-input-inline">
                开始时间：
            </div>
            <div class="layui-input-inline">
                <input type="date" name="startTime" value="${queryDto.startTime}" class="layui-input search_input"/>
            </div>
            <div class="layui-input-inline">
                - 结束时间：
            </div>
            <div class="layui-input-inline">
                <input type="date" name="endTime" value="${queryDto.endTime}" class="layui-input search_input"/>
            </div>

            <div class="layui-input-inline">
                <div>币种：</div>
            </div>
            <div class="layui-input-inline">
                <div> <input type="text" name="txApplyToken" placeholder="请输货币种类" value="${queryDto.txApplyToken}" class="layui-input search_input"/></div>
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
            <col width="10%">
        </colgroup>
        <thead>
            <tr>
                <th>用户名</th>
                <th>币种</th>
                <th>申请额度</th>
                <th>手续费</th>
            </tr>
        </thead>
        <tbody class="data_content">
            <c:forEach var="item" items="${page.resultList}">
                <tr>
                    <td>${item.account}</td>
                    <td>${item.txApplyToken}</td>
                    <td>${item.txApplyValue}</td>
                    <td>${item.cashFee}</td>
                   <%-- <td>
                        <c:if test="${item.cashStatus==0}">
                            提现待确认
                        </c:if>
                        <c:if test="${item.cashStatus==-1}">
                            提现失败
                        </c:if>
                        <c:if test="${item.cashStatus==1}">
                            提现成功
                        </c:if>
                        <c:if test="${item.cashStatus==2}">
                            处理中
                        </c:if>
                    </td>--%>
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
    }).use(['form','layer','jquery','laypage'],function() {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : parent.layer,
            laypage = layui.laypage,
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

        //查询
        $(".search_btn").click(function(){
            $("#searchForm").submit();
        });
    })
</script>
</body>
</html>
