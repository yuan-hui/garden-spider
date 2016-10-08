package com.mlh.spider.pageprocessor.product;

/**
 * 597苗木 清洗处理器
 */
public class MM597PiceCleanProcessor extends Thread{
	
	private String code;
	
	public MM597PiceCleanProcessor(String code) {
		this.code=code;
	}

	public void run() {
		System.out.println("-------------597苗木 清洗开启-------------");
	}
}
