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
public class TestYuanLin {

	public static void main(String[] args) throws IOException {

		File file = new File("D:\\dowload\\yuanlin\\details\\detail_1132248.html");
		String text;
		text = FileUtils.readFileToString(file, "GBK");

		Document htmldoc = Jsoup.parse(text);
		Html html = new Html(htmldoc);

		// 详情内容
		List<String> detailContent = html.xpath("//div[@class='detail']/dl/dd").all();
		Map<String, String> detailcontentMap = DetailsHtmlUtil.changeToAttrMap(detailContent);

		// userInfo
		List<String> userIfo = html.xpath("//dl[@class='user tab']/dd").all();

		Map<String, String> usercontentListMap = DetailsHtmlUtil.changeToAttrMap(userIfo);

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

		System.out.println(tel);
		System.out.println(address);
	}

}
