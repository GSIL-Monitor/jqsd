
var $ = layui.jquery,jQuery = layui.jquery, form = layui.form,
laytpl = layui.laytpl , laypage = layui.laypage,laytable = layui.table,laydate = layui.laydate;;

//时间格式化
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

//格式化模版函数
String.prototype.Format = function (args) {
    if (arguments.length < 1) {
        return this;
    }
    var result = this,
        len = arguments.length,
        reg = function (s) {
            return new RegExp("(\\{" + s + "\\})", "g")
        };
    if (len == 1 &&
        Object.prototype.toString.call(args) == "[object Object]") {
        for (var k in args) {
            result = result.replace(reg(k), args[k]);
        }
    }
    else {
        for (var i = 0; i < len; i++) {
            if (arguments[i] == undefined) {
                continue;
            }
            result = result.replace(reg(i), arguments[i]);
        }
    }
    return result;
};

//替换所有
String.prototype.replaceAll  = function(s1,s2){
    return this.replace(new RegExp(s1,"gm"),s2);
};

// common 主要封装常用方法
var common = {};

/**
 *
 * @param id 表格id
 * @param options 设置列属性
 * @returns {jQuery}
 */
common.table = function(options){
    var height = window.innerHeight - 161;
    var defaults = { //其它参数在此省略
        elem: '#myTable',
        height:height,
        even:true,
        cellMinWidth: 80,
        method: 'post',
        page: true, //开启分页
        limits: [10,20,50,100,300],
        limit: 20, //默认采用20
        request: {
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'numPerPage' //每页数据量的参数名，默认：limit
        }
    };
    $.extend(true,defaults, options);

    return laytable.render(defaults);
};

/**
 * post请求
 * @param url
 * @param data
 * @param success
 * @param error
 */
common.post = function(url, data, success, error) {
    common.loading();
    $.ajax({
        "url": url,
        "data": data,
        "type": "POST",
        "success": function(json) {
            common.stop();
            if(success) success(json);
        },
        "error": function(json, t, tt) {
            common.msg("系统异常，请与管理员联系!");
            common.stop();
            if(error) error(json);
        },
        dataType: "json"
    });
};

common.get = function(url, data, success, error) {
    common.loading();
    $.ajax({
        "url": url,
        "data": data,
        "type": "GET",
        "success": function(json) {
            common.stop();
            if(success) success(json);
        },
        "error": function(json, t, tt) {
            common.stop();
            if(error) error(json);
        },
        dataType: "json"
    });
};

/**
 * 获取日期
 * @param i
 */
common.getToday = function (i) {
    var d = new Date();
    if (i !== undefined) {
        d.setDate(d.getDate() + i);
        return d.Format("yyyy-MM-dd");
    } else {
        return d.Format("yyyy-MM-dd");
    }
};

/**
 * a = d = 当前日期
 * b = 6 - w = 当前周的还有几天过完（不算今天）
 * a + b 的和在除以7 就是当天是当前月份的第几周
 * @param a 年
 * @param b 月
 * @param c 日
 * @returns {number}
 */
common.getMonthWeek = function (a, b, c) {
    var date = new Date(a, parseInt(b) - 1, c), w = date.getDay(), d = date.getDate();
    return Math.ceil(
        (d + 6 - w) / 7
    );
};

/**
 * date1是当前日期
 * date2是当年第一天
 * d是当前日期是今年第多少天
 * 用d + 当前年的第一天的周差距的和在除以7就是本年第几周
 * @returns {number}
 */
common.getYearWeek = function () {
    //new Date(a, parseInt(b) - 1, c)
    var date1 = new Date(), date2 = new Date(date1.getFullYear(), 0, 1),
        d = Math.round((date1.valueOf() - date2.valueOf()) / 86400000);
    return Math.ceil(
        (d + ((date2.getDay() + 1) - 1)) / 7
    )-1;
};

/**
 * 生成相册层
 * @param imgs
 * @returns {string}
 */
common.getImages = function(imgs){
    var html = "<div class='thumbnail' style='width: 80px;'> "
    for (var i in imgs) {
        if (i >= 2) {
            html += "<i style='display: none;'><img layer-src='" + imgs[i] + "' src='" + imgs[i] + "'  /></i>";
        } else {
            html += "<i class='cur_hand'  ><img layer-src='" + imgs[i] + "' src='" + imgs[i] + "'  /></i>";
        }
    }
    html += "</div>";
    return html;
};


/**
 * 信息提示方法
 * 轻提示,3秒后消失
 * @param msg
 * @param icon 0（大于6）感号、1勾、2叉、3问号、4锁、5哭、6笑、
 * @param cb
 */
common.msg = function (msg,icon,cb) {
    var type = typeof icon === 'function';
    if(type) cb = icon,icon = 6;
    layer.msg(msg,{icon: icon || 6 },cb);
};

/**
 * 确认提示
 * @param msg
 * @param btn
 * @param callback
 * @param title
 */
common.alert = function (msg, btn, cb, title) {
    var type = typeof btn === 'function';
    if(type) cb = btn,btn = ['确定'];
    var index = layer.open({
        title: title || '提示',
        content: msg,
        btn: btn || ['确定'],
        yes: cb || function () {
            layer.close(index);
        }
    })
};

