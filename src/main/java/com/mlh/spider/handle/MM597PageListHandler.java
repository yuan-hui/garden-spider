package com.mlh.spider.handle;

import com.mlh.common.AppRun;
import com.mlh.enums.BussCode;
import com.mlh.spider.factory.PageListProcessorFactory;

/**
 * 
 * @Description: 从列表页中整理出需要下载的详情页地址
 * @author liujiecheng
 */
public class MM597PageListHandler {


	public static void main(String[] args) {
		// ----
		AppRun.start();

		// 查询需要提出详情页的所有列表页，并根据不同的业务使用不同的页面解析器
		
			

			System.out.println("进入处理->" + "mm597_price");
			PageListProcessorFactory factory = new PageListProcessorFactory();

			// 根据业务编码从列表页中解析出详情页的地址，并保存起来
			factory.produce("mm597_price");
		
	}

}
