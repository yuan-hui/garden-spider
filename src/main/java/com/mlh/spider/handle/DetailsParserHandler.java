package com.mlh.spider.handle;

import org.apache.commons.lang3.StringUtils;

import com.mlh.common.AppRun;
import com.mlh.enums.BussCode;
import com.mlh.spider.factory.DetailsParserFactory;

/**
 * 
 * @Description: 解析详情页
 * @author liujiecheng
 */
public class DetailsParserHandler {

	private final static int SLEEP_TIME = 8;

	public static void main(String[] args) throws InterruptedException {

		// ----
		AppRun.start();

		System.out.println("开始解析...");

		do {
			// 所有业务编码
			BussCode[] codes = BussCode.values();

			for (BussCode c : codes) {
				String _code = c.getCode();
				if(!StringUtils.equals(_code, BussCode.miaomuzhan_miaomujiage.getCode())){//苗木站 单独一个启动解析器
					DetailsParserFactory factory = new DetailsParserFactory();
					factory.produce(_code);
				}
			}

			System.out.println("程序休眠：" + SLEEP_TIME + "秒.");
			Thread.sleep(SLEEP_TIME * 1000);

			System.out.println("-----------------------------------------------------------------");
		} while (true);
		

	}

}
