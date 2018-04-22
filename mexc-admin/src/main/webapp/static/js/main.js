layui.config({
	base : "static/js/"
}).use(['form','element','layer','jquery'],function(){
	var form = layui.form,
		layer = layui.layer,
		element = layui.element,
		$ = layui.jquery;

	$(".panel a").on("click",function(){
		window.parent.addTab($(this));
	})

});
