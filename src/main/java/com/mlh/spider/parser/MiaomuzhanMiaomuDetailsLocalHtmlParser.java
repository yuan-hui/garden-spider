package com.mlh.spider.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.mlh.buss.content.bean.ContentInfo;
import com.mlh.common.WebMagicFunction;
import com.mlh.enums.Confirm;
import com.mlh.model.Content;
import com.mlh.model.PageDetail;
import com.mlh.spider.pageprocessor.UpdateReleaseTime;

import us.codecraft.webmagic.selector.Html;
/**
 * 解析苗木第一站详情页面
 * @author sjl
 *
 */
public class MiaomuzhanMiaomuDetailsLocalHtmlParser {
	private final static Log logger = Log.getLog(MiaomuzhanMiaomuDetailsLocalHtmlParser.class);
	
	
	/**
	 *解析本地HTML文件 
	 * 
	 */
	private static void process(PageDetail detail) {
		String code = detail.getCode();
		String detailId = detail.getId();
		String source = detail.getUrl();
		String path = detail.getPath();
		String filepath = PropKit.get("details.miaomuzhan.path") + path;
		try {
			logger.error("解析文件：" + filepath);
			
			File file = new File(filepath);
			String text;
			text = FileUtils.readFileToString(file, "GBK");

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
			
			String company = null;
			String contacts  =null;
			String email  =null;
			String tel  =null;
			String address  =null;
			if(userInfor.size()>0){
			//公司
			 company = userInfor.get(1);
			
			//联系人
			 contacts =userInfor.get(2);
			//电话
			 tel = userInfor.get(4);
			// 电子邮箱
			 email =userInfor.get(6);
			// 地址
			 address =userInfor.get(7);
			}
			
			
			String str = tableList.get(8);
			// 省份
			String province =str.indexOf("--")>-1? str.substring(0, str.indexOf("--")).trim():str;
			
			String city =  str.indexOf("--")>-1? str.substring(str.lastIndexOf("-")+1).trim() :str;
		

			// 来源内容ID
			String cid = StringUtils.substringBefore(path, ".");
			
			//备注
			String remark = tableList.get(9);
			
			//组装数据
			ContentInfo info = new ContentInfo();
			info.setCid(cid);
			info.setCode(code);
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
			info.setDetailId(detailId);
			info.setSource(source);
			//save
			
			if(StringUtils.isNoneBlank(title)){
				boolean save = Content.dao.save(info, detailId, source, code);
				if(save){
					logger.error("内容保存成功>>>>>>>>>>"+ title);
					UpdateReleaseTime.main(new String [] {path,detailId});
					logger.error("保存苗木第一站发布时间成功");
					int row = PageDetail.dao.updateParserById(Confirm.yes.toString(), detailId);
					logger.error("详情页更新为已解析：" + row);
				}else{
					logger.error("内容保存失败：" + title + "->" );
					
				}
			}else{
				logger.error("详情页存在异常，请查阅源文件：" + path);
			}

		} catch (IOException e1) {
			logger.error("读取文件错误", e1);
			PageDetail.dao.deleteByDetailId(detailId);
			logger.error("删除该记录》》》"+detailId);
		} 

		logger.error("-----------------------------------------------------------------");

		
	}
	
	
	public static void main(String[] args) {

		String code = args[0];

		logger.error("开始查询需要解析的详情页...");

		List<PageDetail> details = PageDetail.dao.findByCodeAndParser(code, Confirm.no.toString());

		if (details != null && details.size() > 0) {
			for (PageDetail detail : details) {
				process(detail);
			}
		} else {
			logger.error("没有需要解析的详情页：" + details.size());
			try {
				WebMagicFunction.treadSleep();
			} catch (InterruptedException e) {
				logger.error("估计是线程错误",e);
			}
		}
	}



	

}
