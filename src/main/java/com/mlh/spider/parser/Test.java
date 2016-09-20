package com.mlh.spider.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mlh.spider.util.DetailsHtmlUtil;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.selector.Html;

public class Test {

	public static void main(String[] args) throws IOException {
		String filepath = "D:\\dowload\\green321\\details\\122532903814.jpg";
		File file = new File(filepath);
		String text;
		text = FileUtils.readFileToString(file, "GBK");

		Document htmldoc = Jsoup.parse(text);
		Html html = new Html(htmldoc);
		String title = html.xpath("//h1/text()").get();

		List<String> detailcontentList = html.xpath("//div[@id='detailcontent']/ul/li").all();

		Map<String, String> detailcontentMap = DetailsHtmlUtil.changeToAttrMap(detailcontentList);
		System.out.println(detailcontentMap);
		String midiaMeter = detailcontentMap.get("米径");
		String height = detailcontentMap.get("高度");
		String crown = detailcontentMap.get("冠幅");
		String grounddiaMeter = detailcontentMap.get("地径");
		System.out.println(detailcontentMap);
	}

}
