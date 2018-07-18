package com.jqsd.core.controller.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcConstants;
import com.jfinal.plugin.activerecord.*;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jqsd.common.AppConfig;
import com.jqsd.common.model.DbConfig;
import com.jqsd.common.model.ScheduleJob;
import com.jqsd.common.util.DesUtil;
import com.jqsd.common.util.JsonUtil;
import com.jqsd.common.vo.QueryParamVo;
import com.jqsd.core.controller.base.BaseController;
import com.jqsd.core.service.config.DbConfigService;

import java.sql.*;

/**
 * Created by sam on 12/4/17.
 */
public class DbConfigController extends BaseController {

    DbConfigService service = getServiceInstance(DbConfigService.class);

    /**
     * 展示用户信息列表
     */
    public  void list(){
        if(isPost()){
            QueryParamVo vo= getParamVo();
            String name = getPara("name");
            vo.setName(name);
            Page<DbConfig> page= service.getPage(vo);
            renderJson(JsonUtil.getPage(page));
        }else{
            render("dbConfig.html");
        }
    }

    /**
     * 测试连接
     */
    public void testDb(){
        DbConfig dbConfig = getModel(DbConfig.class);
        String url = getUrl(dbConfig);
        String driver = getDriver(dbConfig);
        Connection con = null; //表示数据库的连接对象
        ResultSet result = null; //表示接收数据库的查询结果
        String checkQuery = getCheckQuery(dbConfig);
        try {
            Class.forName(driver) ;//1、使用CLASS 类加载驱动程序
            con = DriverManager.getConnection(url,dbConfig.getUserName(),dbConfig.getPwd()); //2、连接数据库
            Statement stmt = con.createStatement(); //3、Statement 接口需要通过Connection 接口进行实例化操作
            result = stmt.executeQuery(checkQuery); //执行SQL 语句，查询数据库

            renderJson(JsonUtil.getSucc("测试成功"));
        } catch (ClassNotFoundException | SQLException e) {
            log.info(e.toString());
            String msg = e.toString().replace("com.jfinal.plugin.activerecord.ActiveRecordException: ","");
            renderJson(JsonUtil.getFail(msg));
        } finally {
            try {
                if(result!=null){
                    result.close();
                }
                if(con!=null){
                    con.close(); // 4、关闭数据库
                }
            } catch (SQLException e) {
            }
        }
    }

    public void saveDb(){
        DbConfig dbConfig = getModel(DbConfig.class);

        //String url = getUrl(dbConfig);

       // String checkQuery = getCheckQuery(dbConfig);
       // try {

            dbConfig.setName(dbConfig.getDbId());
            dbConfig.setPwd(DesUtil.encrypt(dbConfig.getPwd()));//加密后保存
            //dbConfig.setDbUrl(url);//真实连接地址

           // AppConfig.addConfig(dbConfig);//添加资源池里

            //Db.use(dbConfig.getName()).findFirst(checkQuery);


            service.saveConfig(dbConfig);

            renderJson(JsonUtil.getSucc());
        //}catch (Exception e){
         //   String msg = e.toString().replace("com.jfinal.plugin.activerecord.ActiveRecordException: ","");
          //  renderJson(JsonUtil.getFail(msg));
       // }

    }

    private String getUrl(DbConfig dbConfig){
        String dbType = dbConfig.getDbType();
        String url = "";
        if(JdbcConstants.MYSQL.equals(dbType)){
            url = "jdbc:mysql://"+dbConfig.getUrl()+":"+dbConfig.getPort()+"/"+dbConfig.getDbName();
        }else if(JdbcConstants.ORACLE.equals(dbType)){
            url = "jdbc:oracle:thin:@"+dbConfig.getUrl()+":"+dbConfig.getPort()+":"+dbConfig.getDbName();
        }else if(JdbcConstants.SQL_SERVER.equals(dbType)){
            url = "jdbc:sqlserver://"+dbConfig.getUrl()+":"+dbConfig.getPort()+";DatabaseName="+dbConfig.getDbName();
        }
        return url;
    }

    private String getDriver(DbConfig dbConfig){
        String dbType = dbConfig.getDbType();
        String driver = "";
        if(JdbcConstants.MYSQL.equals(dbType)){
            driver = JdbcConstants.MYSQL_DRIVER;
        }else if(JdbcConstants.ORACLE.equals(dbType)){
            driver = JdbcConstants.ORACLE_DRIVER;
        }else if(JdbcConstants.SQL_SERVER.equals(dbType)){
            driver = JdbcConstants.SQL_SERVER_DRIVER_SQLJDBC4;
        }
        return driver;
    }

    private  String  getCheckQuery(DbConfig dbConfig){
        String dbType = dbConfig.getDbType();
        if(JdbcConstants.ORACLE.equals(dbType)){
            return "select 1 from dual";
        }
        return "select 1";
    }
    
    /**
     * 	修改数据库管理
     */
    public void getJobBbId(){
		String id = getPara("id");
		//ScheduleJob job = service.findJobById(id);
		String sql = "select * from t_yf_db_config where id ="+id;
		
		renderJson(JsonUtil.getData(Db.find(sql)));
	}
}
