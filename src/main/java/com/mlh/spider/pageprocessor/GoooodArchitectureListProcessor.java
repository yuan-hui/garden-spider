package com.mlh.spider.pageprocessor;

import java.util.List;

import com.jfinal.log.Log;
import com.mlh.enums.Confirm;
import com.mlh.model.PageDetail;
import com.mlh.model.PageList;
import com.mlh.spider.handle.PageHandler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * 
 * @Description: 谷德设计网建筑类列表页处理器,用于在列表页中分析出详情页的地址路径，并保存到数据库
 * @author liujiecheng
 */
public class GoooodArchitectureListProcessor implements PageProcessor {

	private final static Log logger = Log.getLog(PageHandler.class);

	/**
	 * 域名
	 */
	private static final String DOMAIN = "www.gooood.hk";

	/**
	 * 休眠时间(毫秒)
	 */
	private static final int SLEEP_TIME = 5000;

	/**
	 * 用户代理
	 */
	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31";

	/**
	 * 设置超时时间，单位是毫秒
	 */
	private static final int TIME_OUT = 200000;

	/**
	 * 设置重试次数
	 */
	private static final int RETRY_TIMES = 3;
	/**
	 * 站点配置
	 */
	private Site site = Site.me().setDomain(DOMAIN).setSleepTime(SLEEP_TIME).setUserAgent(USER_AGENT).setTimeOut(TIME_OUT).setRetryTimes(RETRY_TIMES);

	@Override
	public void process(Page page) {
		try {
			// 传递过来的参数
			String code = page.getRequest().getExtra("code").toString();
			String id = page.getRequest().getExtra("id").toString();
			int pageno = (int) page.getRequest().getExtra("pageno");
			
			System.out.println("正在处理列表页：" + page.getUrl().get());

			// 获取整个页面的HTML
			Html html = page.getHtml();

			// 从标题中获取所有详情页的链接
			List<String> urls = html.css("div.post-thumbnail").links().regex("http://www.gooood.hk/.*htm").all();
			System.out.println("详情页解析完毕：" + urls.size());

			// 将详情页的链接保存到数据库
			int[] rows = PageDetail.dao.saveDetails(urls, code, page.getUrl().get(), pageno);
			System.out.println("详情页保存完毕：" + rows.length);

			// 更新当前列表页为已处理,已分析出详情页
			PageList.dao.updateHandleById(Confirm.yes.toString(), id);
			System.out.println("列表页状态更新完毕：" + Confirm.yes.toString());

			System.out.println("-----------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("message", e);
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		// 业务代码
		String code = args[0];
		System.out.println("进入" + code + "列表页处理器...");

		List<PageList> pages = PageList.dao.findByCodeAndStatus(code, Confirm.no.toString());
		
		for (PageList p : pages) {
			// 列表页ID
			String id = p.getId();

			// 列表页URL
			String url = p.getUrl();
			
			//第几页
			int pageno = p.getPageno();

			// 传递业务参数
			Request request = new Request(url).setPriority(0).putExtra("code", code).putExtra("id", id).putExtra("pageno", pageno);

			// 启动当前路径的爬取
			Spider.create(new GoooodArchitectureListProcessor()).thread(1).addRequest(request).run();
		}

	}
}
