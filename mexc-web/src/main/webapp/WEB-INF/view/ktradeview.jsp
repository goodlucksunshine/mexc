<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">
<script>
    var basePath = "<%=basePath%>";
    var PATH = "${pageContext.request.contextPath}";
    var marketName = '${market.marketName}';
    var vcoinNameEn = '${vcoin.vcoinNameEn}';
    var marketId = '${market.id}';
    var vcoinId = '${vcoin.id}';
</script>
<script type="text/javascript" src="<%=basePath%>static/charting_library/charting_library.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/charting_library/datafeed/udf/datafeed.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="static/js/jquery.i18n.properties-min-1.0.9.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common.js"></script>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>k线图</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="<%=basePath%>static/css/css.css"/>
    <script type="text/javascript" src="<%=basePath%>static/js/tradecenter/trade.charting.js"></script>
</head>
<body style="height: 100%">
    <div id="tradingview-chart"></div>
</body>
</html>