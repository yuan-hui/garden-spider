package com.mlh.spider.handle;

import java.text.ParseException;

import com.mlh.common.AppRun;
import com.mlh.enums.BussCode;
import com.mlh.spider.factory.ProductProcessorFactory;

/**
 * 
 * @Description: 清洗爬取网站数据
 */
public class ProductHandler {

	public static void main(String[] args) throws ParseException {
		// ----
		AppRun.start();

		// 清洗各网站爬取的数据，并根据不同的网站对应执行清洗
		BussCode[] codes = BussCode.values();

		System.out.println("开始数据清洗...");
		//全量开关('N'增量'Y'全量)
		String open = "N";
		for (BussCode c : codes) {
			String _code = c.getCode();

			System.out.println("进入处理->" + _code);
			ProductProcessorFactory factory = new ProductProcessorFactory();

			// 根据业务编码进入对应清洗处理
			factory.produce(_code,open);
		}
		System.out.println("结束数据清洗...");	
	}
}
