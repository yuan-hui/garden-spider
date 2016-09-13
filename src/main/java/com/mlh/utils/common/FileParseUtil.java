package com.mlh.utils.common;

import java.io.File;
import java.io.IOException;

import com.jfinal.ext.plugin.oss.OSSKit;
import com.mlh.common.AppRun;

public class FileParseUtil {

	private static void upload() {
		File file = new File("C:\\Users\\lenovo\\Desktop\\5.jpg");
		String filekey = StringKit.getUUID();
		System.out.println(filekey);
		OSSKit.putImage(filekey, "5.jpg", file.length(), FileUtil.getContentType("5.jpg"), file);
	}
	
	public static void main(String[] args) throws IOException {
		
		AppRun.start();
		upload();
		
//		String filepath = "C:\\Users\\lenovo\\Desktop\\test.html";
//		
//		System.out.println("解析文件：" + filepath);
//		File file = new File(filepath);
//		
//		
//		String text;
//		text = FileUtils.readFileToString(file, "UTF-8");
//		
//
//		Document htmldoc = Jsoup.parse(text);
//		Html html = new Html(htmldoc);
//		
//		String detail = html.xpath("//article[@id='single-content']").get();
//		System.out.println(detail);
		

	}

}
