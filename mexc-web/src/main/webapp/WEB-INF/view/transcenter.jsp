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
    <title><spring:message code="trade"/></title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.js"></script>
    <script type="text/javascript" src="static/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="static/js/sockjs.min.js"></script>
</head>
<script>
    var marketId = '${market.id}';
    var vcoinId = '${vcoin.id}';
</script>
<body class="auto-height">
<jsp:include page="common/head.jsp"/>
<div class="transaction-container">
    <!-- inner[[[ -->
    <div class="transaction">
        <div class="transaction-left">
            <div class="bg-white">
               <div class="transaction-head clearfix">
                    <div class="transaction-msg">
                        <span class="name">
                            ${vcoin.vcoinNameEn}/${market.marketName}
                                <c:if test="${not empty login}">
                                    <c:choose>
                                        <c:when test="${collect==false}">
                                            <i class="btn-uncollect col-green hide"><spring:message code="deleteCollect"/></i>
                                            <i class="btn-collect col-green"><spring:message code="addCollect"/></i>
                                        </c:when>
                                        <c:when test="${collect==true}">
                                            <i class="btn-uncollect col-green"><spring:message code="deleteCollect"/></i>
                                            <i class="btn-collect col-green hide"><spring:message code="addCollect"/></i>
                                        </c:when>
                                    </c:choose>
                                </c:if>
                        </span>
                    </div>
                    <div class="transaction-msg">
                        <div>
                            <span class="tit"><spring:message code="latestPrice"/> </span>
                            <span class="con col-red">
                                <i id="latestPrice">0</i>
                                <b class="info">$<i id="usdValue">0</i></b>
                            </span>
                        </div>
                    </div>
                    <div class="transaction-msg">
                        <div>
                            <span class="tit"><spring:message code="tradeVolume"/></span>
                            <span class="con"><i class="sumPrice">0</i> ${market.marketName}</span>
                        </div>
                    </div>
                </div>
                <div class="transaction-chart">
                    <!-- 图表 -->
                    <iframe src="trans/toTradingViewPage?marketId=${market.id}&vcoinId=${vcoin.id}" width="100%"
                            scrolling="yes" height="404"></iframe>
                </div>
            </div>
            <div class="bg-white">
                <div class="entrust">
                    <div class="entrust-tit">
                        <ul class="clearfix">
                            <li class="current" data-entrust="current-entrust" onclick="shiftEnturstTab(this)"><a
                                    href="javascript:void(0);"><spring:message code="currentEntrust"/></a></li>
                            <li data-entrust="history-entrust" onclick="shiftEnturstTab(this)"><a
                                    href="javascript:void(0);"><spring:message code="historyEntrust"/></a></li>
                        </ul>
                    </div>
                        <%--当前委托--%>
                        <div class="entrust-con mini-table current-entrust">
                            <table>
                                <tr>
                                    <th><spring:message code="market"/></th>
                                    <th><spring:message code="time"/></th>
                                    <th><spring:message code="buyOrSell"/></th>
                                    <th><spring:message code="entrustPrice"/></th>
                                    <th><spring:message code="entrustNumber"/></th>
                                    <th><spring:message code="entrustAmount"/></th>
                                    <th><spring:message code="operate"/></th>
                                </tr>
                                <c:if test="${not empty login}">
                                    <c:forEach items="${current}" var="order">
                                        <tr>
                                            <td>${order.tradelVcoinNameEn}/${order.marketName}</td>
                                            <td>${order.createTime}</td>
                                            <td>
                                                <c:if test="${order.tradeType==1}">
                                                <span class="col-red">
                                                    <spring:message code="buy"/>
                                                </span>
                                                </c:if>
                                                <c:if test="${order.tradeType==2}">
                                                <span class="col-green">
                                                    <spring:message code="sell"/>
                                                </span>
                                                </c:if>
                                            </td>
                                            <td>
                                                <fmt:formatNumber type="number" value="${order.tradePrice}"
                                                                  pattern="0.00"
                                                                  maxFractionDigits="8"/>
                                            </td>
                                            <td><fmt:formatNumber type="number" value="${order.tradeRemainNumber}"
                                                                  pattern="0.00"
                                                                  maxFractionDigits="8"/> ${order.tradelVcoinNameEn}</td>

                                            <td><fmt:formatNumber type="number" value="${order.tradeRemainAmount}"
                                                                  pattern="0.00000000"
                                                                  maxFractionDigits="8"/></td>
                                            <td>
                                                <a href="javascript:void(0);" data-tradeNo="${order.tradeNo}" class="col-green killOrder">
                                                    <spring:message code="cancelEntrust"/>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </table>
                        </div>

                        <%--历史委托--%>
                        <div class="entrust-con mini-table history-entrust hide">
                            <table>
                                <tr>
                                    <th><spring:message code="market"/></th>
                                    <th><spring:message code="time"/></th>
                                    <th><spring:message code="buyOrSell"/></th>
                                    <th><spring:message code="entrustPrice"/></th>
                                    <th><spring:message code="entrustNumber"/></th>
                                    <th><spring:message code="entrustAmount"/></th>
                                    <th><spring:message code="status"/></th>
                                </tr>
                                <c:if test="${not empty login}">
                                    <c:forEach items="${history}" var="order">
                                        <tr>
                                            <td>${order.tradelVcoinNameEn}/${order.marketName}</td>
                                            <td>${order.createTime}</td>
                                            <td>
                                                <c:if test="${order.tradeType==1}">
                                                <span class="col-red">
                                                    <spring:message code="buy"/>
                                                </span>
                                                </c:if>
                                                <c:if test="${order.tradeType==2}">
                                                <span class="col-green">
                                                    <spring:message code="sell"/>
                                                </span>
                                                </c:if>
                                            </td>
                                            <td>
                                                <fmt:formatNumber type="number" value="${order.tradePrice}"
                                                                  pattern="0.00"
                                                                  maxFractionDigits="8"/>
                                            </td>
                                            <td><fmt:formatNumber type="number" value="${order.tradeNumber}"
                                                                  pattern="0.000000000"
                                                                  maxFractionDigits="2"/> ${order.tradelVcoinNameEn}</td>

                                            <td><fmt:formatNumber type="number" value="${order.tradeTotalAmount}"
                                                                  pattern="0.000000000"
                                                                  maxFractionDigits="8"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${order.status==4}">
                                                        <spring:message code="hasCancel"/>
                                                    </c:when>
                                                    <c:when test="${order.status==2}">
                                                        <spring:message code="hasComplete"/>
                                                    </c:when>
                                                    <c:when test="${order.status==5}">
                                                        <spring:message code="partComplete"/>
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </table>
                        </div>
                </div>
            </div>
        </div>
        <div class="transaction-mid">
            <div class="buy-and-sell">
                <div class="bg-white">
                    <div class="tit clearfix">
                        <span class="fl"><spring:message code="buyOrder"/></span>
                        <span class="fr" id="buySumSpan"><spring:message code="amount"/>:<span id="buyAmount">0</span> ${market.marketName}</span>
                    </div>
                    <div class="order-table">
                        <table id="buyTable">
                            <tr>
                                <th><spring:message code="entrustPrice"/></th>
                                <th><spring:message code="entrustNumber"/></th>
                                <th><spring:message code="amount"/></th>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="bg-white">
                    <div class="tit clearfix">
                        <span class="fl"><spring:message code="sellOrder"/></span>
                        <span class="fr"><spring:message code="amount"/>:<span id="saleAmount">0</span> ${market.marketName}</span>
                    </div>
                    <div class="order-table">
                        <table id="saleTable">
                            <tr>
                                <th><spring:message code="entrustPrice"/></th>
                                <th><spring:message code="entrustNumber"/></th>
                                <th><spring:message code="amount"/></th>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="bg-white trade-wrapper not-login">
                <div class="trade-box">
                    <div class="head">
                        <p>
                            <span>${market.marketName} <spring:message code="balance"/>：</span>
                            <span class="buy-balance-amount">
                                <c:if test="${not empty login}">
                                    <fmt:formatNumber type="number" value="${mainVCoinAsset.balanceAmount}"
                                                      pattern="0.000000000"
                                                      maxFractionDigits="8"/>
                                </c:if>
                                <c:if test="${empty login}">--</c:if>
                            </span>
                            <span style="font-size:8px;" class="buy-tips col-red"></span>
                        </p>
                    </div>
                    <form id="buyForm">
                        <input type="hidden" id="buyMarketId" name="marketId" value="${market.id}"/>
                        <input type="hidden" id="buyVcoinId" name="vcoinId" value="${vcoin.id}"/>
                        <input type="hidden" name="tradeType" value="1"/>
                        <ul class="form">
                            <li>
                                <label><spring:message code="price"/></label>
                                <input id="buyTradePrice" name="tradePrice" type="text"/>
                                <div class="price-adjustment">
                                    <a class="btn-top buyPriceTop"><i class="i-top"></i></a>
                                    <a class="btn-bottom buyPriceBottom"><i class="i-bottom"></i></a>
                                </div>
                            </li>
                            <li>
                                <label><spring:message code="number"/></label><input id="buyTradeNumber" name="tradeNumber" type="text"/>
                            </li>
                            <li class="disabled">
                                <label><spring:message code="amount"/></label><input readonly id="buyTradeAmount" name="tradeAmount" type="text"/>
                            </li>
                        </ul>
                        <ul class="percent clearfix">
                            <li onclick="tradeOperate(this,1,1)"><a href="javascript:void(0);">25%</a></li>
                            <li onclick="tradeOperate(this,1,2)"><a href="javascript:void(0);">50%</a></li>
                            <li onclick="tradeOperate(this,1,3)"><a href="javascript:void(0);">75%</a></li>
                            <li onclick="tradeOperate(this,1,4)"><a href="javascript:void(0);">100%</a></li>
                        </ul>
                       <%-- <div class="info">Service Fee <i id="buyFee">0</i>${vcoin.vcoinNameEn}/<i id="buyFeeRate">0</i>%
                        </div>--%>
                        <c:if test="${empty login}">
                            <div class="btn-not-login"><a href="javascript:void(0);" class="t-login-or-reg"><spring:message code="loginIn"/></a>or<a href="javascript:void(0);" class="t-reg"><spring:message code="registerName"/></a><spring:message code="trade"/></div>
                        </c:if>
                        <c:if test="${not empty login}">
                            <a href="javascript:void(0);" class="btn-buy"><spring:message code="buyIn"/>${vcoin.vcoinNameEn}</a>
                        </c:if>
                    </form>
                </div>
                <div class="trade-box">
                    <div class="head">
                        <p>
                            <span>${vcoin.vcoinNameEn} <spring:message code="balance"/>：</span>
                            <c:if test="${not empty login}">
                                <span class="sell-balance-amount">
                                    <fmt:formatNumber type="number" value="${currentVCoinAsset.balanceAmount}"
                                                      pattern="0.000000000"
                                                      maxFractionDigits="8"/>
                                </span>
                            </c:if>
                            <c:if test="${empty login}">
                                <span class="sell-balance-amount">--</span>
                            </c:if>
                            <span style="font-size:8px;" class="sell-tips col-red"></span>
                        </p>
                    </div>
                    <form id="saleForm">
                        <input type="hidden" id="saleMarketId" name="marketId" value="${market.id}"/>
                        <input type="hidden" id="saleVcoinId" name="vcoinId" value="${vcoin.id}"/>
                        <input type="hidden" name="tradeType" value="2"/>
                        <ul class="form">
                            <li>
                                <label><spring:message code="price"/></label>
                                <input id="sellTradePrice" type="text" name="tradePrice"/>
                                <div class="price-adjustment">
                                    <a class="btn-top sellPriceTop"><i class="i-top"></i></a>
                                    <a class="btn-bottom sellPriceBottom"><i class="i-bottom"></i></a>
                                </div>
                            </li>
                            <li>
                                <label><spring:message code="number"/></label><input id="sellTradeNumber" type="text" name="tradeNumber"/>
                            </li>
                            <li class="disabled">
                                <label><spring:message code="amount"/></label><input readonly id="sellTradeAmount" type="text" name="tradeAmount"/>
                            </li>
                        </ul>
                        <ul class="percent clearfix">
                            <li onclick="tradeOperate(this,2,1)"><a href="javascript:void(0);">25%</a></li>
                            <li onclick="tradeOperate(this,2,2)"><a href="javascript:void(0);">50%</a></li>
                            <li onclick="tradeOperate(this,2,3)"><a href="javascript:void(0);">75%</a></li>
                            <li onclick="tradeOperate(this,2,4)"><a href="javascript:void(0);">100%</a></li>
                        </ul>
                     <%--   <div class="info">Service Fee <i id="sellFee">0</i>${market.marketName}/<i
                                id="sellFeeRate">0</i>%
                        </div>--%>
                        <c:if test="${empty login}">
                            <div class="btn-not-login"><a href="javascript:void(0);" class="t-login-or-reg"><spring:message code="loginIn"/></a>or<a href="javascript:void(0);" class="t-reg"><spring:message code="registerName"/></a><spring:message code="trade"/></div>
                        </c:if>
                        <c:if test="${not empty login}">
                            <a href="javascript:void(0);" class="btn-sale"><spring:message code="sellOut"/>${vcoin.vcoinNameEn}</a>
                        </c:if>

                    </form>
                </div>
            </div>
        </div>
        <div class="transaction-right">
            <div class="bg-white last-sale">
                <div class="tit clearfix">
                    <span class="fl"><spring:message code="latestTrade"/></span>
                </div>
                <div class="order-table">
                    <table id="tradeTable">
                        <tr>
                            <th><spring:message code="time"/></th>
                            <th><spring:message code="price"/></th>
                            <th><spring:message code="number"/></th>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- inner]]] -->
