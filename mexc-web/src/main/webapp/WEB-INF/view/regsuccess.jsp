<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ page isELIgnored="false" %>
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
    <title>$.i18n.prop('index')</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.all.js"></script>
</head>
<body style="height: 100%">
<!-- header[ -->
<jsp:include page="common/head.jsp"/>
<div class="inner-container error-page">
    <!-- inner[[[ -->
    <div class="txt-reminder clearfix">
        <p>
            <c:if test="${res.code == 0}">
                <spring:message code="registerSuccessTips" arguments="${res.account}" argumentSeparator=" "/>
            </c:if>
            <c:if test="${res.code!=0}">
                ${res.msg}
            </c:if>
        </p>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
</body>
</html>