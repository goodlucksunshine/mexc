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
    <title><spring:message code="rechargeRecord"/></title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.js"></script>
</head>
<body>
<!-- header[[[ -->
<jsp:include page="common/head.jsp">
    <jsp:param name="current" value="asset"/>
</jsp:include>
<!-- header]]] -->

<div class="inner-container">
    <!-- inner[[[ -->
    <div class="layout order-list min-height">
        <div class="login-record">
            <h3 class="global-tit"><spring:message code="rechargeRecord"/><span>／Withdraw Record</span></h3>
            <div class="global-table">
                <table>
                    <tr>
                        <th><spring:message code="time"/></th>
                        <th><spring:message code="vcoin"/></th>
                        <th><spring:message code="rechargeAmount"/></th>
                        <%--<th><spring:message code="rechargeAddress"/></th>--%>
                        <th>txHash</th>
                        <th>检查</th>
                    </tr>
                    <c:forEach items="${recharge}" var="item">
                        <tr>
                            <td><fmt:formatDate value="${item.receiptTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${item.txToken}</td>
                            <td>
                               <fmt:formatNumber type="number" value="${item.rechargeValue}" pattern="0.000000000"
                                                      maxFractionDigits="8"/>
                            </td>
                            <%--<td>${item.rechargeAddress}</td>--%>
                            <td>${item.txHash} </td>
                            <td>
                                <c:if test="${item.vcoinToken=='BTC' or item.vcoinToken=='btc' }">
                                    <a href="https://blockchain.info/tx/${item.txHash}" target="_blank">检查</a>
                                </c:if>
                                <c:if test="${item.vcoinToken=='ETH' or item.vcoinToken=='eth' }">
                                    <a href="https://etherscan.io/tx/${item.txHash}" target="_blank">检查</a>
                                </c:if>
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