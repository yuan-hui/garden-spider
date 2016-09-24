package com.mlh.spider.pageprocessor.huamu100;

import com.jfinal.log.Log;
import com.mlh.common.WebMagicFunction;
import com.mlh.common.WebMagicParams;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 花木100 详情页面下载器
 * @author sjl
 *
 */
public class HuaMu100PriceDetailsDownloadProcessor extends WebMagicParams implements PageProcessor {
	
	private final static Log logger = Log.getLog(HuaMu100PricePageListProcessor.class);
	/**
	 * 域名
	 */
	private static final String DOMAIN = "http://www.huamu100.com";
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
		WebMagicFunction.DetailDownload(args[0], new HuaMu100PriceDetailsDownloadProcessor(), HUAMU);
	}

}
