<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <title>添加推荐</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all" />
</head>
<body class="childrenBody">
<form id="data-form" class="layui-form" style="width:80%;padding-top: 20px;">

    <div class="layui-form-item">
        <label class="layui-form-label">选择市场</label>
        <div class="layui-input-block">
            <select name="marketId" lay-filter="market" lay-verify="required">
                <option value=""></option>
                <c:forEach var="item" items="${marketList}">
                    <option value="${item.id}">${item.marketName}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">选择币种</label>
        <div class="layui-input-block">
            <select name="vcoinId" id="vcoinId" lay-filter="vcoinId" lay-verify="required">
                <option value=""></option>
            </select>
        </div>
    </div>
<%--
    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <input type="checkbox" name="status"lay-skin="switch" value="0" lay-filter="status" lay-text="开放|关闭">
        </div>
    </div>--%>

    <div class="layui-form-item">
        <label class="layui-form-label">是否推荐</label>
        <div class="layui-input-block">
            <input type="checkbox" name="suggest" lay-skin="switch" value="1" checked lay-filter="suggest" lay-text="是|否">
        </div>
    </div>


   <%-- <div class="layui-form-item">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入描述内容" class="layui-textarea"  name="note"></textarea>
        </div>
    </div>
--%>
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
    var $;
    layui.config({
        base : "static/js/"
    }).use(['form','layer','jquery'],function(){
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;

        //状态
        form.on('switch(status)', function(data){
            if(data.elem.checked) {
                $(this).val("1");
            }else {
                $(this).val("0");
            }
        });
        //是否推荐
        form.on('switch(suggest)', function(data){
            if(data.elem.checked) {
                $(this).val("1");
            }else {
                $(this).val("0");
            }
        });

        //市场切换
        form.on('select(market)', function(data){
            var marketId = data.value;
            $.ajax({
                url: "market/suggest/getMarketVCoinList",
                type: 'POST',
                dataType: "json",
                data: {"marketId":marketId},
                success: function (r) {
                    $("#vcoinId").empty();
                    var vlist =  r.vlist ;
                    for(var i = 0;i<vlist.length;i++) {
                        $("#vcoinId").append("<option value='"+vlist[i].id+"'>"+vlist[i].vcoinNameEn+"</option>")
                    }
                    form.render('select');
                }
            });
        });

        form.on('submit(submitData)',function(data){
            $.ajax({
                url: "market/suggest/update.do",
                type: 'POST',
                dataType: "json",
                data: data.field,
                success: function (r) {
                    if(r.code==0) {
                        top.layer.msg("添加成功！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    }else {
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