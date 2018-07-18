package com.jqsd.common;

import cn.dreampie.quartz.QuartzKey;
import cn.dreampie.quartz.QuartzPlugin;
import cn.dreampie.quartz.job.QuartzCronJob;
import cn.dreampie.quartz.job.QuartzJob;
import cn.dreampie.quartz.job.QuartzOnceJob;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.ext.plugin.sqlinxml.SqlInXmlPlugin;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.*;
import com.jfinal.plugin.activerecord.cache.EhCache;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.jqsd.common.constant.CommonConstant;
import com.jqsd.common.model.DbConfig;
import com.jqsd.common.model.ScheduleJob;
import com.jqsd.common.model._MappingKit;
import com.jqsd.common.util.DesUtil;
import com.jqsd.core.controller.IndexController;
import com.jqsd.core.controller.LoginController;
import com.jqsd.core.controller.config.DbConfigController;
import com.jqsd.core.controller.manager.ManagerController;
import com.jqsd.core.job.DemoJob;

import java.util.Date;
import java.util.List;


/**
 * JFinal项目全局配置
 */
public class AppConfig extends JFinalConfig {
	

	private Routes routes;
	private boolean OPEN_SHIRO = false;
	private boolean ISDEV = true;
	
	/**
	 * 配置常量
	 */
	@Override  
    public void configConstant(Constants me) {  

		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("app-config.txt");
		ISDEV = PropKit.getBoolean("devMode", false);
		me.setViewType(ViewType.FREE_MARKER);
		me.setDevMode(ISDEV);

		if(ISDEV){
			/**
			 *  TODO 生产环境下使用软连接到项目目录下
			 *  ln -s /opt/Modules/upload /opt/tomcat/webapps/Modules/
			 *  ln -s /opt/Modules/download /opt/tomcat/webapps/Modules/
			 */
			me.setBaseUploadPath("/opt/jqsd/upload");
			me.setBaseDownloadPath("/opt/jqsd/download");
		}

		me.setMaxPostSize(1024*1024*50);

		//me.setBaseViewPath("/WEB-INF/views/");
		me.setError401View("/WEB-INF/views/login.html");
		me.setError403View("/WEB-INF/views/login.html");
		me.setError404View("/WEB-INF/views/error/404.html");
		me.setError500View("/WEB-INF/views/error/500.html");

		me.setFreeMarkerTemplateUpdateDelay(100);
		//FreeMarkerRender.setProperty("template_update_delay", "0");// 模板更更新时间,0表示每次都加载
		FreeMarkerRender.setProperty("classic_compatible", "true");// 如果为null则转为空值,不需要再用!处理
		FreeMarkerRender.setProperty("whitespace_stripping", "true");// 去除首尾多余空格
		FreeMarkerRender.setProperty("date_format", "yyyy-MM-dd");
		FreeMarkerRender.setProperty("time_format", "HH:mm:ss");
		FreeMarkerRender.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
		FreeMarkerRender.setProperty("default_encoding", "UTF-8");
		
    }  
  
    @Override  
    public void configRoute(Routes me) {  
    	this.routes = me;
		me.setBaseViewPath("/WEB-INF/views/");

    	me.add("/", IndexController.class, "/index");
		me.add("/login", LoginController.class, "/");
    	me.add("/manager", ManagerController.class,"/manager");
    	me.add("/config", DbConfigController.class,"/config");

    }

	@Override
	public void configEngine(Engine me) {

	}

	@Override
    public void configPlugin(Plugins me) {  
    	DruidPlugin druidPlugin = createDruidPlugin();

		druidPlugin.setInitialSize(PropKit.getInt("druid.initialSize"));
		druidPlugin.setMaxActive(PropKit.getInt("druid.maxActive"));
		druidPlugin.setMaxWait(PropKit.getInt("druid.maxWait"));
		druidPlugin.setMinIdle(PropKit.getInt("druid.minIdle"));

		// 设置 状态监听与 sql防御
		WallFilter wall = new WallFilter();
		wall.setDbType(JdbcConstants.SQL_SERVER);
		druidPlugin.addFilter(wall);
		druidPlugin.addFilter(new StatFilter());



		// 添加使用XML管理SQL插件
		SqlInXmlPlugin sqlInXmlPlugin = new SqlInXmlPlugin();
						
		// 添加shiro插件
		ShiroPlugin shiroPlugin = new ShiroPlugin(routes);
		shiroPlugin.setLoginUrl("/login");
		shiroPlugin.setSuccessUrl("/index");
		shiroPlugin.setUnauthorizedUrl("/login");

		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		//添加 mapping
		//addMapping(arp);
		_MappingKit.mapping(arp);

		arp.setShowSql(true);

		arp.setDialect(new SqlServerDialect());

		// sql记录
		SqlReporter.setLog(true);
		
		me.add(druidPlugin).add(arp).add(sqlInXmlPlugin)
				.add(shiroPlugin)
				.add(createRedisPlugin());

		QuartzPlugin qz = new QuartzPlugin();
		me.add(qz);

    }
  
