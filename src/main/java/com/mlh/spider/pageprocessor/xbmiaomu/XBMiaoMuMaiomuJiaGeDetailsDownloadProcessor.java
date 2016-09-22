package com.mlh.spider.pageprocessor.xbmiaomu;

import com.mlh.common.WebMagicFunction;
import com.mlh.common.WebMagicParams;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class XBMiaoMuMaiomuJiaGeDetailsDownloadProcessor extends WebMagicParams implements PageProcessor {

	
	
	@Override
	public Site getSite() {
		return null;
	}

	@Override
	public void process(Page page) {
		String id = page.getRequest().getExtra("id").toString();
		String code = page.getRequest().getExtra("code").toString();
		page.putField("id", id);
		page.putField("code", code);
		page.putField("result", page.getHtml().get());
		page.putField("url", page.getUrl().get());
		
	}

	public static void main(String[] args) {
		WebMagicFunction.DetailDownload(args[0], new XBMiaoMuMaiomuJiaGeDetailsDownloadProcessor(), XBMIAOMU);
	}

}
