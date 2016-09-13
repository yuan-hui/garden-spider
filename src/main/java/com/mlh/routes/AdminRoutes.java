package com.mlh.routes;

import com.jfinal.config.Routes;
import com.mlh.buss.content.ContentController;

/**
 * @Description: 后端路由(Final路由还可以进行拆分配置)
 * @author liujiecheng
 */
public class AdminRoutes extends Routes {

	@Override
	public void config() {
		add("/content", ContentController.class, "/content");
	}
}
