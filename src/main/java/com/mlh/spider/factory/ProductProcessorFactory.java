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
	public void produce(String code,String open) throws ParseException {
		BussCode busscode = BussCode.valueOf(code);
		switch (busscode) {
			case lvsemiaomu_qiaoguanmu:
				lvsemiaomuQiaoguanmuCleanList(code,open);
				break;
			case miaomuzhan_miaomujiage:
				miaomuzhanMiaomujiageCleanList(code,open);
				break;
			case green321_qiaoguanmu:
				green321QiaoGuanMuCleanList(code,open);
				break;
			case miaomu_price:
				miaomu_PriceCleanList(code,open);
				break;
			case xbmiaomu_maiomujiage:
				xbmiaomu_jiageCleanList(code,open);
				break;
			case mm597_price:
				mm597_PriceCleanList(code,open);
				break;
			case huamu100_price:
				huaMu100_PriceCleanList(code,open);
				break;
			case yuanlin_price:
				yuanlin_PriceCleanList(code,open);
				break;
			case zjyuanlin_price:
				zjYuanLin_PriceCleanList(code,open);
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
	private void zjYuanLin_PriceCleanList(String code,String open) {
		ZJYuanLinPriceCleanProcessor.main(new String []{code,open});
	}

	/**
	 * @Description 中国园林 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void yuanlin_PriceCleanList(String code,String open) throws ParseException {
		YuanLinPriceCleanProcessor.main(new String []{code,open});
	}

	/**
	 * @Description 花木100 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void huaMu100_PriceCleanList(String code,String open) throws ParseException {
		HuaMu100PriceCleanProcessor.main(new String []{code,open});
	}

	/**
	 * @Description 597苗木 清洗处理
	 * @param code
	 */
	private void mm597_PriceCleanList(String code,String open) {
		MM597PiceCleanProcessor.main(new String []{code,open});
	}
	/**
	 * @Description 西北苗木 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void xbmiaomu_jiageCleanList(String code,String open) throws ParseException {
		XBMiaoMuMaiomuJiaGeCleanProcessor.main(new String []{code,open});
	}

	/**
	 * @Description 中国苗木 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void miaomu_PriceCleanList(String code,String open) throws ParseException {
		MiaomuPiceCleanProcessor.main(new String []{code,open});	
	}

	/**
	 * @Description 青青花木 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void green321QiaoGuanMuCleanList(String code,String open) throws ParseException {
		Green321QiaoGuanMuCleanProcessor.main(new String[] {code});
	}
	/**
	 * @Description 第一苗木 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void miaomuzhanMiaomujiageCleanList(String code,String open) throws ParseException {
		MiaomuzhanMiaomujiageCleanProcessor.main(new String[] {code});
	}

	/**
	 * @Description: 中华园林 清洗处理
	 * @param code
	 * @throws ParseException 
	 */
	private void lvsemiaomuQiaoguanmuCleanList(String code,String open) throws ParseException {
		LvsemiaomuQiaoguanmuCleanProcessor.main(new String[] { code });
	}
}