</div>

<%--交易弹框--%>
<div class="mask tradeMask hide"></div>
<div class="alert-box-new tradeBox hide">
    <div class="alert-tit">
        <span class="tit htitle">Buy</span>
        <a class="btn-close"><i class="i-close"></i></a>
    </div>
    <div class="alert-bd">
        <ul>
            <li>
                <label class="alert-label"><spring:message code="number"/> (${vcoin.vcoinNameEn})</label>
                <div class="box-input">
                    <input type="text" id="hnumber" class="alert-input">
                </div>
            </li>
            <li>
                <label class="alert-label">${market.marketName}</label>
                <div class="box-input">
                    <input type="text" readonly id="hamount" class="alert-input">
                </div>
            </li>
            <li>
                <label class="alert-label"><spring:message code="price"/>  (${market.marketName})</label>
                <div class="box-input">
                    <input type="text" readonly id="hprice" class="alert-input">
                </div>
            </li>
        </ul>
    </div>
    <div class="alert-btn">
        <a class="btn-alert-sure">Buy</a>
    </div>
</div>

<!-- footer[[ -->
<jsp:include page="common/footer.jsp"/>
<!-- footer]] -->
<div class="mask hide"></div>
</body>
<script src="static/js/tradecenter/handTrade.js"></script>
<script>
    //最新价
    $("#buyTradePrice").val(Number(${latestTradePrice}).toFixed(8));
    $("#sellTradePrice").val(Number(${latestTradePrice}).toFixed(8));

    layui.config({
        base: "static/js/"
    }).use(['form', 'layer', 'jquery'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;
        var h = $(".transaction-chart").height();
        var w = $(".transaction-chart").width();
        $("iframe").attr("height", h);
        $("iframe").attr("width", w);

        //委托买
        $(".btn-buy").click(function () {
            var tradeAmount = $("#buyTradeAmount").val();
            var tradeNumber = $("#buyTradeNumber").val();
            var tradePrice = $("#buyTradePrice").val();

            updateBuyBalance();
            var buyBalance = $(".buy-balance-amount").text();
            if(Number(buyBalance) - Number(tradeAmount) < 0) {
                layer.msg($.i18n.prop('buyBalancetNotSuppoerTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                return;
            }

            if((Number(tradePrice) * Number(tradeNumber)).toFixed(8) > Number(tradeAmount).toFixed(8)) {
                layer.msg($.i18n.prop('checkAmountTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                return;
            }
            if(tradeAmount !="" && Number(tradeAmount)==0) {
                layer.msg($.i18n.prop('amountNotZeroTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                return;
            }
            $.ajax({
                url: "${basePath}member/trans/entrustTrade",
                type: 'POST',
                dataType: "json",
                data: $("#buyForm").serialize(),
                success: function (r) {
                    if (r.code == 0) {
                        updateBuyBalance();
                        layer.msg($.i18n.prop('buySuccessTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    } else {
                        layer.msg(r.msg, {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    }
                }
            });
        });


        //委托卖
        $(".btn-sale").click(function () {
            var tradeNumber = $("#sellTradeNumber").val();
            var tradeAmount = $("#sellTradeAmount").val();
            var tradePrice = $("#sellTradePrice").val();

            updateSellBalance();
            var sellBalance = $(".sell-balance-amount").text();
            if(Number(sellBalance) < 0) {
                layer.msg($.i18n.prop('sellSuccessTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                return;
            }
            if((Number(tradePrice)*Number(tradeNumber)).toFixed(8) > Number(tradeAmount).toFixed(8)) {
                layer.msg($.i18n.prop('checkAmountTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                return;
            }
            if(tradeAmount != "" && Number(tradeAmount)==0) {
                layer.msg($.i18n.prop('amountNotZeroTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                return;
            }
            $.ajax({
                url: "member/trans/entrustTrade",
                type: 'POST',
                dataType: "json",
                data: $("#saleForm").serialize(),
                success: function (r) {
                    if (r.code == 0) {
                        updateSellBalance();
                        layer.msg($.i18n.prop('sellSuccessTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    } else {
                        layer.msg(r.msg, {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    }
                }
            });
        });

        //更新买资产
        function updateBuyBalance() {
            var memberId = '${memberId}';
            $.ajax({
                url: "${basePath}trans/getMarketVCoinBalance",
                type: 'POST',
                dataType: "json",
                data: {
                    marketId : marketId,
                    memberId : memberId
                },
                success: function (r) {
                    if (r.code == 0) {
                        $(".buy-balance-amount").text(Number(r.balance).toFixed(8));
                    }
                }
            });
        }

        //更新卖资产
        function updateSellBalance() {
            var memberId = '${memberId}';
            $.ajax({
                url: "${basePath}trans/getTradeVCoinBalance",
                type: 'POST',
                dataType: "json",
                data: {
                    vcoinId : vcoinId,
                    memberId : memberId
                },
                success: function (r) {
                    if (r.code == 0) {
                        $(".sell-balance-amount").text(Number(r.balance).toFixed(8));
                    }
                }
            });
        }

        $(".buyPriceTop").bind("click",function() {
            var tradePrice = $("#buyTradePrice").val();
            if(tradePrice != "" && tradePrice != undefined) {
                tradePrice = Number(tradePrice) + Number(0.00000001);
                $("#buyTradePrice").val(Number(tradePrice).toFixed(8));
                fillBuyAmountAndFee();
            }
        });

        $(".buyPriceBottom").bind("click",function() {
            var tradePrice = $("#buyTradePrice").val();
            if(tradePrice != "" && tradePrice != undefined) {
                if(Number(tradePrice) <= Number(0)) {
                    return;
                }
                tradePrice = Number(tradePrice) - Number(0.00000001);
                $("#buyTradePrice").val(Number(tradePrice).toFixed(8));
                fillBuyAmountAndFee();
            }
        });

        $(".sellPriceTop").bind("click",function() {
            var tradePrice = $("#sellTradePrice").val();
            if(tradePrice != "" && tradePrice != undefined) {
                tradePrice = Number(tradePrice) + Number(0.00000001);
                $("#sellTradePrice").val(Number(tradePrice).toFixed(8));
                fillSellAmountAndFee();
            }
        });

        $(".sellPriceBottom").bind("click",function() {
            var tradePrice = $("#sellTradePrice").val();
            if(tradePrice != "" && tradePrice != undefined) {
                if(Number(tradePrice) <= Number(0)) {
                    return;
                }
                tradePrice = Number(tradePrice) - Number(0.00000001);
                $("#sellTradePrice").val(Number(tradePrice).toFixed(8));
                fillSellAmountAndFee();
            }
        });


        //收藏
        $(".btn-collect").click(function () {
            $(this).parent().find("i").removeClass("hide");
            $(this).addClass("hide");
            $.ajax({
                url: "member/trans/collect",
                type: 'POST',
                dataType: "json",
                data: {
                    marketId: marketId,
                    vcoinId: vcoinId
                },
                success: function (r) {
                    if (r.code == 0) {
                        layer.msg($.i18n.prop('addCollectSuccessTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    } else {
                        layer.msg(r.msg, {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    }
                }
            });
        });

        //取消收藏
        $(".btn-uncollect").click(function () {
            $(this).parent().find("i").removeClass("hide");
            $(this).addClass("hide");
            $.ajax({
                url: "member/trans/uncollect",
                type: 'POST',
                dataType: "json",
                data: {
                    marketId: marketId,
                    vcoinId: vcoinId
                },
                success: function (r) {
                    if (r.code == 0) {
                        layer.msg($.i18n.prop('deleteCollectSuccessTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    } else {
                        layer.msg(r.msg, {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    }
                }
            });
        });

        //撤单
        $(document).on("click",".killOrder",function () {
            var tradeNo = $(this).attr("data-tradeNo");
            var tradeType = $(this).attr("data-type");
            var params = {
                "tradeNo": tradeNo
            };
            layer.confirm($.i18n.prop('confirmCancelOrderTips')+'？',{icon:3, title:$.i18n.prop('infoTips')},function(index){
                $.ajax({
                    url: "member/trans/cancelEntrustTrade",
                    type: 'POST',
                    dataType: "json",
                    data: params,
                    success: function (r) {
                        if (r.code == 0) {
                            if(tradeType == 1) {
                                updateBuyBalance();
                            }else if(tradeType == 2) {
                                updateSellBalance();
                            }
                            layer.msg($.i18n.prop('cancelSuccess'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                        } else {
                            layer.msg(r.msg, {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                        }
                    }, error: function () {
                        layer.msg($.i18n.prop('cancelFail'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    }
                });
                layer.close(index);
            });
        });

        //控制买入价格为8位
        $("#buyTradePrice").keyup(function () {
            $(".buy-tips").text("");
            var tradePrice = $(this).val();
            var reg = new RegExp("^[0-9]+([.][0-9]{1}){0,8}$");
            if(tradePrice!="" && !reg.test(tradePrice)){//不是小数和数字
                tradePrice = tradePrice.replace(/[^\d.]/g,"");
            }
            var part = tradePrice.split(".");
            if (part.length < 2) {
                $(this).val(part[0]);
            }else {
                if (part[1].length > 4) {
                    $(this).val(part[0]+"."+part[1].substring(0,4));
                }else {
                    $(this).val(part[0]+"."+part[1]);
                }
            }

            fillBuyAmountAndFee();
        });

        //控制卖价输入为8位
        $("#sellTradePrice").keyup(function () {
            $(".sell-tips").text("");
            var tradePrice = $(this).val();
            var reg = new RegExp("^[0-9]+([.][0-9]{1}){0,8}$");
            if(tradePrice!="" && !reg.test(tradePrice)){//不是小数和数字
                tradePrice = tradePrice.replace(/[^\d.]/g,"");
            }
            var part = tradePrice.split(".");
            if (part.length < 2) {
                $(this).val(part[0]);
            }else {
                if (part[1].length > 4) {
                    $(this).val(part[0]+"."+part[1].substring(0,4));
                }else {
                    $(this).val(part[0]+"."+part[1]);
                }
            }

            fillSellAmountAndFee();
        });

        //控制买入数量
        $("#buyTradeNumber").keyup(function () {
            $(".buy-tips").text("");
            var tradeNumber = $(this).val();
            var reg = new RegExp("^[0-9]+([.][0-9]{1}){0,2}$");
            if(tradeNumber!="" && !reg.test(tradeNumber)){//不是小数和数字
                tradeNumber = tradeNumber.replace(/[^\d.]/g,"");
            }
            var part = tradeNumber.split(".");
            if (part.length < 2) {
                $(this).val(part[0]);
            }else {
                if (part[1].length > 4) {
                    $(this).val(part[0]+"."+part[1].substring(0,4));
                }else {
                    $(this).val(part[0]+"."+part[1]);
                }
            }

            fillBuyAmountAndFee();
        });

        //控制卖出数量
        $("#sellTradeNumber").keyup(function () {
            $(".sell-tips").text("");
            var tradeNumber = $(this).val();
            var reg = new RegExp("^[0-9]+([.][0-9]{1}){0,2}$");
            if(tradeNumber!="" && !reg.test(tradeNumber)){//不是小数和数字
                tradeNumber = tradeNumber.replace(/[^\d.]/g,"");
            }
            var part = tradeNumber.split(".");
            if (part.length < 2) {
                $(this).val(part[0]);
            }else {
                if (part[1].length > 4) {
                    $(this).val(part[0]+"."+part[1].substring(0,4));
                }else {
                    $(this).val(part[0]+"."+part[1]);
                }
            }

            fillSellAmountAndFee();
        });

        //登陆or注册
        $(".t-login-or-reg").click(function () {
            $("#login-box").removeClass("hide");
        });

        //注册
        $(".t-reg").click(function () {
            $("#reg-box").removeClass("hide");
        });


        function fillBuyAmountAndFee() {
            var tradePrice = $("#buyTradePrice").val();
            var tradeNumber = $("#buyTradeNumber").val();
            var buyBalance = $(".buy-balance-amount").text();

            var tradeAmount = Number(tradePrice) * Number(tradeNumber);
            if (Number(tradeAmount) > Number(buyBalance)) {
                if($.cookie("u_id")) {
                    layer.msg($.i18n.prop('buyBalancetNotSuppoerTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                }
                return;
            }
            $("#buyTradeAmount").val(Number(tradeAmount).toFixed(8));
        }

        function fillSellAmountAndFee() {
            var tradePrice = $("#sellTradePrice").val();
            var tradeNumber = $("#sellTradeNumber").val();
            var sellBalance = $(".buy-balance-amount").text();
            if (Number(tradeNumber) > Number(sellBalance)) {
                if($.cookie("u_id")) {
                    layer.msg($.i18n.prop('sellBeyondTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                }
                return ;
            }
            var tradeAmount = Number(tradePrice) * Number(tradeNumber);
            $("#sellTradeAmount").val(Number(tradeAmount).toFixed(8));
        }
    });


    //ajax获取历史委托单
    function reloadHistoryEntrust() {
        $.ajax({
            url: "trans/getHistoryEntrustList",
            type: 'POST',
            dataType: "json",
            success: function (r) {
                if (r.code == 0) {
                    // console.log("history",r.history);
                    $(".history-entrust").find("table").find("tr:not(:first)").empty("");
                    //var history = eval('(' + r.history + ')');
                    var history = r.history;
                    for(var i=0;i<history.length;i++) {
                        var tradeTypeStr = "";
                        if(history[i].tradeType==1) {
                            tradeTypeStr = "<span class='col-green'>"+$.i18n.prop('buy')+"</span>";
                        }else if(history[i].tradeType==2) {
                            tradeTypeStr = "<span class='col-red'>"+$.i18n.prop('sell')+"</span>";
                        }
                        var statusStr="";
                        if(history[i].status==4) {
                            statusStr=$.i18n.prop('hasCancel');
                        }else if(history[i].status==5) {
                            statusStr=$.i18n.prop('partCancel');
                        }else if(history[i].status==2) {
                            statusStr=$.i18n.prop('hasComplete');
                        }

                        $(".history-entrust").find("table").append("<tr>\n" +
                            " <td>"+history[i].tradelVcoinNameEn+"/"+history[i].marketName+"</td>" +
                            " <td>"+history[i].createTime+ "</td>" +
                            " <td>" + tradeTypeStr+ " </td>" +
                            " <td>"+Number(history[i].tradePrice).toFixed(8)+"</td>" +
                            " <td>"+Number(history[i].tradeNumber).toFixed(4)+" "+history[i].tradelVcoinNameEn+"</td>" +
                            " <td>"+Number(history[i].tradeTotalAmount).toFixed(8)+"</td>" +
                            " <td>" + statusStr+ "</td>" +
                            "</tr>");
                    }
                }
            }
        });
    }


    //买卖价格切换
    function tradeOperate(this_, tradeType, pecentType) {
        <c:if test="${empty login}">
            return;
        </c:if>
        /*$(this_).parent().find("li").removeClass("current");
        $(this_).addClass("current");*/

        if (tradeType == 1) {
            var buyBalanceAmount = $(".buy-balance-amount").text();
            var tradePrice = $("#buyTradePrice").val();

            if(buyBalanceAmount =="" || buyBalanceAmount == "--") {
                return;
            }
            if(tradePrice == "" || Number(tradePrice)==0) {
                return;
            }
            var tradeNumber;
            var tradeAmount;
            if (pecentType == 1) {
                tradeAmount = Number(buyBalanceAmount) * Number(0.25);
                tradeNumber = Number(tradeAmount) / Number(tradePrice);
                $("#buyTradeNumber").val(Number(tradeNumber).toFixed(8));
                $("#buyTradeAmount").val(Number(tradeAmount).toFixed(8));
            } else if (pecentType == 2) {
                tradeAmount = Number(buyBalanceAmount) * Number(0.5);
                tradeNumber = Number(tradeAmount) / Number(tradePrice);
                $("#buyTradeNumber").val(Number(tradeNumber).toFixed(8));
                $("#buyTradeAmount").val(Number(tradeAmount).toFixed(8));
            } else if (pecentType == 3) {
                tradeAmount = Number(buyBalanceAmount) * Number(0.75);
                tradeNumber = Number(tradeAmount) / Number(tradePrice);
                $("#buyTradeNumber").val(Number(tradeNumber).toFixed(8));
                $("#buyTradeAmount").val(Number(tradeAmount).toFixed(8));
            } else if (pecentType == 4) {
                tradeAmount = Number(buyBalanceAmount) * Number(1);
                tradeNumber = Number(tradeAmount) / Number(tradePrice);
                $("#buyTradeNumber").val(Number(tradeNumber).toFixed(8));
                $("#buyTradeAmount").val(Number(tradeAmount).toFixed(8));
            }
        } else if (tradeType == 2) {
            var sellBalanceAmount = $(".sell-balance-amount").text();
            var tradePrice = $("#sellTradePrice").val();
            if(sellBalanceAmount =="" || sellBalanceAmount == "--") {
                return;
            }
            if(tradePrice == "" || Number(tradePrice)==0) {
                return;
            }
            var tradeNumber;
            var tradeAmount;
            if (pecentType == 1) {
                tradeNumber = Number(sellBalanceAmount) * Number(0.25);
                tradeAmount = Number(tradeNumber) * Number(tradePrice);
                $("#sellTradeNumber").val(Number(tradeNumber).toFixed(8));
                $("#sellTradeAmount").val(Number(tradeAmount).toFixed(8));
            } else if (pecentType == 2) {
                tradeNumber = Number(sellBalanceAmount) * Number(0.5);
                tradeAmount = Number(tradeNumber) * Number(tradePrice);
                $("#sellTradeNumber").val(Number(tradeNumber).toFixed(8));
                $("#sellTradeAmount").val(Number(tradeAmount).toFixed(8));
            } else if (pecentType == 3) {
                tradeNumber = Number(sellBalanceAmount) * Number(0.75);
                tradeAmount = Number(tradeNumber) * Number(tradePrice);
                $("#sellTradeNumber").val(Number(tradeNumber).toFixed(8));
                $("#sellTradeAmount").val(Number(tradeAmount).toFixed(8));
            } else if (pecentType == 4) {
                tradeNumber = Number(sellBalanceAmount);
                tradeAmount = Number(tradeNumber) * Number(tradePrice);
                $("#sellTradeNumber").val(Number(tradeNumber).toFixed(8));
                $("#sellTradeAmount").val(Number(tradeAmount).toFixed(8));
            }
        }
    }

    /**
     * 当前委托和历史委托切换
     * @param this_
     */
    function shiftEnturstTab(this_) {
        $(this_).parent().find("li").removeClass("current");
        $(this_).addClass("current");
        var entrust = $(this_).attr("data-entrust");
        $("." + entrust).parent().find(".entrust-con").addClass("hide");
        $("." + entrust).removeClass("hide");
        if(entrust == "history-entrust") {
            reloadHistoryEntrust();
        }
    }



    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }

</script>

</html>
