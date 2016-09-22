package com.mlh.spider.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

/**
 * 解析test
 * 
 * @author sjl
 *
 */
public class Test {

	public static void main(String[] args) throws IOException {

		File file = new File("D:\\dowload\\xbmiaomu\\details\\show.php-itemid=672506");
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
		String height = "";
		String crown = "";
		if (index == 8)
			height = html.xpath("//div[@class='info_main']/ul/li[6]/a/text()").get().replace("cm", "");
		if (index == 9)
			crown = html.xpath("//div[@class='info_main']/ul/li[7]/a/text()").get().replace("cm", "");

		String price = html.xpath("//div[@class='info_main']/ul/li[" + (index - 1) + "]/a/text()").get().replace("元/棵","");//

		String company = html.xpath("//div[@class='contact_body']/ul/li[1]/a/text()").get();

		

		List<String> detailcontentList = html.xpath("//div[@class='contact_body']/ul/li").all();
		Map<String, String> detailcontentMap = DetailsHtmlUtil.changeToAttrMap(detailcontentList);
		
		String tmpAddrss = detailcontentMap.get("所在地");
		// 省份
		String province = StringUtils.substring(tmpAddrss, 0, 2);
		
		String city = StringUtils.substring(tmpAddrss, tmpAddrss.length()-3);

		String contacts = detailcontentMap.get("联系人");

		String tel = detailcontentMap.get("电话");

		String email = detailcontentMap.get("邮件");

		String address = detailcontentMap.get("地址");
		
		
		System.out.println(address);
		
		
		
		System.out.println(detailcontentMap);

	}

}
