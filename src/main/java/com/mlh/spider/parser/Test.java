package com.mlh.spider.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mlh.buss.content.bean.ContentInfo;
import com.mlh.enums.Confirm;
import com.mlh.model.Content;
import com.mlh.model.PageDetail;
import com.mlh.spider.pageprocessor.UpdateReleaseTime;
import com.mlh.spider.util.DetailsHtmlUtil;

import us.codecraft.webmagic.selector.Html;

public class Test {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		String path = "detail-55571.html";
		String filepath = "D:\\dowload\\green321\\details\\detail-55571.html";
		System.out.println("解析文件：" + filepath);
		File file = new File(filepath);
		String text = null;
		try {
			text = FileUtils.readFileToString(file, "gbk");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Document htmldoc = Jsoup.parse(text);
		Html html = new Html(htmldoc);

		String title = html.xpath("//h1/text()").get();

		List<String> detailcontentList = html.xpath("//div[@id='detailcontent']/ul/li").all();

		Map<String, String> detailcontentMap = DetailsHtmlUtil.changeToAttrMap(detailcontentList);

		String midiaMeter = detailcontentMap.get("米径");
		String height = detailcontentMap.get("高度");
		String crown = detailcontentMap.get("冠幅");
		String grounddiaMeter = detailcontentMap.get("地径");
		String price = detailcontentMap.get("价格").replace("元/棵", "");
		String unit = "颗";
		String remark = detailcontentMap.get("备注");
		List<String> contactusList = html.xpath("//div[@class='linkmode_row']").all();
		Map<String, String> contactusMap = DetailsHtmlUtil.changeToAttrMap(contactusList);

		String strInfor = contactusMap.get("公司");

		// 公司
		String company = strInfor.substring(0, strInfor.indexOf("["));

		String addrsInfor = html.xpath("//span[@class='zt1']/text()").get();
		addrsInfor = addrsInfor.substring(addrsInfor.indexOf("[") + 1, addrsInfor.lastIndexOf("]")).trim();
		// 省份
		String province = addrsInfor.substring(0, addrsInfor.indexOf(" "));

		String city = addrsInfor.substring(addrsInfor.indexOf(" ") + 1);
		// 联系人
		String contacts = contactusMap.get("联系人").substring(0, contactusMap.get("联系人").indexOf("Q"));

		// 电话
		String tel = contactusMap.get("电话").substring(0, contactusMap.get("电话").indexOf("（"));

		// 电子邮箱
		String email = contactusMap.get("电邮");
		String address = contactusMap.get("地址");

		String fax = contactusMap.get("传真");

		String website = contactusMap.get("网址");
		
		String zipcode = contactusMap.get("邮编");

		String cid = StringUtils.substringBefore(path, ".");
		
		String temStr = html.xpath("//div[@id='detailtime']/span[@class='left']/text()").get();
		//发布时间
		String releasetimeHtml = temStr.substring(temStr.indexOf("：")+1, temStr.indexOf("下")).trim();
//		String releasetimeHtml = html.xpath("//div[@id='detailtime']").get();
		
//		Date releasetime = DetailsHtmlUtil.getReleasetime(releasetimeHtml);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		if (StringUtils.isNotBlank(releasetimeHtml)) {
			try {
				date = formatter.parse(releasetimeHtml);
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		System.out.println(contactusMap.toString());

		System.out.println(date);
		/*
		 * 
		 * // 来源内容ID String cid = StringUtils.substringBefore(path, ".");
		 * 
		 * //备注 String remark = tableList.get(9);
		 * 
		 * //组装数据 ContentInfo info = new ContentInfo(); info.setCid(cid);
		 * info.setTitle(title); info.setMidiameter(midiaMeter);
		 * info.setHeight(height); info.setCrown(crown);
		 * info.setGrounddiameter(grounddiaMeter); info.setUnit(unit);
		 * info.setRemark(remark); info.setPrice(price);
		 * info.setCompany(company); info.setProvince(province);
		 * info.setCity(city); info.setContacts(contacts); info.setTel(tel);
		 * info.setEmail(email); info.setAddress(address);
		 * 
		 * //save
		 * 
		 * if(StringUtils.isNoneBlank(title)){ boolean save =
		 * Content.dao.save(info, cid,
		 * "http://www.miaomuzhan.com/plus/list.php?tid=224&PageNo=1",
		 * "miaomuzhan_miaomujiage"); if(save){ System.out.println("内容保存成功"+
		 * title); UpdateReleaseTime.main(new String [] {path,cid});
		 * System.out.println("保存苗木第一站发布时间成功"); int row =
		 * PageDetail.dao.updateParserById(Confirm.yes.toString(), cid);
		 * System.out.println("详情页更新为已解析：" + row); }else{
		 * System.out.println("内容保存失败：" + title + "->" );
		 * 
		 * } }else{ System.out.println("详情页存在异常，请查阅源文件：" + path); }
		 * 
		 * 
		 */

	}

}