    /**
	 * 配置全局拦截器
	 */
    @Override  
    public void configInterceptor(Interceptors me) {  
    	// 在VIEW中可以使用SESSION
		me.add(new SessionInViewInterceptor());
		// shiro权限拦截器配置
		if (OPEN_SHIRO)
			me.add(new ShiroInterceptor());

    }
    
    /**
	 * 配置处理器
	 */
    @Override  
    public void configHandler(Handlers me) { 
    	//JSEESIONID处理
		me.add(new SessionHandler());
		// 添加druid连接池
		me.add(new DruidStatViewHandler("/druid"));
		// 添加页面contextpath
		me.add(new ContextPathHandler("contextPath"));
    }  
    
    /**
	 * 初始化
	 */
    @Override
	public void afterJFinalStart() {
    	//添加任务DB
		//addDbConfig();

    	//添加任务
		addScheduleJob();
	}

	public static void addDbConfig(){
		List<DbConfig> dblist = DbConfig.dao.findAll();
		dblist.stream().forEach(db->addConfig(db));
	}

	/**
	 * 添加数据库资源池
	 * @param db
	 */
	public static void addConfig(DbConfig db){
		DruidPlugin druidPlugin = new DruidPlugin(db.getDbUrl(),db.getUserName(), DesUtil.decrypt(db.getPwd()));
		druidPlugin.start();
		DruidDataSource ds = (DruidDataSource)druidPlugin.getDataSource();
		String dbType = db.getDbType();
		Dialect dialect = null;

		if(JdbcConstants.MYSQL.equals(dbType)){
			dialect = new MysqlDialect();
		}else if(JdbcConstants.ORACLE.equals(dbType)){
			dialect = new OracleDialect();
		}else if(JdbcConstants.SQL_SERVER.equals(dbType)){
			dialect = new SqlServerDialect();
		}
		Config config = new Config(db.getName(),ds,dialect,true, false, DbKit.DEFAULT_TRANSACTION_LEVEL, IContainerFactory.defaultContainerFactory, new EhCache());



		DbKit.addConfig(config);//添加到系统db池里

	}

	public static void addScheduleJob(){
		List<ScheduleJob> list = ScheduleJob.dao.findAll();

		list.stream().forEach(job->addJob(job));
	}

	public static void addJob(ScheduleJob job){
		Class clazz = null;
		try {
			clazz = Class.forName(job.getJobClass());
		} catch (ClassNotFoundException e) {
		}
		int id = job.getId();
		QuartzKey quartzKey = new QuartzKey(id, job.getName());
		new QuartzCronJob(quartzKey, job.getCron(), clazz)
				.addParam("jobId",id+"")
				.addParam("name", job.getName())
				.addParam("dbId", job.getDbId()).start();
	}

	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbc.url"), PropKit.get("jdbc.username"), PropKit.get("jdbc.password").trim());
	}

	public static RedisPlugin createRedisPlugin(){
    	return new RedisPlugin(PropKit.get("redis.name"),PropKit.get("redis.host"),PropKit.getInt("redis.port"),PropKit.getInt("redis.timeout"));
	}

	/**
	 * 测试方法初始化组件。
	 */
	public static void init(){

		PropKit.use("app-config.txt");

		DruidPlugin druidPlugin = createDruidPlugin();
		druidPlugin.start();

		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setDialect(new SqlServerDialect());
		arp.setShowSql(true);
		_MappingKit.mapping(arp);
		arp.start();

		createRedisPlugin().start();

		//addDbConfig();

	}

}
