var websocket = null;
connect();
function connect() {
    if(websocket==null || !websocket.connected) {
        if (window['WebSocket']) {
            // ws://host:port/project/websocketpath
            var test = window.location.protocol;
            if(test=='http:'){
                websocket = new WebSocket("ws://" + window.location.host + PATH + '/websocket');
            }else{
                websocket = new WebSocket("wss://" + window.location.host + PATH + '/websocket');
            }

        } else {
            websocket = new new SockJS(PATH + '/websocket/socketjs');
        }
    }
}

var firstLoad=true;
//监听建立连接
websocket.onopen = function(event) {
    //console.log('open', event);
    var pathname= window.location.pathname;
    if(pathname==(PATH +'/trans/center')){
        getTradeOrderData();
        window.setInterval(getTradeOrderData,1000);
        if(getcookie("u_id")) {
            getCurrentEntrustOrder();
            window.setInterval(getCurrentEntrustOrder,1000);
        }
    }

    if(firstLoad){
        getTradeGroupInfo();
        window.setInterval(getTradeGroupInfo,1000);
        firstLoad = false;
    }
};
websocket.onclose = function (event) {
    connect();
};

//获取委托单和交易
function getTradeOrderData() {
    var queryMsg = {
        "marketId":marketId,
        "vcoinId":vcoinId,
        "type":1
    };
    websocket.send(JSON.stringify(queryMsg));
}

//发消息获取交易行情
function getTradeGroupInfo() {
    var queryMsg = {
        "marketId":"",
        "vcoinId":"",
        "type":2
    };
    websocket.send(JSON.stringify(queryMsg));
}

//发消息获取用户当前委托
function getCurrentEntrustOrder() {
    var queryMsg = {
        "marketId":marketId,
        "vcoinId":vcoinId,
        "type":3
    };
    websocket.send(JSON.stringify(queryMsg));
}



//监听接收来自服务端的消息
websocket.onmessage = function(event) {
    var data = eval('(' + event.data + ')');
    if(data.type == 2) {
        handleMarketTab(event);
        handleMarketVcoin(event);
        handleMarketSuggest(event);
    }
    if(data.type == 1) {
        handleEntrustAndTradeOrder(event);
    }
    if(data.type == 3) {
        //console.log("currentOrder",event.data)
        handleUserCurrentEnturstOrder(event);
    }
};

//实时刷新用户当前委托
function handleUserCurrentEnturstOrder(event) {
    var data = eval('(' + event.data + ')');
    $(".current-entrust").find("table").find("tr:not(:first)").empty("");
    var current = data.currentUserEntrust;
    if(current.length < 1) {
        return;
    }
    for(var i=0;i<current.length;i++) {
        var tradeTypeStr = "";
        if(current[i].tradeType==1) {
            tradeTypeStr = "<span class='col-green'>"+$.i18n.prop('buy')+"</span>";
        }else if(current[i].tradeType==2) {
            tradeTypeStr = "<span class='col-red'>"+$.i18n.prop('sell')+"</span>";
        }

        $(".current-entrust").find("table").append("<tr>\n" +
            " <td>"+current[i].tradelVcoinNameEn+"/"+current[i].marketName+"</td>" +
            " <td>"+current[i].createTime+ "</td>" +
            " <td>" +tradeTypeStr+" </td>" +
            " <td>"+Number(current[i].tradePrice).toFixed(8)+"</td>" +
            " <td>"+Number(current[i].tradeRemainNumber).toFixed(8)+" "+current[i].tradelVcoinNameEn+"</td>" +
            " <td>"+Number(current[i].tradeRemainAmount).toFixed(8)+"</td>" +
            " <td>" +
            "   <a href='javascript:void(0);'" +
            "      data-tradeNo='"+current[i].tradeNo+"'" +
            "      data-type='"+current[i].tradeType+"'" +
            "      class='col-green killOrder'>"+$.i18n.prop('cancelEntrust')+"</a>" +
            " </td>" +
            "</tr>");
    }
}

