layui.config({base: 'static/js/'}).extend({
    jquery_qrcode: 'jquery.qrcode.min',
    // 默认寻找base也就是js/index.js模块导入
});
layui.use(['layer', 'jquery', 'jquery_qrcode'], function () {
    var layer = layui.layer
        , $ = layui.$;
    $ = layui.jquery_qrcode($);
    $(".btn-submit").click(function () {
        if ($(this).hasClass("disabled")) {
            return;
        }
        $(".balances-open").attr("class", "hide");
        $(this).parents("tr").next().attr("class", "balances-open");
        $(this).parents("tr").next().next().attr("class", "hide");
    });
    $(".btn-modify").click(function () {
        if ($(this).hasClass("disabled")) {
            return;
        }
        $(".balances-open").attr("class", "hide");
        $(this).parents("tr").next().next().attr("class", "balances-open");
        $(this).parents("tr").next().attr("class", "hide");
        var ad_select = $(this).parents("tr").next().next().find(".withdrawals-select");
        if (ad_select.children("option").size() > 1) {
            return;
        }
        var assetId = $(this).attr("asset");
        $.ajax({
            url: basePath + "member/asset/markList?assetId=" + assetId,
            type: 'POST',
            dataType: "json",
            success: function (r) {
                if (r.code == 0) {
                    var list = r.msg;
                    $.each(list, function (index, value) {
                        var op = "<option value='" + value.address + "'>" + value.addressTab + "|" + value.address + "</option>";
                        ad_select.append(op);
                    });
                }
            }
        });
    });
    $(".btn-qr").click(function () {
        $("#qrcode").empty();
        $("#qrcode").qrcode($(this).parents("tr").find(".balances-input").val());
        layer.open({
            type: 1,
            title: false,
            closeBtn: 1,
            area: '260px',
            skin: 'layui-layer-molv', //没有背景色
            content: $("#qrcode"),
            cancel: function (index, layero) {
                $("#qrcode").hide();
            }
        });
    });

    $(".btn-all").click(function () {
        var balance = $(this).attr("balance");
        $(this).parent().prev().val(balance).trigger("change");
    });

    $(".asset-cash").click(function () {
        var assetId = $(this).attr("assetId");
        var data = {
            cashAddress: $("#ad_" + assetId).val(),
            cashValue: $("#am_" + assetId).val(),
            assetId: assetId,
            addressTab: $("#mark_" + assetId).val()
        };
        cashCheck(data);
    });

    $(".i-add").click(function () {
        var asset = $(this).attr("asset");
        $("#withdrawals_" + asset).removeClass("hide");
    });

    $(".withdrawals-select").change(function () {
        var ad = $(this).val();
        var as_id = $(this).attr("asset");
        $("#ad_" + as_id).val(ad);
    });


    $(".asset-gen").click(function () {
        var vcoinId = $(this).attr("vcoinId");
        $.ajax({
            url: basePath + "member/asset/generateAsset?vcoinId=" + vcoinId,
            cache: false,
            dataType: 'json',
            type: "POST",
            success: function (data) {
                if (data.code != 0) {
                    layer.alert(data.msg, {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0, offset: '50%'
                    });
                } else {
                    layer.msg("生成成功");
                    window.location.reload;
                }
            }
        });
    });


    function cashCheck(param) {
        $.ajax({
            url: basePath + "member/asset/cashAmountCheck",
            data: param,
            cache: false,
            dataType: 'json',
            type: "POST",
            success: function (data) {
                if (data.code != 0) {
                    layer.alert(data.msg, {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0, offset: '50%'
                    });
                } else {
                    if (data.cashCheck) {
                        layer.prompt({title: '请输入谷歌验证码', formType: 1}, function (text, index) {
                            layer.close(index);
                            authCheck(text, param);
                        });
                    } else {
                        submitCash(param);
                    }
                }
            }
        });
    }

    function authCheck(code, param) {
        $.ajax({
            url: basePath + "member/asset/googleCheck?code=" + code,
            cache: false,
            dataType: 'json',
            type: "POST",
            success: function (data) {
                if (data.code != 0) {
                    return false;
                }
                if (data.cashCheck) {
                    submitCash(param);
                } else {
                    layer.alert("验证失败,请重新尝试", {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0, offset: '50%'
                    });
                }
                return data.result;
            }
        });
    }


    function submitCash(param) {
        $.ajax({
            url: basePath + "member/asset/assetCash",
            data: param,
            cache: false,
            dataType: 'json',
            type: "POST",
            success: function (data) {
                if (data.code != 0) {
                    layer.alert(data.msg, {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0, offset: '50%'
                    });
                } else {
                    layer.alert('我们已发送邮件到您注册邮箱,请你查收邮件确认提现。', {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0, offset: '50%'
                    });
                }
            },
            error: function () {
                layer.alert("系统繁忙,请稍后再试", {offset: '50%'});
            }
        });
    }

    $(".cashInput").change(function () {
        var assetId = $(this).attr("asset");
        $("#err_" + assetId).text("");
        if ($(this).val() == '') {
            return;
        }
        var param = {"assetId": assetId, "cashValue": $(this).val()};
        $.ajax({
            url: basePath + "member/asset/cashAmountCheck",
            data: param,
            cache: false,
            dataType: 'json',
            type: "POST",
            success: function (data) {
                if (data.code != 0) {
                    $("#err_" + assetId).text(data.msg);
                } else {
                    $("#actual_" + assetId).text("实际到账:" + data.actualAmount);
                }
            },
            error: function () {
                layer.alert("系统繁忙,请稍后再试", {offset: '50%'});
            }
        });
    });
});