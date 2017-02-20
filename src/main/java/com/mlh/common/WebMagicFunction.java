package com.mlh.common;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.mlh.enums.Confirm;
import com.mlh.model.IPList;
import com.mlh.model.PageDetail;
import com.mlh.model.PageList;
import com.mlh.spider.pageprocessor.LvsemiaomuQiaoguanmuPageListProcessor;
import com.mlh.spider.pipeline.HtmlToLocalPipeline;
import com.mlh.utils.common.DateUtils;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 提取一些反复重写的方法
 * 
 * @author sjl
 *
 */
public class WebMagicFunction {

	private final static Log logger = Log.getLog(WebMagicFunction.class);
	
	/***
	 * 列表处理器启动器
	 */
	public static void ListProcessor(String param, PageProcessor processor) {

		String code = param;
		logger.error("进入" + code + "列表页处理器...");

		List<PageList> pages = PageList.dao.findByCodeAndStatus(code, Confirm.no.toString());
		logger.error("没有处理的列表数量:"+pages.size());
		for (PageList p : pages) {
			// 列表页ID
			String id = p.getId();
			// 列表页URL
			String url = p.getUrl();
			// 第几页
			int pageno = p.getPageno();
			// 传递业务参数
			Request request = new Request(url).setPriority(0).putExtra("code", code).putExtra("id", id)
					.putExtra("pageno", pageno);

			// 启动当前路径的爬取
			Spider.create(processor).thread(1).addRequest(request).run();
			try {
				treadSleep();
			} catch (InterruptedException e) {
				logger.error("列表页面处理错误  "+code+"", e);
			}
		}
		
	}

	/**
	 * 详情页面下载
	 * @param code  code   processor 一个 PageProcessor对象
	 * 	pathCode 保存路径   
	 */
	public static void DetailDownload(String code, PageProcessor processor,String pathCode) {

		logger.error("进入" + code + "详情页下载器...");
			// 查询没有下载的列表详情页
			List<PageDetail> details = PageDetail.dao.findByCodeAndDownload(code, Confirm.no.toString());
			if (details != null && details.size() > 0) {
				int index = 1;
				for (PageDetail pageDetail : details) {
					String id = pageDetail.getId();
					String url = pageDetail.getUrl();
					logger.error(index + "、开始下载[" + DateUtils.getNow() + "]：" + id);
					// 保存路劲
					String path = PropKit.get(pathCode);
					// 业务参数
					Request request = new Request(url).setPriority(0).putExtra("code", code).putExtra("id", id);
					request.setMethod("GET");
					// 启动
					try {
						Spider.create(processor)
								.addPipeline(new HtmlToLocalPipeline(path, "GBK")).thread(1).addRequest(request).run();
						logger.error("-----------------------------------------------------------------");
						treadSleep();
						
					} catch (UnsupportedEncodingException e) {
						logger.error("下载错误 --"+code+"",e);
					} catch (FileNotFoundException e) {
						logger.error("下载错误 --"+code+"",e);
					} catch (InterruptedException e) {
						logger.error("下载错误 --"+code+"",e);
					}
					index++;
				}
			} else {
				logger.error("没有需要下载的详情页：" + details.size());
	
				logger.error("程序休眠：" + 8 + "秒.");
				try {
					Thread.sleep(8 * 1000);
				} catch (InterruptedException e) {
					logger.error("解析错误 --"+code+"",e);
				}
				logger.error("-----------------------------------------------------------------");
	
			}
	}
	
	/**
	 * 线程随机休眠 1-10秒
	 * @throws InterruptedException
	 */
	public static void treadSleep() throws InterruptedException{
		Random random = new Random();
		int result=random.nextInt(10)+1;
		
			logger.error("程序休眠：------------>"+result+"秒");
			Thread.sleep(result * 1000);
		
	}
	
	
	/**
	 * 获取iplist
	 */
	public static List<String[]> getIpList (){
		List <String []> iplist = new ArrayList<String[]>();
		
		List<IPList> ipDB = IPList.dao.findIPList();
		for(IPList ip :ipDB){
			
			String _ip = StringUtils.trim(ip.getIp());
			String _port = StringUtils.trim(ip.getPort());
			
			String [] arr = new String []{_ip,_port};
			iplist.add(arr);
		}
		
		return iplist;
	}
	
	
	
}