/*首页市场行情*/
var init=false;
var showMarketTab="";
var prefix="M";
function handleMarketVcoin(event) {
    var data = eval('(' + event.data + ')');
    //console.log(event.data);
    var tradeGroup = data.tradeGroup;

    if(tradeGroup.length < 1){
        return false;
    }
    if(!init){
        $(".global-tab").empty();
        if(getcookie('u_id')) {
            $(".global-tab").append("<a href='javascript:void(0);' onclick='shiftMarketTab(this)' data-marketName='self-define'>"+$.i18n.prop('collectMarket')+"</a>");
        }
    }
    $("#markets-bd").find(".global-table").remove();
    if(!showMarketTab){
        showMarketTab=prefix + tradeGroup[0].marketName;
    }

    var vcoinCapSearchValue = $(".vcoin-cap-search").val();

    //遍历自选市场
    if(getcookie('u_id')) {
        var collectList = data.collectList;
        if(collectList.length > 0) {
            var collectItem="";
            for(var i = 0;i <collectList.length;i++ ) {

                if(vcoinCapSearchValue != "") {
                    if((collectList[i].vcoinNameEn).indexOf(vcoinCapSearchValue) >= 0) {
                        collectItem +=
                            "            <tr >" +
                            "                <td ><i onclick='uncollect(this)' class='i-star'></i><i data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'>"+ collectList[i].vcoinNameEn+"/"+collectList[i].marketName+"</i></td>\n" +
                            "                <td data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'>"+Number(collectList[i].latestTradePrice).toFixed(8)+"</td>\n" +
                            "                <td data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'><span>"+collectList[i].upOrDownRate+"</span></td>\n" +
                            "                <td data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'>"+Number(collectList[i].maxTradePrice).toFixed(8)+"</td>\n" +
                            "                <td data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'>"+Number(collectList[i].minTradePrice).toFixed(8)+"</td>\n" +
                            "                <td data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'>"+Number(collectList[i].sumTradePrice).toFixed(8)+"</td>\n" +
                            "             </tr>\n";
                    }
                }else {
                    collectItem +=
                        "            <tr >" +
                        "                <td ><i onclick='uncollect(this)' class='i-star'></i><i data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'>"+ collectList[i].vcoinNameEn+"/"+collectList[i].marketName+"</i></td>\n" +
                        "                <td data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'>"+Number(collectList[i].latestTradePrice).toFixed(8)+"</td>\n" +
                        "                <td data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'><span>"+collectList[i].upOrDownRate+"</span></td>\n" +
                        "                <td data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'>"+Number(collectList[i].maxTradePrice).toFixed(8)+"</td>\n" +
                        "                <td data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'>"+Number(collectList[i].minTradePrice).toFixed(8)+"</td>\n" +
                        "                <td data-marketId='"+collectList[i].marketId+"' data-vcoinId='"+collectList[i].vcoinId+"' onclick='toTradeCenter(this)'>"+Number(collectList[i].sumTradePrice).toFixed(8)+"</td>\n" +
                        "             </tr>\n";
                }


                $("#markets-bd .global-mod").after(" <div class='global-table hide' id='self-define' >\n" +
                    "        <table>\n" +
                    "            <tr>\n" +
                    "                <th width=\"150\">"+$.i18n.prop('vcoin')+"</th>\n" +
                    "                <th width=\"200\">"+$.i18n.prop('price')+"</th>\n" +
                    "                <th width=\"200\">"+$.i18n.prop('upOrDown')+"</th>\n" +
                    "                <th width=\"200\">"+$.i18n.prop('maxPrice')+"</th>\n" +
                    "                <th width=\"200\">"+$.i18n.prop('minPrice')+"</th>\n" +
                    "                <th width=\"280\">"+$.i18n.prop('tradeVolume')+"</th>\n" +
                    "            </tr>\n" +
                    collectItem +
                    "        </table>\n" +
                    "    </div>");

            }
        }
    }

    //市场下币种行情
    for(var i = 0;i < tradeGroup.length;i++) {
        if(!init){
            $(".global-tab").append("<a href='javascript:void(0);' onclick='shiftMarketTab(this)' data-marketName='"+prefix+tradeGroup[i].marketName+"'>"+tradeGroup[i].marketName+"</a>");
        }

        var showInfo = "";
        var tradeList = tradeGroup[i].list;

        //遍历行情
        for(var j = 0;j <tradeList.length;j++ ) {
            var starTd = "";
            if(tradeList[j].collect == 1) {
                starTd = "<td><i onclick='uncollect(this)' class='i-star'></i> <i onclick='toTradeCenter(this)' data-marketId='"+tradeList[j].marketId+"' data-vcoinId='"+tradeList[j].vcoinId+"'>"+tradeList[j].vcoinNameEn+"/"+tradeList[j].marketName+"</i></td>";
            }else if(tradeList[j].collect == 0) {
                if(getcookie("u_id")) {
                    starTd = "<td><i onclick='collect(this)' class='i-star-gray'></i><i onclick='toTradeCenter(this)' data-marketId='"+tradeList[j].marketId+"' data-vcoinId='"+tradeList[j].vcoinId+"'>"+tradeList[j].vcoinNameEn+"/"+tradeList[j].marketName+"</i></td>";
                }else {
                    starTd = "<td><i onclick='toTradeCenter(this)' data-marketId='"+tradeList[j].marketId+"' data-vcoinId='"+tradeList[j].vcoinId+"'>"+tradeList[j].vcoinNameEn+"/"+tradeList[j].marketName+"</i></td>";
                }
            }

            if(vcoinCapSearchValue != "") {
                if ((tradeList[j].vcoinNameEn).indexOf(vcoinCapSearchValue) >= 0
                            || (tradeList[j].vcoinNameEn).toLowerCase().indexOf(vcoinCapSearchValue) >= 0) {
                    showInfo +=
                        "            <tr data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'>" +
                        starTd +
                        "                <td data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'  onclick='toTradeCenter(this)' >" + Number(tradeList[j].latestTradePrice).toFixed(8) + "</td>\n" +
                        "                <td data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'  onclick='toTradeCenter(this)' ><span>" + tradeList[j].upOrDownRate + "</span></td>\n" +
                        "                <td data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'  onclick='toTradeCenter(this)' >" + Number(tradeList[j].maxTradePrice).toFixed(8) + "</td>\n" +
                        "                <td data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'  onclick='toTradeCenter(this)' >" + Number(tradeList[j].minTradePrice).toFixed(8) + "</td>\n" +
                        "                <td data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'  onclick='toTradeCenter(this)' >" + Number(tradeList[j].sumTradePrice).toFixed(8) + "</td>\n" +
                        "             </tr>\n";
                }
            }else {
                showInfo +=
                    "            <tr data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'>" +
                    starTd +
                    "                <td data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'  onclick='toTradeCenter(this)' >" + Number(tradeList[j].latestTradePrice).toFixed(8) + "</td>\n" +
                    "                <td data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'  onclick='toTradeCenter(this)' ><span>" + tradeList[j].upOrDownRate + "</span></td>\n" +
                    "                <td data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'  onclick='toTradeCenter(this)' >" + Number(tradeList[j].maxTradePrice).toFixed(8) + "</td>\n" +
                    "                <td data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'  onclick='toTradeCenter(this)' >" + Number(tradeList[j].minTradePrice).toFixed(8) + "</td>\n" +
                    "                <td data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'  onclick='toTradeCenter(this)' >" + Number(tradeList[j].sumTradePrice).toFixed(8) + "</td>\n" +
                    "             </tr>\n";
            }
        }

        $("#markets-bd .global-mod").after(" <div class='global-table hide' id='"+prefix+tradeGroup[i].marketName+"' >\n" +
            "        <table>\n" +
            "            <tr>\n" +
            "                <th width=\"150\">"+$.i18n.prop('vcoin')+"</th>\n" +
            "                <th width=\"200\">"+$.i18n.prop('price')+"</th>\n" +
            "                <th width=\"200\">"+$.i18n.prop('upOrDown')+"</th>\n" +
            "                <th width=\"200\">"+$.i18n.prop('maxPrice')+"</th>\n" +
            "                <th width=\"200\">"+$.i18n.prop('minPrice')+"</th>\n" +
            "                <th width=\"280\">"+$.i18n.prop('tradeVolume')+"</th>\n" +
            "            </tr>\n" +
                    showInfo +
            "        </table>\n" +
            "    </div>");
    }
    init=true;
    $("#"+showMarketTab).removeClass("hide");
    $("a[data-marketName='"+showMarketTab+"']").addClass("current");
}

