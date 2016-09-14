package com.mlh.spider.pageprocessor;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.PropKit;
import com.mlh.enums.Confirm;
import com.mlh.model.PageDetail;
import com.mlh.spider.pipeline.HtmlToLocalPipeline;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 * @Description: 中华园林绿色苗木->乔灌木类,把详情页下载到本地，用于后续页面数据分析提取
 * @author liujiecheng
 */
public class LvsemiaomuQiaoguanmuDetailsDowloadProcessor implements PageProcessor {

	/**
	 * 域名
	 */
	private static final String DOMAIN = "www.yuanlin365.com";

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
		System.out.println("进入"+code+"详情页下载器...");

		//查询所有没有下载的列表页
		List<PageDetail> details = PageDetail.dao.findByCodeAndDownload(code, Confirm.no.toString());
		
		if (details != null && details.size() > 0) {
			int index = 1;
			for (PageDetail d : details) {
				String id = d.getId();
				String url = d.getUrl();
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now = formatter.format(new Date());
				System.out.println(index + "、开始下载["+now+"]：" + id);

				//文件保存路径
				//String path = PathUtil.getRootPath() + "download" + "\\" + "details" + "\\";
				String path = PropKit.get("details.yuanlin365.path");
				
				//业务参数
				Request request = new Request(url).setPriority(0).putExtra("code", code).putExtra("id", id);
				
				//启动
				try {
					Spider.create(new LvsemiaomuQiaoguanmuDetailsDowloadProcessor()).addPipeline(new HtmlToLocalPipeline(path,"UTF-8")).thread(1).addRequest(request).run();
					System.out.println("-----------------------------------------------------------------");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				index++;
			}
		} else {
			System.out.println("没有需要下载的详情页：" + details.size());
			
			System.out.println("程序休眠：" + 8 + "秒.");
			try {
				Thread.sleep(8 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("-----------------------------------------------------------------");
		}
		
		
	}
}
