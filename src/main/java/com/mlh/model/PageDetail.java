package com.mlh.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.mlh.enums.Confirm;
import com.mlh.model.base.BasePageDetail;
import com.mlh.utils.common.StringKit;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class PageDetail extends BasePageDetail<PageDetail> {
	public static final PageDetail dao = new PageDetail();

	/**
	 * 
	 * @Description: 批量保存
	 * @author liujiecheng
	 */
	public int[] saveAll(List<PageDetail> list) {
		return Db.batchSave(list, list.size());
	}

	/**
	 * 
	 * @Description: 批量保存URL
	 * @author liujiecheng
	 */
	public int[] saveDetails(List<String> urls, String code, String source, int pageno) {
		List<PageDetail> details = new LinkedList<PageDetail>();

		if (urls != null && urls.size() > 0) {
			for (String url : urls) {
				PageDetail detail = new PageDetail();
				detail.setId(StringKit.getUUID());
				detail.setUrl(url);
				detail.setCode(code);
				detail.setPath(null);
				detail.setDownload(Confirm.no.toString());
				detail.setParser(Confirm.no.toString());
				detail.setCreateTime(new Date());
				detail.setUpdateTime(null);
				detail.setSource(source);
				detail.setPageno(pageno);

				details.add(detail);
			}
		}
		return saveAll(details);
	}

	/**
	 * 
	 * @Description: 根据业务编码及状态查询数据
	 * @author liujiecheng
	 */
	public List<PageDetail> findByCodeAndDownload(String code, String download) {
		return dao.find("select * from t_page_detail where code =? and download = ? order by pageno asc", code, download);
	}
	
	/**
	 * 
	 * @Description: 根据业务编码及状态查询数据
	 * @author liujiecheng
	 */
	public List<PageDetail> findByCodeAndDownloadAndDownimg(String code, String download, String downimg) {
		return dao.find("select * from t_page_detail where code =? and download = ? and downimg = ? order by pageno asc", code, download, downimg);
	}
	
	/**
	 * 
	 * @Description: 根据图片下载情况查询详情页
	 * @author liujiecheng
	 */
	public List<PageDetail> findByCodeAndDownimg(String code, String downimg) {
		return dao.find("select * from t_page_detail where code =? and downimg = ? order by pageno asc", code, downimg);
	}
	
	/**
	 * 
	 * @Description: 查询可用于解析的详情页(页面已下载、图片已下载)
	 * @author liujiecheng
	 */
	public List<PageDetail> findByCodeAndParser(String code, String parser) {
		return dao.find("select * from t_page_detail where download = 'yes' and code =? and parser = ? order by pageno asc", code, parser);
	}

	/**
	 * 
	 * @Description: 更新下载的状态
	 * @author liujiecheng
	 */
	public void updateDownloadById(String download, String id) {
		Db.update("update t_page_detail set download = ?, updateTime = now() where id = ?", download, id);
	}
	
	/**
	 * 
	 * @Description: 更新路径及下载状态
	 * @author liujiecheng
	 */
	public void updatePathAndDownloadById(String path, String download, String id) {
		Db.update("update t_page_detail set path = ?, download = ?, updateTime = now() where id = ?", path, download, id);
	}
	
	/**
	 * 
	 * @Description: 更新当前详情页的全部图片下载状态
	 * @author liujiecheng
	 */
	public void updateDownimgById(String downimg, String id) {
		Db.update("update t_page_detail set downimg = ?, updateTime = now() where id = ?", downimg, id);
	}
	
	/**
	 * 
	 * @Description: 更新当前详情页的全部图片上传状态
	 * @author liujiecheng
	 */
	public void updateUpimgById(String upimg, String id) {
		Db.update("update t_page_detail set upimg = ?, updateTime = now() where id = ?", upimg, id);
	}

	/**
	 * 
	 * @Description: 检查当前详情页的图片下载状态
	 * @author liujiecheng
	 */
	public void checkDownimg(String id) {
		
		List<ImagesList> images = ImagesList.dao.findByDownloadAndDetailsId(Confirm.no.toString(), id);
		
		//说明已全部下载
		if (images == null || images.size() == 0) {
			updateDownimgById(Confirm.yes.toString(), id);
		}
	}

	/**
	 * 
	 * @Description: 检查当前详情页的图片上传状态
	 * @author liujiecheng
	 */
	public void checkUpimg(String id) {
		
		List<ImagesList> images = ImagesList.dao.findByUploadAndDetailsId(Confirm.no.toString(), id);
		
		//说明已全部下载
		if (images == null || images.size() == 0) {
			updateUpimgById(Confirm.yes.toString(), id);
		}
	}
	
	/**
	 * 
	 * @Description: 更新下载的状态
	 * @author liujiecheng
	 */
	public int updateParserById(String parser, String id) {
		return Db.update("update t_page_detail set parser = ?, updateTime = now() where id = ?", parser, id);
	}
}
