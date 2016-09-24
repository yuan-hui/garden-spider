package com.mlh.spider.factory;

import com.mlh.enums.BussCode;
import com.mlh.spider.pageprocessor.Green321QiaoGuanMuPageListProcessor;
import com.mlh.spider.pageprocessor.LvsemiaomuQiaoguanmuPageListProcessor;
import com.mlh.spider.pageprocessor.MiaomuzhanMiaomujiagePageListProcessor;
import com.mlh.spider.pageprocessor.huamu100.HuaMu100PricePageListProcessor;
import com.mlh.spider.pageprocessor.miaomu.MiaomuPicePageList;
import com.mlh.spider.pageprocessor.mm597.MM597PicePageListProcessor;
import com.mlh.spider.pageprocessor.xbmiaomu.XBMiaoMuMiaoMuJiaGePageListProcessor;
import com.mlh.spider.pageprocessor.yuanlin.Yuanlin_PricePageListProcessor;

/**
 * 
 * @Description: 列表页处理器工厂
 * @author liujiecheng
 */
public class PageListProcessorFactory {

	/**
	 * 
	 * @Description: 处理不同的业务
	 * @author liujiecheng
	 */
	public void produce(String code) {
		BussCode busscode = BussCode.valueOf(code);
		switch (busscode) {
			case lvsemiaomu_qiaoguanmu:
				lvsemiaomuQiaoguanmuPageList(code);
				break;
			case miaomuzhan_miaomujiage:
				miaomuzhanMiaomujiagePageList(code);
				break;
			case green321_qiaoguanmu:
				green321QiaoGuanMuPageList(code);
				break;
			case miaomu_price:
				miaomu_PricePageList(code);
				break;
			case xbmiaomu_maiomujiage:
				xbmiaomu_jiagePageList(code);
				break;
			case mm597_price:
				mm597_PricePageList(code);
				break;
			case huamu100_price:
				huaMu100_PricePageList(code);
				break;
			case yuanlin_price:
				yuanlin_pricePageList(code);
				break;
			default:
				System.out.println("列表页业务未处理...");
				break;
		}
	}
	
	
	/**
	 * 中国园林网苗木价格 列表页面处理
	 * @param code
	 */
	private void yuanlin_pricePageList(String code) {
		Yuanlin_PricePageListProcessor.main(new String []{code});
	}

	/**
	 * 花木100苗木价格列表页面处理
	 * @param code
	 */
	private void huaMu100_PricePageList(String code) {
		HuaMu100PricePageListProcessor.main(new String []{code});
	}

	/**
	 * 597苗木站列表页面处理
	 * @param code
	 */
	private void mm597_PricePageList(String code) {
		MM597PicePageListProcessor.main(new String []{code});
	}
	/**
	 * 进入西北苗木站 列表页面处理器
	 * @param code
	 */
	private void xbmiaomu_jiagePageList(String code) {
		XBMiaoMuMiaoMuJiaGePageListProcessor.main(new String []{code});
		
	}

	/**
	 * 进入中国苗木站 列表页面处理器
	 * @param code
	 */
	private void miaomu_PricePageList(String code) {
		
		MiaomuPicePageList.main(new String []{code});
		
	}

	/**
	 * @Description 进入青青苗木列表页面处理器
	 * @author sjl
	 */
	private void green321QiaoGuanMuPageList(String code) {
		Green321QiaoGuanMuPageListProcessor.main(new String[] {code});
	}
	/**
	 * @Description 进入苗木【第一站】列表页面处理器
	 * @author sjl
	 */
	private void miaomuzhanMiaomujiagePageList(String code) {
		MiaomuzhanMiaomujiagePageListProcessor.main(new String[] {code});
	}

	/**
	 * 
	 * @Description: 进入列表页处理器
	 * @author liujiecheng
	 */
	private void lvsemiaomuQiaoguanmuPageList(String code) {
		LvsemiaomuQiaoguanmuPageListProcessor.main(new String[] { code });
	}
}
