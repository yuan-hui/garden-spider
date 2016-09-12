package com.mlh.autorun;

import org.apache.commons.lang.StringUtils;

import com.mlh.enums.Confirm;

public class Main {

	public static void main(String[] args) {
		System.out.println("test main...");
		boolean eq = StringUtils.equals("a", "d");
		System.out.println(eq);
		System.out.println("更新为已下载：" + Confirm.yes.toString());	}

}
