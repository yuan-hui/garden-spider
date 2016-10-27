package com.mlh.spider.test;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mlh.common.AppRun;
import com.mlh.common.WebMagicFunction;
import com.mlh.common.WebMagicParams;
import com.mlh.spider.util.DetailsHtmlUtil;
import com.mlh.utils.common.DateUtils;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.selector.Html;

public class Test extends WebMagicParams {
	public static void main(String[] args) throws Exception {

		 File file = new File("C:\\Users\\Administrator\\Desktop\\123.html");
		 String text;
		 text = FileUtils.readFileToString(file, "gbk");
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
						String midiaMeter = html.xpath("//div[@class='info_main']/ul/li[5]/a/text()").get();
						String height = null;
						String crown = null;
						String grounddiaMeter = null;

						List<String> detailcontentList = html.xpath("//div[@class='info_main']/ul/li").all();
						Map<String, String> detailcontentMap = DetailsHtmlUtil.changeToAttrMap(detailcontentList);
						

						if(StringUtils.isNotBlank(midiaMeter))
							midiaMeter.replace("cm", "");

						height = detailcontentMap.get("高度");
						crown = detailcontentMap.get("冠幅");
						grounddiaMeter = detailcontentMap.get("地径");
						midiaMeter = detailcontentMap.get("米径");

						String price = detailcontentMap.get("产品报价");//

						if(StringUtils.isNotBlank(price))
							price = price.indexOf("元") > -1 ? price.substring(0, price.indexOf("元")) : price;

						String company = html.xpath("//div[@class='contact_body']/ul/li[1]/a/text()").get();

						List<String> usercontentList = html.xpath("//div[@class='contact_body']/ul/li").all();
						Map<String, String> usercontentListMap = DetailsHtmlUtil.changeToAttrMap(usercontentList);

						String tmpAddrss = usercontentListMap.get("所在地");
						// 省份
						String province = StringUtils.substring(tmpAddrss, 0, 2);

						String city = StringUtils.substring(tmpAddrss, tmpAddrss.length() - 3);

						String contacts = usercontentListMap.get("联系人");

						String tel = usercontentListMap.get("电话");

						String email = usercontentListMap.get("邮件");

						String address = usercontentListMap.get("地址");

	}

}
