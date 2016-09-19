package com.mlh.common;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.jfinal.kit.PropKit;
import com.mlh.enums.Confirm;
import com.mlh.model.PageDetail;
import com.mlh.model.PageList;
import com.mlh.spider.pageprocessor.MiaomuzhanMiaomujiageDetailsDownloadProcessor;
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

	/***
	 * 列表处理器启动器
	 */
	public static void ListProcessor(String param, PageProcessor processor) {

		String code = param;
		System.out.println("进入" + code + "列表页处理器...");

		List<PageList> pages = PageList.dao.findByCodeAndStatus(code, Confirm.no.toString());
		System.out.println(pages.size());
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
		}

	}

	/**
	 * 详情页面下载
	 */
	public static void DetailDownload(String code, PageProcessor processor) {

		System.out.println("进入" + code + "详情页下载器...");

		// 查询没有下载的列表详情页
		List<PageDetail> details = PageDetail.dao.findByCodeAndDownload(code, Confirm.no.toString());
		if (details != null && details.size() > 0) {
			int index = 1;
			for (PageDetail pageDetail : details) {
				String id = pageDetail.getId();
				String url = pageDetail.getUrl();
				System.out.println(index + "、开始下载[" + DateUtils.getNow() + "]：" + id);
				// 保存路劲
				String path = PropKit.get("details.green321.path");
				// 业务参数
				Request request = new Request(url).setPriority(0).putExtra("code", code).putExtra("id", id);
				// 启动
				try {
					Spider.create(new MiaomuzhanMiaomujiageDetailsDownloadProcessor())
							.addPipeline(new HtmlToLocalPipeline(path, "GBK")).thread(1).addRequest(request).run();
					System.out.println("-----------------------------------------------------------------");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				index++;
			}
		} else {
			System.out.println("没有需要下载的详情页：" + details.size());

			System.out.println("程序休眠：" + 8 + "秒.");
			try {
				Thread.sleep(8 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("-----------------------------------------------------------------");

		}

	}
	
	
}
