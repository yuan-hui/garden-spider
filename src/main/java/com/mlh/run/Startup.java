package com.mlh.run;

import com.jfinal.core.JFinal;

/**
 * 
 * @ClassName: Startup 
 * @Description: 启动应用
 * @author liujiecheng
 * @date 2016年4月14日 下午3:46:42 
 *
 */
public class Startup {

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目 运行此 main
	 * 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		
		JFinal.start("src/main/webapp", 9999, "/", 5);
	}
}
