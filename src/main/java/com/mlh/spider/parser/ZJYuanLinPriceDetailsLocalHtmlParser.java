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
import com.mlh.enums.Confirm;
import com.mlh.model.Content;
import com.mlh.model.PageDetail;
import com.mlh.spider.util.DetailsHtmlUtil;
import com.mlh.utils.common.DateUtils;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.selector.Html;

/**
 * 浙江园林 详情页面清洗
 * 
 * @author songj
 *
 */
public class ZJYuanLinPriceDetailsLocalHtmlParser {

	private final static Log logger = Log.getLog(ZJYuanLinPriceDetailsLocalHtmlParser.class);

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
		String filepath = PropKit.get("details.zjyuanlin.path") + path;

		try {
			System.out.println("解析文件：" + filepath);
			File file = new File(filepath);
			String text;
			text = FileUtils.readFileToString(file, "GBK");
			Document htmldoc = Jsoup.parse(text);
			Html html = new Html(htmldoc);

			List<String> detailList = html.xpath("//table[@id='tb_mmbj']/tbody/tr").all();

			for (int i = 3; i < detailList.size() + 2; i++) {
				String xpath = "//table[@id='tb_mmbj']/tbody/tr[" + (i - 1) + "]";

				List<String> list = html.xpath(xpath + "/td/text()").all();

				String title = html.xpath(xpath + "/td[1]/a/text()").get();

				String midiaMeter = list.get(1).replace("-", "");
				String height = list.get(2).replace("-", "");
				String crown = list.get(3).replace("-", "");
				String grounddiaMeter = list.get(4).replace("-", "");
				String price = list.get(5);
				String unit = list.get(6);
				String remark = list.get(7);
				String releasetimeHtml = list.get(9);

				Date releaseTime = DateUtils.StringToDateyyyy_MM_dd(releasetimeHtml);
				String province = StringUtils.substring(list.get(8), 1, 3);
				String city = StringUtils.substring(list.get(8), 3,list.get(8).length()-1);
				String userInfo = html.xpath("//div[@id='mtd" + (i - 3) + "']").get();
				String company = html.xpath("//td[@id='title" + (i - 3) + "']/a/text()").get();
				String content = StringKit.getHtmlTextNoBlank(userInfo);
				String contacts = StringUtils.substring(content, content.indexOf("联系人:") + 4, content.indexOf("手机:"));
				String tel = StringUtils.substring(content, content.indexOf("手机:") + 3, content.indexOf("备注"));
				String cid = StringKit.getUUID();
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
				info.setReleasetime(releaseTime);
				info.setRemark(remark);
				if (StringUtils.isNoneBlank(title)) {
					boolean save = Content.dao.save(info, detailId, source, code);
					if (save) {
						System.out.println("内容保存成功 " + title);
						int row = PageDetail.dao.updateParserById(Confirm.yes.toString(), detailId);
						System.out.println("详情页更新为已解析：" + (i - 2));
					} else {
						System.out.println("内容保存失败：" + title + "->");

					}
				} else {
					System.out.println("详情页存在异常，请查阅源文件：" + path);
				}
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
