package com.mlh.spider.handle;

import com.jfinal.kit.LogKit;
import com.mlh.common.AppRun;
import com.mlh.enums.BussCode;
import com.mlh.spider.factory.DetailsParserFactory;

/**
 * 
 * @Description: 单独解析 miaomuzhan_miaomujiage 详情页面
 * @author sjl
 */
public class MiaoMuZhanParseHandler {

	public static void main(String[] args) {
		// ----
		AppRun.start();

		// 查询需要提出详情页的所有列表页，并根据不同的业务使用不同的页面解析器
		LogKit.error("进入处理->" + "miaomuzhan_miaomujiage");
		do {
			DetailsParserFactory factory = new DetailsParserFactory();
			factory.produce(BussCode.miaomuzhan_miaomujiage.getCode());

		} while (true);

	}

}
