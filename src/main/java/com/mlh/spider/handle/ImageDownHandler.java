package com.mlh.spider.handle;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mlh.common.AppRun;
import com.mlh.enums.BussCode;
import com.mlh.enums.Confirm;
import com.mlh.model.ImagesList;
import com.mlh.model.PageDetail;
import com.mlh.spider.pageprocessor.ImageProcessor;

/**
 * 
 * @Description: 图片下载器
 * @author liujiecheng
 */
public class ImageDownHandler {

	private final static int SLEEP_TIME = 2;
	
	public static void main(String[] args) throws InterruptedException {
		// ----
		AppRun.start();

		System.out.println("开始下载图片...");
		do {
			//所有业务编码
			BussCode[] codes = BussCode.values();
			
			for (BussCode c : codes) {
				String _code = c.getCode();

				//查询已下载的详情页面(先把有原文件的详情页相关图片下载了)
				List<PageDetail> details = PageDetail.dao.findByCodeAndDownloadAndDownimg(_code, Confirm.yes.toString(), Confirm.no.toString());
				if (details != null && details.size() > 0) {
					for (PageDetail de : details ) {
						String detailId = de.getId();
						
						List<ImagesList> images = ImagesList.dao.findByDownloadAndDetailsId(Confirm.no.toString(), detailId);
						
						//当前详情页存在需要下载的图片,进入图片下载流程
						if (images != null && images.size() > 0) {
							int index = 1;
							for (ImagesList img : images) {
								
								String _id = img.getId();
								String _url = img.getUrl();
								String _name = StringUtils.substringAfterLast(_url, "/");
								
								System.out.println(index + "正在下载图片：" + _url + "所在详情页,->" + de.getUrl());
								
								String[] params = new String[]{_id, _name, _url};
								try {
									ImageProcessor.main(params);
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
								index ++;
								
								System.out.println("程序休眠：" + SLEEP_TIME + "秒.");
								Thread.sleep(SLEEP_TIME*1000);
								
								System.out.println("-----------------------------------------------------------------");
								
							}
							
							//检查当前详情页的图片下载情况
							PageDetail.dao.checkDownimg(detailId);
							
						} else {
							System.out.println("当前详情页没有需要下载的图片：" + images.size() + ",->" + detailId);
							
							System.out.println("程序休眠：" + SLEEP_TIME + "秒.");
							Thread.sleep(SLEEP_TIME * 1000);

							System.out.println("-----------------------------------------------------------------");
						}
					}
				} else {
					System.out.println("没有下载好的详情页：" + details.size());
					
					System.out.println("程序休眠：" + SLEEP_TIME + "秒.");
					Thread.sleep(SLEEP_TIME * 1000);

					System.out.println("-----------------------------------------------------------------");
				}
				
			}
		} while (true);
	}

}
