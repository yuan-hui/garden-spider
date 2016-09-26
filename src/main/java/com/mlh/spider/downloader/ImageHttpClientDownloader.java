package com.mlh.spider.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mlh.common.AppRun;

public class ImageHttpClientDownloader {

	private static final String USER_AGENT = "Mozilla/5.0 Firefox/26.0";

	private static Logger logger = LoggerFactory.getLogger(ImageHttpClientDownloader.class);

	private static final int TIMEOUT_SECONDS = 120;

	private static final int POOL_SIZE = 120;

	private static CloseableHttpClient httpclient;

	/**
	 * 下载图片或文件
	 * 
	 * @param urls
	 *            图片链接
	 * @param code
	 *            保存位置
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static boolean downloadFile(String[] urls, String filePath, String Referer)
			throws ClientProtocolException, IOException {

		ImageHttpClientDownloader imageDownloader = new ImageHttpClientDownloader();
		imageDownloader.initApacheHttpClient();
		
		File file =new File(filePath);    
		//如果文件夹不存在则创建    
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		} 
		int index = 1;
		for (String url : urls) {
			
			
				filePath= filePath+StringUtils.substring(Referer, Referer.lastIndexOf("/") + 1,Referer.lastIndexOf("."))+".php";
			
			
			imageDownloader.fetchContent(url, filePath, Referer);
		}
		imageDownloader.destroyApacheHttpClient();
		return true;
	}

	public void initApacheHttpClient() {
		// Create global request configuration
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT_SECONDS * 1000)
				.setConnectTimeout(TIMEOUT_SECONDS * 1000).build();

		// Create an HttpClient with the given custom dependencies and
		// configuration.
		httpclient = HttpClients.custom().setUserAgent(USER_AGENT).setMaxConnTotal(POOL_SIZE)
				.setMaxConnPerRoute(POOL_SIZE).setDefaultRequestConfig(defaultRequestConfig).build();
	}

	private void destroyApacheHttpClient() {
		try {
			httpclient.close();
		} catch (IOException e) {
			logger.error("httpclient close fail", e);
		}
	}

	public void fetchContent(String imageUrl, String path, String Referer) throws ClientProtocolException, IOException {

		HttpGet httpget = new HttpGet(imageUrl);
		httpget.setHeader("Referer", Referer);

		System.out.println("executing request " + httpget.getURI());
		CloseableHttpResponse response = httpclient.execute(httpget);

		try {
			HttpEntity entity = response.getEntity();

			if (response.getStatusLine().getStatusCode() >= 400) {
				throw new IOException("Got bad response, error code = " + response.getStatusLine().getStatusCode()
						+ " imageUrl: " + imageUrl);
			}
			if (entity != null) {
				InputStream input = entity.getContent();//
				
				OutputStream output = new FileOutputStream(new File(path));
				IOUtils.copy(input, output);
				output.flush();
			}
		} finally {
			response.close();

		}

	}

	public static void main(String[] args) {

		AppRun.start();
		String url = "http://www.miaomu.net/api/task.js.php?moduleid=23&html=show&itemid=46445&refresh=0.5904558456348565.js";
		String Referer = "http://www.miaomu.net/prices/201303/12/prices_show_46445.html";
		// System.out.println(PropKit.get("details.miaomuPhp.path")+StringUtils.substringAfterLast(Referer,
		// "/"));
		try {
			ImageHttpClientDownloader.downloadFile(new String[] { url }, "details.miaomuPhp.path", Referer);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
