package com.mlh.spider.factory;

import com.mlh.enums.BussCode;
import com.mlh.spider.pageprocessor.LvsemiaomuQiaoguanmuDetailsDowloadProcessor;

/**
 * 
 * @Description: 详情页处理器工厂
 * @author liujiecheng
 */
public class DetailsDowloadProcessorFactory {

	/**
	 * 
	 * @Description: 处理不同的业务
	 * @author liujiecheng
	 */
	public void produce(String code) {
		BussCode busscode = BussCode.valueOf(code);
		switch (busscode) {
			case lvsemiaomu_qiaoguanmu:
				lvsemiaomuQiaoguanmuDetailsDownloadProcessor(code);
				break;
			default:
				System.out.println("列表页业务未处理...");
				break;
		}
	}
	
	/**
	 * 
	 * @Description: 进入详情页处理器
	 * @author liujiecheng
	 */
	private void lvsemiaomuQiaoguanmuDetailsDownloadProcessor(String code) {
		LvsemiaomuQiaoguanmuDetailsDowloadProcessor.main(new String[] { code });
	}
}
