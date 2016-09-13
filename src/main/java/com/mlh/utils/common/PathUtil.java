package com.mlh.utils.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Description: 路径相关工具类
 * @author liujiecheng
 */
public class PathUtil {

	public static String getRootPath() {
		String path = Class.class.getClass().getResource("/").getPath();
		return StringUtils.substringBefore(path, "mlh-spider") + "mlh-spider" + "/";
	}
	
}
