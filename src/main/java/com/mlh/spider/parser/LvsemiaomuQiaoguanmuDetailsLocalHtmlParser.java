package com.mlh.spider.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.mlh.buss.content.bean.ContentInfo;
import com.mlh.enums.Confirm;
import com.mlh.model.Content;
import com.mlh.model.PageDetail;

import us.codecraft.webmagic.selector.Html;

/**
 * 
 * @Description: 本地文件解析
 * @author liujiecheng
 */
public class LvsemiaomuQiaoguanmuDetailsLocalHtmlParser {

	private final static Log logger = Log.getLog(LvsemiaomuQiaoguanmuDetailsLocalHtmlParser.class);

	private final static int SLEEP_TIME = 1;

	/**
	 * 
	 * @Description: 解析页面
	 * @author liujiecheng
	 */
	private static void process(PageDetail detail) {

		String code = detail.getCode();
		String detailId = detail.getId();
		String source = detail.getUrl();
		String path = detail.getPath();
		String filepath = PropKit.get("details.yuanlin365.path") + path;

		try {
			System.out.println("解析文件：" + filepath);
			File file = new File(filepath);
			String text;
			text = FileUtils.readFileToString(file, "UTF-8");

			Document htmldoc = Jsoup.parse(text);
			Html html = new Html(htmldoc);

			String title = "";

			// 标题
			title = html.xpath("//input[@name='Search21:KeyWord']").$("input", "value").get();
			System.out.println("title==" + title);

			//米径(cm)
			Integer midiameter = null;
			
			List<String> detailcontentList = html.xpath("//div[@id='detailcontent']/ul/li").all();
			for (String info : detailcontentList) {
				System.out.println("info=" + info);
			}
			
			//高度(cm)
			Integer height = null;
			
			//冠幅(cm)
			Integer crown = null;
			
			//地径(cm)
			Integer grounddiameter = null;
			
			//单位
			String unit = null;
			
			//价格
			String price = null;
			
			//公司
			String company = null;
			
			//省份
			String province = null;
			
			//城市
			String city = null;
			
			//联系人
			String contacts = null;
			
			//电话
			String tel = null;
			
			//传真
			String fax = null;
			
			//电子邮箱
			String email = null;
			
			//网址
			String website = null;
			
			//地址
			String address = null;
			
			//邮编
			String zipcode = null;
			
			//发布时间
			String releasetime = null;

			
			// 来源内容ID
			String cid = StringUtils.substringBefore(path, ".");

			//内容信息保存
			ContentInfo info = new ContentInfo();
			info.setCid(cid);
			info.setTitle(title);
			info.setMidiameter(0);
			info.setHeight(0);
			info.setCrown(0);
			info.setGrounddiameter(0);
			info.setUnit("0");
			info.setPrice("0");
			info.setCompany("0");
			info.setProvince("0");
			info.setCity("0");
			info.setContacts("0");
			info.setTel("0");
			info.setFax("0");
			info.setEmail("0");
			info.setWebsite("0");
			info.setAddress("0");
			info.setZipcode("0");
			info.setReleasetime(null);
			
//			boolean save = Content.dao.save(info, detailId, source, code);
//			
//			if (save) {
//				System.out.println("内容保存成功：" + title);
//
//				//int row = PageDetail.dao.updateParserById(Confirm.yes.toString(), detailId);
//				//System.out.println("详情页更新为已解析：" + row);
//			} else {
//				System.out.println("内容保存失败：" + title + "->" + detailId);
//			}

		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error("message", e1);
		}

		System.out.println("程序休眠：" + SLEEP_TIME + "秒.");
		try {
			Thread.sleep(SLEEP_TIME * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("-----------------------------------------------------------------");
	}

	/**
	 * 
	 * @Description: 应用主入口
	 * @author liujiecheng
	 */
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
