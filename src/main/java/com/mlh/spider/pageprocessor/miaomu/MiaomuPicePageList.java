package com.mlh.spider.pageprocessor.miaomu;

import java.util.List;

import com.jfinal.log.Log;
import com.mlh.common.WebMagicFunction;
import com.mlh.common.WebMagicParams;
import com.mlh.enums.Confirm;
import com.mlh.model.PageDetail;
import com.mlh.model.PageList;
import com.mlh.spider.pageprocessor.Green321QiaoGuanMuPageListProcessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
/**
 * 中国苗木站列表页面处理器，
 * @author sjl
 *
 */
public class MiaomuPicePageList extends WebMagicParams implements PageProcessor {
	
	private final static Log logger = Log.getLog(MiaomuPicePageList.class);
	/**
	 * 域名
	 */
	private static final String DOMAIN = "http://www.miaomu.net";
	/**
	 * 站点配置
	 */
	private Site site = Site.me().setDomain(DOMAIN).setSleepTime(SLEEP_TIME).setUserAgent(USER_AGENT)
			.setTimeOut(TIME_OUT).setRetryTimes(RETRY_TIMES);


	@Override
	public void process(Page page) {
		String code = page.getRequest().getExtra("code").toString();
		String id = page.getRequest().getExtra("id").toString();
		int pageno = (int) page.getRequest().getExtra("pageno");
		logger.error("第" + pageno + "页正在处理：" + page.getUrl().get());
		Html html = page.getHtml();
		List <String> urls = html.xpath("//table[@bgcolor='#E0E5E7']").xpath("//tr[@bgcolor='#FFFFFF']/td[1]").links().all();
		
		// 将详情页的链接保存到数据库
		int[] rows = PageDetail.dao.saveDetails(urls, code, page.getUrl().get(), pageno);
		logger.error("详情页保存完毕：" + rows.length);
		// 更新当前列表页为已处理,已分析出详情页
		PageList.dao.updateHandleById(Confirm.yes.toString(), id);
		logger.error("列表页状态更新完毕：" + Confirm.yes.toString());
		
		
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		WebMagicFunction.ListProcessor(args[0], new MiaomuPicePageList());
		

	}

}
