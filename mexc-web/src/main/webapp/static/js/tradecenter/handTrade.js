/**手动交易*/
//关闭窗口
$(".btn-close").bind("click",function() {
    closeDialog();
});

function closeDialog() {
    $(".tradeMask").addClass("hide");
    $(".tradeBox").addClass("hide");
}

var params = {};
//点击买列表，弹出买弹框
$(document).on("click",".toSellDialog",function () {
    $(".tradeMask").removeClass("hide");
    $(".tradeBox").removeClass("hide");
    var hnumber = $(this).find(".hnumber").text();
    var hprice = $(this).find(".hprice").text();
    var hamount = Number(hnumber) * Number(hprice);
    $("#hnumber").val(hnumber);
    $("#hamount").val(Number(hamount).toFixed(8));
    $("#hprice").val(hprice);
    $(".btn-alert-sure").text($.i18n.prop('buy'));
    $(".htitle").text($.i18n.prop('buy'));
    params.marketId = marketId;
    params.vcoinId = vcoinId;
    params.tradeType = 1;
    params.tradePrice = hprice;
    params.tradeNumber = hnumber;
});

//点击卖列表，展示买弹框
$(document).on("click",".toBuyDialog",function () {
    $(".tradeMask").removeClass("hide");
    $(".tradeBox").removeClass("hide");
    var hnumber = $(this).find(".hnumber").text();
    var hprice = $(this).find(".hprice").text();
    var hamount = Number(hnumber) * Number(hprice);
    $("#hnumber").val(hnumber);
    $("#hamount").val(Number(hamount).toFixed(8));
    $("#hprice").val(hprice);
    $(".btn-alert-sure").text($.i18n.prop('sell'));
    $(".htitle").text($.i18n.prop('sell'));
    params.marketId = marketId;
    params.vcoinId = vcoinId;
    params.tradeType = 2;
    params.tradePrice = hprice;
    params.tradeNumber = hnumber;
});

//确认委托买卖
$(".btn-alert-sure").bind("click",function() {
    params.tradeNumber = $("#hnumber").val();
    if(!$.cookie("u_id")) {
        closeDialog();
        layer.msg($.i18n.prop('noLoginTradeTips'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
    }
    /** 委托买卖 */
    $.ajax({
        url: "member/trans/handEntrustTrade",
        type: 'POST',
        dataType: "json",
        data: params,
        success: function (r) {
            if (r.code == 0) {
                closeDialog();
                layer.msg($.i18n.prop('tradeSuccess'), {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
            } else {
                layer.msg(r.msg, {offset: '50%',time:1000, shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
            }
        }
    });
});

//控制卖出数量
$("#hnumber").keyup(function () {
    var tradeNumber = $(this).val();
    var reg = new RegExp("^[0-9]+([.][0-9]{1}){0,2}$");
    if(tradeNumber!="" && !reg.test(tradeNumber)){//不是小数和数字
        tradeNumber = tradeNumber.replace(/[^\d.]/g,"");
    }
    var part = tradeNumber.split(".");
    if (part.length < 2) {
        $(this).val(part[0]);
    }else {
        if (part[1].length > 8) {
            $(this).val(part[0]+"."+part[1].substring(0,8));
        }else {
            $(this).val(part[0]+"."+part[1]);
        }
    }
    var tradeNumber = $("#hnumber").val();
    var tradeAmount = Number(params.tradePrice) * Number(tradeNumber);
    $("#hamount").val(Number(tradeAmount).toFixed(8));

});