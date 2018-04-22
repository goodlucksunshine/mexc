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
    <title>角色添加</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="static/btree/css/bootstrapStyle/bootstrapStyle.css" type="text/css">
</head>
<body class="childrenBody">
<form id="data-form" class="layui-form" style="width:80%;padding-top: 20px;">

    <div class="layui-form-item">
        <label class="layui-form-label">角色名称</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input"  name="roleName" lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">角色编码</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="roleCode" lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入描述内容" class="layui-textarea"  name="remark"></textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">权限</label>
        <div class="layui-input-block">
            <ul id="roleTree" class="ztree"></ul>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn add-data" lay-submit="" lay-filter="addUser">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script type="text/javascript" src="static/js/common.js"></script>

<script type="text/javascript" src="static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="static/btree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="static/btree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="static/btree/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript">
    var $;
    var editorIndex;
    layui.config({
        base : "static/js/"
    }).use(['form','layer','jquery'],function(){
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;


        form.on('submit(addUser)',function(data){
            var form = new FormData($("#data-form")[0]);

            var menuIds = onCheck();
            $.ajax({
                url: "role/saveOrUpdate.do?menuIds="+menuIds,
                type: 'POST',
                dataType: "json",
                data: form,
                cache: false,
                processData: false,
                contentType: false,
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

<script type="text/javascript">
    var setting = {
        view: {
            addHoverDom: false,
            removeHoverDom: false,
            selectedMulti: true
        },
        check: {
            enable: true,
            chkboxType:  { "Y": "ps", "N": "" }
        },
        data: {
            simpleData: {
                enable: true,
                idKey:"id",
                pidKey:"pid",
            }
        },
        edit: {
            enable: false
        }
    };
    var zNodes;
    $(document).ready(function(){
        $.ajax({
            async : false,
            cache : false,
            type : 'get',
            dataType : 'json',
            url : "resource/resourceTree.do",
            error : function() {
                layer.msg('亲，请求失败！');
            },
            success : function(data) {
                zNodes = data;
                $.fn.zTree.init($("#roleTree"), setting, zNodes);
            }
        });
    });

    function onCheck() {
        var treeObj = $.fn.zTree.getZTreeObj("roleTree");
        var nodes = treeObj.getCheckedNodes(true);
        var idArray = [];
        for (var i = 0; i < nodes.length; i++) {
            idArray.push(nodes[i].id);
        }
        return idArray;
    }

</script>
</body>
</html>