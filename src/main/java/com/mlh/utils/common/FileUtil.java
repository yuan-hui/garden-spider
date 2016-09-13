package com.mlh.utils.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.log.Log;
import com.mlh.spider.handle.PageHandler;

/***
 * 
 * @Description: 文件工具类
 * @author liujiecheng
 */
public class FileUtil {

	private final static Log logger = Log.getLog(PageHandler.class);
	
	/**
	 * 
	 * @Description: 保存文件
	 * @author liujiecheng
	 */
	public static boolean saveFile(File file, byte[] bytes) {
		boolean save = false;
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes, 0, bytes.length);
			fos.flush();
			fos.close();
			
			save = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("message", e);
		} catch (IOException e) {
			logger.error("message", e);
			e.printStackTrace();
		}
		return save;
	}
	
	public static String getContentType(String extension) {
		String ext = StringUtils.lowerCase(extension);
		if (StringUtils.endsWith(ext, "jpeg")) {
			return "image/jpeg";
		} else if (StringUtils.endsWith(ext, "jpg")) {
			return "image/jpeg";
		} else if (StringUtils.endsWith(ext, "gif")) {
			return "image/gif";
		} else if (StringUtils.endsWith(ext, "bmp")) {
			return "application/x-bmp";
		} else if (StringUtils.endsWith(ext, "png")) {
			return "image/png";
		}
		return "image/jpeg";
	}
}
