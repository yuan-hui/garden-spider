package com.mlh.spider.parser;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	 * @Description: 解析页面{}
	 * @author liujiecheng
	 */
	private static void process(PageDetail detail) {

		String code = detail.getCode();
		String detailId = detail.getId();
		String source = detail.getUrl();
		String path = detail.getPath();
		String filepath = PropKit.get("details.yuanlin365.path") + path;

		try {
			logger.error("解析文件：" + filepath);
			File file = new File(filepath);
			String text;
			text = FileUtils.readFileToString(file, "GBK");

			Document htmldoc = Jsoup.parse(text);
			Html html = new Html(htmldoc);

			// 详情内容
			List<String> detailcontentList = html.xpath("//div[@id='detailcontent']/ul/li").all();
			Map<String, String> detailcontentMap = DetailsHtmlUtil.changeToAttrMap(detailcontentList);

			// 联系信息
			List<String> contactusList = html.xpath("//div[@id='contactus']/div[@id='linkmode']/div[@class='linkmode_row']").all();
			Map<String, String> contactusMap = DetailsHtmlUtil.changeToAttrMap(contactusList);

			String title = null;

			// 标题
			title = html.xpath("//input[@name='Search21:KeyWord']").$("input", "value").get();

			// 米径(cm)
			String midiameter = DetailsHtmlUtil.getMidiameter(detailcontentMap);

			// 高度(cm)
			String height = DetailsHtmlUtil.getHeight(detailcontentMap);

			// 冠幅(cm)
			String crown = DetailsHtmlUtil.getCrown(detailcontentMap);

			// 地径(cm)
			String grounddiameter = DetailsHtmlUtil.getGrounddiameter(detailcontentMap);

			// 单位
			String unit = DetailsHtmlUtil.getUnit(detailcontentMap);

			// 价格
			String price = DetailsHtmlUtil.getPrice(detailcontentMap);

			//备注
			String remark = DetailsHtmlUtil.getRemark(detailcontentMap);
			
			// 公司
			String company = DetailsHtmlUtil.getCompany(contactusMap);

			String provinceAndCityHtml = html.xpath("//div[@class='linkmode_row']/span[@class='zt1']").get();
			// 省份
			String province = DetailsHtmlUtil.getProvince(provinceAndCityHtml);

			// 城市
			String city = DetailsHtmlUtil.getCity(provinceAndCityHtml);

			// 联系人
			String contacts = DetailsHtmlUtil.getContacts(contactusMap);

			// 电话
			String tel = DetailsHtmlUtil.getTel(contactusMap);

			// 传真
			String fax = DetailsHtmlUtil.getFax(contactusMap);

			// 电子邮箱
			String email = DetailsHtmlUtil.getEmail(contactusMap);

			// 网址
			String website = DetailsHtmlUtil.getWebsite(contactusMap);

			// 地址
			String address = DetailsHtmlUtil.getAddress(contactusMap);

			// 邮编
			String zipcode = DetailsHtmlUtil.getZipcode(contactusMap);

			// 发布时间
			String releasetimeHtml = html.xpath("//div[@id='detailtime']").get();
			Date releasetime = DetailsHtmlUtil.getReleasetime(releasetimeHtml);

			// 来源内容ID
			String cid = StringUtils.substringBefore(path, ".");

			// 内容信息保存
			ContentInfo info = new ContentInfo();
			info.setCid(cid);
			info.setTitle(title);
			info.setMidiameter(midiameter);
			info.setHeight(height);
			info.setCrown(crown);
			info.setGrounddiameter(grounddiameter);
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
			info.setReleasetime(releasetime);

			
			if (StringUtils.isNotBlank(title)) {
				boolean save = Content.dao.save(info, detailId, source, code);

				if (save) {
					logger.error("内容保存成功：" + title);
					int row = PageDetail.dao.updateParserById(Confirm.yes.toString(), detailId);
					logger.error("详情页更新为已解析：" + row);
				} else {
					logger.error("内容保存失败：" + title + "->" + detailId);
				}
			} else {
				logger.error("详情页存在异常，请查阅源文件：" + path);
				Content.dao.deleteById(detailId);
				logger.error("异常详情页已删除"+detailId);
			}
			

		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error("message", e1);
		}

		/*logger.error("程序休眠：" + SLEEP_TIME + "秒.");
		try {
			Thread.sleep(SLEEP_TIME * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

		logger.error("-----------------------------------------------------------------");
	}

	/**
	 * 
	 * @Description: 应用主入口
	 * @author liujiecheng
	 */
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
}