function shiftMarketTab(this_) {
    $(this_).parent().find("a").removeClass("current");
    $(this_).addClass("current");
    var marketname=$(this_).attr("data-marketName");
    $("#"+marketname).parent().find(".global-table").addClass("hide");
    $("#"+marketname).removeClass("hide");
    showMarketTab=marketname;
}


/*市场切换*/
var initMarket=false;
var showTab="";
function handleMarketTab(event) {
    var data = eval('(' + event.data + ')');
    var tradeGroup = data.tradeGroup;
    if(tradeGroup.length < 1){
        return false;
    }
    if(!initMarket){
        $(".marketList").empty();
    }
    $("#alert-transaction").find(".order-table").remove();
    if(!showTab){
        showTab=tradeGroup[0].marketName;
    }

    //搜索词
    var vcoinSearchValue = $(".vcoin-tab-search").val();
    for(var i = 0;i < tradeGroup.length;i++) {
        if(!initMarket){
            $(".marketList").append("<a href='javascript:void(0);' onclick='shiftTab(this)' data-marketName='"+tradeGroup[i].marketName+"'>"+tradeGroup[i].marketName+"</a>");
        }

        var showInfo = "";
        var tradeList = tradeGroup[i].list;
        if(vcoinSearchValue != "") {//搜索词不为空
            for(var j = 0;j <tradeList.length;j++ ) {
                if ((tradeList[j].vcoinNameEn).indexOf(vcoinSearchValue)>=0 ||
                                    (tradeList[j].vcoinNameEn).toLowerCase().indexOf(vcoinSearchValue)>=0 ) {
                    showInfo +=
                        "            <tr onclick='toTradeCenter(this)' data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'>\n" +
                        "                <td>" + tradeList[j].vcoinNameEn+"/"+tradeGroup[i].marketName + "</td>\n" +
                        "                <td>" + Number(tradeList[j].latestTradePrice).toFixed(8) + "</td>\n" +
                        "                <td>" + tradeList[j].upOrDownRate + "</td>\n" +
                        "                <td>" + Number(tradeList[j].sumTradePrice).toFixed(8) + "</td>\n";

                }
            }
        }else {
            for(var j = 0;j <tradeList.length;j++ ) {
                showInfo +=
                    "            <tr onclick='toTradeCenter(this)' data-marketId='" + tradeList[j].marketId + "' data-vcoinId='" + tradeList[j].vcoinId + "'>\n" +
                    "                <td>" + tradeList[j].vcoinNameEn +"/"+tradeGroup[i].marketName+ "</td>\n" +
                    "                <td>" + Number(tradeList[j].latestTradePrice).toFixed(8) + "</td>\n" +
                    "                <td>" + tradeList[j].upOrDownRate + "</td>\n" +
                    "                <td>" + Number(tradeList[j].sumTradePrice).toFixed(8) + "</td>\n";

            }
        }


        $("#alert-transaction").append(" <div class='order-table hide' id='"+tradeGroup[i].marketName+"' >\n" +
            "        <table>\n" +
            "            <tr>\n" +
            "                <th>"+$.i18n.prop('vcoin')+"</th>\n" +
            "                <th>"+$.i18n.prop('latestPrice')+"</th>\n" +
            "                <th>"+$.i18n.prop('upOrDown')+"</th>\n" +
            "                <th>"+$.i18n.prop('tradeVolume')+"</th>\n" +
            "            </tr>\n" +
                        showInfo +
            "            </tr>\n"+
            "        </table>\n" +
            "    </div>");
    }
    initMarket=true;
    $("#"+showTab).removeClass("hide");
    $("a[data-marketName='"+showTab+"']").addClass("current");
}

