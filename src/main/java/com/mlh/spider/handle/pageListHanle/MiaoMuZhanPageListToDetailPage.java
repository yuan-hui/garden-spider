package com.mlh.spider.handle.pageListHanle;

import com.mlh.common.AppRun;
import com.mlh.spider.factory.PageListProcessorFactory;
/***
 * miaomuzhan_miaomujiage列表页面处理器
 * @author sjl
 *
 */
public class MiaoMuZhanPageListToDetailPage {

	public static void main(String[] args)  {
		AppRun.start();
		/***
		 * green321_qiaoguanmu
		 * 
		 * lvsemiaomu_qiaoguanmu
		 * 
		 */
		String _code = "miaomuzhan_miaomujiage";
		
		/********保存详情页面连接开始*********/
		PageListProcessorFactory factory = new PageListProcessorFactory();
		factory.produce(_code);// 根据业务编码从列表页中解析出详情页的地址，并保存起来
		/********保存详情页面连接结束*********/
		
		
		
	
	}

}
