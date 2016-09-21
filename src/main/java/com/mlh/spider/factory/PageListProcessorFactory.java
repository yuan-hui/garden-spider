package com.mlh.spider.factory;

import com.mlh.enums.BussCode;
import com.mlh.spider.pageprocessor.Green321QiaoGuanMuPageListProcessor;
import com.mlh.spider.pageprocessor.LvsemiaomuQiaoguanmuPageListProcessor;
import com.mlh.spider.pageprocessor.MiaomuzhanMiaomujiagePageListProcessor;
import com.mlh.spider.pageprocessor.miaomu.MiaomuPicePageList;

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
			case miaomu_pice:
				miaomu_PicePageList(code);
				break;
			default:
				System.out.println("列表页业务未处理...");
				break;
		}
	}
	
	/**
	 * 进入中国苗木站 列表页面处理器
	 * @param code
	 */
	private void miaomu_PicePageList(String code) {
		
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
