package com.mlh.common;

import com.jfinal.ext.kit.Reflect;

/**
 * 
 * @Description: 本地启动Jfinal
 * @author liujiecheng
 */
public class AppRun {

	public static void start() {
		System.out.println("正在启动应用...");
		AppConfig app = new AppConfig();
		
		Reflect.on("com.jfinal.core.Config").call("configJFinal", app);
		System.out.println("应用启动完毕！");
		
		System.out.println("-----------------------------------------------------------------");
	}
}
