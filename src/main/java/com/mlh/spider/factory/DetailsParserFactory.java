package com.mlh.spider.factory;

import com.mlh.enums.BussCode;
import com.mlh.spider.parser.LvsemiaomuQiaoguanmuDetailsLocalHtmlParser;
import com.mlh.spider.parser.MiaomuzhanMiaomuDetailsLocalHtmlParser;

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
			case miaomuzhan_miaomujiage:
				miaomuzhanMiaomuDetailsLocalHtmlParser(code);
			default:
				System.out.println("详情解析业务未处理...");
				break;
		}
	}
	
	/**
	 * @Description  进去解析详情页面处理器
	 * @author sjl
	 */
	private void miaomuzhanMiaomuDetailsLocalHtmlParser(String code) {
		MiaomuzhanMiaomuDetailsLocalHtmlParser.main(new String[] { code });
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
