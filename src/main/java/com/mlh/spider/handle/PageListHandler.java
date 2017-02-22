package com.mlh.spider.handle;

import com.jfinal.log.Log;
import com.mlh.common.AppRun;
import com.mlh.enums.BussCode;
import com.mlh.spider.factory.PageListProcessorFactory;

/**
 * 
 * @Description: 从列表页中整理出需要下载的详情页地址
 * @author liujiecheng
 */
public class PageListHandler {

	private final static Log logger = Log.getLog(DownloaderConfigHandler.class);
	public static void main(String[] args) {
		// ----
		//AppRun.start();

		// 查询需要提出详情页的所有列表页，并根据不同的业务使用不同的页面解析器
		/*BussCode[] codes = BussCode.values();

		logger.error("开始处理业务...");
		for (BussCode c : codes) {
			String _code = c.getCode();
			logger.error("进入处理->" + _code);
			PageListProcessorFactory factory = new PageListProcessorFactory();

			// 根据业务编码从列表页中解析出详情页的地址，并保存起来
			factory.produce(_code);
		}*/
		String codes [] = {"lvsemiaomu_qiaoguanmu","miaomuzhan_miaomujiage","green321_qiaoguanmu",
				"xbmiaomu_maiomujiage","mm597_price","yuanlin_price","zjyuanlin_price","huamu100_price"
			};

		logger.error("开始处理业务...");
		for (String c : codes) {
			String _code = c; 
			logger.error("进入处理->" + _code);
			PageListProcessorFactory factory = new PageListProcessorFactory();

			// 根据业务编码从列表页中解析出详情页的地址，并保存起来
			factory.produce(_code);
		}
	}

}
