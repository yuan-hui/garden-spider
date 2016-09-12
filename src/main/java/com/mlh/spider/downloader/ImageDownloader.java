package com.mlh.spider.downloader;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.mlh.enums.Confirm;
import com.mlh.model.ImagesList;
import com.mlh.utils.common.FileUtil;

import us.codecraft.webmagic.downloader.HttpClientDownloader;

/**
 * 
 * @Description: 图片下载器
 * @author liujiecheng
 */
public class ImageDownloader extends HttpClientDownloader {

	private String id;

	private String name;
	
	private String path;

	public ImageDownloader(String id, String name, String path, String url) {
		this.id = id;
		this.name = name;
		this.path = path;
		download(url);
	}

	@Override
	protected String getContent(String charset, HttpResponse httpResponse) {

		try {
			//图片字节
			byte[] bytes = EntityUtils.toByteArray(httpResponse.getEntity());
			File file = new File(path);
			
			//保存图片
			boolean save = FileUtil.saveFile(file, bytes);
			
			//图片保存成功后数据库更新为已下载
			if (save) {
				ImagesList.dao.updatePathAndDownloadById(name, Confirm.yes.toString(), id);
				System.out.println("图片下载成功：" + path);
			} else {
				System.out.println("图片下载失败.");
			}
			return new String(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
