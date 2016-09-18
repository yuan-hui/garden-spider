package com.mlh.spider.factory;

import com.mlh.enums.BussCode;
import com.mlh.spider.pageprocessor.Green321QiaoGuanMuDetailsDownloadProcessor;
import com.mlh.spider.pageprocessor.LvsemiaomuQiaoguanmuDetailsDowloadProcessor;
import com.mlh.spider.pageprocessor.MiaomuzhanMiaomujiageDetailsDownloadProcessor;

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
			case miaomuzhan_miaomujiage:
				miaomuzhanMiaomujiageDetailsDownloadProcessor(code);
				break;
			case green321_qiaoguanmu:
				green321QiaoGuanMuDetailsDownloadProcessor(code);
				break;
			default:
				System.out.println("列表页业务未处理...");
				break;
		}
	}
	
	/**
	 * @Description "进如青青苗木，详情页处理器"
	 * @param code
	 * @author sjl
	 */
	private void green321QiaoGuanMuDetailsDownloadProcessor(String code) {
		Green321QiaoGuanMuDetailsDownloadProcessor.main(new String [] {code});
		
	}


	/**
	 * @Description "进入第一苗木站_苗木价格，详情页处理器"
	 * @param code
	 * @author sjl
	 */
	private void miaomuzhanMiaomujiageDetailsDownloadProcessor(String code) {
		MiaomuzhanMiaomujiageDetailsDownloadProcessor.main(new String[] { code });
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
