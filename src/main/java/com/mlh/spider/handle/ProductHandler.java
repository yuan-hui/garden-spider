package com.mlh.spider.handle;

import java.text.ParseException;

import com.mlh.enums.BussCode;
import com.mlh.model.Content;
import com.mlh.spider.factory.ProductProcessorFactory;

/**
 * 
 * @Description: 清洗爬取网站数据
 */
public class ProductHandler {

	public static void main(String[] args) throws ParseException {
		// ----
		/*AppRun.start();*/

		// 清洗各网站爬取的数据，并根据不同的网站对应执行清洗
		BussCode[] codes = BussCode.values();

		System.out.println("开始数据清洗...");
		Content content = new Content();
		content.updateRepeatstate();
		for (BussCode c : codes) {
			String _code = c.getCode();
			System.out.println("进入处理->" + _code);
			ProductProcessorFactory factory = new ProductProcessorFactory();
			// 根据业务编码进入对应清洗处理
			factory.produce(_code);
		}
		System.out.println("结束数据清洗...");	
	}
}
