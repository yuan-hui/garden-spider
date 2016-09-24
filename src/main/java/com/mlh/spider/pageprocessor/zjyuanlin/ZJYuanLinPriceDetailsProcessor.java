package com.mlh.spider.pageprocessor.zjyuanlin;

import java.util.ArrayList;
import java.util.List;

import com.mlh.enums.Confirm;
import com.mlh.model.PageDetail;
import com.mlh.model.PageList;

public class ZJYuanLinPriceDetailsProcessor {



	public static void main(String[] args) {
		String code = args[0];
		System.out.println("进入" + code + "列表页处理器...");

		List<PageList> pages = PageList.dao.findByCodeAndStatus(code, Confirm.no.toString());
		List<String>urls = new ArrayList<String>();
		int index = 1;
		for(PageList p :pages){
		
			urls.add(p.getUrl());
			 PageDetail.dao.saveDetails(urls, code, p.getUrl(), p.getPageno());
			
			System.out.println("保存浙江列表页面为详情页面");
			System.out.println("共"+pages.size()+"页，第："+index+"页"+p.getUrl());
			
			// 更新当前列表页为已处理,已分析出详情页
			PageList.dao.updateHandleById(Confirm.yes.toString(), p.getId());
			System.out.println("列表页状态更新完毕：" + Confirm.yes.toString());

			System.out.println("-----------------------------------------------------------------");
			index ++;
		}
		
		
	}

}
