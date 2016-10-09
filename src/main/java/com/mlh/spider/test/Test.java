package com.mlh.spider.test;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mlh.common.AppRun;
import com.mlh.common.WebMagicFunction;
import com.mlh.common.WebMagicParams;
import com.mlh.spider.util.DetailsHtmlUtil;
import com.mlh.utils.common.DateUtils;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.selector.Html;

public class Test extends WebMagicParams {
	public static void main(String[] args) throws Exception {

		 File file = new File("C:\\Users\\Administrator\\Desktop\\123.html");
		 String text;
		 text = FileUtils.readFileToString(file, "gbk");
		 Document htmldoc = Jsoup.parse(text);
			Html html = new Html(htmldoc);
			// 标题
			String title = html.xpath("//div[@class='info_main']/h1/text()").get().replace("价格", "");

			// 发布时间
			String releaseTimeStr = html.xpath("//div[@class='info_main']/ul/li[1]/text()").get().replace("：", "");
			Date releaseTime = DateUtils.StringToDateyyyy_MM_dd(releaseTimeStr);

			// 拿到苗木规格数据源 解析
			String detailcontent = html.xpath("//div[@class='info_main']/ul/li[3]/a[2]/text()").get().trim();
			String[] tempArry = detailcontent.split("cm");
			Map<String, String> detailcontentMap = new HashMap<String, String>();
			String arryName = null;
			String arryVlue = null;

			if (StringUtils.isNotBlank(detailcontent)) {
				for (int i = 0; i < tempArry.length; i++) {
					String temp = tempArry[i].trim();
					arryName = temp.substring(0, 2);
					arryVlue = temp.substring(2);
					detailcontentMap.put(arryName, arryVlue);
				}
			}
			String height = detailcontentMap.get("高度");
			String crown = detailcontentMap.get("冠幅");
			String grounddiaMeter = detailcontentMap.get("地径");
			String midiaMeter = detailcontentMap.get("米径");

			String str = html.xpath("//div[@class='info_main']/ul/li[3]/a[1]/text()").get();//
			String price = StringKit.strReturnNumber(str);
			String unit = StringKit.strReturnStr(str);
			String remark = html.xpath("//div[@id='content']/text()").get();

			List<String> usrInfo = html.xpath("//div[@id='contact']/ul/li").all();
			Map<String, String> usercontentListMap = DetailsHtmlUtil.changeToAttrMap(usrInfo);

		
	}

}
