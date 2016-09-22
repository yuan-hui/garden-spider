package com.mlh.spider.parser;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.mlh.buss.content.bean.ContentInfo;
import com.mlh.common.AppRun;
import com.mlh.enums.Confirm;
import com.mlh.model.Content;
import com.mlh.model.PageDetail;
import com.mlh.spider.util.DetailsHtmlUtil;
import com.mlh.utils.common.DateUtils;

import us.codecraft.webmagic.selector.Html;

public class XBMiaoMu_MaioMuJiaGeDetailsLocalHtmlParser {
	
	private final static Log logger = Log.getLog(XBMiaoMu_MaioMuJiaGeDetailsLocalHtmlParser.class);
	
	private final static int SLEEP_TIME = 1;
	
	
	private static void process(PageDetail detail) {
		String code = detail.getCode();
		String detailId = detail.getId();
		String source = detail.getUrl();
		String path = detail.getPath();
		String filepath = PropKit.get("details.xbmiaomu.path") + path;
		
		try{
			
			System.out.println("解析文件：" + filepath);
			
			File file = new File(filepath);
			String text;
			text = FileUtils.readFileToString(file, "GBK");

			Document htmldoc = Jsoup.parse(text);
			Html html = new Html(htmldoc);
			// 标题
			String title = html.xpath("//div[@class='info_main']/h1/text()").get().replace("价格", "");

			// 发布时间
			String releaseTimeStr = html.xpath("//div[@class='info_main']/ul/li[3]/text()").get().replace("：", "");
			Date releaseTime = DateUtils.StringToDateyyyy_MM_dd(releaseTimeStr);

			List<String> info_main = html.xpath("//div[@class='info_main']/ul/li").all();
			int index = info_main.size();
			// 米径
			String midiaMeter = html.xpath("//div[@class='info_main']/ul/li[5]/a/text()").get().replace("cm", "");
			String height = null;
			String crown = null;
			String grounddiaMeter =null; 
			
			List<String> detailcontentList = html.xpath("//div[@class='info_main']/ul/li").all();
			Map<String, String> detailcontentMap = DetailsHtmlUtil.changeToAttrMap(detailcontentList);
			
			height = detailcontentMap.get("高度");
			crown = detailcontentMap.get("冠幅");
			grounddiaMeter = detailcontentMap.get("地径");
			midiaMeter = detailcontentMap.get("米径");

			String price = detailcontentMap.get("产品报价");//
			
			price = price.indexOf("元")>-1? price.substring(0, price.indexOf("元")):price;

			String company = html.xpath("//div[@class='contact_body']/ul/li[1]/a/text()").get();

			List<String> usercontentList = html.xpath("//div[@class='contact_body']/ul/li").all();
			Map<String, String> usercontentListMap = DetailsHtmlUtil.changeToAttrMap(usercontentList);
			
			String tmpAddrss = usercontentListMap.get("所在地");
			// 省份
			String province = StringUtils.substring(tmpAddrss, 0, 2);
			
			String city = StringUtils.substring(tmpAddrss, tmpAddrss.length()-3);

			String contacts = usercontentListMap.get("联系人");

			String tel = usercontentListMap.get("电话");

			String email = usercontentListMap.get("邮件");

			String address = usercontentListMap.get("地址");
			
			String cid = path;
			String unit = "颗";
			ContentInfo info = new ContentInfo();
			info.setCid(cid);
			info.setTitle(title);
			info.setMidiameter(midiaMeter);
			info.setHeight(height);
			info.setCrown(crown);
			info.setGrounddiameter(grounddiaMeter);
			info.setUnit(unit);
			info.setPrice(price);
			info.setCompany(company);
			info.setProvince(province);
			info.setCity(city);
			info.setContacts(contacts);
			info.setTel(tel);
			info.setEmail(email);
			info.setAddress(address);
			info.setReleasetime(releaseTime);
			
			
			if(StringUtils.isNoneBlank(title)){
				boolean save = Content.dao.save(info, detailId, source, code);
				if(save){
					System.out.println("内容保存成功 "+ title);
					int row = PageDetail.dao.updateParserById(Confirm.yes.toString(), detailId);
					System.out.println("详情页更新为已解析：" + row);
				}else{
					System.out.println("内容保存失败：" + title + "->" );
					
				}
			}else{
				System.out.println("详情页存在异常，请查阅源文件：" + path);
			}
			

	} catch (IOException e1) {
		e1.printStackTrace();
		logger.error("message", e1);
	}
	}
	
	
	public static void main(String[] args) {
		String code = args[0];

		System.out.println("开始查询需要解析的详情页...");
		List<PageDetail> details = PageDetail.dao.findByCodeAndParser(code, Confirm.no.toString());

		if (details != null && details.size() > 0) {
			for (PageDetail detail : details) {
				process(detail);
			}
		} else {
			System.out.println("没有需要解析的详情页：" + details.size());
		}
	}


	

}
