package com.mlh.spider.test;

import com.mlh.common.AppRun;
import com.mlh.model.PageDetail;

public class TestString {
	public static void main(String[] args) {
		AppRun.start();
		PageDetail.dao.deleteByDetailId("006b7b8470774b24bd91a4d146317124");
		
	}
}
