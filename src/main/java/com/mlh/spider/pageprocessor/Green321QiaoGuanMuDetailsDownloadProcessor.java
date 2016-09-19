package com.mlh.spider.pageprocessor;

import java.util.List;

import com.mlh.common.WebMagicFunction;
import com.mlh.common.WebMagicParams;
import com.mlh.enums.Confirm;
import com.mlh.model.PageDetail;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class Green321QiaoGuanMuDetailsDownloadProcessor extends WebMagicParams implements PageProcessor {

	/**
	 * 域名
	 */
	private static final String DOMAIN = "http://www.312green.com/price/view-c1005-s-t-v-p1.html";

	/**
	 * 站点配置
	 */
	private Site site = Site.me().setDomain(DOMAIN).setSleepTime(SLEEP_TIME).setUserAgent(USER_AGENT)
			.setTimeOut(TIME_OUT).setRetryTimes(RETRY_TIMES);

	@Override
	public void process(Page page) {
		String id = page.getRequest().getExtra("id").toString();
		String code = page.getRequest().getExtra("code").toString();
		page.putField("id", id);
		page.putField("code", code);
		page.putField("result", page.getHtml().get());
		page.putField("url", page.getUrl().get());

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		String code = args[0];
		WebMagicFunction.DetailDownload(code, new Green321QiaoGuanMuDetailsDownloadProcessor());
		

	}

}
