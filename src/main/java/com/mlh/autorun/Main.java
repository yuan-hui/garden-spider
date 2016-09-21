package com.mlh.autorun;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import com.mlh.common.AppRun;
import com.mlh.spider.factory.DetailsDowloadProcessorFactory;
import com.mlh.spider.factory.DetailsParserFactory;
import com.mlh.spider.factory.PageListProcessorFactory;
import com.mlh.spider.parser.Test;
import com.mlh.utils.common.StringKit;
/***
 * 测试调试类
 * @author sjl
 *
 */
public class Main {

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
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
		 */
		
		String _code = "miaomu_pice";
		
		/********保存详情页面连接开始*********/
//		PageListProcessorFactory factory = new PageListProcessorFactory();
//		factory.produce(_code);// 根据业务编码从列表页中解析出详情页的地址，并保存起来
		/********保存详情页面连接结束*********/
		
		
		
		/********下载详情页面开始*********/
		
		DetailsDowloadProcessorFactory factory = new DetailsDowloadProcessorFactory();
		factory.produce(_code);
		/********下载详情页面结束*********/
		
		/********解析详情页面开始*********/
//		DetailsParserFactory factory = new DetailsParserFactory();
//		factory.produce(_code);
	
		/********解析详情页面结束*********/
		
	}

}
