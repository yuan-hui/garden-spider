package com.mlh.spider.test;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mlh.common.AppRun;
import com.mlh.common.WebMagicFunction;
import com.mlh.common.WebMagicParams;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.selector.Html;

public class Test extends WebMagicParams {
	public static void main(String[] args) throws Exception {

		// File file = new File("C:\\Users\\Administrator\\Desktop\\123.html");
		// String text;
		// text = FileUtils.readFileToString(file, "utf-8");
		//
		// Document htmldoc = Jsoup.parse(text);
		// Html html = new Html(htmldoc);
		// List<String> list =
		// html.xpath("//div[@id='list']/table[@class='table']/tbody/tr").all();
		//// System.out.println(List);
		// for(int i = 0 ; i < list.size();i++){
		// String xpth=
		// "//div[@id='list']/table[@class='table']/tbody/tr["+(i+1)+"]/td[@class='ip']";
		// String ip1 = html.xpath(xpth+"/span[1]/text()").get();
		// String ip2 = html.xpath(xpth+"/text()").get();
		// System.out.println(ip1);
		// i++;
		// }

		// }
		
	}

}
