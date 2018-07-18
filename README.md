
## 后台代码规范

###  controller层
   
   - 新建`controller`都要继承`BaseController`
   - 如何定义`service`
   
  ```java
      SaleService service = getServiceInstance(SaleService.class);
  ```
 -  访问页面与获取表单数据使用同一方法,用`isPost()`来区分,例如
 
 ```java
 	public void list() {
 		if(isPost()){
 			QueryParamVo vo = getParamVo();
 			vo.setPlatform(getPara("platform"));
 			vo.setShop(getPara("shop"));
 			vo.setCreateTime(getPara("createTime"));
 			List<BrushOrder> list = service.getBrushList(vo);
 			renderJson(JsonUtil.getData(list));
 		}else{
 			render("brush_order_input.html");
 		}
 	}
 ```
 - `BaseContoller` 默认提供的三个方法
 
 ```java
 /**
 	 * 判断是否post请求
 	 * @return
 	 */
 	protected boolean isPost(){
 	 	return POST.equals(getRequest().getMethod());
 	 }
 
 	/**
 	 * 获取参数vo
 	 * @return
 	 */
 	protected QueryParamVo getParamVo(){
 		int pageNumber = getParaToInt("pageNum",1);		//当前页
 		int pageSize = getParaToInt("numPerPage", PageConstant.DEFAULT_PAGESIZE_TEN);//分页数
 		return new QueryParamVo(pageNumber,pageSize);
 	}
 	
 	/**
     * 获取service实例
     * @param clazz
     * @param <Ser>
     * @return
     */
    protected  <Ser extends Service> Ser getServiceInstance(Class<Ser> clazz) {
    	Ser service = Service.getInstance(clazz,this);
    	return Duang.duang(service);
    }
 ```
 - 尽量做到`controller`与`service`解耦，查询方法所以有参数都是通过`QueryParamVo`来传递。
 - 如果是添加修改方法，也可以使用`model`来传值，如:
 
 ```java
    Admin admin = getModel(Admin.class);
```
页面上使用

```html
   <input type="text" name="admin.realName" id="realName" class="layui-input" >
   <input type="text" name="admin.phone" id="phone" class="layui-input" >
    .......
```

###  service层

- 新建`service`都要继承`Service`
- 在业务层尽量不要出现`SQL`代码，统一写到`dao`层，提高代码复用率。
- 手动开启事务，要在方法上加上注解`@Before(Tx.class)`，减少数据库连接压力。

```java
    @Before(Tx.class)
    public String inputExcel(QueryParamVo vo){
       ......
    }
```

###  dao层

- 代码逻辑都要加上注释，写复杂`sql`前一定要查看执行计划`explain`，减少慢查询的出现。
- 尽量不要出现`for`循环的查询代码。
- 查询相应表的方法都要写在对应表的`model`类里，并且禁止出现静态方法。

**自动生成model**

在数据库有变化重新生成一次便可,自动生成的`model`不会覆盖已经存在的文件。
运行`_JFinalDemoGenerator`的`main`方法

###  数据库

- 表命名格式: `t_模块_功能`  
- 字段名格式: 驼峰命名 `userName`
- 建表都要加上id字段

## 前端页面代码规范

### table表格的两个生成方法

- 使用`tlp`模板生成

```js
    form.on("submit(search)",function(data){
        getPage(data);
    });

    var getPage = function (data) {
        common.post("list",data.field,function (json) {
            $("#myTable").html(common.tpl(json));//根据数据生成表格
            common.page(data,json,getPage);//分页
        })
    };
```
- 使用`layui`的`table`模块生成

```js
    form.on("submit(search)",function(data){
        table.reload({
			where:data.field
		})
    });

    var table = common.table({
            url: 'roleList',
            cols: [[
                {field:null,title:'序号',width:60,templet:"<div><span class='xh'></span></div>"},
                {field:'roleName',title:'角色名',width: 150},
                {field:'description',title:'描述',width: 150},
                {field:'id', title:'操作',templet:"#barDemo"},
            ]],
            done:function (res, curr, count) {
                var start = 1; //生成序号
                $(".xh").each(function (i, cell) {
                    cell.innerHTML = i + start;
                });
            }
        });
```

- 还有更多方法详情见[`common.js`](https://gitee.com/samwork/jqsd/blob/master/src/main/webapp/static/js/common.js)





