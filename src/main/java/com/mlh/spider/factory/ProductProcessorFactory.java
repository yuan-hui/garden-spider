package com.mlh.spider.factory;

import java.text.ParseException;

import com.mlh.enums.BussCode;
import com.mlh.spider.pageprocessor.LvsemiaomuQiaoguanmuPageListProcessor;
import com.mlh.spider.pageprocessor.product.Green321QiaoGuanMuCleanProcessor;
import com.mlh.spider.pageprocessor.product.HuaMu100PriceCleanProcessor;
import com.mlh.spider.pageprocessor.product.MM597PiceCleanProcessor;
import com.mlh.spider.pageprocessor.product.MiaomuPiceCleanProcessor;
import com.mlh.spider.pageprocessor.product.MiaomuzhanMiaomujiageCleanProcessor;
import com.mlh.spider.pageprocessor.product.XBMiaoMuMaiomuJiaGeCleanProcessor;
import com.mlh.spider.pageprocessor.product.YuanLinPriceCleanProcessor;
import com.mlh.spider.pageprocessor.product.ZJYuanLinPriceCleanProcessor;

/**
 * 
 * @Description: 数据清洗工厂
 */
public class ProductProcessorFactory {

	/**
	 * @throws ParseException 
	 * @Description: 处理不同业务数据
	 */
	public void produce(String code) throws ParseException {
		BussCode busscode = BussCode.valueOf(code);
		switch (busscode) {
			case lvsemiaomu_qiaoguanmu:
				lvsemiaomuQiaoguanmuCleanList(code);
				break;
			case miaomuzhan_miaomujiage:
				miaomuzhanMiaomujiageCleanList(code);
				break;
			case green321_qiaoguanmu:
				green321QiaoGuanMuCleanList(code);
				break;
			case miaomu_price:
				miaomu_PriceCleanList(code);
				break;
			case xbmiaomu_maiomujiage:
				xbmiaomu_jiageCleanList(code);
				break;
			case mm597_price:
				mm597_PriceCleanList(code);
				break;
			case huamu100_price:
				huaMu100_PriceCleanList(code);
				break;
			case yuanlin_price:
				yuanlin_PriceCleanList(code);
				break;
			case zjyuanlin_price:
				zjYuanLin_PriceCleanList(code);
				break;
			default:
				System.out.println("清洗未处理...");
				break;
		}
	}
	
	/**
	 * @Description 浙江园林 清洗处理
	 * @param code
	 */
	private void zjYuanLin_PriceCleanList(String code) {
		ZJYuanLinPriceCleanProcessor.main(new String []{code});
	}

	/**
	 * @Description 中国园林 清洗处理
	 * @param code
	 */
	private void yuanlin_PriceCleanList(String code) {
		YuanLinPriceCleanProcessor.main(new String []{code});
	}

	/**
	 * @Description 花木100 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void huaMu100_PriceCleanList(String code) throws ParseException {
		HuaMu100PriceCleanProcessor.main(new String []{code});
	}

	/**
	 * @Description 597苗木 清洗处理
	 * @param code
	 */
	private void mm597_PriceCleanList(String code) {
		MM597PiceCleanProcessor.main(new String []{code});
	}
	/**
	 * @Description 西北苗木 清洗处理
	 * @param code
	 */
	private void xbmiaomu_jiageCleanList(String code) {
		XBMiaoMuMaiomuJiaGeCleanProcessor.main(new String []{code});
	}

	/**
	 * @Description 中国苗木 清洗处理
	 * @param code
	 */
	private void miaomu_PriceCleanList(String code) {
		MiaomuPiceCleanProcessor.main(new String []{code});	
	}

	/**
	 * @Description 青青苗木 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void green321QiaoGuanMuCleanList(String code) throws ParseException {
		Green321QiaoGuanMuCleanProcessor.main(new String[] {code});
	}
	/**
	 * @Description 第一苗木 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void miaomuzhanMiaomujiageCleanList(String code) throws ParseException {
		MiaomuzhanMiaomujiageCleanProcessor.main(new String[] {code});
	}

	/**
	 * @Description: 中华园林 清洗处理
	 * @param code
	 */
	private void lvsemiaomuQiaoguanmuCleanList(String code) {
		LvsemiaomuQiaoguanmuPageListProcessor.main(new String[] { code });
	}
}
