<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>费率说明</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
</head>
<body>
<!-- header[ -->
<jsp:include page="common/head.jsp"/>
<!-- header] -->

<div class="inner-container">
        <!-- inner[[[ -->
        <div class="layout">
            <div class="page-help">
                <h3 class="global-tit">费率说明<span>／Fees</span></h3>
                <div class="bg-white page-about page-fees">
                    <div class="p-area">
                        <p class="weight">交易费率</p>
                        <p>0.1% 交易手续费。（扣除收取到的资产）</p>
                    </div>

                   <div class="p-area">
                       <p class="weight">充值费率</p>
                       <p>免费</p>
                   </div>

                   <div class="p-area">
                       <p class="weight">提现费率</p>
                       <p class="col-red">提现手续费将会根据区块实际情况定期调整</p>
                   </div>

                    <table class="global-table">
                        <tr>
                            <th width="" align="left">币种</th>
                            <th width="" align="center">币种全称</th>
                            <th width="" align="center">最小提币数量</th>
                            <th width="" align="right">提币手续费</th>
                        </tr>
                        <tr>
                            <td><i class="i-ark"></i>ARK</td>
                            <td>Ark</td>
                            <td>4</td>
                            <td>0.54 BNB</td>
                        </tr>
                        <tr>
                            <td><i class="i-ark"></i>ARK</td>
                            <td>Ark</td>
                            <td>4</td>
                            <td>0.54 BNB</td>
                        </tr>
                        <tr>
                            <td><i class="i-ark"></i>ARK</td>
                            <td>Ark</td>
                            <td>4</td>
                            <td>0.54 BNB</td>
                        </tr>
                        <tr>
                            <td><i class="i-ark"></i>ARK</td>
                            <td>Ark</td>
                            <td>4</td>
                            <td>0.54 BNB</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <!-- inner]]] -->
    </div>
<jsp:include page="common/footer.jsp"/>

</body>
</html>