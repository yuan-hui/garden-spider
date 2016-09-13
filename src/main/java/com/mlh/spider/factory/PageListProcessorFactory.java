package com.mlh.spider.factory;

import com.mlh.enums.BussCode;
import com.mlh.spider.pageprocessor.LvsemiaomuQiaoguanmuPageListProcessor;

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
			default:
				System.out.println("列表页业务未处理...");
				break;
		}
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
