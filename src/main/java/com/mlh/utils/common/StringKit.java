package com.mlh.utils.common;

import java.security.MessageDigest;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * @ClassName: StringKit
 * @Description: String工具类
 * @author liujiecheng
 * @date 2016年4月15日 下午6:09:43
 *
 */
public class StringKit {
	
	private final static String RANDOM_CHARACTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/**
	 * 
	 * @Title: getUUID
	 * @Description: 生成指定长度字符串
	 * @param @param
	 *            number
	 * @param @return
	 *            参数说明
	 * @return String 返回类型
	 * @throws @author
	 *             liujiecheng
	 * @date 2016年4月15日 下午6:11:50
	 */
	public static String getUUID(int number) {
		String uuid = UUID.randomUUID().toString();
		if (number > 0 && number < uuid.length()) {
			uuid = uuid.replaceAll("-", "").substring(0, number).toUpperCase();
		}
		return uuid;
	}

	/**
	 * 
	 * @Title: getUUID
	 * @Description: 生成32位UUID
	 * @param @return
	 *            参数说明
	 * @return String 返回类型
	 * @throws @author
	 *             liujiecheng
	 * @date 2016年4月15日 下午6:11:36
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");

	}

	/**
	 * 
	 * @Title: encrypt
	 * @Description: 使用md5散列字符串
	 * @param @param
	 *            srcStr
	 * @param @return
	 *            参数说明
	 * @return String 返回类型
	 * @throws @author
	 *             liujiecheng
	 * @date 2016年4月21日 下午6:43:30
	 */
	public static String encrypt(String str) {
		try {
			String result = "";
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(str.getBytes("utf-8"));
			for (byte b : bytes) {
				String hex = Integer.toHexString(b & 0xFF).toUpperCase();
				result += ((hex.length() == 1) ? "0" : "") + hex;
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @Title: getToken
	 * @Description: Token随机串
	 * @param @return
	 *            参数说明
	 * @return String 返回类型
	 * @throws @author
	 *             liujiecheng
	 * @date 2016年4月21日 下午6:55:36
	 */
	public static String getToken() {
		return encrypt(getUUID()).toLowerCase();
	}

	/**
	 * @Description: 判断字符串为Double类型
	 * @author liujiecheng
	 */
	public static boolean isDouble(String str) {
		boolean is = false;
		if (StringUtils.isNotBlank(str) && str.contains(".")) {
			String[] result = StringUtils.split(str, ".");
			for (String s : result) {
				if (StringUtils.isNumeric(s)) {
					is = true;
				} else {
					is = false;
					break;
				}
			}
		}
		return is;
	}

	/**
	 * @Description: 过滤特殊字符串
	 * @author liujiecheng
	 */
	public static String filter(String str) {
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>《》•㎡·/?~！@#￥%……&*（）——+|{}【】‘；：”“’\"。，、？]";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);

		return m.replaceAll("").trim();
	}

	/**
	 * @Description: 过滤标签的特殊字符串用于拼音转换
	 * @author liujiecheng
	 */
	public static String filterTagForPinYin(String str) {
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>《》•㎡·/?~！@#￥%……&*（）——+|{}【】‘；：”“’\"。，、？]";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);

		return m.replaceAll("").trim();
	}

	/**
	 * @Description: 过滤标签的特殊字符串
	 * @author liujiecheng
	 */
	public static String filterTag(String str) {
		// 清除掉所有特殊字符
		String regEx = "[`~@#$%^&*()=|{}':;'\\[\\]<>《》•㎡·/?~@#￥%……&*（）——+|{}【】‘；：’\"。、？]";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);

		return m.replaceAll("").trim();
	}

	public static String getFileKey() {
		String key = RandomStringUtils.random(18, RANDOM_CHARACTER) + getUUID() + RandomStringUtils.random(18, RANDOM_CHARACTER);
		return key.toUpperCase();
	}

	/**
	 * 
	 * @Description: 提取Html内部的文本
	 * @author liujiecheng
	 */
	public static String getHtmlText(String html) {
		Document doc = Jsoup.parse(html);
		String text = doc.text();
		return text;
	}
	
	/**
	 * 
	 * @Description: 提取Html内部的文本
	 * @author liujiecheng
	 */
	public static String getHtmlTextNoBlank(String html) {
		html = StringUtils.replaceEach(html, new String[]{"&nbsp;"," "}, new String[]{"",""});
		Document doc = Jsoup.parse(html);
		String text = doc.text();
		return StringUtils.trim(text);
	}
	
	public static void main(String[] args) {
		// String str = "A.N，D";
		// System.out.println(str);
		// System.out.println(filterTag(str));
		System.out.println(getFileKey());
	}

}