/*首页推荐*/
function handleMarketSuggest(event) {
    $(".exchange-show").find("ul").empty();
    var data = eval('(' + event.data + ')');
    var tradeGroup = data.tradeGroup;
    if(tradeGroup.length < 1){
        return false;
    }
    var count = 0;
    for(var i = 0;i < tradeGroup.length;i++) {
        var tradeList = tradeGroup[i].list;
        for(var j =0 ;j< tradeList.length;j++) {
            if (count >= 5) { return; }
            if (tradeList[j].suggest == 1) {
                $(".exchange-show").find("ul").append("<li class='exchange-msg' onclick='toTradeCenter(this)' data-marketId='"+tradeList[j].marketId+"' data-vcoinId='"+tradeList[j].vcoinId+"' >\n" +
                    "        <p class='head clearfix'>\n" +
                    "        <span class='fl'>" + tradeList[j].vcoinNameEn + "/" + tradeList[j].marketName + "</span>\n" +
                    "        <span class='fr'>" + tradeList[j].upOrDownRate + "</span>\n" +
                    "        </p>\n" +
                    "        <div class='con clearfix'>\n" +
                    "        <div class='fl'>\n" +
                    "        <span class='big-price'>"+tradeList[j].upOrDown+"</span>\n" +
                    "        <span class='small-txt'>"+Number(tradeList[j].latestTradePrice).toFixed(8)+" "+tradeList[j].marketName+"</span>\n" +
                    "    <p class='small-txt'>"+$.i18n.prop('tradeVolume')+": " + Number(tradeList[j].sumTradePrice).toFixed(8) + " " + tradeList[j].marketName + "</p>\n" +
                    "    </div>\n" +
                    "        </li>");
                count++;
            }
        }
    }
}



