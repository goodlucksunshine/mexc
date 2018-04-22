<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">

<html>
<head lang="en">
    <meta charset="UTF-8">
    <title><spring:message code="entrustOrder"/></title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.js"></script>
</head>
<body>
<!-- header[[[ -->
<jsp:include page="common/head.jsp">
    <jsp:param name="current" value="order"/>
</jsp:include>
<!-- header]]] -->

<div class="inner-container">
    <!-- inner[[[ -->
    <div class="layout order-list min-height">
        <div class="login-record">
            <h3 class="global-tit"><spring:message code="currentEntrust"/><span>／My open order</span></h3>
            <div class="global-table">
                <table>
                    <tr>
                        <th><spring:message code="market"/></th>
                        <th><spring:message code="time"/></th>
                        <th><spring:message code="buyOrSell"/></th>
                        <th><spring:message code="price"/></th>
                        <th><spring:message code="hasCompleteNumber"/></th>
                        <th><spring:message code="entrustNumber"/></th>
                        <th><spring:message code="entrustAmount"/></th>
                        <th><spring:message code="operate"/></th>
                    </tr>
                    <c:forEach items="${currentEntrust}" var="order">
                        <tr>
                            <td>${order.tradelVcoinNameEn}/${order.marketName}</td>
                            <td>${order.createTime}</td>
                            <td>
                                <c:if test="${order.tradeType==1}">
                                    <span class="col-green"><spring:message code="buy"/></span>
                                </c:if>
                                <c:if test="${order.tradeType==2}">
                                    <span class="col-red"><spring:message code="sell"/></span>
                                </c:if>
                            </td>
                            <td><fmt:formatNumber type="number" value="${order.tradePrice}" pattern="0.00000000"
                                                  maxFractionDigits="8"/>${order.marketName}</td>
                            <td><fmt:formatNumber type="number" value="${order.tradedNumber}" pattern="0.00000000"
                                                  maxFractionDigits="8"/>${order.tradelVcoinNameEn}</td>
                            <td><fmt:formatNumber type="number" value="${order.tradeNumber}" pattern="0.00000000"
                                                  maxFractionDigits="8"/>${order.tradelVcoinNameEn}</td>

                            <td><fmt:formatNumber type="number" value="${order.tradeTotalAmount}" pattern="0.00000000"
                                                  maxFractionDigits="8"/></td>
                            <td>
                                <a href="javascript:void(0);" data-tradeNo="${order.tradeNo}" class="col-green cancelEntrust">
                                    <spring:message code="cancelEntrust"/>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="page">

        </div>
    </div>
    <!-- inner]]] -->
</div>
<script>
    layui.config({
        base: "static/js/"
    }).use(['form', 'layer', 'jquery'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;
        //撤单
        $(".cancelEntrust").click(function () {
            var tradeNo = $(this).attr("data-tradeNo");

            var params = {
                "tradeNo": tradeNo
            };

            layer.confirm($.i18n.prop('confirmCancelOrderTips')+'？',{icon:3, title:'tips'},function(index){
                $.ajax({
                    url: "member/trans/cancelEntrustTrade",
                    type: 'POST',
                    dataType: "json",
                    data: params,
                    success: function (r) {
                        if (r.code == 0) {
                            layer.msg($.i18n.prop('cancelSuccess'), {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                            window.location.href="member/order/list";
                        } else {
                            layer.msg(r.msg, {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                        }
                    }, error: function () {
                        layer.msg($.i18n.prop('cancelFail'), {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    }
                });
                layer.close(index);
            });
        });
    });
</script>
<!-- footer[[ -->
<jsp:include page="common/footer.jsp"/>
<!-- footer]] -->
</body>
</html>