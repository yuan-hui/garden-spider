package com.mlh.spider.pageprocessor.product;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.mlh.enums.Confirm;
import com.mlh.model.PageDetail;
import com.mlh.spider.pipeline.HtmlToLocalPipeline;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

/**
 * 第一苗木 清洗处理器
 */
public class MiaomuzhanMiaomujiageCleanProcessor {
	
	private final static Log logger = Log.getLog(MiaomuzhanMiaomujiageCleanProcessor.class);

	public static void main(String[] args) {

		String code = args[0];
		System.out.println("进入"+code+"详情页下载器...");

		//查询所有没有下载的列表页
		List<PageDetail> details = PageDetail.dao.findByCodeAndDownload(code, Confirm.no.toString());
		
		if (details != null && details.size() > 0) {
			int index = 1;
			for (PageDetail d : details) {
				String id = d.getId();
				String url = d.getUrl();
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now = formatter.format(new Date());
				System.out.println(index + "、开始下载["+now+"]：" + id);

				//文件保存路径
				//String path = PathUtil.getRootPath() + "download" + "\\" + "details" + "\\";
				String path = PropKit.get("details.yuanlin365.path");
				
				//业务参数
				Request request = new Request(url).setPriority(0).putExtra("code", code).putExtra("id", id);
				
				//启动
				try {
					Spider.create(new LvsemiaomuQiaoguanmuCleanProcessor()).addPipeline(new HtmlToLocalPipeline(path,"UTF-8")).thread(1).addRequest(request).run();
					System.out.println("-----------------------------------------------------------------");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				index++;
			}
		} else {
			System.out.println("没有需要下载的详情页：" + details.size());
			
			System.out.println("程序休眠：" + 8 + "秒.");
			try {
				Thread.sleep(8 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("-----------------------------------------------------------------");
		}
	}
}
