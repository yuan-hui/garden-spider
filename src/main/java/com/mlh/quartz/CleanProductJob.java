package com.mlh.quartz;

import java.text.ParseException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.log.Log;
import com.mlh.spider.handle.ProductHandler;



/**
 * @Description: 定时清洗数据并向mysql导入数据
 */
public class CleanProductJob implements Job {
	
	private final static Log logger = Log.getLog(CleanProductJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			System.out.println("执行清洗");
			ProductHandler.main(new String[]{});
		} catch (ParseException e) {
			logger.info("清洗失败！Exception：{}", e);
		}

	}
}
