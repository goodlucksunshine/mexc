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
<script type="text/javascript" src="static/js/ajax_loading.js"></script>
<script type="text/javascript" src="static/js/md5.js"></script>
<script type="text/javascript" src="static/js/header.js"></script>
<script type="text/javascript" src="static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="static/js/sockjs.min.js"></script>
<script type="text/javascript" src="static/js/jquery.i18n.properties-min-1.0.9.js"></script>
<script type="text/javascript" src="static/js/common.js"></script>
<script>
    var basePath = "<%=basePath%>";
    var current = "<%=request.getParameter("current")%>";
    var PATH = "${pageContext.request.contextPath}";
</script>
<div class="top top-inner">
    <div class="layout clearfix">
        <div class="logo">
            <a href="${basePath}" class="logo-pic"></a>
            <div class="top-transaction current">
                <div class="go-exchange current">
                    <a href="${basePth}trans/center" class="btn-exchange" id="go-exchange">
                        <i class="i-exchange"></i><span><spring:message code="tradeCenter"/></span>
                    </a>
                    <a href="javascript:void(0);" class="btn-down" id="btn-down">
                        <i class="i-down"></i>
                    </a>
                </div>
                <!-- 交易弹框 -->
                <div class="alert-transaction hide" id="alert-transaction">
                    <div class="clearfix mb-10">
                        <div class="fl tab marketList">
                            <%-- <a href="javascript:void(0);" class="current">BTC</a>
                             <a href="javascript:void(0);">ETH</a>--%>
                        </div>
                        <div class="fr search-box">
                            <span class="btn-search"><i class="i-search"></i></span>
                            <input type="text" class="vcoin-tab-search search-input"/>
                        </div>
                    </div>
                </div>
                <!-- 交易弹框 -->
            </div>
        </div>
        <div class="header-login">
            <a href="javascript:void(0);" class="btn-login hide" id="btn-login"><spring:message code="loginIn"/></a>
            <!-- 登录后 hide控制[[ -->
            <span class="top-center hide">
                <a href=""><spring:message code="helpCenter"/></a>
                <%--<a href="${basePth}member/order/list" class="order">订单管理</a>
                <a href="${basePth}member/asset/index" class="asset">资产</a>--%>
            </span>

            <div class="top-person order-manage hide">
                <a href="javascript:void(0);" class="btn-person order"><spring:message code="order"/></a>
                <ul class="down-person down-order-manage hide">
                    <li class="current">
                        <p class="normal-txt now-entrust"><spring:message code="currentEntrust"/></p>
                    </li>
                    <li>
                        <p class="normal-txt history-entrust"><spring:message code="historyEntrust"/></p>
                    </li>
                    <li>
                        <p class="normal-txt history-trade"><spring:message code="historyTrade"/></p>
                    </li>
                </ul>
            </div>

            <div class="top-person asset-record hide">
                <a href="javascript:void(0);" class="btn-person asset"><spring:message code="asset"/></a>
                <ul class="down-person down-asset-record hide">
                    <li class="current">
                        <p class="normal-txt my-asset"><spring:message code="myAsset"/></p>
                    </li>
                    <li>
                        <p class="normal-txt recharge-record"><spring:message code="rechargeRecord"/></p>
                    </li>
                    <li>
                        <p class="normal-txt cash-record"><spring:message code="cashRecord"/></p>
                    </li>
                </ul>
            </div>

            <!-- 个人中心 current选中，down-person hide隐藏 -->
            <div class="top-person ucenter hide">
                <a href="javascript:void(0);" class="btn-person btn-ucenter">
                    <i class="i-person"></i>
                </a>
                <ul class="down-person down-ucenter hide">
                    <li class="current">
                        <p class="font-weight" id="userCenter"><spring:message code="userCenter"/></p>
                        <p id="user"></p>
                    </li>
                    <li>
                        <p class="font-weight" id="assetAmount"></p>
                    </li>
                    <li>
                        <p class="btn-exit"><spring:message code="loginOut"/></p>
                    </li>
                </ul>
            </div>


            <!-- 登录后]] -->
            <a href="javascript:void(0);" class="btn-register hide" id="btn-register"><spring:message code="registerName"/></a>
            <div class="top-country">
                <a href="javascript:" class="change-country">
                    <i class="i-chinese"></i>
                </a>
                <div class="country-con hide">
                    <a class="chinese"><i class="i-chinese"></i><spring:message code="chinnese"/></a>
                    <a class="english"><i class="i-english"></i><spring:message code="english"/></a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- login box -->
