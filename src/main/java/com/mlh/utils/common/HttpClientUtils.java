package com.mlh.utils.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.jfinal.log.Log;

public class HttpClientUtils {

	private static Log logger = Log.getLog(HttpClientUtils.class);

	private static PoolingHttpClientConnectionManager connectionManager = null;

	private static HttpClientBuilder clientBuilder = null;

	private static RequestConfig requestConfig = null;

	public static int REQUEST_TIMEOUT = 300;

	public static int CONNECT_TIMEOUT = 300;

	public static int SOCKET_TIMEOUT = 300;

	public static int MAX_CONNECTION = 100;

	public static int MAX_HOST_CONNECTION = 30;

	public static String UTF_8 = "UTF-8";

	public static int READER_SIZE = 8192;

	static {
		requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(REQUEST_TIMEOUT).build();
		connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(MAX_CONNECTION);
		connectionManager.setDefaultMaxPerRoute(MAX_HOST_CONNECTION);
		clientBuilder = HttpClients.custom().setConnectionManagerShared(true);
		clientBuilder.setConnectionManager(connectionManager);
	}

	public static String requestByGetMethod(String s) {
		CloseableHttpClient httpClient = clientBuilder.build();
		StringBuilder entityStringBuilder = null;

		try {
			HttpGet get = new HttpGet(s);
			get.setConfig(requestConfig);
			CloseableHttpResponse httpResponse = httpClient.execute(get);
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;

			try {
				HttpEntity entity = httpResponse.getEntity();
				entityStringBuilder = new StringBuilder();
				if (null != entity) {
					inputStreamReader = new InputStreamReader(httpResponse.getEntity().getContent(), UTF_8);
					bufferedReader = new BufferedReader(inputStreamReader, READER_SIZE);
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						entityStringBuilder.append(line + "\n");
					}
				}
			} catch (Exception e) {
				logger.error("Request {} Exception {}", e);
			} finally {
				try {
					if (inputStreamReader != null) {
						inputStreamReader.close();
					}
				} catch (Exception e) {
					logger.error("inputStreamReader close error Request {} Exception {}", e);
				}
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (Exception e) {
					logger.error("bufferedReader close error Request {} Exception {}", e);
				}
				try {
					if (httpResponse != null) {
						httpResponse.close();
					}
				} catch (Exception e) {
					logger.error("httpResponse close error Request {} Exception {}", e);
				}
			}
		} catch (Exception e) {
			logger.error("Request {} Exception {}", e);
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (Exception e) {
				logger.error("httpClient close error Request {} Exception {}", e);
			}
		}

		return entityStringBuilder.toString();
	}
}
