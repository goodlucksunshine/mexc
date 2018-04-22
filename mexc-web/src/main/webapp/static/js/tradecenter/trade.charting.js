
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
var scheme = window.location.protocol;
var URL = scheme+"//"+window.location.host + PATH + "/tradingview";
//交易对
var tradeGroup = marketName+"/"+vcoinNameEn;

var locale = 'zh';
if(getcookie("lang") == 'zh_CN') {
    locale = 'zh';
}else if(getcookie("lang") == 'en_US') {
    locale = 'en';
}

TradingView.onready(function() {
    var widget = window.tvWidget = new TradingView.widget({
        fullscreen: true,
        symbol: tradeGroup,
        interval: 'D',
        toolbar_bg: '#ffffff',
        timezone: "Asia/Shanghai",
        container_id: "tradingview-chart",
        allow_symbol_change: false,
        datafeed: new Datafeeds.UDFCompatibleDatafeed(URL),
        //datafeed: new Datafeeds.UDFCompatibleDatafeed("https://demo_feed.tradingview.com"),
        library_path: basePath+"static/charting_library/",
        locale: locale,
        drawings_access: { type: 'black', tools: [ { name: "Regression Trend" } ] },
        width:'fullscreen',
        height:'fullscreen',
        disabled_features: ["use_localstorage_for_settings",
            "header_settings",
            "header_saveload",
            "header_indicators",
            "header_symbol_search",
            "header_saveload_to_the_right",
            "header_compare",
            "legend_context_menu",
            "edit_buttons_in_legend",
            "volume_force_overlay",
            "dont_show_boolean_study_arguments",
            /*"display_market_status",*/
            "hide_last_na_study_ontput",
            "move_logo_to_main_pane",
            "property_pages",
            "main_series_scale_menu",
            "left_toolbar"
        ],
        numeric_formatting: {
            decimal_sign : "."
        },
        enabled_features: [
            "show_dom_first_time",
            "study_templates",
            "header_chart_type"
        ],
        overrides: {
            "mainSeriesProperties.style": 1,
            "symbolWatermarkProperties.color": "#994",
            "volumePaneSize": "medium",
            //"volumePaneSize": "large",
            "mainSeriesProperties.candleStyle.upColor": "#53B987",
            "mainSeriesProperties.candleStyle.downColor": "#EB4D5C",
            "mainSeriesProperties.candleStyle.drawWick": true,
            "mainSeriesProperties.candleStyle.drawBorder": true,
            "mainSeriesProperties.candleStyle.borderColor": "#EB4D5C",
            "mainSeriesProperties.candleStyle.borderUpColor": "#53B987",
            "mainSeriesProperties.candleStyle.borderDownColor": "#EB4D5C",
            "mainSeriesProperties.candleStyle.wickUpColor": '#53B987',
            "mainSeriesProperties.candleStyle.wickDownColor": '#EB4D5C',
            "mainSeriesProperties.candleStyle.barColorsOnPrevClose": false,
            "paneProperties.legendProperties.showLegend":false,

            "volume.volume.color.0": "#00FFFF",
            "volume.volume.color.1": "#0000FF",
            "volume.volume.transparency": 70,
            "volume.volume ma.color": "#FF0000",
            "volume.volume ma.transparency": 30,
            "volume.volume ma.linewidth": 5,
            "volume.show ma": true,
            "bollinger bands.median.color": "#33FF88",
            "bollinger bands.upper.linewidth": 7
        },
        studies_overrides: {
            "bollinger bands.median.color": "#33FF88",
            "bollinger bands.upper.linewidth": 7
        },
     /*  time_frames: [
            { text: "1m", resolution: "60" },
            { text: "1d", resolution: "30" },
            { text: "3d", resolution: "15" },
            { text: "1d", resolution: "5" }
        ],*/
        favorites: {
            intervals: ["1","5","D","M"],
            chartTypes: ["Area", "Line"]
        },
        charts_storage_api_version: "1.1",
        client_id: 'tradingview.com',
        user_id: 'public_user_id'
    });

    widget.onChartReady(function() {
        // 现在可以调用其他widget的方法了
        //widget.chart().createStudy('MACD', false, false, [14, 30, "close", 9]);
        widget.chart().createStudy('Moving Average Exponential', false, false, [26]);
        //widget.chart().createStudy('Moving Average', false, false, [26], null, {'Plot.linewidth': 10});
        //widget.chart().createStudy('Stochastic', false, false, [26], null, {"%d.color" : "#FF0000"});
    });
});




