package com.mlh.utils.common;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class ImageBase64Utils {

	/**
	 * 
	 * @Description: 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @author liujiecheng
	 */
	public static String getImageStr(byte[] data) {//

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return Base64.encodeBase64String(md.digest(data));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Description: 对字节数组字符串进行Base64解码并生成图片
	 * @author liujiecheng
	 */
	public static boolean generateImage(String path, String filename, String img) {//
		try {
			// Base64解码
			byte[] b = Base64.decodeBase64(img);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 新生成的图片
			String imgFilePath = path + filename;
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