/**
 *
 * @param msg
 * @param btn
 * @param callback
 * @param title
 */
common.confirm = function (msg,callback,callback2,btn,title) {
    var index = layer.confirm(msg, {
            title: title || '提示',
            btn: btn || ['确定', '取消'], //按钮
            //yes:callback,
            cancel:callback2 //右上角关闭回调
        },callback,callback2);
};

var _loading_index = 0;
/**
 * 加载层
 */
common.loading = function () {
    _loading_index = parent.layer.load();
};
/**
 * 停止加载
 */
common.stop = function () {
    parent.layer.close(_loading_index);
};

/**
 * 页面层
 * @param content
 * @param options
 */
common.open = function (content,options) {
    var defaults = {
        type: 1,
        area: '360px',
        content: content,
        title: '提示信息',
        btn: ['确定'], //按钮
        yes: function () {
            layer.close(index);
        },
    };
    $.extend(true,defaults, options);
    var index = layer.open(defaults);
};

/**
 * tips层
 * @param msg 信息
 * @param id id
 */
common.tips = function(msg,id){
    layer.tips(msg, id, {
        tips: [1, '#0FA6D8'] //还可配置颜色
    });
};

/**
 * 关闭层
 * @param index
 */
common.close = function (index) {
    if (index) {
        layer.close(index);
    } else {
        layer.closeAll();
    }
};

/**
 * 像册层
 * @param id
 */
common.photos = function(id){
    layer.photos({
        shift: 5,
        photos: id

    });
};

/**
 * 固定表头
 */
common.fieldTable = function () {
    var timer,is;
    $(document).on('scroll', function(){
        if(timer) clearTimeout(timer);
        timer = setTimeout(function(){
            scroll();
        }, 100);
    });
    var wi = $(".layui-table").width()-10;
    var th = $(".layui-table thead").html();
    var dom = "<table class='layui-table table_fixed'>"+th+"</table>";
    $("body").append(dom);
    $("body .table_fixed").css("width",wi+"px");
    var scroll = function(){
        var stop = $(document).scrollTop();
        if(stop >= 200 ){
            is || ($("body .table_fixed").show(), is = 1);
        } else {
            is && ($("body .table_fixed").hide(), is = 0);
        }
    };
};

/**
 * 上传文件附带参数 (新版uplad可以自带参数了)
 * @param input
 * @param data
 */
/*common.upload_data = function (input,data){
    var item=[];
    $.each(data,function(k,v){
        item.push('<input type="hidden" name="'+k+'" value="'+v+'">');
    });
    $(input).after(item.join(''));
};*/

/**
 * 文件上传
 * @param url
 * @param data
 * @param cb    回调方法
 * @param elem 上传文件的input ID,默认是 _layui_upload
 */
common.upload = function (url,data,cb,btn,elem) {
    layui.upload.render({
        url   : url, //上传接口
        data  : data,
        accept: 'file', //允许上传的文件类型
        auto: false, //选择文件后不自动上传
        bindAction: btn||'#_layui_upload_btn',//指向一个按钮触发上传
        elem  : elem || '#_layui_upload',
        before: function(input){
            //返回的参数item，即为当前的input DOM对象
            common.loading();
            //common.upload_data(input,data);
        },
        done: function(res){ //上传成功后的回调
            common.stop();
            if(cb)cb(res);
        }
    });
};

/**
 * 文件下载
 * @param fileName
 * @param path 下载文件所在相对路径，默认下载model模板文件
 */
common.download = function (fileName,path) {
    var a = document.createElement("a");
    document.body.appendChild(a);
    a.href = "/jqsd/"+( path || "model" )+"/"+fileName;
    a.download = fileName;
    a.click();
    document.body.removeChild(a);
};


/**
 * 分页方法
 * @param data 请求数据的参数
 * @param json 返回的page信息
 * @param fn   回调
 */
common.page = function (data,json,fn) {
    laypage.render({
        elem    :'page',
        count   :json.totalRow,
        limit   :json.pageSize,
        curr    :json.pageNumber,
        height: 'full-20',
        request :{
            pageName    : 'pageNum', //页码的参数名称，默认：page
            limitName   : 'numPerPage' //每页数据量的参数名，默认：limit
        },
        /*response: {
            statusName  : 'state', //数据状态的字段名称，默认：code
            statusCode  :  200,//成功的状态码，默认：0
            msgName     : 'msg', //状态信息的字段名称，默认：msg
            countName   : 'data.totalRow', //数据总数的字段名称，默认：count
            dataName    : 'data.list', //数据列表的字段名称，默认：data
        },*/
        layout  :['prev', 'page', 'next','count'],
        jump    :function(obj, first){
            if(!first){
                data.field.pageNum = obj.curr;
                if(fn)fn(data);
            }
        }
    });
};

/**
 * 生成模板
 * @param json 模板数据
 * @param elem 模板ID，默认 tpl
 */
common.tpl = function (json,elem) {
    var tpl = laytpl($(elem||"#tpl").html());
    return tpl.render(json);
};

/**
 * laydate 日期
 * @param elem
 * @param options
 */
common.date = function (elem,options) {
    var defaults = {
        elem: elem,
    };
    $.extend(true,defaults, options);
    return laydate.render(defaults);
};

