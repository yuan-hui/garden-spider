package com.mlh.spider.test;

import com.mlh.utils.common.StringKit;

public class Test {
	public static void main(String[] args) {
		System.out.println(StringKit.getUUID());
		
		String url = "http://zj.yuanlin.com/mmbj_1.html?Class1ID=1&amp;Class2ID=6&amp;q=%ba%ec%d2%b6%ca%af%e9%aa";
		
		String cid = url.substring(url.lastIndexOf("=")+1);
		System.out.println(cid);
		
	}

	

}
