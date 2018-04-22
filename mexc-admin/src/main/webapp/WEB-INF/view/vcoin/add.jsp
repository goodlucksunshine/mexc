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
    <title>币种添加</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="static/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="static/css/common.css" media="all"/>
    <link rel="stylesheet" href="static/css/user.css" media="all"/>
</head>
<body class="childrenBody">
<form id="data-form" class="layui-form" style="padding-top: 20px;">
    <input type="hidden" name="id" id="vcoinId" value="${vCoin.id}"/>

    <div class="user_left">
        <div class="layui-form-item">
            <label class="layui-form-label">币种名称</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" name="vcoinName" value="${vCoin.vcoinName}"
                       lay-verify="required">
            </div>

            <label class="layui-form-label">英文名称</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" name="vcoinNameEn" value="${vCoin.vcoinNameEn}"
                       lay-verify="required">
            </div>
        </div>


        <div class="layui-form-item">
            <div class="layui-form-item">
                <label class="layui-form-label">全称</label>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input" name="vcoinNameFull" value="${vCoin.vcoinNameFull}"
                           lay-verify="required">
                </div>
                <label class="layui-form-label">排序</label>
                <div class="layui-input-inline">
                    <input type="text" class="layui-input" value="${vCoin.sort}" name="sort" lay-verify="required">
                </div>
            </div>
        </div>


        <div class="layui-form-item">
            <label class="layui-form-label">接入状态</label>
            <div class="layui-input-block">
                <input type="radio" name="status" value="-1" title="未接入"
                       <c:if test="${vCoin.status==0}">checked</c:if>/>
                <input type="radio" name="status" value="1" title="已接入" <c:if test="${vCoin.status==1}">checked</c:if>>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否可提现</label>
            <div class="layui-input-inline">
                <input type="radio" name="canCash" value="1" title="开放"
                       <c:if test="${vCoin.canCash==1}">checked</c:if>/>
                <input type="radio" name="canCash" value="-1" title="停止"
                       <c:if test="${vCoin.canCash==-1}">checked</c:if>/>
            </div>
            <label class="layui-form-label">是否可充值</label>
            <div class="layui-input-inline">
                <input type="radio" name="canRecharge" value="1" title="开放"
                       <c:if test="${vCoin.canRecharge==1}">checked</c:if>/>
                <input type="radio" name="canRecharge" value="-1" title="停止"
                       <c:if test="${vCoin.canRecharge==-1}">checked</c:if>/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否是主币</label>
            <div class="layui-input-block">
                <input type="radio" name="mainCoin" value="1" title="主币"
                       <c:if test="${vCoin.mainCoin==1}">checked</c:if>/>
                <input type="radio" name="mainCoin" value="0" title="代币"
                       <c:if test="${vCoin.mainCoin==0}">checked</c:if>/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">虚拟币系列</label>
            <div class="layui-input-inline">
                <select name="vcoinToken" lay-verify="required">
                    <c:forEach items="${codelist}" var="code">
                        <option value="${code}" <c:if test="${code==vCoin.vcoinToken}">selected</c:if>>${code}</option>
                    </c:forEach>
                </select>
            </div>
            <label class="layui-form-label">最小单位数</label>
            <div class="layui-input-inline">
                <select name="scale">
                    <option value="0" <c:if test="${vCoin.scale==0}">selected</c:if> >0</option>
                    <option value="2" <c:if test="${vCoin.scale==2}">selected</c:if> >2</option>
                    <option value="4" <c:if test="${vCoin.scale==4}">selected</c:if>>4</option>
                    <option value="6" <c:if test="${vCoin.scale==6}">selected</c:if> >6</option>
                    <option value="8" <c:if test="${vCoin.scale==8}">selected</c:if>>8</option>
                    <option value="10" <c:if test="${vCoin.scale==10}">selected</c:if> >10</option>
                    <option value="12" <c:if test="${vCoin.scale==12}">selected</c:if> >12</option>
                    <option value="14" <c:if test="${vCoin.scale==14}">selected</c:if> >14</option>
                    <option value="16" <c:if test="${vCoin.scale==16}">selected</c:if> >16</option>
                    <option value="18" <c:if test="${vCoin.scale==18}">selected</c:if>>18</option>
                </select>
                <span class="layui-bg-red">BTC为0,ETH为18,ETH代币请到ethscan查询小数位</span>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">买单费率</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" value="${vCoinFee.buyRate}" name="buyRate" lay-verify="required">
            </div>

            <label class="layui-form-label">卖单费率</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" value="${vCoinFee.sellRate}" name="sellRate"
                       lay-verify="required">
            </div>
        </div>


        <div class="layui-form-item">
            <label class="layui-form-label">挂单费率</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" value="${vCoinFee.putUpRate}" name="putUpRate"
                       lay-verify="required">
            </div>

            <label class="layui-form-label">提现费率</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" value="${vCoinFee.cashRate}" name="cashRate"
                       lay-verify="required">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">最小提现额</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" value="${vCoinFee.cashLimitMin}" name="cashLimitMin"
                       lay-verify="required">
            </div>

            <label class="layui-form-label">最大提现额</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" value="${vCoinFee.cashLimitMax}" name="cashLimitMax"
                       lay-verify="required">
            </div>
        </div>
        <div class="layui-form-item hide" id="contractAddress">
            <label class="layui-form-label">合约地址</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入合约地址" class="layui-textarea"
                          name="contractAddress">${vCoin.contractAddress}</textarea>
            </div>
            <span class="red">ETH合约地址请输入小写,否则代币会无法入账</span>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">描述</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入描述内容" class="layui-textarea" name="note">${vCoin.note}</textarea>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn add-data" lay-submit="" lay-filter="addUser">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </div>

    <div class="user_right">
        <div class="layui-form-item">
            <c:choose>
                <c:when test="${vCoin.icon!=null}">
                    <input type="file" name="file" style="display: none;" onchange="previewImage(this)"
                           id="previewImg"/>
                </c:when>
                <c:otherwise>
                    <input type="file" name="file" style="display: none;" lay-verify="required"
                           onchange="previewImage(this)" id="previewImg"/>
                </c:otherwise>
            </c:choose>

            <div class="layui-input-block">
                币种图标
                <div id="preview">
                    <c:choose>
                        <c:when test="${vCoin.icon!=null}">
                            <img id="pic" border="0" src="file/download/${vCoin.icon}" width="30" height="30"
                                 onclick="$('#previewImg').click();">
                        </c:when>
                        <c:otherwise>
                            <img id="pic" border="0" src="static/images/photo_icon.jpg" width="30" height="30"
                                 onclick="$('#previewImg').click();">
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
    </div>


</form>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script type="text/javascript" src="static/js/common.js"></script>
<script type="text/javascript" src="static/js/jquery-1.10.2.min.js"></script>

<script type="text/javascript">
    var $;
    layui.config({
        base: "static/js/"
    }).use(['form', 'layer', 'jquery'], function () {
        var form = layui.form,
            layer = layui.layer;
        $ = layui.jquery;

        form.on('submit(addUser)', function (data) {
            var form = new FormData($("#data-form")[0]);
            $.ajax({
                url: "vcoin/saveOrUpdate.do",
                type: 'POST',
                dataType: "json",
                data: form,
                cache: false,
                processData: false,
                contentType: false,
                success: function (r) {
                    if (r.code == 0) {
                        if ($("#vcoinId").val() != "") {
                            top.layer.msg("编辑成功");
                        } else {
                            top.layer.msg("添加成功！");
                        }
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