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
    <title>首页</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.all.js"></script>
</head>
<body style="height: 100%">
<!-- header[ -->
<jsp:include page="common/head.jsp">
    <jsp:param name="current" value="index"/>
</jsp:include>
<!-- header] -->
<div class="banner">
    <div class="banner-slider layout">
        <c:forEach var="item" items="${bannerList}">
            <img src="file/download/${item.imageUrl}" data-href="${item.url}" alt="${item.description}" class="img-icon">
        </c:forEach>
        <!-- banner轮播 -->
        <div class="banner-btn">
            <a href="javascript:" class="btn-left"></a>
            <a href="javascript:" class="btn-right"></a>
        </div>
    </div>
</div>
<div class="notice-slider layout">
    <!-- 公告轮播 -->
    <ul class="clearfix" id="notice">
        <li><span class="tit">币种</span></li>
        <li><span class="tit">价格</span></li>
        <li><span class="tit">涨跌</span></li>
        </li>
    </ul>
</div>

<div class="exchange-show clearfix">
    <div class="layout">
        <ul></ul>
    </div>
</div>

<div class="markets-container">
    <div class="layout clearfix">
        <div class="markets-bd" id="markets-bd">
            <div class="global-mod clearfix">
                <div class="global-tab fl">
                    <a class="current" onclick="shiftMarketTab(this)">BTC</a>
                    <a onclick="shiftMarketTab(this)">ETH</a>
                </div>
                <div class="fr search-box">
                    <span class="btn-search"><i class="i-search"></i></span>
                    <input type="text" class="vcoin-cap-search search-input"/>
                </div>
            </div>
            <%--<div class="global-table">
                <table>
                    <tr>
                        <th width="150">币种</th>
                        <th width="200">价格</th>
                        <th width="200">涨跌</th>
                        <th width="200">24小时最高价</th>
                        <th width="200">24小时最低价</th>
                        <th width="280">分时图</th>
                    </tr>
                </table>
            </div>--%>
           <%-- <a href="${basePth}trans/center" class="btn-markets-more">MORE +</a>--%>
        </div>
    </div>
</div>
<jsp:include page="common/footer.jsp"/>
<script>
    //收藏
    function collect(this_) {
        var marketId = $(this_).parent().parent().attr("data-marketId");
        var vcoinId = $(this_).parent().parent().attr("data-vcoinId");
        $.ajax({
            url: "member/trans/collect",
            type: 'POST',
            dataType: "json",
            data: {
                marketId: marketId,
                vcoinId: vcoinId
            },
            success: function (r) {
                if (r.code != 0) {
                    layer.msg(r.msg, {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                }
            }
        });
    }

    $(document).ready(function() {
        var imagesArray = $(".img-icon");
        showBanner(imagesArray);
        getNotice();
    });

    //展示Banner
    var count = 0;
    function showBanner(imagesArray) {
        count = 0;
        setBackgroundImage(imagesArray[count]);
        setInterval(function(){
            count++;
            if(count > imagesArray.length-1){
                count = 0;
            }
            setBackgroundImage(imagesArray[count]);
        }, 5000);

        $(".btn-left").bind("click",function() {
            count--;
            if(count < 0) {
                count = imagesArray.length-1;
            }
            setBackgroundImage(imagesArray[count]);
        });

        $(".btn-right").bind("click",function() {
            count++;
            if(count > imagesArray.length-1) {
                count = 0;
            }
            setBackgroundImage(imagesArray[count]);
        });
    }
    //设置banner背景
    function setBackgroundImage(imageObj) {
        var image = $(imageObj).attr("src");
        $(".banner").css("background","url('"+image+"') center top no-repeat");
        //$(".banner").stop().animate({left: '+1200px'}, 5000).slideDown("slow");
        $(".banner").stop().animate({left: '+1000px'}, "slow");
        //$(".banner").fadeOut(500).fadeIn(1000);
    }

    //未收藏
    function uncollect(this_) {
        var marketId = $(this_).parent().parent().attr("data-marketId");
        var vcoinId = $(this_).parent().parent().attr("data-vcoinId");
        $.ajax({
            url: "member/trans/uncollect",
            type: 'POST',
            dataType: "json",
            data: {
                marketId: marketId,
                vcoinId: vcoinId
            },
            success: function (r) {
                if (r.code != 0) {
                    layer.msg(r.msg, {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                }
            }
        });
    }

    function getNotice() {
        $.ajax({
            url: "notice",
            type: 'POST',
            dataType: "json",
            data: {},
            success: function (r) {
                var articles = r.msg;
                var li = "";
                $(articles).each(function (index, element) {
                    if (index > 2) {
                        return;
                    }
                    li += "<li><span class='tit'><a href='" + element.html_url + "' target='_blank'>" + element.title + "</a></span></li>"
                });
                $("#notice").html(li);
            }
        });
    }

</script>
</body>
</html>