package com.mlh.spider.handle;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mlh.common.AppRun;
import com.mlh.enums.BussCode;
import com.mlh.enums.Confirm;
import com.mlh.model.DownloaderConfig;
import com.mlh.model.PageList;
import com.mlh.spider.factory.PageListProcessorFactory;
import com.mlh.utils.common.StringKit;

/**
 * 
 * @Description: 整理出需要下载的详情页面
 * @author liujiecheng
 */
public class PageHandler {

	private final static String PLACEHOLDER = "{0}";

	public static void main(String[] args) {
		// 读取需要页面处理的下载配置(未处理过的)

		// ----
		AppRun.start();

		// ----(把地址统一转换为可爬取列表页地址)
		System.out.println("正在查询需要转换的配置...");
		List<DownloaderConfig> configs = DownloaderConfig.dao.findByStatus(Confirm.no.toString());
		
		if (configs != null && configs.size() > 0) {
			System.out.println("转换记录数：" + configs.size());

			System.out.println("开始转换为列表页...");
			List<PageList> pages = new LinkedList<PageList>();
			for (DownloaderConfig c : configs) {
				String _id = c.getId();
				
				//通用URL
				String _url = c.getUrl();
				
				//起始页
				int _startpage = c.getStartpage();
				
				//结束页
				int _endpage = c.getEndpage();

				//业务编码
				String _code = c.getCode();

				//所有的表面页
				for (int i = _startpage; i <= _endpage; i++) {
					PageList p = new PageList();
					p.setId(StringKit.getUUID());
					p.setUrl(StringUtils.replace(_url, PLACEHOLDER, String.valueOf(i)));
					p.setPageno(i);
					p.setCode(_code);
					p.setHandle(Confirm.no.toString());
					p.setCreateTime(new Date());

					pages.add(p);
				}

				System.out.println("共转换列表页：" + pages.size());

				// 保存当前业务所有的列表
				int[] rows = PageList.dao.saveAll(pages);
				System.out.println("共保存列表页：" + rows.length);

				//状态更新为已处理
				DownloaderConfig.dao.updateStatusById(Confirm.yes.toString(), _id);
				System.out.println("转换状态更新完毕！");

				System.out.println("-----------------------------------------------------------------");
			}
		} else {
			System.out.println("没有配置需要转换：" + configs.size());
		}

		// 查询需要提出详情页的所有列表页，并根据不同的业务使用不同的页面解析器
		BussCode[] codes = BussCode.values();

		for (BussCode c : codes) {
			String _code = c.getCode();

			PageListProcessorFactory factory = new PageListProcessorFactory();

			// 根据业务编码从列表页中解析出详情页的地址，并保存起来
			factory.produce(_code);
		}
	}

}
