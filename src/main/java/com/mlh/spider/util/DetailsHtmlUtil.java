package com.mlh.spider.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mlh.utils.common.StringKit;

/**
 * 
 * @Description: 详情页的一些HTML特殊处理
 * @author liujiecheng
 */
public class DetailsHtmlUtil {

	/**
	 * 
	 * @Description: 把类似“联系人：刘金国”这种结构的数据再做一边处理以方便读取
	 * @author liujiecheng
	 */
	public static Map<String, String> changeToAttrMap(List<String> list) {
		Map<String, String> map = new HashMap<String, String>();

		for (String info : list) {
			String str = null;
			if (StringUtils.contains(info, "电&nbsp;&nbsp;话：")) {
				str = getNewTelStr(info);
			} else {
				str = StringKit.getHtmlTextNoBlank(info);
			}
			if (StringUtils.isNotBlank(str)) {
				String[] attrs = null;
				if (str.contains("：")) {
					attrs = str.split("：");
				} else if (str.contains(":")) {
					attrs = str.split(":");
				}
				
				String attrName = null;
				String attrValue = null;
				if (attrs != null && attrs.length == 1) {
					attrName = StringUtils.trim(attrs[0]);
				} else if (attrs != null && attrs.length == 2) {
					attrName = StringUtils.trim(attrs[0]);
					attrValue = StringUtils.trim(attrs[1]);
				}
				map.put(attrName, attrValue);
			}
		}
		return map;
	}
	
	private static String getNewTelStr(String info) {
		String html = StringUtils.replaceEach(info, new String[]{"&nbsp;"," "}, new String[]{",",""});
		Document doc = Jsoup.parse(html);
		String text = doc.text();
		String[] telArray = text.split("：");
		String str = StringUtils.replaceEach(telArray[0], new String[]{","}, new String[]{""}) + "：" + telArray[1];
		return str;
	}
	
	
	/**
	 * 
	 * @Description: 处理发布时间
	 * @author liujiecheng
	 */
	public static Date getReleasetime(String html) {
		String temp = StringUtils.substringBetween(html, "发布时间：", "<table>");
		temp = StringUtils.replace(temp, "&nbsp;", "");
		temp = StringUtils.trim(temp);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		if (StringUtils.isNotBlank(temp)) {
			try {
				date = formatter.parse(temp);
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		return date;
	}

	/**
	 * 
	 * @Description: 米径(cm)
	 * @author liujiecheng
	 */
	public static String getMidiameter(Map<String, String> map) {
		String key = "米径(cm)";
		String value = map.get(key);
		return value;
	}

	/**
	 * 
	 * @Description: 高度(cm)
	 * @author liujiecheng
	 */
	public static String getHeight(Map<String, String> map) {
		String key = "高度(cm)";
		String value = map.get(key);
		return value;
	}

	/**
	 * 
	 * @Description: 冠幅(cm)
	 * @author liujiecheng
	 */
	public static String getCrown(Map<String, String> map) {
		String key = "冠幅(cm)";
		String value = map.get(key);
		return value;
	}

	/**
	 * 
	 * @Description: 地径(cm)
	 * @author liujiecheng
	 */
	public static String getGrounddiameter(Map<String, String> map) {
		String key = "地径(cm)";
		String value = map.get(key);
		return value;
	}

	/**
	 * 
	 * @Description: 单位
	 * @author liujiecheng
	 */
	public static String getUnit(Map<String, String> map) {
		String key = "单位";
		String value = map.get(key);
		return value;
	}

	/**
	 * 
	 * @Description: 价格
	 * @author liujiecheng
	 */
	public static String getPrice(Map<String, String> map) {
		String key = "价格";
		String value = map.get(key);
		return value;
	}
	
	/**
	 * 
	 * @Description: 备注
	 * @author liujiecheng
	 */
	public static String getRemark(Map<String, String> map) {
		String key = "备注";
		String value = map.get(key);
		return value;
	}

	/**
	 * 
	 * @Description: 公司
	 * @author liujiecheng
	 */
	public static String getCompany(Map<String, String> map) {
		String key = "公司";
		String value = map.get(key);
		value = StringUtils.substringBefore(value, "[");
		value = StringUtils.trim(value);
		return value;
	}

	/**
	 * 
	 * @Description: 省份
	 * @author liujiecheng
	 */
	public static String getProvince(String html) {
		html = StringUtils.replaceEach(html, new String[]{"&nbsp;"," "}, new String[]{",",""});
		String provinceAndCityStr = StringUtils.substringBetween(html, "[", "]");
		String[] provinceAndCityArray = null;
		if (StringUtils.isNotBlank(provinceAndCityStr) ) {
			provinceAndCityArray = provinceAndCityStr.split(",");
		}
		
		String province = null;
		if (provinceAndCityArray != null) {
			province = StringUtils.trim(provinceAndCityArray[0]);
		}
		return province;
	}

	/**
	 * 
	 * @Description: 城市
	 * @author liujiecheng
	 */
	public static String getCity(String html) {
		html = StringUtils.replaceEach(html, new String[]{"&nbsp;"," "}, new String[]{",",""});
		String provinceAndCityStr = StringUtils.substringBetween(html, "[", "]");
		String[] provinceAndCityArray = null;
		
		if (StringUtils.isNotBlank(provinceAndCityStr)) {
			provinceAndCityArray = provinceAndCityStr.split(",");
		}
		
		String city = null;
		if (provinceAndCityArray != null && provinceAndCityArray.length == 2) {
			city = StringUtils.trim(provinceAndCityArray[1]);
		}
		return city;
	}

	/**
	 * 
	 * @Description: 联系人
	 * @author liujiecheng
	 */
	public static String getContacts(Map<String, String> map) {
		String key = "联系人";
		String value = map.get(key);
		return value;
	}

	/**
	 * 
	 * @Description: 电话
	 * @author liujiecheng
	 */
	public static String getTel(Map<String, String> map) {
		String key = "电话";
		String value = map.get(key);
		return value;
	}

	/**
	 * 
	 * @Description: 传真
	 * @author liujiecheng
	 */
	public static String getFax(Map<String, String> map) {
		String key = "传真";
		String value = map.get(key);
		return value;
	}

	/**
	 * 
	 * @Description: 电子邮箱
	 * @author liujiecheng
	 */
	public static String getEmail(Map<String, String> map) {
		String key = "E-mail";
		String value = map.get(key);
		return value;
	}
	
	/**
	 * 
	 * @Description: 网址
	 * @author liujiecheng
	 */
	public static String getWebsite(Map<String, String> map) {
		String key = "网址";
		String value = map.get(key);
		return value;
	}
	
	/**
	 * 
	 * @Description: 地址
	 * @author liujiecheng
	 */
	public static String getAddress(Map<String, String> map) {
		String key = "地址";
		String value = map.get(key);
		return value;
	}
	
	/**
	 * 
	 * @Description: 邮编
	 * @author liujiecheng
	 */
	public static String getZipcode(Map<String, String> map) {
		String key = "邮编";
		String value = map.get(key);
		return value;
	}
}
