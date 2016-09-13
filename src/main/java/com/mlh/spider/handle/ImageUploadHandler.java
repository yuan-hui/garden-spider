package com.mlh.spider.handle;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.oss.OSSKit;
import com.jfinal.kit.PropKit;
import com.mlh.common.AppRun;
import com.mlh.enums.BussCode;
import com.mlh.enums.Confirm;
import com.mlh.model.ImagesList;
import com.mlh.model.PageDetail;
import com.mlh.utils.common.FileUtil;
import com.mlh.utils.common.PathUtil;
import com.mlh.utils.common.StringKit;

/**
 * 
 * @Description: 图片上传器(上传到OSS)
 * @author liujiecheng
 */
public class ImageUploadHandler {

	private final static int SLEEP_TIME = 2;

	private static void process(PageDetail de) throws InterruptedException {
		String detailId = de.getId();
		
		// 查询需要上传的图片
		List<ImagesList> images = ImagesList.dao.findByDownloadAndUploadAndDetailId(Confirm.yes.toString(), Confirm.no.toString(), detailId);
		if (images != null && images.size() > 0) {
			int index = 1;
			for (ImagesList img : images) {

				String _id = img.getId();
				String _path = img.getPath();

				System.out.println(index + "、正在上传图片：" + _path);

				//OSS文件Key
				String fileKey = StringKit.getFileKey();
				String filename = _path;
				
				//本地文件所在路径
				String filepath = PathUtil.getRootPath() + "download" + "\\" + "images" + "\\" + filename;
				
				//读取
				File image = new File(filepath);
				long length = image.length(); 
				
				String suffix = StringUtils.substringAfterLast(filename, ".");
				String contentType = FileUtil.getContentType(suffix);
				
				//上传到oSS
				boolean upload = OSSKit.putImage(fileKey, filename, length, contentType, image);
				if (upload) {
					ImagesList.dao.updateFileKeyAndUploadById(fileKey, Confirm.yes.toString(), _id);
					System.out.println("OSS图片访问路径：" + PropKit.get("production.oss.file.url") + fileKey);
					System.out.println("图片上传成功：" + filename);
				}
				index++;

				System.out.println("程序休眠：" + SLEEP_TIME + "秒.");
				Thread.sleep(SLEEP_TIME * 1000);

				System.out.println("-----------------------------------------------------------------");

			}
			
			//检查当前详情页的图片上传情况
			PageDetail.dao.checkUpimg(detailId);
		} else {
			System.out.println("没有需要上传的图片：" + images.size());

			System.out.println("程序休眠：" + SLEEP_TIME * 2 + "秒.");
			Thread.sleep(SLEEP_TIME * 2 * 1000);

			System.out.println("-----------------------------------------------------------------");
		}
	}
	public static void main(String[] args) throws InterruptedException {
		// ----
		AppRun.start();

		System.out.println("开始上传图片...");

		do {
			//所有业务编码
			BussCode[] codes = BussCode.values();
			
			for (BussCode c : codes) {
				String _code = c.getCode();

				//查询已下载图片的详情页面(先把有所有图片都下载的详情页相关图片上传了)
				List<PageDetail> details = PageDetail.dao.findByCodeAndDownimg(_code, Confirm.yes.toString());
				
				if (details != null && details.size() > 0) {
					for (PageDetail de : details ) {
						process(de);
					}
				} else {
					System.out.println("没有下载好全部图片的详情页：" + details.size());
					
					System.out.println("程序休眠：" + SLEEP_TIME + "秒.");
					Thread.sleep(SLEEP_TIME * 1000);

					System.out.println("-----------------------------------------------------------------");
				}
			}
		} while (true);

	}

}