//刷委托单和交易行情
function handleEntrustAndTradeOrder(event) {
    //console.log(event.data)
    var data = eval('(' + event.data + ')');
    var buyList = data.buyList;
    var sellList = data.sellList;
    var buySum = data.buySum;
    var sellSum = data.sellSum;
    var tradeList = data.tradeList;
    var tradeGroupInfo = data.tradeGroupInfo;

    //交易行情
    //$(".maxPrice").text(Number(tradeGroupInfo.maxTradePrice).toFixed(8));
    //$(".minPrice").text(Number(tradeGroupInfo.minTradePrice).toFixed(8));
    $(".sumPrice").text(Number(tradeGroupInfo.sumTradePrice).toFixed(8));
    $("#latestPrice").text(Number(tradeGroupInfo.latestTradePrice).toFixed(8));
    $("#usdValue").text(Number(tradeGroupInfo.usdValue).toFixed(2));

    $("#upDown").html(tradeGroupInfo.upOrDown);
    $(".upDownRate").html(tradeGroupInfo.upOrDownRate);

    /*交易记录*/
    $("#tradeTable  tr:not(:first)").empty("");
    if(tradeList !=null && tradeList != undefined ) {
        for(var i = 0;i < tradeList.length;i++) {
            if(tradeList[i].tradeType==1) {
                $("#tradeTable").append('<tr><td>'+tradeList[i].tradeTime+'</td>' +
                    '<td><span class="col-green">'+tradeList[i].tradePrice+'</span></td>' +
                    '<td>'+tradeList[i].tradeNumber+'</td>' +
                    '</tr>');
            }else {
                $("#tradeTable").append('<tr><td>'+tradeList[i].tradeTime+'</td>' +
                    '<td><span class="col-red">'+tradeList[i].tradePrice+'</span></td>' +
                    '<td>'+tradeList[i].tradeNumber+'</td>' +
                    '</tr>');
            }
        }
    }

    //委托买
    if(buyList !=null && buyList != undefined ) {
        $("#buyTable  tr:not(:first)").empty("");
        for (var i = buyList.length - 1; i >= 0; i--) {
            $("#buyTable").append('<tr class="toBuyDialog"><td class="hprice">' + buyList[i].tradePrice + '</td>' +
                '<td class="hnumber">' + buyList[i].tradeNumberSum + '</td>' +
                '<td><span class="col-green">' + buyList[i].tradeAmountSum + '</span></td>' +
                '</tr>');
        }
    }
    //委托卖
    if(sellList !=null && sellList != undefined ) {
        $("#saleTable  tr:not(:first)").empty("");
        for (var i = sellList.length - 1; i >= 0; i--) {
            $("#saleTable").append('<tr class="toSellDialog"><td class="hprice">' + sellList[i].tradePrice + '</td>' +
                '<td  class="hnumber">' + sellList[i].tradeNumberSum + '</td>' +
                '<td><span class="col-red">' + sellList[i].tradeAmountSum + '</span></td>' +
                '</tr>');
        }
    }
    //委托买总额
    if(buySum != undefined) {
        $("#buyAmount").text(buySum);
    }
    //委托卖总额
    if(sellSum != undefined) {
        $("#saleAmount").text(sellSum);
    }
}
function shiftTab(this_) {
    $(this_).parent().find("a").removeClass("current");
    $(this_).addClass("current");
    var marketname=$(this_).attr("data-marketName");
    $("#"+marketname).parent().find(".order-table").addClass("hide");
    $("#"+marketname).removeClass("hide");
    showTab=marketname;
}

function toTradeCenter(this_) {
    var marketId = $(this_).attr("data-marketId");
    var vcoinId = $(this_).attr("data-vcoinId");
    window.location.href = basePath + "trans/center?marketId="+marketId+"&vcoinId="+vcoinId;
}


