layui.config({base: 'static/js/'}).extend({
    jquery_cookie: 'jquery.cookie',
    jquery_validate: 'jquery.validate.min',
    // 默认寻找base也就是js/index.js模块导入
});
layui.use(['layer', 'jquery', "jquery_cookie", 'jquery_validate'], function () {
    var layer = layui.layer
        , $ = layui.$;
    $ = layui.jquery_cookie($);
    $ = layui.jquery_validate($);
    var pathName = window.document.location.pathname;
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    if ($.cookie('u_id')) {
        if (current != 'undefined') {
            $("." + current).addClass("current");
        }
        //赋值估值
        $(".btc-value").text(Number($.cookie('btcValue')).toFixed(8));
        $(".usd-value").text(Number($.cookie('usdValue')).toFixed(8));

        $("#user").text($.cookie('account'));
        $("#assetAmount").text(Number($.cookie('btcValue')).toFixed(8)+"BTC");
        $("#btn-login,#btn-register").addClass("hide");
        $(".top-center").removeClass("hide");
        $(".top-person").removeClass("hide");
    } else {
        $("#btn-login,#btn-register").removeClass("hide");
        $(".top-center").addClass("hide");
        $(".top-person").addClass("hide");
    }
    $(".btn-login").click(function () {
        $("#login-box").removeClass("hide");
    });
    $(".btn-register").click(function () {
        $("#reg-box").removeClass("hide");
    });
    $("#login-box .i-alert-close").click(function () {
        $("#login-box").addClass("hide");
    });
    $("#reg-box .i-alert-close").click(function () {
        $("#reg-box").addClass("hide");
    });

    $("#login").click(function () {
        var account = $("#account");
        var password = $("#password");
        var captchaCode = $("#captchaCode");
        if (!account.val()) {
            account.addClass("error-input");
            account.parent().find(".error-tip").text($.i18n.prop('accountInputTips'));
            return;
        } else {
            account.removeClass("error-input");
            account.parent().find(".error-tip").text("");
        }
        if (!password.val()) {
            password.addClass("error-input");
            password.parent().find(".error-tip").text($.i18n.prop('passwordInputTips'));
            return;
        } else {
            password.removeClass("error-input");
            password.parent().find(".error-tip").text("");
        }
        if (!captchaCode.val()) {
            captchaCode.addClass("error-input");
            captchaCode.parent().find(".error-tip").text($.i18n.prop('captchaCodeInputTips'));
            return;
        } else {
            captchaCode.removeClass("error-input");
            captchaCode.parent().find(".error-tip").text("");
        }
        $.ajax({
            url: basePath + "login",
            data: {account: $("#account").val(), password: hex_md5($("#password").val()),captchaCode: $("#captchaCode").val()},
            cache: false,
            dataType: 'json',
            type: "POST",
            success: function (data) {
                if (data.code != 0) {
                    layer.msg(data.msg, {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
                    changeCode();
                } else {
                    if (data.status == 0) {
                        layer.confirm($.i18n.prop('noRegisterTips')+'？', {
                            btn: [$.i18n.prop('hasRegisterTips'), $.i18n.prop('resendEmailTips')], //按钮
                            offset: '40%'
                        }, function () {
                            window.location.reload();
                        }, function () {
                            $.ajax({
                                url: basePath + "sendMail",
                                data: {account: $("#account").val()},
                                cache: false,
                                dataType: 'json',
                                type: "POST",
                                success: function (data) {
                                    $("#login-box").addClass("hide");
                                    if (data.code != 0) {
                                        layer.msg($.i18n.prop('sendFailTips'), {
                                            offset: '40%',
                                            shade: [0.8, '#393D49'],
                                            skin: 'layui-layer-molv',
                                            time: 5000
                                        });
                                        changeCode();
                                    } else {
                                        layer.msg($.i18n.prop('finishSendEmailTips') + data.account + $.i18n.prop('toRegisterTips'), {
                                            offset: '40%',
                                            shade: [0.8, '#393D49'],
                                            skin: 'layui-layer-molv',
                                            time: 5000
                                        });
                                    }
                                }
                            });
                        });
                    } else {
                        if (data.secondAuthType == 2) {
                            window.location.href = basePath + "login/check";
                        } else {
                            window.location.href = basePath + "index";
                        }
                    }
                }
            },
            error: function () {
                $("#login-box").addClass("hide");
                layer.msg($.i18n.prop('loadFailTips'), {offset: '50%', shade: [0.8, '#393D49'], skin: 'layui-layer-molv'});
            }
        });
    });

    $("#reg-form").validate({
        errorPlacement: function (error, element) {
            error.appendTo(element.next(".error-box"));
        },
        errorClass: "error-tip",
        rules: {
            regAccount: {
                required: true,
                checkEmail: true
            },
            regPassword: {
                required: true,
                checkPwd: true,
                minlength: 8
            },
            regPassword2: {
                required: true,
                checkPwd: true,
                equalTo: "#regPassword"
            }
        }
        ,
        messages: {
            regAccount: {
                required: $.i18n.prop('accountInputTips'),
                email: $.i18n.prop('accountInputError'),
            },
            regPassword: {
                required: $.i18n.prop('passwordInputTips'),
                password: $.i18n.prop('passwordInputLengthTips'),
                minlength: $.i18n.prop('passwordInputFormat')
            },
            regPassword2: {
                required: $.i18n.prop('passwordInputAgainTips'),
                password: $.i18n.prop('passwordInputLengthTips'),
                equalTo: $.i18n.prop('passwordInputNoSame')
            }
        },
        submitHandler: function () {
            if($("#regCaptchaCode").val() == "") {
                $(".regCaptchaCodeTip").text($.i18n.prop('captchaCodeInputTips'));
                return;
            }
            if (!$("#remember").is(':checked')) {
                layer.alert($.i18n.prop('agreeServiceTermTips'), {offset: '50%'});
                return;
            }
            $("#hexPassword").val(hex_md5($("#regPassword").val()));
            $.ajax({
                url: basePath + "register",
                data: $("#reg-form").serialize(),
                cache: false,
                dataType: 'json',
                type: "POST",
                success: function (data) {
                    if (data.code != 0) {
                        layer.alert(data.msg, {offset: '50%'});
                        changeCode();
                    } else {
                        $("#reg-box").addClass("hide");
                        layer.alert($.i18n.prop('finishRegisterTips'), {
                            skin: 'layui-layer-molv' //样式类名
                            , closeBtn: 0, offset: '50%'
                        });
                    }
                },
                error: function () {
                    layer.alert($.i18n.prop('loadFailTips'), {offset: '50%'});
                }
            });
        }
    });

    $(".top-transaction").hover(function (event) {
        $("#alert-transaction").removeClass("hide");
    }, function () {
        $("#alert-transaction").addClass("hide");
    });

    $(".ucenter").hover(function () {
        $(".down-ucenter").removeClass("hide");
    }, function () {
        $(".down-ucenter").addClass("hide");
    });

    $(".asset-record").hover(function () {
        $(".down-asset-record").removeClass("hide");
    }, function () {
        $(".down-asset-record").addClass("hide");
    });

    $(".order-manage").hover(function () {
        $(".down-order-manage").removeClass("hide");
    }, function () {
        $(".down-order-manage").addClass("hide");
    });

    $(".top-country").hover(function () {
        $(".country-con").removeClass("hide");
    }, function () {
        $(".country-con").addClass("hide");
    });


    $(".btn-exit").click(function () {
        $.cookie('u_id', null, {expires: -1, path: '/'});
        window.location.href = basePath;
    });

    $("#userCenter").click(function () {
        window.location.href = basePath + "member/ucenter/index";
    });

    $("#assetAmount").click(function () {
        window.location.href = basePath + "member/asset/index";
    });
    //当前委托
    $(".now-entrust").click(function () {
        window.location.href = basePath + "member/order/currentEntrust";
    });
    //历史委托
    $(".history-entrust").click(function () {
        window.location.href = basePath + "member/order/historyEntrust";
    });
    //历史成交
    $(".history-trade").click(function () {
        window.location.href = basePath + "member/order/historyTrade";
    });

    $(".my-asset").click(function () {
        window.location.href = basePath + "member/asset/index";
    });
    //充值记录
    $(".recharge-record").click(function () {
        window.location.href = basePath + "member/asset/recharge";
    });
    //提现记录
    $(".cash-record").click(function () {
        window.location.href = basePath + "member/asset/cash";
    });

    $(".chinese").click(function() {
        $.cookie('lang', '', { expires: -1 });
        $.cookie("lang", "zh_CN",{path:projectName});
        location.reload();
    });
    $(".english").click(function() {
        $.cookie('lang', '', { expires: -1 });
        $.cookie("lang", "en_US",{path:projectName});
        location.reload();
    });

    //更换验证码
    $(".code-img").bind("click",function() {
        $(this).attr("src","code.do?t="+ genTimestamp())
    });

    function changeCode() {
        $(".code-img").attr("src","code.do?t="+ genTimestamp());
    }

    function genTimestamp() {
        var time = new Date();
        return time.getTime();
    }

    $.validator.addMethod("checkEmail", function (value, element, params) {
        var checkEmail = /^\w+@[a-z0-9]+(\.[a-z]+){1,3}$/;
        return this.optional(element) || (checkEmail.test(value));
    }, "*"+$.i18n.prop('accountInputError'));

    $.validator.addMethod("checkPwd", function (value, element, params) {
        var checkPwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
        return this.optional(element) || (checkPwdReg.test(value));
    }, "*"+$.i18n.prop('passwordInputLengthTips'));
});