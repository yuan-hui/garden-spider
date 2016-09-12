package com.mlh.spider.parser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.mlh.enums.Confirm;
import com.mlh.model.Content;
import com.mlh.model.ImagesList;
import com.mlh.model.PageDetail;
import com.mlh.utils.common.PathUtil;

import us.codecraft.webmagic.selector.Html;

/**
 * 
 * @Description: 本地文件解析
 * @author liujiecheng
 */
public class GoooodArchitectureDetailsLocalHtmlParser {

	private final static String TITLE_IMG_REGEX = "<img src=\"([\\s\\S]*)\" class=\"img-responsive\"";

	private final static Log logger = Log.getLog(GoooodArchitectureDetailsLocalHtmlParser.class);

	private final static String GALLERY_IMG_REGEX = "<img src=\"([\\s\\S]*)\" class=\"img-responsive\"";

	private final static int SLEEP_TIME = 2;

	private final static String IMAGES_W_H = "-150x150";
	/**
	 * 
	 * @Description: 解析页面
	 * @author liujiecheng
	 */
	private static void process(PageDetail detail) {
		String ossFileUrl = PropKit.get("production.oss.file.url");
		
		String detailId = detail.getId();
		String source = detail.getUrl();
		String path = detail.getPath();
		String filepath = PathUtil.getRootPath() + "download" + "\\" + "details" + "\\" + path;

		Map<String, String> imagesMap = ImagesList.dao.getImagesMapByDetailId(Confirm.yes.toString(), Confirm.yes.toString(), detailId);
		try {
			System.out.println("解析文件：" + filepath);
			File file = new File(filepath);
			String text;
			text = FileUtils.readFileToString(file, "UTF-8");

			Document htmldoc = Jsoup.parse(text);
			Html html = new Html(htmldoc);

			String title = "";
			String titleImage = "";
			List<String> keywordList;
			String content = "";
			List<String> imageList;

			// 标题
			title = html.xpath("//header[@class='entry-header']").xpath("//h1[@class='entry-title']/text()").get();
			// System.out.println(title);

			// 标题图
			titleImage = html.xpath("//div[@class='entry-header-wrapper']").xpath("//div[@class='entry-cover']").xpath("//div[@class='post-thumbnail']").regex(TITLE_IMG_REGEX).get();
			String ossTitleImage = "";
			if (imagesMap.get(titleImage) != null) {
				ossTitleImage = ossFileUrl + imagesMap.get(titleImage);
			} else {
				ossTitleImage = titleImage;
			}
			// System.out.println(titleImage);

			// 关键词
			keywordList = html.xpath("//p[@class='entry-tags']").xpath("//a[@rel='tag']/text(0)").all();
			// System.out.println(keywordList);

			// 相册(注册处理"-150x150")
			imageList = html.xpath("//ul[@class='post-gallery']").xpath("//a[@class='colorbox_gallery']").regex(GALLERY_IMG_REGEX).all();
			// System.out.println(imageList);

			// 内容
			content = html.xpath("//div[@class='entry-content']/html()").get();
			for (Map.Entry<String, String> entry : imagesMap.entrySet()) {
				String oldImg = entry.getKey();
				String newImg = ossFileUrl + entry.getValue();
				content = content.replace(oldImg, newImg);
			}
			
			// System.out.println(content);

			// 转成字符串
			String keywords = ArrayUtils.toString(keywordList);
			keywords = StringUtils.substringBetween(keywords, "[", "]");

			// 转成字符串
			List<String> ossImageList = new LinkedList<String>();
			for (String img : imageList) {
				String src = "";
				img = StringUtils.replace(img, IMAGES_W_H, "");
				if (imagesMap.get(img) != null) {
					src = ossFileUrl + imagesMap.get(img);
				} else {
					src = img;
				}
				ossImageList.add(src);
			}
			String images = ArrayUtils.toString(ossImageList);
			images = StringUtils.substringBetween(images, "[", "]");

			boolean save =false;// Content.dao.saveOrUpdate(title, ossTitleImage, keywords, images, content, detailId, source);
			if (save) {
				System.out.println("内容保存成功：" + title);
				
				int row = PageDetail.dao.updateParserById(Confirm.yes.toString(), detailId);
				System.out.println("详情页更新为已解析：" + row);
			} else {
				System.out.println("内容保存失败：" + title);
			}

		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error("message", e1);
		}

		System.out.println("程序休眠：" + SLEEP_TIME + "秒.");
		try {
			Thread.sleep(SLEEP_TIME * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("-----------------------------------------------------------------");
	}

	/**
	 * 
	 * @Description: 应用主入口
	 * @author liujiecheng
	 */
	public static void main(String[] args) {

		String code = args[0];

		System.out.println("开始查看需要解析的详情页...");

		List<PageDetail> details = PageDetail.dao.findByCodeAndParser(code, Confirm.no.toString());

		if (details != null && details.size() > 0) {
			for (PageDetail detail : details) {
				process(detail);
			}
		} else {
			System.out.println("没有需要解析的详情页：" + details.size());
		}
	}
}
