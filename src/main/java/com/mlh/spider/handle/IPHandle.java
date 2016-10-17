package com.mlh.spider.handle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mlh.buss.content.bean.IPBean;
import com.mlh.common.AppRun;
import com.mlh.common.WebMagicFunction;
import com.mlh.model.IPList;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * ip代理库爬取
 * 
 * @author sjl
 *
 */
public class IPHandle implements PageProcessor {
	/**
	 * 域名
	 */
	private static final String DOMAIN = "www.kxdaili.com";

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
	private Site site = Site.me().setHttpProxyPool(WebMagicFunction.getIpList()).setDomain(DOMAIN)
			.setSleepTime(SLEEP_TIME).setUserAgent(USER_AGENT).setTimeOut(TIME_OUT).setRetryTimes(RETRY_TIMES);

	@Override
	public void process(Page page) {

		Html html = page.getHtml();
		List<String> trList = html.xpath("//tbody/tr").all();
		
		IPBean ipBean = new IPBean();
		for(int i = 0; i<trList.size();i++){
			String xpath = "//tbody/tr["+(i+1)+"]/td/text()";
			List <String>tdList = html.xpath(xpath).all();
			
			if(Double.parseDouble(StringKit.strReturnNumber(tdList.get(4)))<5){//响应时间小于五秒
				ipBean.setIp(tdList.get(0));
				ipBean.setProt(tdList.get(1));
				
				boolean save  = IPList.dao.save(ipBean);
				if(save ){
					System.out.println("IP["+tdList.get(0)+"]保存成功");
				}else{
					System.out.println("异常错误");
				}
			}
			
		}
		
		

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {

		AppRun.start();
		do {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = formatter.format(new Date());
			System.out.println("、开始爬取IP[" + now + "]：");
			Request request = new Request("http://www.kxdaili.com/");
			Spider.create(new IPHandle()).addRequest(request).thread(1).run();

			System.out.println("程序休眠：" + 30 + "分钟");
			
			try {
				Thread.sleep(30 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("-----------------------------------------------------------------");
 
		} while (true);

	}

}
