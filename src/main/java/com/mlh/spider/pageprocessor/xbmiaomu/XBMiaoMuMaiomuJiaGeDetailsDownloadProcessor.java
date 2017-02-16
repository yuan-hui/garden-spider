package com.mlh.spider.pageprocessor.xbmiaomu;

import com.jfinal.log.Log;
import com.mlh.common.WebMagicFunction;
import com.mlh.common.WebMagicParams;
import com.mlh.spider.pageprocessor.miaomu.MiaomuPicePageList;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 西北苗木站详情页面下载
 * @author sjl
 *
 */
public class XBMiaoMuMaiomuJiaGeDetailsDownloadProcessor extends WebMagicParams implements PageProcessor {

	private final static Log logger = Log.getLog(XBMiaoMuMaiomuJiaGeDetailsDownloadProcessor.class);
	/**
	 * 域名
	 */
	private static final String DOMAIN = "http://www.xbmiaomu.com";
	/**
	 * 站点配置
	 */
	private Site site = Site.me().setDomain(DOMAIN).setSleepTime(SLEEP_TIME).setUserAgent(USER_AGENT)
			.setTimeOut(TIME_OUT).setRetryTimes(RETRY_TIMES);
	
	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		String id = page.getRequest().getExtra("id").toString();
		String code = page.getRequest().getExtra("code").toString();
		page.putField("id", id);
		page.putField("code", code);
		page.putField("result", page.getHtml().get());
		page.putField("url", page.getUrl().get());
		
	}

	public static void main(String[] args) {
		WebMagicFunction.DetailDownload(args[0], new XBMiaoMuMaiomuJiaGeDetailsDownloadProcessor(), XBMIAOMU);
	}

}
