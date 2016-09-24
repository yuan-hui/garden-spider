package com.mlh.spider.parser;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mlh.spider.util.DetailsHtmlUtil;
import com.mlh.utils.common.DateUtils;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.selector.Html;

/**
 * mm597 详情页面解析
 * 
 * @author sjl
 *
 */
public class MM597PriceDetailsLocalHtmlParser {
	private final static Log logger = Log.getLog(MM597PriceDetailsLocalHtmlParser.class);

	private final static int SLEEP_TIME = 1;

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

	private static void process(PageDetail detail) {
		
		String code = detail.getCode();
		String detailId = detail.getId();
		String source = detail.getUrl();
		String path = detail.getPath();
		String filepath = PropKit.get("details.mm597.path") + path;
		try {
			System.out.println("解析文件：" + filepath);
			File file = new File(filepath);
			String text;
			text = FileUtils.readFileToString(file, "GBK");
			Document htmldoc = Jsoup.parse(text);
			Html html = new Html(htmldoc);
			// 标题
			String title = html.xpath("//div[@class='info_main']/h1/text()").get().replace("价格", "");

			// 发布时间
			String releaseTimeStr = html.xpath("//div[@class='info_main']/ul/li[1]/text()").get().replace("：", "");
			Date releaseTime = DateUtils.StringToDateyyyy_MM_dd(releaseTimeStr);

			// 拿到苗木规格数据源 解析
			String detailcontent = html.xpath("//div[@class='info_main']/ul/li[3]/a[2]/text()").get().trim();
			String[] tempArry = detailcontent.split("cm");
			Map<String, String> detailcontentMap = new HashMap<String, String>();
			String arryName = null;
			String arryVlue = null;

			for (int i = 0; i < tempArry.length; i++) {
				String temp = tempArry[i].trim();
				arryName = temp.substring(0, 2);
				arryVlue = temp.substring(2);
				detailcontentMap.put(arryName, arryVlue);
			}
			String height = detailcontentMap.get("高度");
			String crown = detailcontentMap.get("冠幅");
			String grounddiaMeter = detailcontentMap.get("地径");
			String midiaMeter = detailcontentMap.get("米径");

			String str = html.xpath("//div[@class='info_main']/ul/li[3]/a[1]/text()").get();//
			String  price = StringKit.strReturnNumber(str);
			String unit = StringKit.strReturnStr(str);
			String remark = html.xpath("//div[@id='content']/text()").get();
			
			List<String > usrInfo = html.xpath("//div[@id='contact']/ul/li").all();
			Map<String, String> usercontentListMap = DetailsHtmlUtil.changeToAttrMap(usrInfo);
			
			//公司
			String company = html.xpath("//div[@id='contact']/ul/li[1]/a/text()").get();
			
			String tmpAddrss = usercontentListMap.get("所在地");
			// 省份
			String province = StringUtils.substring(tmpAddrss, 0, 2);

			String city = StringUtils.substring(tmpAddrss, tmpAddrss.length() - 3);

			String contacts = usercontentListMap.get("联系人");

			String tel = usercontentListMap.get("电话");

			String address = usercontentListMap.get("地址");
			String cid = path;
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
			info.setAddress(address);
			info.setReleasetime(releaseTime);
			info.setRemark(remark);
			
			if (StringUtils.isNoneBlank(title)) {
				boolean save = Content.dao.save(info, detailId, source, code);
				if (save) {
					System.out.println("内容保存成功 " + title);
					int row = PageDetail.dao.updateParserById(Confirm.yes.toString(), detailId);
					System.out.println("详情页更新为已解析：" + row);
				} else {
					System.out.println("内容保存失败：" + title + "->");

				}
			} else {
				System.out.println("详情页存在异常，请查阅源文件：" + path);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("message", e);
		}

		System.out.println("程序休眠：" + SLEEP_TIME + "秒.");
		try {
			Thread.sleep(SLEEP_TIME * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("-----------------------------------------------------------------");
	}
}
