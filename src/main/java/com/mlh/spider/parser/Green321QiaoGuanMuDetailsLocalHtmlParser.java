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
import com.mlh.spider.pageprocessor.UpdateReleaseTime;
import com.mlh.spider.util.DetailsHtmlUtil;
import com.mlh.utils.common.DateUtils;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.selector.Html;

public class Green321QiaoGuanMuDetailsLocalHtmlParser {
	private final static Log logger = Log.getLog(Green321QiaoGuanMuDetailsLocalHtmlParser.class);

	private final static int SLEEP_TIME = 1;

	public static void main(String[] args) {
		String code = args[0];

		logger.error("开始查询需要解析的详情页...");

		List<PageDetail> details = PageDetail.dao.findByCodeAndParser(code, Confirm.no.toString());

		if (details != null && details.size() > 0) {
			for (PageDetail detail : details) {
				process(detail);
			}
		} else {
			logger.error("没有需要解析的详情页：" + details.size());
		}

	}

	private static void process(PageDetail detail) {
		String code = detail.getCode();
		String detailId = detail.getId();
		String source = detail.getUrl();
		String path = detail.getPath();
		// 文件路劲
		String filepath = PropKit.get("details.green321.path") + path;
		logger.error("解析文件：" + filepath);
		try {
			File file = new File(filepath);
			String text;
			text = FileUtils.readFileToString(file, "GBK");
			Document htmldoc = Jsoup.parse(text);
			Html html = new Html(htmldoc);
			String title = html.xpath("//h1/text()").get();

			List<String> detailcontentList = html.xpath("//div[@id='detailcontent']/ul/li").all();

			Map<String, String> detailcontentMap = DetailsHtmlUtil.changeToAttrMap(detailcontentList);

			String midiaMeter = detailcontentMap.get("米径");
			String height = detailcontentMap.get("高度");
			String crown = detailcontentMap.get("冠幅");
			String grounddiaMeter = detailcontentMap.get("地径");

			String price = StringKit.strReturnNumber(detailcontentMap.get("价格"));
			String unit = "颗";
			String remark = detailcontentMap.get("备注");
			List<String> contactusList = html.xpath("//div[@class='linkmode_row']").all();
			Map<String, String> contactusMap = DetailsHtmlUtil.changeToAttrMap(contactusList);

			String company = null;
			String addrsInfor = null;
			String province = null;
			String city = null;
			String contacts = null;
			String tel = null;
			String email = null;
			String address = null;
			String fax = null;
			String website = null;
			String zipcode = null;

			addrsInfor = html.xpath("//span[@class='zt1']/text()").get();

			if (StringUtils.isNotBlank(addrsInfor)) {
				addrsInfor = StringUtils.isNotBlank(addrsInfor)
						? addrsInfor.substring(addrsInfor.indexOf("[") + 1, addrsInfor.lastIndexOf("]")).trim() : null;
				// 省份
				province = StringUtils.isNotBlank(addrsInfor) ? addrsInfor.substring(0, addrsInfor.indexOf(" ")) : null;
				city = StringUtils.isNotBlank(addrsInfor) ? addrsInfor.substring(addrsInfor.indexOf(" ") + 1) : null;

			}
			if (contactusMap.size() > 0) {

				String strInfor = contactusMap.get("公司");
				// 公司
				company = StringUtils.isNotBlank(strInfor) ? strInfor.substring(0, strInfor.indexOf("[")) : null;

				// 联系人
				contacts = contactusMap.get("联系人").substring(0, contactusMap.get("联系人").indexOf("Q") > -1
						? contactusMap.get("联系人").indexOf("Q") : contactusMap.get("联系人").length());

				// 电话
				tel = contactusMap.get("电话").substring(0, contactusMap.get("电话").indexOf("（") > -1
						? contactusMap.get("电话").indexOf("（") : contactusMap.get("电话").length());

				// 电子邮箱
				email = contactusMap.get("电邮");
				address = contactusMap.get("地址");

				fax = contactusMap.get("传真");

				website = contactusMap.get("网址");

				zipcode = contactusMap.get("邮编");
			}
			String cid = StringUtils.substringBefore(path, ".");

			String temStr = html.xpath("//div[@id='detailtime']/span[@class='left']/text()").get();
			// 发布时间
			String releaseTimeStr = temStr.substring(temStr.indexOf("：") + 1, temStr.indexOf("下")).trim();
			Date releaseTime = DateUtils.StringToDate(releaseTimeStr);
			// 内容信息保存
			ContentInfo info = new ContentInfo();
			info.setCid(cid);
			info.setTitle(title);
			info.setMidiameter(midiaMeter);
			info.setHeight(height);
			info.setCrown(crown);
			info.setGrounddiameter(grounddiaMeter);
			info.setUnit(unit);
			info.setRemark(remark);
			info.setPrice(price);
			info.setCompany(company);
			info.setProvince(province);
			info.setCity(city);
			info.setContacts(contacts);
			info.setTel(tel);
			info.setFax(fax);
			info.setEmail(email);
			info.setWebsite(website);
			info.setAddress(address);
			info.setZipcode(zipcode);
			info.setReleasetime(releaseTime);

			if (StringUtils.isNoneBlank(title)) {
				boolean save = Content.dao.save(info, detailId, source, code);
				if (save) {
					logger.error("内容保存成功" + title);
					int row = PageDetail.dao.updateParserById(Confirm.yes.toString(), detailId);
					logger.error("详情页更新为已解析：" + row);
				} else {
					logger.error("内容保存失败：" + title + "->");

				}
			} else {
				logger.error("详情页存在异常，请查阅源文件：" + path);
			}

		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error("message", e1);
		}

		logger.error("-----------------------------------------------------------------");

	}

}
