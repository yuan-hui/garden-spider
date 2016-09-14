package com.mlh.spider.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mlh.buss.content.bean.ContentInfo;
import com.mlh.enums.Confirm;
import com.mlh.model.Content;
import com.mlh.model.PageDetail;
import com.mlh.spider.pageprocessor.UpdateReleaseTime;

import us.codecraft.webmagic.selector.Html;

public class Test {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		String path="70943.shtml";
		String filepath = "D:\\dowload\\miaomuzhan\\details\\70943.shtml";
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
		

		List<String> tableList = html.xpath("//tr[@class='table_back']/td/text()").all(); 
		
		String title = tableList.get(0);
			
		String midiaMeter = tableList.get(1);
		
		String height = tableList.get(2);
		// 冠幅(cm)
		String crown = tableList.get(3);
		// 地径(cm)
		String grounddiaMeter =tableList.get(4);
		// 价格
		String price = tableList.get(5);
		// 单位
		String unit =  tableList.get(6);
		 
		
		List<String> userInfor = html.xpath("//div[@class='infolist']").xpath("//span/text()").all();
		
		//公司
		String company = userInfor.get(1);
		
		String str = tableList.get(8);
		// 省份
		String province = str.substring(0, str.indexOf("--")).trim();
		
		String city =  str.substring(str.lastIndexOf("-")+1).trim();
		//联系人
		String contacts =userInfor.get(2);
		//电话
		String tel = userInfor.get(4);
		// 电子邮箱
		String email =userInfor.get(7);
		// 地址
		String address =userInfor.get(8);

		// 来源内容ID
		String cid = StringUtils.substringBefore(path, ".");
		
		//备注
		String remark = tableList.get(9);
		
		//组装数据
		ContentInfo info = new ContentInfo();
		info.setCid(cid);
		info.setTitle(title);
		info.setMidiameter(midiaMeter);
		info.setHeight(height);
		info.setCrown(crown);
		info.setGrounddiameter(grounddiaMeter);
		info.setUnit(unit);
		info.setRemark(remark);
		info.setPrice(price);
		info.setCompany(company);
		info.setProvince(province);
		info.setCity(city);
		info.setContacts(contacts);
		info.setTel(tel);
		info.setEmail(email);
		info.setAddress(address);
		
		//save
		
		if(StringUtils.isNoneBlank(title)){
			boolean save = Content.dao.save(info, cid, "http://www.miaomuzhan.com/plus/list.php?tid=224&PageNo=1", "miaomuzhan_miaomujiage");
			if(save){
				System.out.println("内容保存成功"+ title);
				UpdateReleaseTime.main(new String [] {path,cid});
				System.out.println("保存苗木第一站发布时间成功");
				int row = PageDetail.dao.updateParserById(Confirm.yes.toString(), cid);
				System.out.println("详情页更新为已解析：" + row);
			}else{
				System.out.println("内容保存失败：" + title + "->" );
				
			}
		}else{
			System.out.println("详情页存在异常，请查阅源文件：" + path);
		}
		
		
		
		
		
	

	}

}
