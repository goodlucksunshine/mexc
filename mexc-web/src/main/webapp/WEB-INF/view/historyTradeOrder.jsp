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
    <title><spring:message code="historyTrade"/></title>
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
            <h3 class="global-tit"><spring:message code="historyTrade"/><span>／History trade order</span></h3>
            <div class="global-table">
                <table>
                    <tr>
                        <th><spring:message code="market"/></th>
                        <th><spring:message code="time"/></th>
                        <th><spring:message code="buyOrSell"/></th>
                        <th><spring:message code="price"/></th>
                        <th><spring:message code="number"/></th>
                        <th><spring:message code="amount"/></th>
                        <th><spring:message code="serviceFee"/></th>
                    </tr>
                    <c:forEach items="${historyTrade}" var="order">
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
                            <td><fmt:formatNumber type="number" value="${order.tradeAvgPrice}" pattern="0.00000000"
                                                  maxFractionDigits="8"/>${order.marketName}</td>
                            <td><fmt:formatNumber type="number" value="${order.tradeTotalNumber}" pattern="0.00000000"
                                                  maxFractionDigits="8"/>${order.tradelVcoinNameEn}</td>
                            <td><fmt:formatNumber type="number" value="${order.tradeTotalAmount}" pattern="0.00000000"
                                                  maxFractionDigits="8"/></td>
                            <td>
                                <fmt:formatNumber type="number" value="${order.tradeTotalFee}" pattern="0.00000000"
                                                  maxFractionDigits="8"/>
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
<!-- footer[[ -->
<jsp:include page="common/footer.jsp"/>
<!-- footer]] -->
</body>
</html>