layui.use(['layer', 'jquery'], function () {
    var layer = layui.layer
        , $ = layui.$;
    $.ajaxSetup({
        layerIndex: -1,
        beforeSend: function () {
            this.layerIndex = layer.load(1, {offset: '50%', shade: [0.5, '#393D49']});
        },
        complete: function (XMLHttpRequest, textStatus) {
            if (textStatus == '401') {
                window.location.href = basePath;
                return;
            }
            layer.close(this.layerIndex);
        }
    });
});
