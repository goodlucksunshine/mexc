<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title><spring:message code="asset"/></title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.all.js"></script>
    <script type="text/javascript" src="static/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="static/js/clipboard.min.js"></script>
</head>
<body>
<jsp:include page="common/head.jsp">
    <jsp:param name="current" value="asset"/>
</jsp:include>
<div id="qrcode" style="display: none;"></div>
<div class="inner-container">
    <!-- inner[[[ -->
    <div class="layout">
        <div class="balances clearfix">
            <div class="fl">
                <h3 class="global-tit"><spring:message code="asset"/><span>／Balances</span></h3>
                <p class="balances-info"><spring:message code="ownerAsset"/>： <span><i class="btc-value"></i> BTC / $ <i class="usd-value"></i></span></p>
            </div>

            <div class="fr">
                <div class="hide-zero">
                    <input type="checkbox" class="input-hide"><label for=""><spring:message code="hideZeroAmount"/></label>
                </div>
                <div class="search-box">
                    <span class="btn-search"><i class="i-search"></i></span>
                    <input type="text" class="search-input vcoin-search"/>
                </div>
            </div>
        </div>
    </div>

    <div class="layout min-height">
        <div class="global-table balances-table">
            <table>
                <tr>
                    <th><spring:message code="vcoin"/></th>
                    <th><spring:message code="name"/></th>
                    <%--<th><spring:message code="amount"/></th>--%>
                    <th><spring:message code="useableAmount"/></th>
                    <th><spring:message code="frozenAmount"/></th>
                    <th>BTC<spring:message code="overValue"/></th>
                </tr>
                <tbody>
                    <c:forEach items="${assetList}" var="asset" varStatus="index">
                        <tr class="VCOIN-${asset.vcoinNameEn}" data-totalAmount="${asset.totalAmount}">
                            <td>
                                    <%--<img class="img-icon" src="/file/download/${asset.icon}"/>--%>
                                    ${asset.vcoinNameEn}
                            </td>
                            <td>${asset.vcoinNameFull}</td>
                            <%--<td><fmt:formatNumber type="number" value="${asset.totalAmount}" pattern="0.00000000"--%>
                                                  <%--maxFractionDigits="8"/></td>--%>
                            <td><fmt:formatNumber type="number" value="${asset.balanceAmount}" pattern="0.00000000"
                                                  maxFractionDigits="8"/></td>
                            <td><fmt:formatNumber type="number" value="${asset.frozenAmount}" pattern="0.00000000"
                                                  maxFractionDigits="8"/></td>
                            <td><fmt:formatNumber type="number" value="${asset.btcAmount}" pattern="0.00000000"
                                                  maxFractionDigits="8"/></td>
                            <td width="160">
                                <a href="javascript:void(0);"
                                   class="btn-submit btn-table <c:if test="${asset.canRecharge==-1}">disabled</c:if>"><spring:message code="recharge"/></a>
                                <a href="javascript:void(0);"
                                   class="btn-modify btn-table <c:if test="${asset.canCash==-1}">disabled</c:if>"
                                   asset="${asset.assetId}"><spring:message code="cash"/></a>
                            </td>
                        </tr>
                        <!-- 展开之后 -->
                        <tr class="hide">
                            <td colspan="7">
                                <div class="recharge clearfix">
                                    <div class="fl">
                                        <p class="recharge-name">${asset.vcoinNameEn} <spring:message code="rechargeAddress"/>:</p>
                                        <div class="recharge-addr">
                                            <input type="text" class="balances-input" id="bar${index.index}"
                                                   value="${asset.walletAddress}">
                                            <a href="javascript:void(0);" class="btn-copy" data-clipboard-action="copy"
                                               data-clipboard-target="#bar${index.index}"><i class="i-copy"></i></a>
                                            <a href="javascript:void(0);" class="btn-qr"><i class="i-qr"></i></a>
                                        </div>
                                    </div>
                                    <ul class="fr">
                                        <li>
                                            <i class="i-dot"></i><spring:message code="rechargeTips1"/>
                                        </li>
                                        <li><i class="i-dot"></i>
                                            <fmt:formatNumber var="formattedValue1" value='${asset.sysRechargeBlock}' currencySymbol='$' type='number'/>
                                            <spring:message code="rechargeTips2" arguments="${formattedValue1}" htmlEscape="false"/>
                                        </li>
                                        <li>
                                            <i class="i-dot"></i><spring:message code="rechargeTips3"/>
                                            <a href="member/asset/recharge" target="_blank"><spring:message code="historyRecord"/></a>
                                        </li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        <!-- 展开之后 -->
                        <tr class="balances-open hide">
                            <td colspan="7">
                                <div class="recharge clearfix">
                                    <input name="asset_${asset.assetId}" type="hidden" id="asset_${asset.assetId}"/>
                                    <div class="fl withdrawals">
                                        <div class="clearfix">
                                            <label for="" class="withdrawals-label">${asset.vcoinName} <spring:message code="cashAddress"/>:</label>
                                            <div class="withdrawals-con">
                                                <select name="" id="" class="withdrawals-select" asset="${asset.assetId}">
                                                    <option value=""><spring:message code="selectCurrentCashAddress"/></option>
                                                </select>
                                                <a href="javascript:void(0);" class="btn-add"><i class="i-add"
                                                                                         asset="${asset.assetId}"></i></a>
                                            </div>
                                        </div>
                                        <div class="withdrawals-con-block hide" id="withdrawals_${asset.assetId}">
                                            <input type="text" placeholder="<spring:message code="tag"/>" name="remark" id="mark_${asset.assetId}"
                                                   class="withdrawals-input1">
                                            <span>-</span>
                                            <input type="text" placeholder="<spring:message code="address"/>" name="ad_${asset.assetId}"
                                                   id="ad_${asset.assetId}" class="withdrawals-input2"/>
                                        </div>
                                        <div class="clearfix">
                                            <label for="" class="withdrawals-label"><spring:message code="cashAmount"/>:</label>
                                            <div class="withdrawals-con withdrawals-money">
                                                <input type="text" name="am_${asset.assetId}" id="am_${asset.assetId}"
                                                       class="balances-input cashInput" value="" max="${asset.cashMin}"
                                                       min="${asset.cashMax}" asset="${asset.assetId}"/>
                                                <div class="withdrawals-available">
                                                    <span class="txt">
                                                        <spring:message code="canUserBalance"/><fmt:formatNumber type="number" value="${asset.balanceAmount}" pattern="0.00000000"
                                                                                                                 maxFractionDigits="8"/></span>
                                                    <a href="javascript:void(0);"
                                                       balance="<fmt:formatNumber type="number" maxIntegerDigits="8" value="${asset.btcAmount}" pattern="0.00000000"/>"
                                                       class="btn-all"><spring:message code="cashAll"/></a>
                                                </div>
                                                <div class="col-red" id="err_${asset.assetId}"></div>
                                            </div>
                                        </div>
                                        <div class="withdrawals-con-block withdrawals-actual">
                                            <span><spring:message code="serviceFee"/>：<fmt:formatNumber type="number" maxIntegerDigits="8"
                                                                        value="${asset.cashFee}"/></span>
                                            <span id="actual_${asset.assetId}"><spring:message code="actualAmount"/>：0.0000000</span>
                                        </div>
                                        <div class="withdrawals-con-block">
                                            <a href="javascript:void(0);" class="layui-btn layui-btn-normal asset-cash"
                                               assetId="${asset.assetId}"><spring:message code="submit"/></a>
                                        </div>
                                    </div>
                                    <ul class="fr withdrawals-right">
                                        <li><i class="i-dot"></i><spring:message code="minCashNumber"/>:<fmt:formatNumber type="number"
                                                                                           maxIntegerDigits="8"
                                                                                           value="${asset.cashMin}"/>${asset.vcoinName}
                                            。
                                        </li>
                                        <li><i class="i-dot"></i><spring:message code="cashTips1"/></li>
                                        <li><i></i><spring:message code="cashTips2"/></li>
                                        <li>
                                            <i class="i-dot"></i><spring:message code="cashTips3"/>
                                            <a href="member/asset/cash" target="_blank"><spring:message code="historyRecord"/></a>
                                        </li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
    <!-- inner]]] -->
