package com.mlh.spider.test;

import java.util.List;

import com.mlh.enums.Confirm;
import com.mlh.model.PageList;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Test {
	public static void main(String[] args) {
		System.out.println("hha");
	}

	public static void ListProcessor(String param, PageProcessor pp) {
		
			
			String code = param;
			System.out.println("进入" + code + "列表页处理器...");

			List<PageList> pages = PageList.dao.findByCodeAndStatus(code, Confirm.no.toString());
			System.out.println(pages.size());
		for (PageList p : pages) {
				// 列表页ID
				String id = p.getId();

				// 列表页URL
				String url = p.getUrl();

				// 第几页
				int pageno = p.getPageno();

				// 传递业务参数
				Request request = new Request(url).setPriority(0).putExtra("code", code).putExtra("id", id)
						.putExtra("pageno", pageno);

				// 启动当前路径的爬取
				Spider.create(pp).thread(1).addRequest(request).run();
			}

			// 传递业务参数
		
	}

}
