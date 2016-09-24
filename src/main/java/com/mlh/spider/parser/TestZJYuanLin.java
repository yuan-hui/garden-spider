package com.mlh.spider.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mlh.spider.util.DetailsHtmlUtil;
import com.mlh.utils.common.DateUtils;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.selector.Html;

/**
 * 解析test
 * 
 * @author sjl
 *
 */
public class TestZJYuanLin {

	public static void main(String[] args) throws IOException {

		File file = new File("D:\\dowload\\zjyuanlin\\details\\mmbj_1.html");
		String text;
		text = FileUtils.readFileToString(file, "GBK");

		Document htmldoc = Jsoup.parse(text);
		Html html = new Html(htmldoc);

		List<String> detailList = html.xpath("//table[@id='tb_mmbj']/tbody/tr").all();
		
		for(int i =3 ;i<detailList.size()+2;i++){
			String xpath = "//table[@id='tb_mmbj']/tbody/tr["+(i-1)+"]";
			
			List<String > list =html.xpath(xpath+"/td/text()").all();
			
			String title = html.xpath(xpath+"/td[1]/a/text()").get();
			
			
			String midiaMeter = list.get(1);
			String height =  list.get(2);
			String crown = list.get(3);
			String grounddiaMeter = list.get(4);
			String price = list.get(5);
			String unit = list.get(6);
			String remark =  list.get(7);
			String releasetimeHtml = list.get(9);;
			Date releaseTime = DateUtils.StringToDateyyyy_MM_dd(releasetimeHtml);
			String province = StringUtils.substring(list.get(8), 0, 2);
			String city = StringUtils.substring(list.get(8), 2);
			String userInfo = html.xpath("//div[@id='mtd"+(i-3)+"']").get();
			String company = html.xpath("//td[@id='title"+(i-3)+"']/a/text()").get();
			String content = StringKit.getHtmlTextNoBlank(userInfo);
			String contacts = StringUtils.substring(content, content.indexOf("联系人:")+4, content.indexOf("手机:"));
			String tel =  StringUtils.substring(content, content.indexOf("手机:")+3, content.indexOf("备注"));
			
			
			String cid = html.xpath(xpath+"/td[1]").links().get();
			 cid = cid.substring(cid.lastIndexOf("=")+1);
		}
		
/*
		// 标题
		String title = html.xpath("//div[@class='detail']/dl/dt/h1/text()").get();

		// 米径
		String midiameter = StringKit.strReturnNumber(detailcontentMap.get("米径"));

		// 高度
		String height = StringKit.strReturnNumber(detailcontentMap.get("高度"));

		// 冠幅
		String crown = StringKit.strReturnNumber(detailcontentMap.get("冠幅"));

		// 地径
		String grounddiameter = StringKit.strReturnNumber(detailcontentMap.get("地径"));

		String price = StringKit.strReturnNumber(detailcontentMap.get("价格"));

		// 单位
		String unit = StringKit.strReturnStr(detailcontentMap.get("价格"));

		String remark = detailcontentMap.get("备注");

		// 发布时间
		String releasetimeHtml = detailcontentMap.get("日期");
		Date releasetime = DateUtils.StringToDateyyyy_MM_dd(releasetimeHtml);

		System.out.println(usercontentListMap);

		String tmpAddrss = detailcontentMap.get("地区");

		// 省份
		String province = StringUtils.substring(tmpAddrss, 0, 2);

		String city = StringUtils.substring(tmpAddrss, 2);

		// 公司
		String company = html.xpath("//dl[@class='user tab']/dt/a/text()").get();
		
		String contacts = usercontentListMap.get("联系人");
		
		String tel = usercontentListMap.get("手机");

		String address = usercontentListMap.get("地址");
*/
		
	}

}
