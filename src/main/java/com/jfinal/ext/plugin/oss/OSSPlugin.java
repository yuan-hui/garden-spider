package com.jfinal.ext.plugin.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;

/**
 * 
 * @Description: OSS Plugin
 * @author liujiecheng
 */
public class OSSPlugin implements IPlugin {

	private final static Log logger = Log.getLog(OSSPlugin.class);

	/**
	 * 
	 */
	private OSSClient client = null;

	/**
	 * 设置HTTP最大连接数为20
	 */
	private int MAX_CONNECTIONS = 20;

	/**
	 * 设置TCP连接超时为60000毫秒
	 */
	private int CONNECTION_TIMEOUT = 60000;

	/**
	 * 设置最大的重试次数为8
	 */
	private int MAX_ERROR_RETRY = 8;

	/**
	 * 设置Socket传输数据超时的时间为6000毫秒
	 */
	private int SOCKET_TIMEOUT = 60000;

	@Override
	public boolean start() {
		try {
				ClientConfiguration conf = new ClientConfiguration();

				// 设置HTTP最大连接数为10
				conf.setMaxConnections(MAX_CONNECTIONS);

				// 设置TCP连接超时为5000毫秒
				conf.setConnectionTimeout(CONNECTION_TIMEOUT);

				// 设置最大的重试次数为8
				conf.setMaxErrorRetry(MAX_ERROR_RETRY);

				// 设置Socket传输数据超时的时间为6000毫秒
				conf.setSocketTimeout(SOCKET_TIMEOUT);

				String endpoint = PropKit.get("production.oss.file.end.point");
				String accessKeyId = PropKit.get("production.oss.file.access.key.id");
				String accessKeySecret = PropKit.get("production.oss.file.access.key.secret");
				
				//初始化
				client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new RuntimeException("can't connect oss , please check the config or key or secret.");
		}
		String bucketName = PropKit.get("production.oss.file.bucket.name");
		OSSKit.init(client, bucketName);

		return true;
	}

	@Override
	public boolean stop() {
		if (client != null) {
			client.shutdown();
		}
		return true;
	}

}
