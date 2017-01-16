package com.mlh.spider.pageprocessor;

import java.util.List;

import com.jfinal.log.Log;
import com.mlh.common.WebMagicFunction;
import com.mlh.common.WebMagicParams;
import com.mlh.enums.Confirm;
import com.mlh.model.PageDetail;
import com.mlh.model.PageList;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * 
 * @Description: 中华园林绿色苗木->乔灌木类列表页处理器,用于在列表页中分析出详情页的地址路径，并保存到数据库
 * @author liujiecheng
 */
public class LvsemiaomuQiaoguanmuPageListProcessor extends WebMagicParams implements PageProcessor {

	private final static Log logger = Log.getLog(LvsemiaomuQiaoguanmuPageListProcessor.class);

	/**
	 * 域名
	 */
	private static final String DOMAIN = "miaomu.yuanlin365.com";

	/**
	 * 站点配置
	 */
	private Site site = Site.me().setDomain(DOMAIN).setSleepTime(SLEEP_TIME).setUserAgent(USER_AGENT)
			.setTimeOut(TIME_OUT).setRetryTimes(RETRY_TIMES);

	
	@Override
	public void process(Page page) {
		try {
			// 传递过来的参数
			String code = page.getRequest().getExtra("code").toString();
			String id = page.getRequest().getExtra("id").toString();
			int pageno = (int) page.getRequest().getExtra("pageno");
			
			logger.error("第"+pageno+"页正在处理：" + page.getUrl().get());

			// 获取整个页面的HTML
			Html html = page.getHtml();

			// 从标题中获取所有详情页的链接
			List<String> urls = html.xpath("//table[@bgcolor='#CCCCCC']").xpath("//tr[@bgcolor='#FFFFFF']/td[1]").links().all();
		
			// 将详情页的链接保存到数据库
			int[] rows = PageDetail.dao.saveDetails(urls, code, page.getUrl().get(), pageno);
			System.out.println("详情页保存完毕：" + rows.length);

			// 更新当前列表页为已处理,已分析出详情页
			PageList.dao.updateHandleById(Confirm.yes.toString(), id);
			logger.error("列表页状态更新完毕：" + Confirm.yes.toString());

			logger.error("-----------------------------------------------------------------");
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
		WebMagicFunction.ListProcessor(args[0], new LvsemiaomuQiaoguanmuPageListProcessor());
	}
}
