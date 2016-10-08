package com.mlh.spider.pageprocessor.product;

/**
 * 浙江园林 清洗处理器
 */
public class ZJYuanLinPriceCleanProcessor extends Thread{

	private String code;
	
	public ZJYuanLinPriceCleanProcessor(String code) {
		this.code=code;
	}
	
	public void run() {
		System.out.println("-------------浙江园林 清洗开启-------------");
	}

}
