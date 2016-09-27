package com.mlh.spider.pageprocessor.xbmiaomu;

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

public class XBMiaoMuMiaoMuJiaGePageListProcessor  extends WebMagicParams implements PageProcessor{
	private final static Log logger = Log.getLog(XBMiaoMuMiaoMuJiaGePageListProcessor.class);
	/**
	 * 域名
	 */
	private static final String DOMAIN = "http://www.xbmiaomu.com/miaomujiage/index-htm-page-1.html";
	/**
	 * 站点配置
	 */
	private Site site = Site.me().setHttpProxyPool(WebMagicFunction.getIpList()).setDomain(DOMAIN).setSleepTime(SLEEP_TIME).setUserAgent(USER_AGENT)
			.setTimeOut(TIME_OUT).setRetryTimes(RETRY_TIMES);

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		String code = page.getRequest().getExtra("code").toString();
		String id = page.getRequest().getExtra("id").toString();
		int pageno = (int) page.getRequest().getExtra("pageno");
		System.out.println("第" + pageno + "页正在处理：" + page.getUrl().get());
		Html html = page.getHtml();
		
		List <String> urls = html.xpath("//table[@bgcolor='#8DF885']").xpath("//tr[@bgcolor='#FFFFFF']/td[1]").links().all();
		
		int[] rows = PageDetail.dao.saveDetails(urls, code, page.getUrl().get(), pageno);
		System.out.println("详情页保存完毕：" + rows.length);
		// 更新当前列表页为已处理,已分析出详情页
		PageList.dao.updateHandleById(Confirm.yes.toString(), id);
		System.out.println("列表页状态更新完毕：" + Confirm.yes.toString());
		
	}

	public static void main(String[] args) {
		WebMagicFunction.ListProcessor(args[0], new XBMiaoMuMiaoMuJiaGePageListProcessor());
	}

}
