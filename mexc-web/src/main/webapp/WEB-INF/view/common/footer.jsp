<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
<!-- footer[[ -->
<div class="footer-box">
    <div class="footer">
        <div class="layout">
            <ul class="clearfix">
                <li><a href="${basePath}about"><spring:message code="aboutUs"/></a></li>
                <li><a href="${basePath}serviceterm"><spring:message code="terms"/></a></li>
                <li><a href="${basePath}privacypolicy"><spring:message code="privacy"/></a></li>
                <li><a href="${basePath}servicefee"><spring:message code="fees"/></a></li>
                <li><a href="${basePath}contact"><spring:message code="contactUs"/></a></li>
            </ul>
        </div>

    </div>
    <div class="copyright">
        <span>© 2018 Mexc.io All Rights Reserved</span>
    </div>
</div>
<!-- footer]] -->