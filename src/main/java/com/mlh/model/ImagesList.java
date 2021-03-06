package com.mlh.model;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.mlh.enums.Confirm;
import com.mlh.model.base.BaseImagesList;
import com.mlh.utils.common.StringKit;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class ImagesList extends BaseImagesList<ImagesList> {
	public static final ImagesList dao = new ImagesList();
	
	/**
	 * 
	 * @Description: 批量保存
	 * @author liujiecheng
	 */
	public int[] saveAll(List<ImagesList> list) {
		return Db.batchSave(list, list.size());
	}
	
	/**
	 * 
	 * @Description: 批量保存URL
	 * @author liujiecheng
	 */
	public int[] saveImages(List<String> urls, String code, String detailId, String source) {
		List<ImagesList> images = new LinkedList<ImagesList>();

		if (urls != null && urls.size() > 0) {
			for (String url : urls) {
				//去小图
				url = StringUtils.replace(url, "-150x150", "");
				
				ImagesList image = new ImagesList();
				image.setId(StringKit.getUUID());
				image.setUrl(url);
				image.setPath(null);
				image.setDownload(Confirm.no.toString());
				image.setUpload(Confirm.no.toString());
				image.setFileKey(null);
				image.setCode(code);
				image.setDetailId(detailId);
				image.setSource(source);
				image.setCreateTime(new Date());
				image.setUpdateTime(null);
				
				images.add(image);
			}
		}
		return saveAll(images);
	}

	/**
	 * 
	 * @Description: 按下载状态查询图片
	 * @author liujiecheng
	 */
	public List<ImagesList> findByDownload(String download) {
		return dao.find("select * from t_images_list where download =? order by createTime asc", download);
	}
	
	/**
	 * 
	 * @Description: 按下载状态查询图片
	 * @author liujiecheng
	 */
	public List<ImagesList> findByDownloadAndDetailsId(String download, String detailId) {
		return dao.find("select * from t_images_list where download =? and detailId = ? order by createTime asc", download, detailId);
	}
	
	/**
	 * 
	 * @Description: 按上传状态查询图片
	 * @author liujiecheng
	 */
	public List<ImagesList> findByUploadAndDetailsId(String upload, String detailId) {
		return dao.find("select * from t_images_list where upload =? and detailId = ? order by createTime asc", upload, detailId);
	}
	
	/**
	 * 
	 * @Description: 取得详情页相关的图片
	 * @author liujiecheng
	 */
	public List<ImagesList> findImagesByDetailId(String download, String upload, String detailId) {
		return dao.find("select * from t_images_list where download = ? and upload =? and detailId = ? order by createTime asc", download, upload, detailId);
	}
	
	/**
	 * 
	 * @Description: 图片源地址与OSSKEY对应
	 * @author liujiecheng
	 */
	public Map<String, String> getImagesMapByDetailId(String download, String upload, String detailId) {
		Map<String, String> map = new HashMap<String, String>();
		
		List<ImagesList> list = findImagesByDetailId(download, upload, detailId);
		for (ImagesList image : list) {
			map.put(image.getUrl(), image.getFileKey());
		}
		return map;
	}
	
	
	
	/**
	 * 
	 * @Description: 按上传状态查询图片
	 * @author liujiecheng
	 */
	public List<ImagesList> findByDownloadAndUpload(String download, String upload) {
		return dao.find("select * from t_images_list where download =? and upload =? order by createTime asc", download, upload);
	}
	
	/**
	 * 
	 * @Description: 根据详情页ID按上传状态查询图片
	 * @author liujiecheng
	 */
	public List<ImagesList> findByDownloadAndUploadAndDetailId(String download, String upload, String detailId) {
		return dao.find("select * from t_images_list where download =? and upload =? and detailId = ? order by createTime asc", download, upload, detailId);
	}
	/**
	 * 
	 * @Description: 更新下载状态
	 * @author liujiecheng
	 */
	public void updateDownloadById( String download, String id) {
		Db.update("update t_images_list set download = ?, updateTime = now() where id = ?", download, id);
	}
	
	/**
	 * 
	 * @Description: 更新下载状态
	 * @author liujiecheng
	 */
	public void updateFileKeyAndUploadById(String fileKey, String upload, String id) {
		Db.update("update t_images_list set fileKey = ?, upload = ?, updateTime = now() where id = ?",fileKey,  upload, id);
	}
	
	
	/**
	 * 
	 * @Description: 更新下载路径及状态
	 * @author liujiecheng
	 */
	public void updatePathAndDownloadById(String path, String download, String id) {
		Db.update("update t_images_list set path = ?, download = ?, updateTime = now() where id = ?",path, download, id);
	}
}