</div>
<jsp:include page="common/footer.jsp"/>
<script type="text/javascript" src="static/js/asset.js"></script>
<script>
    var clipboard = new Clipboard('.btn-copy');
    clipboard.on('success', function (e) {
        layer.alert($.i18n.prop('copyToBlock'), {
            skin: 'layui-layer-molv' //样式类名
            , closeBtn: 0, offset: '50%'
        });
    });

    $(".history-txt").click(function() {
        window.location.href="member/order/history";
    });


    $(".input-hide").bind("change",function() {
        var checked = $(".input-hide").is(":checked");
        if(checked) {
            var trArray = $("tr[class^='VCOIN-']");
            if(trArray.length < 1 ) {
                return;
            }
            for(var i = 0;i<trArray.length;i++) {
                var totalAmount = $(trArray[i]).attr("data-totalAmount");
                if(Number(totalAmount).toFixed(8) == Number(0).toFixed(8)) {
                    $(trArray[i]).addClass("hide");
                }
            }
        }else {
            var trArray = $("tr[class^='VCOIN-']");
            if(trArray.length < 1 ) {
                return;
            }
            for(var i = 0;i<trArray.length;i++) {
                var totalAmount = $(trArray[i]).attr("data-totalAmount");
                if(Number(totalAmount).toFixed(8) == Number(0).toFixed(8)) {
                    $(trArray[i]).removeClass("hide");
                }
            }
        }
    }) ;


    $(".vcoin-search").keyup(function() {
        var searchValue = $(this).val();
        if(searchValue == "") {
            $("tr[class^='VCOIN-']").removeClass("hide");
        }else {
            var upValue = searchValue.toUpperCase();
            $(".balances-table").find("table").find("tr:not(:first)").addClass("hide");
            $("tr[class^='VCOIN-" + upValue + "']").removeClass("hide");
        }
    });
</script>
</body>
</html>