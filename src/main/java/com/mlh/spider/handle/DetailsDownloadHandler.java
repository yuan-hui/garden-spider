package com.mlh.spider.handle;

import com.mlh.common.AppRun;
import com.mlh.enums.BussCode;
import com.mlh.spider.factory.DetailsDowloadProcessorFactory;


/**
 * 
 * @Description: 详情页下载处理者入口
 * @author liujiecheng
 */
public class DetailsDownloadHandler {

	public static void main(String[] args) {
		// 启动本地应用
		AppRun.start();

		do {
			//所有业务编码
			BussCode[] codes = BussCode.values();
			
			for (BussCode c : codes) {
				String _code = c.getCode();

				DetailsDowloadProcessorFactory factory = new DetailsDowloadProcessorFactory();

				// 根据业务编码从列表页中解析出详情页的地址，并保存起来
				factory.produce(_code);
			}
		} while (true);
		
	}

}
