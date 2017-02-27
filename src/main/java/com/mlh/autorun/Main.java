package com.mlh.autorun;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.mlh.common.AppRun;
import com.mlh.spider.factory.DetailsDowloadProcessorFactory;
import com.mlh.spider.factory.DetailsParserFactory;
import com.mlh.spider.factory.PageListProcessorFactory;
/***
 * 测试调试类
 * @author sjl
 *
 */
public class Main {

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, ParseException {
		AppRun.start();
		
		/***
		 * green321_qiaoguanmu
		 * 
		 * lvsemiaomu_qiaoguanmu
		 * 
		 * miaomuzhan_miaomujiage
		 * 
		 * miaomu_price   IP限制严重
		 * 
		 * xbmiaomu_maiomujiage
		 * 
		 * mm597_price
		 * 
		 * huamu100_price  
		 * 
		 * yuanlin_price
		 * 
		 * zjyuanlin_price
		 */
		
		String _code = "lvsemiaomu_qiaoguanmu";
		
		/********保存详情页面连接开始*********/
		PageListProcessorFactory factory = new PageListProcessorFactory();
		factory.produce(_code);// 根据业务编码从列表页中解析出详情页的地址，并保存起来
		/********保存详情页面连接结束*********/
		
		
		
		/********下载详情页面开始*********/
		
//		DetailsDowloadProcessorFactory factory = new DetailsDowloadProcessorFactory();
//		factory.produce(_code);
		/********下载详情页面结束*********/
		
		/********解析详情页面开始*********/
		DetailsParserFactory factory2 = new DetailsParserFactory();
		factory2.produce(_code);
		/********解析详情页面结束*********/
		
		/*********查询爬取网站的数据*******************/
//		Content Content  = new Content();
//		List<Content> list= Content.findByCode(_code,1,100);
//		for (Content content2 : list) {
//			System.out.println(content2.getArea());
//		}
	}

}
