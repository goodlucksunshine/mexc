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
    <title><spring:message code="identityAuth"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link rel="stylesheet" href="static/css/css.css"/>
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="static/layui/layui.all.js"></script>
    <script type="text/javascript" src="static/js/jquery-1.10.2.min.js"></script>
</head>
<body>
    <!-- header[[[ -->
    <jsp:include page="../common/head.jsp"/>
    <!-- header]]] -->

    <div class="inner-container">
        <!-- inner[[[ -->
        <div class="layout id-validate-box">
            <h3 class="global-tit"><spring:message code="identityAuth"/><span>／Identity Authentication</span></h3>
            <form id="data-form" onsubmit="return false;">
                <div class="id-validate">
                    <ul>
                        <li class="clearfix">
                            <dl class="id-explain">
                                <dt><spring:message code="uploadIdentityTips1"/>：</dt>
                                <dd>·<spring:message code="uploadIdentityTips2"/></dd>
                                <dd>·<spring:message code="uploadIdentityTips3"/></dd>
                            </dl>
                            <div class="id-upload" id="previewHome">
                                <i class="i-upload" ></i>
                                <input type="file" name="idCardHomeFile" class="input-file" onchange="previewImage(this,'previewHome')">
                                <span><spring:message code="uploadIdentityTips4"/></span>
                            </div>
                            <div>
                                <span class="show-tit"><spring:message code="example"/><i class="i-right"></i></span>
                                <i class="id1"></i>
                            </div>
                        </li>

                        <li class="clearfix">
                            <dl class="id-explain">
                                <dt><spring:message code="uploadIdentityTips5"/>：</dt>
                                <dd>·<spring:message code="uploadIdentityTips2"/></dd>
                                <dd>·<spring:message code="uploadIdentityTips6"/></dd>
                                <dd>·<spring:message code="uploadIdentityTips3"/></dd>
                            </dl>
                            <div class="id-upload" id="previewBack">
                                <i class="i-upload"></i>
                                <input type="file" name="idCardBackFile" class="input-file" onchange="previewImage(this,'previewBack')">
                                <span><spring:message code="uploadIdentityTips11"/></span>
                            </div>
                            <div>
                                <span class="show-tit"><spring:message code="example"/><i class="i-right"></i></span>
                                <i class="id2"></i>
                            </div>
                        </li>
                        <li class="clearfix">
                            <dl class="id-explain">
                                <dt><spring:message code="uploadIdentityTips7"/>：</dt>
                                <dd>·<spring:message code="uploadIdentityTips8"/></dd>
                                <dd>·<spring:message code="uploadIdentityTips9"/></dd>
                                <dd>·<spring:message code="uploadIdentityTips10"/></dd>
                            </dl>
                            <div class="id-upload" id="previewHand">
                                <i class="i-upload"></i>
                                <input type="file" name="idCardHandFile" class="input-file" onchange="previewImage(this,'previewHand')">
                                <span><spring:message code="uploadIdentityTips12"/></span>
                            </div>
                            <div>
                                <span class="show-tit"><spring:message code="example"/><i class="i-right"></i></span>
                                <i class="id3"></i>
                            </div>
                        </li>
                    </ul>
                    <div class="id-uload-btn">
                        <a href="javascript:" class="btn-submit"><spring:message code="submit"/></a>
                        <a href="javascript:" class="btn-modify"><spring:message code="cancel"/></a>
                    </div>
                </div>
            </form>
        </div>
        <!-- inner]]] -->
    </div>


    <!-- footer[[ -->
    <jsp:include page="../common/footer.jsp"/>
    <!-- footer]] -->

</body>
<script>
    var $;
    layui.config({
        base : "static/js/"
    }).use(['form','layer','jquery'],function() {
        var form = layui.form,
            layer = layui.layer;
            $ = layui.jquery;


        $(".btn-submit").bind("click",function () {
            var form = new FormData($("#data-form")[0]);
            $.ajax({
                url: "member/ucenter/saveIdentityAuth.do",
                type: 'POST',
                dataType: "json",
                data: form,
                cache: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data.code != 0) {
                        layer.msg($.i18n.prop('uploadIdentityFailTips'), {
                            offset: '40%',
                            shade: [0.8, '#393D49'],
                            skin: 'layui-layer-molv',
                            time: 5000
                        });
                    } else {
                        layer.msg($.i18n.prop('uploadIdentitySuccess'), {
                            offset: '40%',
                            shade: [0.8, '#393D49'],
                            skin: 'layui-layer-molv',
                            time: 5000
                        });
                        window.location.href = "member/ucenter/index";
                    }
                }, error : function() {
                    layer.msg($.i18n.prop('uploadIdentityFailTips'), {
                        offset: '40%',
                        shade: [0.8, '#393D49'],
                        skin: 'layui-layer-molv',
                        time: 5000
                    });
                }
            });
        });
    });

    //建立一個可存取到該file的url
    function getObjectURL(file) {
        var url = null ;
        if (window.createObjectURL!=undefined) { // basic
            url = window.createObjectURL(file) ;
        } else if (window.URL!=undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file) ;
        } else if (window.webkitURL!=undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file) ;
        }
        return url ;
    }

    //图片上传预览    IE是用了滤镜。
    function previewImage(file,divId)
    {
        var div = document.getElementById(divId);
        if (file.files && file.files[0])
        {
            var fileUrl = getObjectURL(file.files[0]);
            var reader = new FileReader();
            reader.onload = function(evt)
            {
                div.style.cssText = 'background-image: url(\"'+fileUrl+'\");background-size:100% 100%;';
            };
            reader.readAsDataURL(file.files[0]);
        }
    }



</script>
</html>