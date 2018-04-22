<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
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
    <title>服务条款</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.all.js"></script>
</head>
<body>
<!-- header[ -->
<jsp:include page="common/head.jsp"/>
<!-- header] -->

    <div class="inner-container">
        <!-- inner[[[ -->
        <div class="layout">
            <div class="page-help">
                <h3 class="global-tit">联系我们<span>／Contact</span></h3>
                <div class="bg-white page-about page-contact">
                    <div class="p-area">
                        <p class="weight">Telegram英文群</p>
                        <p>https://t.me/MEXC_EN</p>
                    </div>

                    <div class="p-area">
                        <p class="weight">Telegram中文群</p>
                        <p>https://t.me/MEXC_CH</p>
                    </div>

                    <div class="p-area">
                        <p class="weight">客服邮箱</p>
                        <p>Sevicemexc@gmail.com</p>
                    </div>

                </div>
            </div>
        </div>
        <!-- inner]]] -->
    </div>

<jsp:include page="common/footer.jsp"/>

</body>
</html>