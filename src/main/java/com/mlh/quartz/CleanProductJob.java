package com.mlh.quartz;

import java.text.ParseException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mlh.spider.handle.ProductHandler;



/**
 * @Description: 定时清洗数据并向mysql导入数据
 */
public class CleanProductJob implements Job {
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
       System.out.println("执行清洗");
       try {
		ProductHandler.main(new String[]{});
       } catch (ParseException e) {
		e.printStackTrace();
       }
	}
}
