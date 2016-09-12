package com.mlh.spider.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import us.codecraft.webmagic.selector.Html;

public class Test {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String filepath = "D:/work/palm/svn/palm_mlh/code/trunk/mlh-spider/download/details/elizabethan-theater-chateau-d-hardelot-by-studio-andrew-todd.htm";
		System.out.println("解析文件：" + filepath);
		File file = new File(filepath);
		String text = null;
		try {
			text = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Document htmldoc = Jsoup.parse(text);
		Html html = new Html(htmldoc);
		
		String content = html.xpath("//div[@class='entry-content']/html()").nodes().get(0).get();
		
		List<String> keywordList = html.xpath("//p[@class='entry-tags']").xpath("//a[@rel='tag']/text(0)").all();
		System.out.println(keywordList);
		
	}

}
