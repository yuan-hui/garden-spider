package com.mlh.spider.test;

import com.mlh.utils.common.StringKit;

public class TestString {
	public static void main(String[] args) {
		String str = "50000-120000元";
		
		str = StringKit.strReturnNumber(str);
		System.out.println(str);
		
	}
}
