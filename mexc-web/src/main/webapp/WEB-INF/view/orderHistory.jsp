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
    <title><spring:message code="historyRecord"/></title>
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
                        <th><spring:message code="rechargeAddress"/></th>
                        <th>txHash</th>
                    </tr>
                    <c:forEach items="${recharge}" var="item">
                        <tr>
                            <td><fmt:formatDate value="${item.receiptTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${item.txToken}</td>
                            <td>
                               <fmt:formatNumber type="number" value="${item.rechargeValue}" pattern="0.000000000"
                                                      maxFractionDigits="8"/>
                            </td>
                            <td>${item.rechargeAddress}</td>
                            <td>${item.txHash}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <div class="login-record clearfix">
            <h3 class="global-tit"><spring:message code="cashRecord"/><span>／Cash Record</span>
               <%-- <a href="" class="fr btn-export"><i class="i-export"></i><span>导出历史成交记录</span></a>--%>
            </h3>
            <div class="global-table">
                <table>
                    <tr>
                        <th><spring:message code="time"/></th>
                        <th><spring:message code="status"/></th>
                        <th><spring:message code="applyAmount"/></th>
                        <th><spring:message code="cashAddress"/></th>
                        <th>txHash</th>
                        <th><spring:message code="serviceFee"/></th>
                        <th><spring:message code="settlementAmount"/></th>
                    </tr>
                    <c:forEach items="${cash}" var="item">
                        <tr>
                            <td><fmt:formatDate value="${item.txApplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:if test="${item.tradeType==0}"><spring:message code="waitingAudit"/></c:if>
                                <c:if test="${item.tradeType==1}"><spring:message code="passAudit"/></c:if>
                                <c:if test="${item.tradeType==-1}"><spring:message code="noPassAudit"/></c:if>
                                </span>
                            </td>
                            <td><fmt:formatNumber type="number" value="${item.txApplyValue}" pattern="0.00000000"
                                                  maxFractionDigits="8"/></td>
                            <td>${item.txCashAddress}</td>
                            <td>${item.txHash}</td>
                            <td>
                                <fmt:formatNumber type="number" value="${item.txGaslimit}" pattern="0.00000000"
                                                  maxFractionDigits="8"/>
                            </td>

                            <td><fmt:formatNumber type="number" value="${item.txAmount}" pattern="0.00000000"
                                                  maxFractionDigits="8"/></td>

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