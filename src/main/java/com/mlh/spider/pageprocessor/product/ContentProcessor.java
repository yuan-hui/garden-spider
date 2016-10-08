package com.mlh.spider.pageprocessor.product;

import java.util.List;
import java.util.stream.Collectors;

import com.mlh.model.Content;

/**
 * 改变爬取数据(原始数据)的清洗状态
 */
public class ContentProcessor extends Thread{
	
	private String code;
	
	private List<String> ids;
	
	public ContentProcessor(String code,List<String> ids) {
		this.ids=ids;
		this.code=code;
	}
		
	public void run() {
		Content content  = new Content();
		System.out.println("---------更新t_content【code:"+code+" ,size："+ids.size()+" ,cleanState:Y】--------------");
		for (int i=ids.size(),j=0;i>j;j++) {
			int strat = j*100;
			int end = 100;
			content.updateContent(ids.stream().skip(strat).limit(end).collect(Collectors.toList()));	
		}
	}
}
