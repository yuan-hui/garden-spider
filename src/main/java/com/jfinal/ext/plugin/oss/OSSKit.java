package com.jfinal.ext.plugin.oss;

import java.io.File;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.jfinal.log.Log;

public class OSSKit {

	private final static Log logger = Log.getLog(OSSKit.class);

	/**
	 * OSSClient
	 */
	private static OSSClient client;

	/**
	 * 默认Bucket名称
	 */

	private static String bucketName;

	/**
	 * @Description: 初始化
	 * @author liujiecheng
	 */
	public static void init(OSSClient client, String bucket) {
		OSSKit.client = client;
		OSSKit.bucketName = bucket;

	}

	public static String getBucketName() {
		return bucketName;
	}

	public static OSSClient getClient() {
		return client;
	}

	public static void setOSSClient(OSSClient client) {
		OSSKit.client = client;
	}

	/**
	 * 
	 * @Description: 上传文件流
	 * @author liujiecheng
	 */
	public static PutObjectResult putStream(String key, String filename, long length, String contentType, InputStream inputStream) {

		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();

		// Content-Disposition请求头
		meta.setContentDisposition("filename=" + filename);

		// 设置Content-Type
		meta.setContentType(contentType);

		// 必须设置ContentLength
		meta.setContentLength(length);

		// 上传Object.
		PutObjectResult result = client.putObject(getBucketName(), key, inputStream, meta);
		return result;
	}

	/**
	 * 
	 * @Description: 上传本地文件
	 * @author liujiecheng
	 */
	public static PutObjectResult putFile(String key, String filename, long length, String contentType, File file) {

		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();

		// Content-Disposition请求头
		meta.setContentDisposition("filename=" + filename);

		// 设置Content-Type
		meta.setContentType(contentType);

		// 必须设置ContentLength
		meta.setContentLength(length);

		// 上传Object.
		PutObjectResult result = client.putObject(bucketName, key, file, meta);

		return result;
	}

	/**
	 * 
	 * @Description: 上传本地文件(只返回布尔结果)
	 * @author liujiecheng
	 */
	public static boolean putImage(String key, String filename, long length, String contentType, File image) {
		boolean upload = false;

		try {
			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();

			// Content-Disposition请求头
			meta.setContentDisposition("filename=" + filename);

			// 设置Content-Type
			meta.setContentType(contentType);

			// 必须设置ContentLength
			meta.setContentLength(length);

			// 上传Object.
			PutObjectResult result = client.putObject(bucketName, key, image, meta);
			logger.info("PutObjectResult：" + result);
			upload = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}

		return upload;
	}
}
