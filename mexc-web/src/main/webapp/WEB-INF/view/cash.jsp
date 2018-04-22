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
    <title><spring:message code="cashRecord"/></title>
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

        <div class="login-record clearfix">
            <h3 class="global-tit"><spring:message code="cashRecord"/><span>／Cash Record</span>
               <%-- <a href="" class="fr btn-export"><i class="i-export"></i><span>导出历史成交记录</span></a>--%>
            </h3>
            <div class="global-table">
                <table>
                    <tr>
                        <th>币名</th>
                        <th><spring:message code="time"/></th>
                        <th><spring:message code="status"/></th>
                        <th>申请额</th>
                        <%--<th>提现地址</th>--%>
                        <th>txHash</th>
                        <th>手续费</th>
                        <%--<th>提现金额</th>--%>
                        <th>结算金额</th>
                        <th>检查</th>
                    </tr>
                    <c:forEach items="${cash}" var="item">
                        <tr>
                            <td>${item.vcoinName}</td>
                            <td><fmt:formatDate value="${item.txApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <%--<c:if test="${item.cashStatus==0}">提现待确认</c:if>--%>
                                <%--<c:if test="${item.cashStatus==1}">提现成功</c:if>--%>
                                <%--<c:if test="${item.cashStatus==2}">处理中</c:if>--%>
                                <%--<c:if test="${item.cashStatus==-1}">提现失败</c:if>--%>
                                    <c:choose>
                                        <c:when test="${item.cashStatus==1}">    提现成功
                                        </c:when>

                                        <c:otherwise>  处理中
                                        </c:otherwise>
                                    </c:choose>
                            </td>
                            <td><fmt:formatNumber type="number" value="${item.txApplyValue}" pattern="0.00000000" maxFractionDigits="8"/></td>
                            <%--<td>--%>
                                <%--<jsp:useBean id="tool" class="com.mexc.common.util.ThressDescUtil" scope="page" />--%>
                                <%--<c:out value="${tool.decodeAssetAddress(item.txCashAddress)}"/>--%>
                            <%--</td>--%>
                            <td>${item.txHash} </td>
                            <td><fmt:formatNumber type="number" value="${item.cashFee}" pattern="0.00000000" maxFractionDigits="8"/></td>
                            <%--<td><fmt:formatNumber type="number" value="${item.txAmount}" pattern="0.00000000"--%>
                                                  <%--maxFractionDigits="8"/></td>--%>
                            <td><fmt:formatNumber type="number" value="${item.actualAmount}" pattern="0.00000000" maxFractionDigits="8"/></td>
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