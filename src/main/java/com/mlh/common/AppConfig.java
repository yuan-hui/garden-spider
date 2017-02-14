package com.mlh.common;

import javax.sql.DataSource;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.plugin.mongo.MongoJFinalPlugin;
import com.jfinal.ext.plugin.quartz.QuartzPlugin;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.SqlReporter;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.mlh.model._MappingKit;
import com.mlh.routes.AdminRoutes;

/**
 * 
 * @ClassName: AppConfig
 * @Description: API引导式配置
 * @author liujiecheng
 * @date 2016年4月14日 下午3:31:11
 *
 */
public class AppConfig extends JFinalConfig {
	protected final Log logger = Log.getLog(this.getClass().getName());

	/**
	 * 供Shiro插件使用。
	 */
	Routes routes;

	@Override
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("config.properties");

		// 配置JFinal运行模式(true开发模式)
		me.setDevMode(PropKit.getBoolean("devMode", false));
	}

	@Override
	public void configRoute(Routes me) {
		this.routes = me;

		// 后端路由
		me.add(new AdminRoutes());
	}

	/**
	 * 
	 * @Title: createdDruidPlugin
	 * @Description: 阿里巴巴Druid数据库连接池插件
	 * @param @return
	 *            参数说明
	 * @return DruidPlugin 返回类型
	 * @throws @author
	 *             liujiecheng
	 * @date 2016年4月15日 下午6:19:52
	 */
	public static DruidPlugin createdDruidPlugin() {
		Prop prop = PropKit.use("jdbc.properties");
		String url = prop.get("local.jdbc.url");
		String username = prop.get("local.jdbc.username");
		String password = prop.get("local.jdbc.password");

		DruidPlugin druidPlugin = new DruidPlugin(url, username, password);
		druidPlugin.setInitialSize(2);
		druidPlugin.setMinIdle(2);
		druidPlugin.setMaxActive(50);
		druidPlugin.setFilters("stat,wall");
		druidPlugin.setValidationQuery("select 1 from dual");
		return druidPlugin;
	}
	
	/**
	 * mongodb 数据库连接
	 */
	public static MongoJFinalPlugin createMongoPlugin(){
		Prop prop = PropKit.use("jdbc.properties");
		String host = prop.get("local.mongodb.host");
		int port = Integer.valueOf(prop.get("local.mongodb.port"));
		String database = prop.get("local.mongodb.database");
		String username = prop.get("local.mongodb.username");
		String password = prop.get("local.mongodb.password");
		MongoJFinalPlugin jFinalPlugin = new MongoJFinalPlugin(host,port,database,username,password);
		return jFinalPlugin;
	}

	/**
	 * 
	 * @Description: 获取数据源
	 * @author liujiecheng
	 */
	public static DataSource getDataSource() {
		DruidPlugin druidPlugin = AppConfig.createdDruidPlugin();
		druidPlugin.start();
		return druidPlugin.getDataSource();
	}

	/**
	 * 此方法用来配置 JFinal的Plugin JFina插件架构 是其主要 扩展方式之一 ，可 以方便地创建插件并应用到项目 中.
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 配置druid数据库连接池插件
		DruidPlugin druidPlugin = createdDruidPlugin();
		me.add(druidPlugin);

		// 配置ActiveRecord插件(通过 ActiveRecord来操作数据库)
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		me.add(arp);
		arp.setShowSql(true);

		// 配置Mysql方言
		arp.setDialect(new MysqlDialect());
		
		//配置mongodb数据库连接插件
		MongoJFinalPlugin jFinalPlugin =createMongoPlugin();
		me.add(jFinalPlugin);

		// 配置属性名(字段名)大小写不敏感容器工厂
		arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		
//		//配置定时任务
//		QuartzPlugin quartzPlugin =  new QuartzPlugin("quartzjob.properties","quartz.properties");
//		quartzPlugin.version(QuartzPlugin.VERSION_1);
//		me.add(quartzPlugin);
		
		// MappingKit映射
		_MappingKit.mapping(arp);

		SqlReporter.setLog(true);
		
	}

	@Override
	public void configInterceptor(Interceptors me) {

	}
	

	/**
	 * 此方法用来配置 JFinal的Handler
	 */
	@Override
	public void configHandler(Handlers me) {
	}

	/**
	 * 系统启动完成后回调
	 */
	@Override
	public void afterJFinalStart() {
	}

	/**
	 * 系统关闭前回调
	 */
	@Override
	public void beforeJFinalStop() {
	}
}
