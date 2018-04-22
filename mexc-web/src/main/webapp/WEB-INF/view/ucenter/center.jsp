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
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>

<html>
<head lang="en">
    <meta charset="UTF-8">
    <title><spring:message code="userCenter"/></title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="static/layui/layui.all.js"></script>
</head>
<body>
<!-- header[[[ -->
<jsp:include page="../common/head.jsp">
    <jsp:param name="current" value="ucenter"/>
</jsp:include>
<!-- header]]] -->

<div class="inner-container">
    <!-- inner[[[ -->
    <div class="person-bd">
        <div class="layout clearfix">
            <div class="fl person-msg">
                <i class="i-user"></i>
                <div class="con">
                    <p class="account">${loginMember.account}</p>
                    <p class="assets"><spring:message code="asset"/>：
                        <span class="col-yellow btc-value"></span> BTC/
                        <span class="col-yellow usd-value"></span> USD
                    </p>
                    <p class="time">
                        <spring:message code="lastLoginTime"/>: <fmt:formatDate value="${loginMember.lastLoginTime}" pattern="yyyy-MM-dd hh:ss:mm"/>
                        <span>IP: ${loginMember.lastLoginIp}</span>
                    </p>
                </div>
            </div>
            <div class="fr person-step">
                <ul class="clearfix">
                    <li>
                        <p><spring:message code="cashLimit24h"/>：<span class="col-yellow">${level1}</span>BTC</p>
                        <i class="i-step1"></i>
                    </li>
                    <li <c:if test="${loginMember.authLevel lt 2}">class="disabled"</c:if>>
                        <p><spring:message code="cashLimit24h"/>：<span class="col-yellow">${level2}</span>BTC</p>
                        <i class="i-step2"></i>
                        <c:if test="${loginMember.authLevel lt 2}">
                            <a href="member/ucenter/identityAuthPage"><spring:message code="finishIdentityAuth"/></a>
                        </c:if>
                    </li>
                    <li <c:if test="${loginMember.authLevel lt 3}">class="disabled"</c:if>>
                        <p><spring:message code="advanceCashAmount"/></p>
                        <i class="i-step3"></i>
                        <a href="${basePath}contact"><spring:message code="contact"/></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="person-operate layout clearfix">
        <div class="fl">
            <div class="bg-white person-pwd">
                <div class="bg-white-tit"><spring:message code="loginPassword"/></div>
                <div class="bg-white-con clearfix">
                    <i class="i-lock"></i>
                    <div class="con">
                        <p class="tit"><spring:message code="loginPassword"/></p>
                        <p class="info"><spring:message code="whenLoginUse"/></p>
                        <a href="member/ucenter/changePwdPage" class="btn-modify"><spring:message code="changePassword"/></a>
                    </div>
                </div>
            </div>
            <div class="bg-white person-validate">
                <div class="bg-white-tit"><spring:message code="identityAuth"/></div>
                <div class="bg-white-con">
                    <i class="i-id"></i>
                    <div class="con">
                        <p class="tit"><spring:message code="identityAuth"/></p>
                        <%--<p class="info">副标题</p>--%>
                        <c:if test="${idenAuth!=null}">
                            <c:if test="${idenAuth.status==0}">
                                <a href="javascript:void(0);" class="btn-submit"><spring:message code="hasUpload"/></a>
                            </c:if>
                            <c:if test="${idenAuth.status==1}">
                                <a href="javascript:void(0);" class="btn-submit"><spring:message code="hasIdentity"/></a>
                            </c:if>
                            <c:if test="${idenAuth.status==2}">
                                <a href="member/ucenter/identityAuthPage" class="btn-submit"><spring:message code="noPassAudit"/></a>
                            </c:if>
                        </c:if>
                        <c:if test="${idenAuth==null}">
                            <a href="member/ucenter/identityAuthPage" class="btn-submit"><spring:message code="uploadIdentityFile"/></a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="fl">
            <div class="bg-white person-two-validate">
                <div class="bg-white-tit"><spring:message code="secondAuth"/></div>
                <div class="bg-white-con">
                    <%--  <div>
                          <i class="i-phone"></i>
                          <div class="con">
                              <p class="tit">手机验证</p>
                              <p class="info">提现，修改密码，及安全设置时用以收取验证短信</p>
                              <c:if test="${loginMember.secondAuthType==0}">
                                  <a href="javascript:;" class="btn-submit">绑定</a>
                              </c:if>
                              <c:if test="${loginMember.secondAuthType==1}">
                                  <a href="javascript:;" class="btn-submit">关闭</a>
                              </c:if>
                          </div>
                      </div>
                      <div class="line"></div>--%>
                    <div>
                        <i class="i-google"></i>
                        <div class="con">
                            <p class="tit"><spring:message code="googleAuth"/></p>
                            <p class="info"><spring:message code="googleAuthUseTips"/></p>
                            <c:if test="${loginMember.secondAuthType==0}">
                                <a href="member/ucenter/googleAuthPage" class="btn-submit"><spring:message code="open"/></a>
                            </c:if>
                            <c:if test="${loginMember.secondAuthType==2}">
                                <a href="member/ucenter/closeGoogleAuthPage" class="btn-submit"><spring:message code="close"/></a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="layout">
        <div class="login-record">
            <h3 class="global-tit"><spring:message code="latestLogin"/><span>／Last login</span></h3>
            <div class="global-table">
                <table>
                    <tr>
                        <th><spring:message code="loginTime"/></th>
                        <th>IP<spring:message code="address"/></th>
                        <th><spring:message code="loginAddress"/></th>
                    </tr>
                    <c:forEach var="log" items="${logList}">
                        <tr>
                            <td><fmt:formatDate value="${log.loginTime}" pattern="yyyy-MM-dd hh:ss:mm"/></td>
                            <td>${log.loginIp}</td>
                            <td>${log.loginAddress}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
    <!-- inner]]] -->
</div>


<!-- footer[[ -->
<jsp:include page="../common/footer.jsp"/>

<!-- footer]] -->
</body>
</html>