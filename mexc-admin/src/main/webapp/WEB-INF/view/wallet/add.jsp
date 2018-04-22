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
<head>
    <meta charset="utf-8">
    <title>钱包添加</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
</head>
<body class="childrenBody">
<form id="data-form" class="layui-form" style="width:80%;padding-top: 20px;">
    <input type="hidden" name="id" value="${wallet.id}">

    <div class="layui-form-item">
        <label class="layui-form-label">钱包名称</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="name" value="${wallet.name}" lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">钱包地址</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="url" value="${wallet.url}" lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">钱包类型</label>
        <div class="layui-input-inline">
            <select id="type"  name="type" lay-verify="required" lay-filter="walletType">
                <option value="ETH" <c:if test="${wallet.type=='ETH'}">selected</c:if>>ETH</option>
                <option value="BTC" <c:if test="${wallet.type=='BTC'}">selected</c:if>>BTC</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">rpc端口</label>
        <div class="layui-input-block">
            <input type="number" class="layui-input" name="rpcPort" value="${wallet.rpcPort}" lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">socket端口</label>
        <div class="layui-input-block">
            <input type="number" class="layui-input" name="socketPort" value="${wallet.socketPort}"
                   lay-verify="required">
        </div>
    </div>
    <div class="layui-form-item" >
        <label class="layui-form-label">热钱包账号</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" readonly name="hotAddress" value="${wallet.hotAddress}"
                   lay-verify="required">
        </div>

        <c:if test="${wallet.type!='BTC'}">
            <label class="layui-form-label hotPwdLabel">热钱包密码</label>
            <div class="layui-input-inline">
                <input id="hotPwd" type="password" class="layui-input" name="hotPwd" value="${wallet.hotPwd}" lay-verify="required">
            </div>
        </c:if>

        <c:if test="${wallet.type!='BTC'}">
            <div class="layui-input-inline">
                <a class="layui-btn test-pwd">测试</a>
            </div>
        </c:if>
    </div>
    <c:if test="${wallet.hotAddress==null}">
        <div class="layui-form-item">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline">
                <a class="layui-btn createHotAddress">生成热钱包地址</a>
            </div>
        </div>
    </c:if>

    <div class="layui-form-item">
        <label class="layui-form-label">热钱包账号文件地址</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="hotFile" value="${wallet.hotFile}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">冷钱包账号</label>
        <div class="layui-input-block">
            <c:if test="${wallet.coldAddress!=null}">
                <input type="text" class="layui-input" readonly name="coldAddress" value="${wallet.coldAddress}"
                       lay-verify="required">
            </c:if>
            <c:if test="${empty wallet.coldAddress}">
                <input type="text" class="layui-input" name="coldAddress" value="${wallet.coldAddress}"
                       lay-verify="required">
            </c:if>
        </div>
    </div>
    <div class="layui-form-item" id="walletInfo" <c:if test="${wallet.type!='BTC'}">style="display: none;" </c:if>>
        <div class="layui-form-item">
            <label class="layui-form-label">比特币钱包账号</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" name="walletUser" value="${wallet.walletUser}">
            </div>
            <label class="layui-form-label">比特币钱包密码</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" name="walletPwd" value="${wallet.walletPwd}">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <c:choose>
                <c:when test="${wallet.status==null}">
                    <input type="checkbox" name="status" lay-skin="switch" value="0" lay-filter="status"
                           lay-text="开放|关闭">
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${wallet.status==1}">
                            <input type="checkbox" name="status" value="${wallet.status}" checked lay-skin="switch"
                                   lay-text="开放|关闭">
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="status" value="${wallet.status}" lay-skin="switch"
                                   lay-text="开放|关闭">
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入描述内容" class="layui-textarea" name="note">${wallet.note}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn add-data" lay-submit="" lay-filter="submitData">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script type="text/javascript" src="static/js/common.js"></script>
<script type="text/javascript" src="static/js/jquery-1.10.2.min.js"></script>

<script type="text/javascript">
    $(".test-pwd").bind("click",function () {
        var address = $("input[name='hotAddress']").val();
        var pwd = $("input[name='hotPwd']").val();
        var type = $("#type option:selected").val();
        $.ajax({
            url: "wallet/testPwd.do",
            type: 'POST',
            dataType: "json",
            data: {
                "type":type,
                "pwd":pwd,
                "address":address
            },
            success: function (r) {
                if(r.result) {
                    layer.msg("密码正确", {icon: 6});
                }else {
                    layer.msg("密码不正确", {icon: 5});
                }
            },error: function () {
                layer.msg("密码不正确", {icon: 5});
            }
        });
    });

    $(".createHotAddress").bind("click",function () {
        var type = $("#type option:selected").val();
        var pwd="";
        if(type!='BTC') {
            pwd = $("input[name='hotPwd']").val();
            if(pwd == "") {
                layer.msg("密码不能为空", {icon: 5});
                return;
            }
        }
        $.ajax({
            url: "wallet/createHotAddress.do",
            type: 'POST',
            dataType: "json",
            data: {
                "type":type,
                "pwd":pwd
            },
            success: function (r) {
                $("input[name='hotAddress']").val(r.hotAddress);
            },error:function () {
                layer.msg("创建地址失败", {icon: 5});
            }
        });
    });


    var $;
    layui.config({
        base: "static/js/"
    }).use(['form', 'layer', 'jquery'], function () {
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;

        form.on('switch(status)', function (data) {
            if (data.elem.checked) {
                $(this).val("1");
            } else {
                $(this).val("0");
            }
        });

        form.on('select(walletType)', function(data){
            var type=data.value;
            if (type == 'BTC') {
                $("#walletInfo").attr("style","");
                $(".hotPwdLabel").addClass("layui-hide");
                $("#hotPwd").addClass("layui-hide");
                $(".test-pwd").addClass("layui-hide");
            } else {
                $("#walletInfo").attr("style","display:none;");
                $(".hotPwdLabel").removeClass("layui-hide");
                $("#hotPwd").removeClass("layui-hide");
                $(".test-pwd").removeClass("layui-hide");
            }
        });


        form.on('submit(submitData)', function (data) {
            $.ajax({
                url: "wallet/saveOrUpdate.do",
                type: 'POST',
                dataType: "json",
                data: data.field,
                success: function (r) {
                    if (r.code == 0) {
                        top.layer.msg("添加成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    } else {
                        layer.msg(r.msg, {icon: 5});
                    }
                }
            });
            return false;
        });

    });
</script>

</body>
</html>