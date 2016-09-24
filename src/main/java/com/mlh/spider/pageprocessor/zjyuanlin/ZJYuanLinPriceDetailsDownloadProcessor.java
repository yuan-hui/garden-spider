package com.mlh.spider.pageprocessor.zjyuanlin;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.mlh.common.WebMagicFunction;
import com.mlh.common.WebMagicParams;
import com.mlh.enums.Confirm;
import com.mlh.model.PageDetail;
import com.mlh.spider.pageprocessor.yuanlin.YuanLinPriceDetailsDownloadProcessor;
import com.mlh.spider.pipeline.HtmlToLocalPipeline;
import com.mlh.utils.common.DateUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 
 * @author songj
 *
 */
public class ZJYuanLinPriceDetailsDownloadProcessor extends WebMagicParams implements PageProcessor {
	
	private final static Log logger = Log.getLog(ZJYuanLinPriceDetailsDownloadProcessor.class);
	/**
	 * 域名
	 */
	private static final String DOMAIN = "http://zj.yuanlin.com";
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
		
		WebMagicFunction.DetailDownload(args[0], new ZJYuanLinPriceDetailsDownloadProcessor(), ZJYUNANLIN);
		
	}

}
