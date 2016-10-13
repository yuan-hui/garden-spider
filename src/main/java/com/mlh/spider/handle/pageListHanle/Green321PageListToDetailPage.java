package com.mlh.spider.handle.pageListHanle;

import com.mlh.common.AppRun;
import com.mlh.spider.factory.PageListProcessorFactory;
/***
 * green321_qiaoguanmu列表页面处理器
 * @author sjl
 *
 */
public class Green321PageListToDetailPage {

	public static void main(String[] args)  {
		AppRun.start();
		/***
		 * green321_qiaoguanmu
		 * 
		 */
		String _code = "green321_qiaoguanmu";
		
		/********保存详情页面连接开始*********/
		PageListProcessorFactory factory = new PageListProcessorFactory();
		factory.produce(_code);// 根据业务编码从列表页中解析出详情页的地址，并保存起来
		/********保存详情页面连接结束*********/
		
		
		
	
	}

}
