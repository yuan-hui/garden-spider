package com.mlh.spider.factory;

import java.text.ParseException;

import com.mlh.enums.BussCode;
import com.mlh.spider.pageprocessor.product.Green321QiaoGuanMuCleanProcessor;
import com.mlh.spider.pageprocessor.product.HuaMu100PriceCleanProcessor;
import com.mlh.spider.pageprocessor.product.LvsemiaomuQiaoguanmuCleanProcessor;
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
		ZJYuanLinPriceCleanProcessor zJYuanLinPriceCleanProcessor=new ZJYuanLinPriceCleanProcessor(code);
		zJYuanLinPriceCleanProcessor.start();
	}

	/**
	 * @Description 中国园林 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void yuanlin_PriceCleanList(String code) throws ParseException {
		YuanLinPriceCleanProcessor yuanLinPriceCleanProcessor=new YuanLinPriceCleanProcessor(code);
		yuanLinPriceCleanProcessor.start();
	}

	/**
	 * @Description 花木100 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void huaMu100_PriceCleanList(String code) throws ParseException {
		HuaMu100PriceCleanProcessor huaMu100PriceCleanProcessor=new HuaMu100PriceCleanProcessor(code);
		huaMu100PriceCleanProcessor.start();
	}

	/**
	 * @Description 597苗木 清洗处理
	 * @param code
	 */
	private void mm597_PriceCleanList(String code) {
		MM597PiceCleanProcessor mM597PiceCleanProcessor=new MM597PiceCleanProcessor(code);
		mM597PiceCleanProcessor.start();
	}
	
	/**
	 * @Description 西北苗木 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void xbmiaomu_jiageCleanList(String code) throws ParseException {
		XBMiaoMuMaiomuJiaGeCleanProcessor xBMiaoMuMaiomuJiaGeCleanProcessor=new XBMiaoMuMaiomuJiaGeCleanProcessor(code);
		xBMiaoMuMaiomuJiaGeCleanProcessor.start();
	}

	/**
	 * @Description 中国苗木 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void miaomu_PriceCleanList(String code) throws ParseException {
		MiaomuPiceCleanProcessor miaomuPiceCleanProcessor=new MiaomuPiceCleanProcessor(code);
		miaomuPiceCleanProcessor.start();

	}

	/**
	 * @Description 青青花木 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void green321QiaoGuanMuCleanList(String code) throws ParseException {
		Green321QiaoGuanMuCleanProcessor green321QiaoGuanMuCleanProcessor=new Green321QiaoGuanMuCleanProcessor(code);
		green321QiaoGuanMuCleanProcessor.start();
	}
	
	/**
	 * @Description 第一苗木 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void miaomuzhanMiaomujiageCleanList(String code) throws ParseException {
		MiaomuzhanMiaomujiageCleanProcessor miaomuzhanMiaomujiageCleanProcessor=new MiaomuzhanMiaomujiageCleanProcessor(code);
		miaomuzhanMiaomujiageCleanProcessor.start();
	}

	/**
	 * @Description: 中华园林 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void lvsemiaomuQiaoguanmuCleanList(String code) throws ParseException {
		LvsemiaomuQiaoguanmuCleanProcessor lvsemiaomuQiaoguanmuCleanProcessor=new LvsemiaomuQiaoguanmuCleanProcessor(code);
		lvsemiaomuQiaoguanmuCleanProcessor.start();
	}
}
