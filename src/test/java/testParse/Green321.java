package testParse;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mlh.spider.util.DetailsHtmlUtil;
import com.mlh.utils.common.DateUtils;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.selector.Html;

public class Green321 {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// AppRun.start();
		String filepath = "C:\\Users\\Administrator\\Desktop\\123.html";
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

		String company, addrsInfor, province, city, contacts, tel, email, address, fax, website, zipcode = null;

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
		// String cid = StringUtils.substringBefore(path, ".");

		String temStr = html.xpath("//div[@id='detailtime']/span[@class='left']/text()").get();
		// 发布时间
		String releaseTimeStr = temStr.substring(temStr.indexOf("：") + 1, temStr.indexOf("下")).trim();
		Date releaseTime = DateUtils.StringToDate(releaseTimeStr);

	}
}
