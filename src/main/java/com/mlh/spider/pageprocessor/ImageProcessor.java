package com.mlh.spider.pageprocessor;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.mlh.spider.downloader.ImageDownloader;
import com.mlh.utils.common.PathUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 * @Description: 谷德设计网处理器(建筑类列表页)
 * @author liujiecheng
 */
public class ImageProcessor implements PageProcessor {

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

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		String id = args[0];
		String name = args[1];
		String url = args[2];

		// 图片保存路径
		String path = PathUtil.getRootPath() + "download" + "\\" + "images" + "\\" + name;
		Spider.create(new ImageProcessor()).setDownloader(new ImageDownloader(id, name, path, url)).thread(1).run();
		
	}
}
