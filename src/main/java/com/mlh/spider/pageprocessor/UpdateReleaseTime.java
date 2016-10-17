package com.mlh.spider.pageprocessor;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

import com.mlh.common.WebMagicFunction;
import com.mlh.model.PageDetail;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
/**
 * 此方法用于苗木第一站解析数据
 * 获取苗木发布时间 并保存到数据库
 * @author sjl
 *
 */
public class UpdateReleaseTime implements PageProcessor {

	/**
	 * 域名
	 */
	private static final String DOMAIN = "http://www.miaomuzhan.com";

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
	private Site site = Site.me().setDomain(DOMAIN).setSleepTime(SLEEP_TIME).setUserAgent(USER_AGENT)
			.setTimeOut(TIME_OUT).setRetryTimes(RETRY_TIMES);
	private static String detailId;
	
	@Override
	public void process(Page page) {
		Html html = page.getHtml();
		
		String time = html.xpath("//body/text()").get();
		time=time.substring(time.indexOf("：")+1, time.lastIndexOf("'"));
		System.out.println(time);
		PageDetail.dao.updateReleaseTime(time,detailId);
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		// 传递业务参数
		String endCode = args[0];
		detailId=args[1];
		
		
		Request request = new Request("http://www.miaomuzhan.com/plus/updatetim.php?aid="+endCode).setPriority(0);

		// 启动当前路径的爬取
		Spider.create(new UpdateReleaseTime()).thread(1).addRequest(request).run();
		
	}

}
