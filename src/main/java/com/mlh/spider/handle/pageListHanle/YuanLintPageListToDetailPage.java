package com.mlh.spider.handle.pageListHanle;

import com.mlh.common.AppRun;
import com.mlh.spider.factory.PageListProcessorFactory;
/***
 * yuanlin_price列表页面处理器
 * @author sjl
 *
 */
public class YuanLintPageListToDetailPage {

	public static void main(String[] args)  {
		AppRun.start();
		/***
		 * green321_qiaoguanmu
		 * 
		 * lvsemiaomu_qiaoguanmu
		 * 
		 * miaomuzhan_miaomujiage
		 * 
		 * mm597_price
		 * 
		 * xbmiaomu_maiomujiage
		 * 
		 * mm597_pice
		 * 
		 * huamu100_price
		 * 
		 * yuanlin_price
		 * 
		 */
		String _code = "yuanlin_price";
		
		/********保存详情页面连接开始*********/
		PageListProcessorFactory factory = new PageListProcessorFactory();
		factory.produce(_code);// 根据业务编码从列表页中解析出详情页的地址，并保存起来
		/********保存详情页面连接结束*********/
		
		
		
	
	}

}
