package com.mlh.spider.factory;

import com.mlh.enums.BussCode;
import com.mlh.spider.parser.LvsemiaomuQiaoguanmuDetailsLocalHtmlParser;

/**
 * 
 * @Description: 详情页解析器工厂
 * @author liujiecheng
 */
public class DetailsParserFactory {

	/**
	 * 
	 * @Description: 处理不同的业务
	 * @author liujiecheng
	 */
	public void produce(String code) {
		BussCode busscode = BussCode.valueOf(code);
		switch (busscode) {
			case lvsemiaomu_qiaoguanmu:
				lvsemiaomuQiaoguanmuDetailsLocalHtmlParser(code);
				break;
			default:
				System.out.println("详情解析业务未处理...");
				break;
		}
	}
	
	/**
	 * 
	 * @Description: 进入详情页处理器
	 * @author liujiecheng
	 */
	private void lvsemiaomuQiaoguanmuDetailsLocalHtmlParser(String code) {
		LvsemiaomuQiaoguanmuDetailsLocalHtmlParser.main(new String[] { code });
	}
}
