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
 * 中国园林 详情页面清洗
 * 
 * @author songj
 *
 */
public class YuanLinPriceDetailsLocalHtmlParser {

	private final static Log logger = Log.getLog(YuanLinPriceDetailsLocalHtmlParser.class);

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
		String filepath = PropKit.get("details.yuanlin.path") + path;

		try {
			System.out.println("解析文件：" + filepath);
			File file = new File(filepath);
			String text;
			text = FileUtils.readFileToString(file, "GBK");
			Document htmldoc = Jsoup.parse(text);
			Html html = new Html(htmldoc);

			// 详情内容
			List<String> detailContent = html.xpath("//div[@class='detail']/dl/dd").all();
			Map<String, String> detailcontentMap = DetailsHtmlUtil.changeToAttrMap(detailContent);

			// userInfo
			List<String> userIfo = html.xpath("//dl[@class='user tab']/dd").all();

			Map<String, String> usercontentListMap = DetailsHtmlUtil.changeToAttrMap(userIfo);

			// 标题
			String title = html.xpath("//div[@class='detail']/dl/dt/h1/text()").get();

			// 米径
			String midiaMeter = StringKit.strReturnNumber(detailcontentMap.get("米径"));

			// 高度
			String height = StringKit.strReturnNumber(detailcontentMap.get("高度"));

			// 冠幅
			String crown = StringKit.strReturnNumber(detailcontentMap.get("冠幅"));

			// 地径
			String grounddiaMeter = StringKit.strReturnNumber(detailcontentMap.get("地径"));

			String price = StringKit.strReturnNumber(detailcontentMap.get("价格"));

			// 单位
			String unit = StringKit.strReturnStr(detailcontentMap.get("价格"));

			String remark = detailcontentMap.get("备注");

			// 发布时间
			String releasetimeHtml = detailcontentMap.get("日期");
			Date releaseTime = DateUtils.StringToDateyyyy_MM_dd(releasetimeHtml);


			String tmpAddrss = detailcontentMap.get("地区");

			// 省份
			String province = StringUtils.substring(tmpAddrss, 0, 2);

			String city = StringUtils.substring(tmpAddrss, 2);

			// 公司
			String company = html.xpath("//dl[@class='user tab']/dt/a/text()").get();

			String contacts = usercontentListMap.get("联系人");

			String tel = usercontentListMap.get("手机");

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
