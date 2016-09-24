package com.mlh.spider.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Test {
	public static void main(String[] args) {
//		System.out.println(StringKit.getUUID());
		
		String str = "12.3元/课";
		if(StringUtils.isNotBlank(str)){
			Pattern pattern = Pattern.compile("[\\d\\.]");
			Matcher matcher = pattern.matcher(str);
			str = matcher.replaceAll("");
			
		}
		
		System.out.println(str);
	}

	

}
