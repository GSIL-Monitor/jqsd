var $,tab;
layui.config({
	base : "/jqsd/static/js/"
}).use(['bodyTab','form','element','layer','jquery','md5'],function(){
	var form = layui.form,
		layer = layui.layer,
		element = layui.element;
		$ = layui.jquery;
		tab = layui.bodyTab({
			openTabNum : "50",  //最大可打开窗口数量
			//url : "getNavs" //获取菜单json地址
			data: JSON.parse($("#_hide_modules").val())
		});
   // var laydate = layui.laydate;
	var date = new Date();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var time = date.getFullYear()+"年"+(month<10?"0"+month:month)+"月"+(day<10?"0"+day:day)+"日";

	$(".now_time").html(time);

	//更换皮肤
	var skin = window.sessionStorage.getItem("skin")
	if(skin){  //如果更换过皮肤
		if(window.sessionStorage.getItem("skinValue") != "自定义"){
			$("body").addClass(window.sessionStorage.getItem("skin"));
		}else{
			$(".layui-layout-admin .layui-header").css("background-color",skin.split(',')[0]);
			$(".layui-bg-black").css("background-color",skin.split(',')[1]);
			$(".hideMenu").css("background-color",skin.split(',')[2]);
		}
	}

	$(".updatePwd").on("click",function () {
		layer.open({
			title:"修改密码",
			area:["340px","300px"],
			type:1,
			content:'<div class="layui-form layui-form-pane" style="margin: 10px;">' +
					    '<div class="layui-form-item">' +
							'<div class="layui-inline"><label class="layui-form-label">原始密码</label>'+
				            	'<div class="layui-input-inline"><input type="password" value="" name="oPwd" placeholder="请输入原始密码" class="layui-input" lay-verify="required"></div>'+
							'</div>'+
            			'</div>'+
						'<div class="layui-form-item">' +
							'<div class="layui-inline"><label class="layui-form-label">新密码</label>'+
								'<div class="layui-input-inline"><input type="password" value="" name="pwd" placeholder="请输入新密码" class="layui-input" lay-verify="required"></div>'+
							'</div>'+
						'</div>'+
						'<div class="layui-form-item">' +
							'<div class="layui-inline"><label class="layui-form-label">确认新密码</label>'+
								'<div class="layui-input-inline"><input type="password" value="" name="rPwd" placeholder="请确认新密码" class="layui-input" lay-verify="required"></div>'+
							'</div>'+
						'</div>'+
            			'<div class="layui-form-item" style="text-align: center;">'+
            				'<a href="javascript:;" class="layui-btn layui-btn-normal" lay-submit="" lay-filter="changePwd">修改</a>'+
            				'<a href="javascript:;" class="layui-btn layui-btn-primary" id="pwd_close" >取消</a>'+
            			'</div>'+
					'</div>',
			success:function (index,layero) {
                form.render();
                $("#pwd_close").on("click",function () {
                    layer.closeAll();
                });

                form.on("submit(changePwd)",function(data){
                	if(data.field.rPwd!=data.field.pwd){
                		layer.msg("确认密码不一致",{icon: 5});
                		return false;
					}
					var param = {
                		oPwd : $.md5(data.field.oPwd),
                        pwd : $.md5(data.field.pwd),
					};

					$.post("updatePwd",param,function (json) {
						if(json.state==200){
                           layer.msg(json.msg,{icon: 6},function () {
							   location.href= 'logout';
                           })
						}else{
							layer.msg(json.msg,{icon: 5});
						}

                    })
                })
            }
		})
    });

	$(".changeSkin").click(function(){
		layer.open({
			title : "更换皮肤",
			area : ["310px","280px"],
			type : "1",
			content : '<div class="skins_box">'+
						'<form class="layui-form">'+
							'<div class="layui-form-item">'+
								'<input type="radio" name="skin" value="默认" title="默认" lay-filter="default" checked="">'+
								'<input type="radio" name="skin" value="橙色" title="橙色" lay-filter="orange">'+
								'<input type="radio" name="skin" value="蓝色" title="蓝色" lay-filter="blue">'+
								'<input type="radio" name="skin" value="自定义" title="自定义" lay-filter="custom">'+
								'<div class="skinCustom">'+
									'<input type="text" class="layui-input topColor" name="topSkin" placeholder="顶部颜色" />'+
									'<input type="text" class="layui-input leftColor" name="leftSkin" placeholder="左侧颜色" />'+
									'<input type="text" class="layui-input menuColor" name="btnSkin" placeholder="顶部菜单按钮" />'+
								'</div>'+
							'</div>'+
							'<div class="layui-form-item skinBtn">'+
								'<a href="javascript:;" class="layui-btn layui-btn-small layui-btn-normal" lay-submit="" lay-filter="changeSkin">确定更换</a>'+
								'<a href="javascript:;" class="layui-btn layui-btn-small layui-btn-primary" lay-submit="" lay-filter="noChangeSkin">我再想想</a>'+
							'</div>'+
						'</form>'+
					'</div>',
			success : function(index, layero){
				if(window.sessionStorage.getItem("skinValue")){
					$(".skins_box input[value="+window.sessionStorage.getItem("skinValue")+"]").attr("checked","checked");
				};
				if($(".skins_box input[value=自定义]").attr("checked")){
					$(".skinCustom").css("visibility","inherit");
					$(".topColor").val(skin.split(',')[0]);
					$(".leftColor").val(skin.split(',')[1]);
					$(".menuColor").val(skin.split(',')[2]);
				};
				form.render();
				$(".skins_box").removeClass("layui-hide");
				$(".skins_box .layui-form-radio").on("click",function(){
					var skinColor;
					if($(this).find("span").text() == "橙色"){
						skinColor = "orange";
					}else if($(this).find("span").text() == "蓝色"){
						skinColor = "blue";
					}else if($(this).find("span").text() == "默认"){
						skinColor = "";
					}
					if($(this).find("span").text() != "自定义"){
						$("body").removeAttr("class").addClass("main_body "+skinColor+"");
						$(".skinCustom").removeAttr("style");
						$(".layui-bg-black,.hideMenu,.layui-layout-admin .layui-header").removeAttr("style");
					}else{
						$(".skinCustom").css("visibility","inherit");
					}
				})
				var skinStr,skinColor;
				$(".topColor").blur(function(){
					$(".layui-layout-admin .layui-header").css("background-color",$(this).val());
				})
				$(".leftColor").blur(function(){
					$(".layui-bg-black").css("background-color",$(this).val());
				})
				$(".menuColor").blur(function(){
					$(".hideMenu").css("background-color",$(this).val());
				})

				form.on("submit(changeSkin)",function(data){
					if(data.field.skin != "自定义"){
						if(data.field.skin == "橙色"){
							skinColor = "orange";
						}else if(data.field.skin == "蓝色"){
							skinColor = "blue";
						}else if(data.field.skin == "默认"){
							skinColor = "";
						}
						window.sessionStorage.setItem("skin",skinColor);
					}else{
						skinStr = $(".topColor").val()+','+$(".leftColor").val()+','+$(".menuColor").val();
						window.sessionStorage.setItem("skin",skinStr);
					}
					window.sessionStorage.setItem("skinValue",data.field.skin);
					layer.closeAll("page");
				});
				form.on("submit(noChangeSkin)",function(){
					$("body").removeAttr("class").addClass("main_body "+window.sessionStorage.getItem("skin")+"");
					$(".layui-bg-black,.hideMenu,.layui-layout-admin .layui-header").removeAttr("style");
					layer.closeAll("page");
				});
			},
			cancel : function(){
				$("body").removeAttr("class").addClass("main_body "+window.sessionStorage.getItem("skin")+"");
				$(".layui-bg-black,.hideMenu,.layui-layout-admin .layui-header").removeAttr("style");
			}
		})
	})

	//隐藏左侧导航
	$(".hideMenu").click(function(){
		$(".layui-layout-admin").toggleClass("showMenu");
		//渲染顶部窗口
		tab.tabMove();
	})

	//渲染左侧菜单
	tab.render();

    //$(".hideMenu").click();

	//手机设备的简单适配
	var treeMobile = $('.site-tree-mobile'),
		shadeMobile = $('.site-mobile-shade')

	treeMobile.on('click', function(){
		$('body').addClass('site-mobile');
	});

	shadeMobile.on('click', function(){
		$('body').removeClass('site-mobile');
	});


	//changeModules
    form.on("select(changeModules)",function (data) {
		var val = data.value;
		$(".navBar .layui-nav .layui-nav-item a").each(function () {
			var url = $(this).attr("data-url");
			if(url == val){
                addTab($(this));
                return;
			}
        });
    });
	//
    $(".layui-unselect").on("focus",function () {
        $(this).val("");

    });


	// 添加新窗口
	$("body").on("click",".navBar .layui-nav .layui-nav-item a,.top_menu.layui-nav .layui-nav-child a:not('.noAddTab'),.mobileAddTab,#modules option",function(){
		//如果不存在子级
		if($(this).siblings().length == 0){
			addTab($(this));
			$('body').removeClass('site-mobile');  //移动端点击菜单关闭菜单层
		}
		$(this).parent("li").siblings().removeClass("layui-nav-itemed");
	})

	//公告层
	function showNotice(){
		layer.open({
	        type: 1,
	        title: "系统公告",
	        closeBtn: false,
	        area: '310px',
	        shade: 0.8,
	        id: 'LAY_layuipro',
	        btn: ['知道了'],
	        moveType: 1,
	        content: '<div style="padding:15px 20px; text-align:justify; line-height: 22px; text-indent:2em;border-bottom:1px solid #e2e2e2;"><p>这里是公告，有什么消息可以在这里说</p></div>',
	        success: function(layero){
				var btn = layero.find('.layui-layer-btn');
				btn.css('text-align', 'center');
				btn.on("click",function(){
					window.sessionStorage.setItem("showNotice","true");
				})
				if($(window).width() > 432){  //如果页面宽度不足以显示顶部“系统公告”按钮，则不提示
					btn.on("click",function(){
						layer.tips('系统公告躲在了这里', '#showNotice', {
							tips: 3
						});
					})
				}
	        }
	    });
	}
	//如果关闭以后则未关闭浏览器之前不再显示
	// if(window.sessionStorage.getItem("showNotice") != "true"){
	// 	showNotice();
	// }
	// $(".showNotice").on("click",function(){
	// 	showNotice();
	// })


	//刷新后还原打开的窗口
	if(window.sessionStorage.getItem("menu") != null){
		menu = JSON.parse(window.sessionStorage.getItem("menu"));
        window.sessionStorage.removeItem("menu");
		var curmenu = window.sessionStorage.getItem("curmenu");
		var openTitle = '';
		for(var i=0;i<menu.length;i++){
			openTitle = '';
			if(menu[i].icon){
				if(menu[i].icon.split("-")[0] == 'icon'){
					openTitle += '<i class="iconfont '+menu[i].icon+'"></i>';
				}else{
					openTitle += '<i class="layui-icon">'+menu[i].icon+'</i>';
				}
			}
			openTitle += '<cite>'+menu[i].title+'</cite>';
			openTitle += '<i class="layui-icon layui-unselect layui-tab-close" data-id="'+menu[i].layId+'">&#x1006;</i>';

			//定位到刷新前的窗口
			if(curmenu != "undefined"){
				if(curmenu == '' || curmenu == "null"){  //定位到后台首页
					element.tabChange("bodyTab",'');
				}else if(JSON.parse(curmenu).title == menu[i].title){  //定位到刷新前的页面
                    var mm = [menu[i]];
					window.sessionStorage.setItem("menu",JSON.stringify(mm));
                    element.tabAdd("bodyTab",{
                        title : openTitle,
                        content :"<iframe src='"+menu[i].href+"' data-id='"+menu[i].layId+"'></frame>",
                        id : menu[i].layId
                    });
					element.tabChange("bodyTab",menu[i].layId);
				}
			}else{
				//element.tabChange("bodyTab",menu[menu.length-1].layId);
			}
		}
		//渲染顶部窗口
		tab.tabMove();
	}

	//关闭其他
	$(".closePageOther").on("click",function(){
		if($("#top_tabs li").length>2 && $("#top_tabs li.layui-this cite").text()!="后台首页"){
			var menu = JSON.parse(window.sessionStorage.getItem("menu"));
			$("#top_tabs li").each(function(){
				if($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")){
					element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
					//此处将当前窗口重新获取放入session，避免一个个删除来回循环造成的不必要工作量
					for(var i=0;i<menu.length;i++){
						if($("#top_tabs li.layui-this cite").text() == menu[i].title){
							menu.splice(0,menu.length,menu[i]);
							window.sessionStorage.setItem("menu",JSON.stringify(menu));
						}
					}
				}
			})
		}else if($("#top_tabs li.layui-this cite").text()=="后台首页" && $("#top_tabs li").length>1){
			$("#top_tabs li").each(function(){
				if($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")){
					element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
					window.sessionStorage.removeItem("menu");
					menu = [];
					window.sessionStorage.removeItem("curmenu");
				}
			})
		}else{
			layer.msg("没有可以关闭的窗口了@_@");
		}
		//渲染顶部窗口
		tab.tabMove();
	})
	//关闭全部
	$(".closePageAll").on("click",function(){
		if($("#top_tabs li").length > 1){
			$("#top_tabs li").each(function(){
				if($(this).attr("lay-id") != ''){
					element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
					window.sessionStorage.removeItem("menu");
					menu = [];
					window.sessionStorage.removeItem("curmenu");
				}
			})
		}else{
			layer.msg("没有可以关闭的窗口了@_@");
		}
		//渲染顶部窗口
		tab.tabMove();
	})

    //刷新当前
    $(".refresh").on("click",function(){
    	var iframe =$("iframe",$(".clildFrame > .layui-show"))[0] ;
    	iframe.contentWindow.location.reload(true);
    })

});

//打开新窗口
function addTab(_this){
	tab.tabAdd(_this);
}