<div id="login-box" class="hide">
    <div class="alert-box login-box">
        <a href="javascript:void(0);" class="i-alert-close"></a>
        <div class="alert-login">
            <div class="alert-box-head">
                <i class="logo-login"></i>
                <div class="title"><span><spring:message code="loginIn"/></span></div>
            </div>
            <div class="alert-box-con">
                <ul>
                    <li>
                        <input type="text" placeholder="<spring:message code="accountInputTips"/>" id="account" name="account" class="login-input"/>
                        <div class="error-box"><span class="error-tip"></span></div>
                    </li>
                    <li>
                        <input type="password" placeholder="<spring:message code="passwordInputTips"/>" id="password" name="password" class="login-input"/>
                        <div class="error-box"><span class="error-tip hide"></span></div>
                    </li>
                    <li>
                        <input type="text" placeholder="<spring:message code="captchaCodeInputTips"/>" id="captchaCode" name="captchaCode" class="login-input code-input"/>
                        <img src="code.do" alt="点击更换" class="code-img"/>
                        <div class="error-box"><span class="error-tip hide"></span></div>
                    </li>
                </ul>
            </div>
            <div class="alert-box-bottom">
                <a href="javascript:void(0);" id="login" class="btn-submit"><spring:message code="loginIn"/></a>
            </div>
            <div class="login-info">
                <a href="findPasswordPage"><spring:message code="forgetPassword"/></a><span>|</span><a href="to_reg"><spring:message code="registerName"/></a>
            </div>
        </div>
    </div>
    <div class="mask"></div>
</div>
<!-- login box -->
<!-- register box -->
<div id="reg-box" class="hide">
    <form id="reg-form" action="register" name="reg-form">
        <div class="alert-box">
            <a href="javascript:void(0);" class="i-alert-close"></a>
            <div class="alert-login">
                <div class="alert-box-head">
                    <i class="logo-login"></i>
                    <div class="title"><span><spring:message code="registerName"/></span></div>
                </div>
                <div class="alert-box-con">
                    <ul>
                        <li>
                            <input type="text" id="regAccount" name="regAccount" placeholder="<spring:message code="accountInputTips"/>"
                                   class="login-input"/>
                            <div class="error-box"></div>
                        </li>
                        <li>
                            <input type="password" id="regPassword" name="regPassword" placeholder="<spring:message code="passwordInputTips"/>"
                                   class="login-input"/>
                            <div class="error-box"></div>
                        </li>
                        <li>
                            <input type="password" id="regPassword2" name="regPassword2" placeholder="<spring:message code="passwordInputAgainTips"/>"
                                   class="login-input"/>
                            <div class="error-box"></div>
                        </li>
                        <li>
                            <input type="text" id="regCaptchaCode" name="regCaptchaCode" placeholder="<spring:message code="captchaCodeInputTips"/>" class="login-input code-input"/>
                            <img src="code.do" alt="点击更换" class="code-img" />
                            <div class="error-box"><span class="error-tip regCaptchaCodeTip"></span></div>
                        </li>
                        <input type="hidden" name="hexPassword" id="hexPassword"/>
                    </ul>
                    <label name="remember" class="in-check">
                        <input type="checkbox" checked="checked" class="check-box" name="remember" id="remember">
                        <span><spring:message code="agreeTips"/>&nbsp;&nbsp;<a href="serviceterm" class="">&lt;&lt;<spring:message code="serviceTerm"/>&gt;&gt;</a></span>
                    </label>
                </div>
                <div class="alert-box-bottom">
                    <button href="javascript:void(0);" id="register" class="btn-submit" type="submit"><spring:message code="registerName"/></button>
                </div>
            </div>
        </div>
        <div class="mask"></div>
    </form>
</div>
<!-- register box -->
<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
<script src="static/js/tradecenter/web.socket.trade.js"></script>

