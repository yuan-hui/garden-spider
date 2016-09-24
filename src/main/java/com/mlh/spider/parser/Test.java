package com.mlh.spider.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		File file = new File("D:\\dowload\\miaomu\\details\\prices_show_52572.html");
		String text;
		text = FileUtils.readFileToString(file, "GBK");

		Document htmldoc = Jsoup.parse(text);
		Html html = new Html(htmldoc);
		// 详情内容
		List<String> infomain = html.xpath("//div[@class='info_main']/table/tbody/tr/td[3]/ul/li").all();
		Map<String, String> detailcontentMap = DetailsHtmlUtil.changeToAttrMap(infomain);

		// 标题
		String title = html.xpath("//div[@class='info_main']/table/tbody/tr/td[3]/h1/strong/text()").get();

		// 米径(cm)
		String midiameter = DetailsHtmlUtil.getMidiameter(detailcontentMap);

		// 高度(cm)
		String height = DetailsHtmlUtil.getHeight(detailcontentMap);

		// 冠幅(cm)
		String crown = DetailsHtmlUtil.getCrown(detailcontentMap);

		// 地径(cm)
		String grounddiameter = DetailsHtmlUtil.getGrounddiameter(detailcontentMap);

		// 发布时间
		String releasetimeHtml = detailcontentMap.get("发布日期");
		Date releasetime = DateUtils.StringToDateyyyy_MM_dd(releasetimeHtml);

		

	}

}
