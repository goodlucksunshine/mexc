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
    <title>隐私声明</title>
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
            <div class="page-help ">
                <h3 class="global-tit">隐私声明<span>／Privacy</span></h3>
                <div class="bg-white page-1 page-declare">
                    <div class="info">
                        <p>本隐私声明适用MEXC的所有相关服务，MEXC尊重并保护所有使用MEXC平台服务用户的个人隐私权。为了给您提供更准确、更有个性化的服务，会按照本隐私权政策的规定使用和披露您的个人信息但MEXC将以高度的勤勉、审慎义务对待这些信息。除本隐私权政策另有规定外，在未征得您事先许可的情况下，MEXC不会将这些信息对外披露或向第三方提供。MEXC会不时更新本隐私权政策。 您在同意MEXC服务协议之时，即视为您已经同意本隐私权政策全部内容。本隐私权政策属于服务协议不可分割的一部分。</p>
                    </div>
                    <div class="page-2">
                        <div class="con">
                            <h4 class="tit">1.	适用范围</h4>
                            <p>在您注册或激活可以登录我们平台的账户时，您在我们其关联公司提供的其他平台提供的个人注册信息（应法律法规要求需公示的企业名称等相关工商注册信息以及自然人经营者的信息除外）； 在您使用MEXC，或MEXC网页时，MEXC并记录的您的浏览器和计算机上的信息，包括但不限于您的IP地址、浏览器的类型、使用的语言、访问日期和时间、软硬件特征信息及您需求的网页记录等数据；MEXC途径从商业伙伴处取得的用户个人数据。</p>

                        </div>
                        <div class="con">
                            <h4 class="tit">2.	信息使用</h4>
                            <p>我们不会向任何无关第三方提供、出售、出租、分享或交易您的个人信息，除非事先得到您的许可，或该第三方和我们（含我们关联公司）单独或共同为您提供服务，且在该服务结束后，其将被禁止访问包括其以前能够访问的所有这些资料。 我们亦不允许任何第三方以任何手段收集、编辑、出售或者无偿传播您的个人信息。任何我们平台用户如从事上述活动，一经发现，我们有权立即终止与该用户的服务协议。</p>
                        </div>

                       <div class="con">
                           <h4 class="tit">3、信息披露</h4>
                           <p>在如下情况下，我们将依据您的个人意愿或法律的规定全部或部分的披露您的个人信息： 经您事先同意，向第三方披露； 如您是适格的知识产权投诉人并已提起投诉，应被投诉人要求，向被投诉人披露，以便双方处理可能的权利纠纷； 根据法律的有关规定，或者行政或司法机构的要求，向第三方或者行政、司法机构披露； 如您出现违反有关法律、法规或者我们服务协议或相关规则的情况，需要向第三方披露； 为提供您所要求的产品和服务，而必须和第三方分享您的个人信息； 在我们平台上创建的某一交易中，如交易任何一方履行或部分履行了交易义务并提出信息披露请求的，我们有权决定向该用户提供其交易对方的联络方式等必要信息，以促成交易的完成或纠纷的解决。 我们根据法律、法规或者网站政策认为合适的披露。</p>
                       </div>

                        <div class="con">
                            <h4 class="tit">4、信息存储和交换</h4>
                            <p>我们收集的有关您的信息和资料将保存在我们及（或）其关联公司的服务器上，这些信息和资料可能传送至您所在国家、地区或我们收集信息和资料所在地的境外并在境外被访问、存储和展示。</p>
                        </div>

                        <div class="con">
                            <h4 class="tit">5、Cookie的使用</h4>
                            <p>在您未拒绝接受cookies的情况下，我们会在您的计算机上设定或取用cookies，以便您能登录或使用依赖于cookies的我们平台服务或功能。我们使用cookies可为您提供更加周到的个性化服务包括推广服务。 您有权选择接受或拒绝接受cookies。您可以通过修改浏览器设置的方式拒绝接受cookies。但如果您选择拒绝接受cookies，则您可能无法登录或使用依赖于cookies的我们平台服务或功能。 通过币安所设cookies所取得的有关信息，将适用本政策；</p>
                        </div>

                        <div class="con">
                            <h4 class="tit">6、信息安全</h4>
                            <p>您的账户均有安全保护功能，请妥善保管您的账户及密码信息。我们将通过向其它服务器备份、对用户密码进行加密等安全措施确保您的信息不丢失，不被滥用和变造。尽管有前述安全措施，但同时也请您注意在信息网络上不存在“完善的安全措施”。 在使用我们平台服务进行网上交易时，您不可避免的要向交易对方或潜在的交易对方披露自己的个人信息，如联络方式或者邮政地址。请您妥善保护自己的个人信息，仅在必要的情形下向他人提供。如您发现自己的个人信息泄密，尤其是你的账户及密码发生泄露，请您立即联络我们客服，以便我们采取相应措施。</p>
                            <p>未成年人的特别注意事项 如果您不是具备完全民事权利能力和完全民事行为能力的自然人，您无权使用我们平台服务，因此我们希望您不要向我们提供任何个人信息。</p>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!-- inner]]] -->
    </div>

<jsp:include page="common/footer.jsp"/>

</body>
</html>