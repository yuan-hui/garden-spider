package com.mlh.spider.parser;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
import com.mlh.utils.common.DateUtils;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.selector.Html;

/**
 * 花木100_详情页面解析
 * 
 * @author sjl
 *
 */

public class HuaMu100PriceDetailsLocalHtmlParser {
	private final static Log logger = Log.getLog(HuaMu100PriceDetailsLocalHtmlParser.class);

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

	/**
	 * 解析文件控制器
	 * 
	 * @param detail
	 */
	private static void process(PageDetail detail) {
		String code = detail.getCode();
		String detailId = detail.getId();
		String source = detail.getUrl();
		String path = detail.getPath();
		String filepath = PropKit.get("details.huamu100.path") + path;
		try {
			System.out.println("解析文件：" + filepath);
			
			File file = new File(filepath);
			String text;
			text = FileUtils.readFileToString(file, "GBK");

			Document htmldoc = Jsoup.parse(text);
			Html html = new Html(htmldoc);
			List<String> tableList = html.xpath("//tr[@class='table_back']/td/text()").all();

			String title = tableList.get(0);

			String midiaMeter = tableList.get(1);

			String height = tableList.get(2);
			// 冠幅(cm)
			String crown = tableList.get(3);
			// 地径(cm)
			String grounddiaMeter = tableList.get(4);
			// 价格
			String price = StringKit.strReturnNumber(tableList.get(5));
			// 单位
			String unit = tableList.get(6);

			String str = tableList.get(8);

			String remark = tableList.get(9);

			// 省份
			String province = str.indexOf("--") > -1 ? str.substring(0, str.indexOf("--")).trim() : str;

			String city = str.indexOf("--") > -1 ? str.substring(str.lastIndexOf("-") + 1).trim() : str;

			List<String> userInfor = html.xpath("//div[@class='infolist']").xpath("//span/text()").all();

			String company = null;
			String contacts = null;
			String email = null;
			String tel = null;
			String address = null;
			if (userInfor.size() > 0) {
				// 公司
				company = userInfor.get(1);
				// 联系人
				contacts = userInfor.get(2);
				tel = userInfor.get(4);
				if(userInfor.size()==8){
					email = userInfor.get(6);
					address = userInfor.get(7);
				
				}else if(userInfor.size()==9){
					email = userInfor.get(7);
					address = userInfor.get(8);
				}
			}
			// 发布时间
			String releaseTimeTep = html.xpath("//div[@class='info']/text()").get();
			releaseTimeTep = releaseTimeTep.substring(releaseTimeTep.indexOf("：") + 1, releaseTimeTep.indexOf("|"))
					.trim();
			Date releaseTime = DateUtils.StringToDateyyyy_MM_dd(releaseTimeTep);

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
			info.setEmail(email);

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
