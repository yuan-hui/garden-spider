package com.mlh.autorun;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.mlh.common.AppRun;
import com.mlh.spider.factory.PageListProcessorFactory;
import com.mlh.model.Content;
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
		 * miaomu_pice
		 * 
		 * xbmiaomu_maiomujiage
		 * 
		 * mm597_pice
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
//		DetailsParserFactory factory = new DetailsParserFactory();
//		factory.produce(_code);
//		List<String[]> list =  WebMagicFunction.getIpList();
//		System.out.println(list);
		/********解析详情页面结束*********/
		
		/*********查询爬取网站的数据*******************/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Content Content  = new Content();
		List<Content> list= Content.findByCodeAndTime(_code,sdf.parse("2016-09-27 00:00:00"));
		for (Content content2 : list) {
			System.out.println(content2.getArea());
		}
	}

}
