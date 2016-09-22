package com.mlh.spider.factory;

import com.mlh.enums.BussCode;
import com.mlh.spider.pageprocessor.Green321QiaoGuanMuDetailsDownloadProcessor;
import com.mlh.spider.pageprocessor.LvsemiaomuQiaoguanmuDetailsDowloadProcessor;
import com.mlh.spider.pageprocessor.MiaomuzhanMiaomujiageDetailsDownloadProcessor;
import com.mlh.spider.pageprocessor.miaomu.MiaomuPiceDetailsDownloadProcessor;
import com.mlh.spider.pageprocessor.xbmiaomu.XBMiaoMuMaiomuJiaGeDetailsDownloadProcessor;
import com.mlh.spider.pageprocessor.xbmiaomu.XBMiaoMuMiaoMuJiaGePageListProcessor;

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
			case miaomu_pice:
				miaomuPiceDetailsDownloadProcessor(code);
				break;
			case xbmiaomu_maiomujiage:
				xbmiaomu_maiomujiageDetailsDownloadProcessor(code);
				break;
			default:
				System.out.println("列表页业务未处理...");
				break;
		}
	}
	
	/**
	 * 西北苗木站 ——绿化苗木详情页面下载
	 * @param code
	 */
	private void xbmiaomu_maiomujiageDetailsDownloadProcessor(String code) {
		XBMiaoMuMaiomuJiaGeDetailsDownloadProcessor.main(new String []{code});
	}
	/**
	 * 中国苗木站 详情页面下载
	 * @param code
	 */
	private void miaomuPiceDetailsDownloadProcessor(String code) {
		MiaomuPiceDetailsDownloadProcessor.main(new String []{code});
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
