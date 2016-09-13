package com.mlh.spider.factory;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mlh.enums.BussCode;
import com.mlh.model.ImagesList;

import us.codecraft.webmagic.Page;

/**
 * 
 * @Description: 图片提取处理器工厂
 * @author liujiecheng
 */
public class ImageProcessorFactory {

	private final static String GOOOOD_TITLE_IMG_REGEX = "<img src=\"([\\s\\S]*)\" class=\"img-responsive\"";

	private final static String GOOOOD_CONTENT_IMG_REGEX = "src=\"([\\s\\S]*)\" alt=";

	private final static String GOOOOD_GALLERY_IMG_REGEX = "<img src=\"([\\s\\S]*)\" class=\"img-responsive\"";

	/**
	 * 
	 * @Description: 处理不同的业务
	 * @author liujiecheng
	 */
	public void produce(Page page, String code, String detailId) {
		BussCode busscode = BussCode.valueOf(code);
		switch (busscode) {
		case lvsemiaomu_qiaoguanmu:
			goooodArchitectureDetailsImage(page, code, detailId);
			break;
		default:
			System.out.println("业务未处理...");
			break;
		}
	}

	/**
	 * 
	 * @Description: 把详情页里面的图片记录提取出来
	 * @author liujiecheng
	 */
	private void goooodArchitectureDetailsImage(Page page, String code, String detailId) {
		List<String> urls = new LinkedList<String>();

		String source = page.getUrl().get();
		System.out.println("开始分析图片：" + source);
		
		// 标题图
		String titleImage = page.getHtml().xpath("//div[@class='entry-header-wrapper']").xpath("//div[@class='entry-cover']").xpath("//div[@class='post-thumbnail']").regex(GOOOOD_TITLE_IMG_REGEX).get();
		if (StringUtils.isNotBlank(titleImage)) {
			urls.add(titleImage);
		}
		System.out.println("分析出标题图：" + titleImage);
		
		// 内容里面的图片
		List<String> contentImages = page.getHtml().xpath("//div[@class='entry-content']").xpath("//img[@*]").regex(GOOOOD_CONTENT_IMG_REGEX).all();
		if (contentImages != null && contentImages.size() > 0) {
			urls.addAll(contentImages);
		}
		System.out.println("分析出内容图：" + contentImages.size());

		// 相册里面的图片
		List<String> images = page.getHtml().xpath("//ul[@class='post-gallery']").xpath("//a[@class='colorbox_gallery']").regex(GOOOOD_GALLERY_IMG_REGEX).all();
		if (images != null && images.size() > 0) {
			urls.addAll(images);
		}
		System.out.println("分析出相册：" + images.size());
		
		//保存所有图片链接
		int[] rows = ImagesList.dao.saveImages(urls, code, detailId, source);
		System.out.println("保存详情页相关图片：" + rows.length);
		
		
		System.out.println("-----------------------------------------------------------------");

	}
}
