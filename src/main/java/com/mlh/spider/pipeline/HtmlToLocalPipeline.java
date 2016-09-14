package com.mlh.spider.pipeline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.mlh.enums.Confirm;
import com.mlh.model.PageDetail;
import com.mlh.spider.handle.PageHandler;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

/**
 * 
 * @Description: HTML本地化 Pipeline
 * @author liujiecheng
 */
public class HtmlToLocalPipeline extends FilePersistentBase implements Pipeline {

	private final static Log logger = Log.getLog(PageHandler.class);
	
	//根据网页编码设置编码格式   *
	private String encoding ;

	public HtmlToLocalPipeline(String path,String charSet) throws UnsupportedEncodingException, FileNotFoundException {
		setPath(path);
		
		this.encoding=charSet;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		String id = resultItems.get("id");
		String result = resultItems.get("result");
		String url = resultItems.get("url");
		String filename = StringUtils.substringAfterLast(url, "/");

		try {
			//完整路径
			String filepath = this.path + filename;
			
			//保存在本地文件
			FileUtils.write(new File(filepath), result, this.encoding);
			System.out.println("文件保存成功：" + id + "->" + filepath);

			//更新详情页为已下载
			PageDetail.dao.updatePathAndDownloadById(filename, Confirm.yes.toString(), id);
			
			
			System.out.println("更新为已下载：" + Confirm.yes.toString());
		} catch (IOException e) {
			System.out.println("文件保存失败：" + id + "->" + filename);
			e.printStackTrace();
			logger.error("filename：" + id + "->" + filename);
			logger.error("message", e);
		}
	}

		
}
