package com.mlh.spider.factory;

import com.mlh.enums.BussCode;
import com.mlh.spider.parser.Green321QiaoGuanMuDetailsLocalHtmlParser;
import com.mlh.spider.parser.HuaMu100PriceDetailsLocalHtmlParser;
import com.mlh.spider.parser.LvsemiaomuQiaoguanmuDetailsLocalHtmlParser;
import com.mlh.spider.parser.MM597PriceDetailsLocalHtmlParser;
import com.mlh.spider.parser.MiaoMuDetailsLocalHtmlParser;
import com.mlh.spider.parser.MiaomuzhanMiaomuDetailsLocalHtmlParser;
import com.mlh.spider.parser.XBMiaoMu_MaioMuJiaGeDetailsLocalHtmlParser;
import com.mlh.spider.parser.YuanLinPriceDetailsLocalHtmlParser;
import com.mlh.spider.parser.ZJYuanLinPriceDetailsLocalHtmlParser;

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
				break;
			case miaomu_price:
				miaoMuPriceDetailsLocalHtmlParser(code);
				break;
			case green321_qiaoguanmu:
				green321QiaoGuanMuDetailsLocalHtmlParser(code);
				break;
			case xbmiaomu_maiomujiage:
				xbMiaoMuMaioMujiageDetailsLocalHtmlParser(code);
				break;
			case mm597_price:
				mm597PriceDetailsLocalHtmlParser(code);
				break;
			case huamu100_price:
				huaMu100PriceDetailsLocalHtmlParser(code);
				break;
			case yuanlin_price:
				yuanLinPriceDetailsLocalHtmlParser(code);
				break;
			case zjyuanlin_price:
				zjYuanLinPriceDetailsLocalHtmlParser(code);
				break;
			default:
				System.out.println("详情解析业务未处理...");
				break;
		}
	}
	
	/**
	 * miaomu 详情页解析
	 * @param code
	 */
	private void miaoMuPriceDetailsLocalHtmlParser(String code) {
		MiaoMuDetailsLocalHtmlParser.main(new String []{code});;		
	}
	/**
	 * 浙江园林网站详情解析
	 * @param code
	 */
	private void zjYuanLinPriceDetailsLocalHtmlParser(String code) {
		ZJYuanLinPriceDetailsLocalHtmlParser.main(new String []{code});
		
	}

	/**
	 * 中国园林网站详情解析
	 * @param code
	 */
	private void yuanLinPriceDetailsLocalHtmlParser(String code) {
		YuanLinPriceDetailsLocalHtmlParser.main(new String []{code});
		
	}
	/**
	 * 花木100 详情页面解析
	 * @param code
	 */
	private void huaMu100PriceDetailsLocalHtmlParser(String code) {
		HuaMu100PriceDetailsLocalHtmlParser.main(new String []{code});
	}
	/**
	 * 597苗木网 详情页面解析
	 * @param code
	 * @author sjl
	 */
	private void mm597PriceDetailsLocalHtmlParser(String code) {
		MM597PriceDetailsLocalHtmlParser.main(new String []{code});
	}

	/**
	 * 西北苗木站 详情页面解析 控制器
	 * @param code
	 */
	private void xbMiaoMuMaioMujiageDetailsLocalHtmlParser(String code) {
		
		XBMiaoMu_MaioMuJiaGeDetailsLocalHtmlParser.main(new String []{code});
	}

	/**
	 * 
	 * @param code
	 */
	private void green321QiaoGuanMuDetailsLocalHtmlParser(String code) {
		Green321QiaoGuanMuDetailsLocalHtmlParser.main(new String []{code});
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
