package com.mlh.spider.pageprocessor;

import java.util.ArrayList;
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

/***
 * 解析青青苗木站列表哪出详情页地址保存数据库
 * 
 * @author sjl
 *
 */
public class Green321QiaoGuanMuPageListProcessor extends WebMagicParams implements PageProcessor {

	private final static Log logger = Log.getLog(Green321QiaoGuanMuPageListProcessor.class);
	/**
	 * 域名
	 */
	private static final String DOMAIN = "http://www.312green.com/price/view-c1005-s-t-v-p1.html ";
	/**
	 * 站点配置
	 */
	private Site site = Site.me().setHttpProxyPool(WebMagicFunction.getIpList()).setDomain(DOMAIN).setSleepTime(SLEEP_TIME).setUserAgent(USER_AGENT)
			.setTimeOut(TIME_OUT).setRetryTimes(RETRY_TIMES);

	@Override
	public void process(Page page) {
		String code = page.getRequest().getExtra("code").toString();
		String id = page.getRequest().getExtra("id").toString();
		int pageno = (int) page.getRequest().getExtra("pageno");
		System.out.println("第" + pageno + "页正在处理：" + page.getUrl().get());
		Html html = page.getHtml();

		// 获取详情页所有连接
		List<String> urls = html.xpath("//table[@class='table_gridline']").xpath("//tr[@class='table_back']/td[1]").links().all();
		int index = 0 ;
		List<Integer> temp = new ArrayList<Integer>();
		for(String url :urls ){
			
			if(!url.endsWith(".html")){
				temp.add(index); 
			}
			
			index++;
			
		}
		if(temp.size()>0){
			for(int i =0;i<temp.size();i++){
				urls.remove((int)temp.get(i)-i);
				
			}
		}
		// 将详情页的链接保存到数据库
		int[] rows = PageDetail.dao.saveDetails(urls, code, page.getUrl().get(), pageno);
		System.out.println("详情页保存完毕：" + rows.length);
		
		// 更新当前列表页为已处理,已分析出详情页
		PageList.dao.updateHandleById(Confirm.yes.toString(), id);
		System.out.println("列表页状态更新完毕：" + Confirm.yes.toString());
		
		System.out.println("-----------------------------------------------------------------");

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {

		WebMagicFunction.ListProcessor(args[0], new Green321QiaoGuanMuPageListProcessor());
	}

}
