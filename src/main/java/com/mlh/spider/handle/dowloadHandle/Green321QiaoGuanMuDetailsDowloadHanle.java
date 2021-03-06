package com.mlh.spider.handle.dowloadHandle;

import com.mlh.common.AppRun;
import com.mlh.enums.BussCode;
import com.mlh.spider.factory.DetailsDowloadProcessorFactory;

/**
 * green321详情页下载 单独线程
 * 
 * @author sjl
 *
 */
public class Green321QiaoGuanMuDetailsDowloadHanle {

	public static void main(String[] args) {
		// 启动本地应用
		AppRun.start();

		do {

			DetailsDowloadProcessorFactory factory = new DetailsDowloadProcessorFactory();

			// 根据业务编码从列表页中解析出详情页的地址，并保存起来
			factory.produce("green321_qiaoguanmu");

		} while (true);

	}

}
