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
                <h3 class="global-tit">关于我们<span>／About</span></h3>
                <div class="bg-white page-about">
                    <h4 class="info"><span>MEXC</span>一群数字资产爱好者创建而成的一个专注区块链资产的交易平台。<br/>
                    为用户提供更加安全、便捷的区块链资产兑换服务，聚合全球优质区块链资产，<br/>
                    致力于打造世界级的区块链资产交易平台。</h4>

                    <div class="p-area">
                        <p class="weight">安全稳定</p>
                        <p>多层、多集群系统架构</p>
                    </div>

                    <div class="p-area">
                        <p class="weight">高性能</p>
                        <p>高达140万单/秒的高性能撮合引擎技术</p>
                    </div>

                    <div class="p-area">
                        <p class="weight">高流动性</p>
                        <p>丰富的资源和众多的合作伙伴为平台提供流动性</p>
                    </div>

                    <div class="p-area">
                        <p class="weight">多语言支持</p>
                        <p>提供世界范围内多种主流语言支持</p>
                    </div>

                    <div class="p-area">
                        <p class="weight">多币种支持</p>
                        <p>BTC、ETH等（不支持法定货币）</p>
                    </div>

                    <div class="about-line"></div>

                    <h4 class="tit">我们的优势</h4>

                    <div class="p-area">
                        <p class="weight">经过验证的成熟产品</p>
                        <p>整套系统已在30+个交易平台上线运营，支持全客户端平台及多语言，用户体验优秀。</p>
                    </div>

                    <div class="p-area">
                        <p class="weight">国际化的投资顾问团队</p>
                        <p>丰富的资源和众多的合作伙伴为平台提供流动性，助力社区运营。</p>
                    </div>

                    <div class="p-area">
                        <p class="weight">经过验证的成熟产品</p>
                        <p>整套系统已在30+个交易平台上线运营，支持全客户端平台及多语言，用户体验优秀。</p>
                    </div>

                </div>

            </div>
        </div>
        <!-- inner]]] -->
    </div>

<jsp:include page="common/footer.jsp"/>

</body>
</html>