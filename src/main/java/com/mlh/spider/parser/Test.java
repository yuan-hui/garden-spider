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
/**
 *解析test
 * @author sjl
 *
 */
public class Test {

	public static void main(String[] args) throws IOException {
		String filepath = "D:\\dowload\\miaomu\\details\\prices_show_14879.html";
		File file = new File(filepath);
		String text;
		text = FileUtils.readFileToString(file, "GBK");

		Document htmldoc = Jsoup.parse(text);
		Html html = new Html(htmldoc);
		List<String> tableList = html.xpath("//tr[@class='table_back']/td/text()").all(); 
		List<String> userInfor = html.xpath("//div[@class='infolist']").xpath("//span/text()").all();
		
		
		
	}

}
