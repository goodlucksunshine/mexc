<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
    <title>会员列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
    <link rel="stylesheet" href="static/css/user.css" media="all" />
</head>
<body class="childrenBody">
<blockquote class="layui-elem-quote news_search">
    <div class="layui-inline">
        <form id="searchForm" name="searchForm" class="layui-form" action="member/list">
            <input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}"/>
            <div class="layui-input-inline">
                <input type="text" value="${queryDto.account}" name="account" placeholder="请输入用户账号" class="layui-input search_input"/>
            </div>
            <div class="layui-input-inline">
                <select name="status">
                    <option value=""></option>
                    <c:if test="${queryDto.status==null}">
                        <option value="1">正常</option>
                        <option value="0">未激活</option>
                        <option value="-1">冻结</option>
                    </c:if>
                    <c:if test="${queryDto.status==1}">
                        <option value="1" selected="selected">正常</option>
                        <option value="0">未激活</option>
                        <option value="-1">冻结</option>
                    </c:if>
                    <c:if test="${queryDto.status==0}">
                        <option value="1">正常</option>
                        <option value="0" selected="selected">未激活</option>
                        <option value="-1">冻结</option>
                    </c:if>
                    <c:if test="${queryDto.status==-1}">
                        <option value="1">正常</option>
                        <option value="0">未激活</option>
                        <option value="-1" selected="selected">冻结</option>
                    </c:if>
                </select>
            </div>

            <a class="layui-btn search_btn">查询</a>
        </form>
    </div>

    <div class="layui-inline">
        <%--<shiro:hasPermission name="role:add">--%>
           <%-- <a class="layui-btn layui-btn-normal doAdd_btn">添加</a>--%>
        <%--</shiro:hasPermission>--%>
    </div>
    <div class="layui-inline">
        <div class="layui-form-mid layui-word-aux"></div>
    </div>
</blockquote>
<div class="layui-form data_list">
    <table class="layui-table">
        <colgroup>
            <col width="2%">
            <col width="10%">
            <col width="10%">
            <col width="10%">
            <col width="8%">
            <col width="15%">
            <col width="20%">
        </colgroup>
        <thead>
            <tr>
                <th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose" id="allChoose"></th>
                <th>账号</th>
                <th>认证等级</th>
                <th>状态</th>
                <th>更新时间</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody class="data_content">
            <c:forEach var="item" items="${page.resultList}">
                <tr>
                    <td>
                        <input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose">
                    </td>
                    <td>${item.account}</td>
                    <td>
                        <c:if test="${item.authLevel==1}">
                            lv1
                        </c:if>
                        <c:if test="${item.authLevel==2}">
                            lv2
                        </c:if>
                        <c:if test="${item.authLevel==3}">
                            lv3
                        </c:if>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${item.status==1}">
                                正常
                            </c:when>
                            <c:when test="${item.status==0}">
                                未激活
                            </c:when>
                            <c:when test="${item.status==-1}">
                                冻结
                            </c:when>
                        </c:choose>

                    </td>
                    <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>
                        <%--<shiro:hasPermission name="role:edit">--%>
                            <a class="layui-btn layui-btn-mini do_edit" data-id="${item.id}">
                                <i class="iconfont icon-edit"></i> 查看
                            </a>
                       <%-- </shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="role:del">--%>
                            <a class="layui-btn layui-btn-danger layui-btn-mini do_del" data-id="${item.id}">
                                <i class="layui-icon">&#xe640;</i> 删除
                            </a>
                        <%--</shiro:hasPermission>--%>
                            <c:if test="${item.status==-1}">
                                <a class="layui-btn layui-btn-mini do_unfrozen" data-id="${item.id}">
                                    <i class="iconfont icon-edit"></i> 解冻
                                </a>
                            </c:if>

                            <c:if test="${item.status==1}">
                                <a class="layui-btn layui-btn-mini do_frozen" data-id="${item.id}">
                                    <i class="iconfont icon-edit"></i> 冻结
                                </a>
                            </c:if>
                    </td>
                </tr>
            </c:forEach>

        </tbody>
    </table>
</div>
<div id="page"></div>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script>
    layui.config({
        base : "static/js/"
    }).use(['form','layer','jquery','laypage'],function() {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : parent.layer,
            laypage = layui.laypage,
            $ = layui.jquery;

        laypage.render({
            elem: 'page'
            ,count: ${page.totalResult} //数据总数，从服务端得到
            ,curr: ${page.currentPage}
            ,layout: ['count', 'prev', 'page', 'next', 'skip']
            ,jump: function(obj, first){
                $("#currentPage").val(obj.curr);
                //首次不执行
                if(!first){
                    $("#searchForm").submit();
                }
            }
        });
        //查询
        $(".search_btn").click(function(){
            $("#searchForm").submit();
        });

        //编辑
        $("body").on("click",".do_edit",function(){
            var _this = $(this);
            var id = _this.attr("data-id");
            var index = layui.layer.open({
                title : "查看会员",
                type : 2,
                content : "member/view?id="+id,
                success : function(layero, index){
                    setTimeout(function(){
                        layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    },500)
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function(){
                layui.layer.full(index);
            });
            layui.layer.full(index);
        });

        //冻结
        $("body").on("click",".do_frozen",function(){
            var _this = $(this);
            var id = _this.attr("data-id");
            layer.confirm('确定冻结此用户？',{icon:3, title:'提示信息'},function(index){
                $.ajax({
                    url: "member/frozen.do?id="+id,
                    type: 'POST',
                    dataType: "json",
                    success: function (data) {
                        layer.msg("冻结成功！");
                        location.reload();
                    }
                });
                layer.close(index);
            });
        });

        //解冻
        $("body").on("click",".do_unfrozen",function(){
            var _this = $(this);
            var id = _this.attr("data-id");
            layer.confirm('确定解冻此用户？',{icon:3, title:'提示信息'},function(index){
                $.ajax({
                    url: "member/unfrozen.do?id="+id,
                    type: 'POST',
                    dataType: "json",
                    success: function (data) {
                        layer.msg("解冻成功！");
                        location.reload();
                    }
                });
                layer.close(index);
            });
        });

        //删除
        $("body").on("click",".do_del",function(){
            var _this = $(this);
            var id = _this.attr("data-id");
            layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
                $.ajax({
                    url: "member/del.do?id="+id,
                    type: 'POST',
                    dataType: "json",
                    success: function (data) {
                        layer.msg("删除成功！");
                        location.reload();
                    }
                });
                layer.close(index);
            });
        });


        //全选
        form.on('checkbox(allChoose)', function(data){
            var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
            child.each(function(index, item){
                item.checked = data.elem.checked;
            });
            form.render('checkbox');
        });


        //通过判断是否全部选中来确定全选按钮是否选中
        form.on("checkbox(choose)",function(data){
            var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
            var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"]):checked');
            if(childChecked.length == child.length){
                $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
            }else{
                $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
            }
            form.render('checkbox');
        });
    })
</script>
</body>
</html>
