package com.mlh.spider.parser;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.mlh.buss.content.bean.ContentInfo;
import com.mlh.enums.Confirm;
import com.mlh.model.Content;
import com.mlh.model.PageDetail;
import com.mlh.spider.downloader.ImageHttpClientDownloader;
import com.mlh.spider.pageprocessor.UpdateReleaseTime;
import com.mlh.spider.util.DetailsHtmlUtil;
import com.mlh.utils.common.DateUtils;
import com.mlh.utils.common.StringKit;

import us.codecraft.webmagic.selector.Html;
/**
 * 解析苗木第一站详情页面
 * @author sjl
 *
 */
public class MiaoMuDetailsLocalHtmlParser {
	private final static Log logger = Log.getLog(MiaoMuDetailsLocalHtmlParser.class);
	
	private final static int SLEEP_TIME = 1;
	
	/**
	 *解析本地HTML文件 
	 * 
	 */
	private static void process(PageDetail detail) {
		String code = detail.getCode();
		String detailId = detail.getId();
		String source = detail.getUrl();
		String path = detail.getPath();
		String filepath = PropKit.get("details.miaomu.path") + path;
		try {
			System.out.println("解析文件：" + filepath);
			File file = new File(filepath);
			String text;
			text = FileUtils.readFileToString(file, "GBK");
			Document htmldoc = Jsoup.parse(text);
			Html html = new Html(htmldoc);
			// 详情内容
			List<String> infomain = html.xpath("//div[@class='info_main']/table/tbody/tr/td[3]/ul/li").all();
			Map<String, String> detailcontentMap = DetailsHtmlUtil.changeToAttrMap(infomain);

			// 标题
			String title = html.xpath("//div[@class='info_main']/table/tbody/tr/td[3]/h1/strong/text()").get();

			// 米径(cm)
			String midiaMeter = DetailsHtmlUtil.getMidiameter(detailcontentMap);

			// 高度(cm)
			String height = DetailsHtmlUtil.getHeight(detailcontentMap);

			// 冠幅(cm)
			String crown = DetailsHtmlUtil.getCrown(detailcontentMap);

			// 地径(cm)
			String grounddiaMeter = DetailsHtmlUtil.getGrounddiameter(detailcontentMap);

			// 发布时间
			String releasetimeHtml = detailcontentMap.get("发布日期");
			Date releasetime = DateUtils.StringToDateyyyy_MM_dd(releasetimeHtml);
			
			String tmpStr = detailcontentMap.get("价格");
			String price  = StringKit.strReturnNumber(tmpStr);
			String unit = StringKit.strReturnStr(tmpStr);
			
			String itemid = StringUtils.substring(path, path.lastIndexOf("_")+1, path.lastIndexOf("."));
			
			String phpUrl = "http://www.miaomu.net/api/task.js.php?moduleid=23&html=show&itemid="+itemid+"&refresh="+new Random().nextDouble()+".js";
			String savePath =  PropKit.get("details.miaomuPhp.path");
			String referer = source;
			boolean downloadImg = ImageHttpClientDownloader.downloadFile(new String []{phpUrl},savePath,referer);
			String company = null;
			String tempAddress = null;
			String province = null;
			String city = null;
			String address = null;
			String contacts = null;
			
			if(downloadImg){
				String filePath = savePath+StringUtils.substring(referer, referer.lastIndexOf("/") + 1,referer.lastIndexOf("."))+".php";
				File phpFile = new File(filePath);
				
				Document htmlPhp = Jsoup.parse(FileUtils.readFileToString(phpFile, "gbk"));
				Html php = new Html(htmlPhp);
				List <String> conentstList = php.xpath("//body/ul/li").all();
				Map<String, String> contactusMap = DetailsHtmlUtil.changeToAttrMap(conentstList);
				 company = php.xpath("//body/ul/li[1]/a/text()").get();
				 tempAddress = php.xpath("//body/ul/li[7]/text()").get();
				 province = StringUtils.substringBefore(tempAddress, "-");
				 city = StringUtils.substringAfter(tempAddress, "-");
				 address = contactusMap.get("地址");
				 contacts = contactusMap.get("联系人");
				 
			}
			

			// 来源内容ID
			String cid = StringUtils.substringBefore(path, ".");
			
			
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
			info.setPrice(price);
			info.setCompany(company);
			info.setProvince(province);
			info.setCity(city);
			info.setContacts(contacts);
			info.setAddress(address);
			info.setDetailId(detailId);
			info.setSource(source);
			info.setReleasetime(releasetime);
			//save
			
			if(StringUtils.isNoneBlank(title)){
				boolean save = Content.dao.save(info, detailId, source, code);
				if(save){
					System.out.println("内容保存成功"+ title);
					UpdateReleaseTime.main(new String [] {path,detailId});
					System.out.println("保存苗木第一站发布时间成功");
					int row = PageDetail.dao.updateParserById(Confirm.yes.toString(), detailId);
					System.out.println("详情页更新为已解析：" + row);
				}else{
					System.out.println("内容保存失败：" + title + "->" );
					
				}
			}else{
				System.out.println("详情页存在异常，请查阅源文件：" + path);
			}
			
			


		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error("message", e1);
		}

		System.out.println("程序休眠：" + SLEEP_TIME + "秒.");
		try {
			Thread.sleep(SLEEP_TIME * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("-----------------------------------------------------------------");

		
	}
	
	
	public static void main(String[] args) {

		String code = args[0];

		System.out.println("开始查询需要解析的详情页...");

		List<PageDetail> details = PageDetail.dao.findByCodeAndParser(code, Confirm.no.toString());

		if (details != null && details.size() > 0) {
			for (PageDetail detail : details) {
				process(detail);
			}
		} else {
			System.out.println("没有需要解析的详情页：" + details.size());
		}
	}



	

}
