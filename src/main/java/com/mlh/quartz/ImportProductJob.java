package com.mlh.quartz;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.ext.plugin.mongo.MongoQuery;
import com.jfinal.log.Log;
import com.mlh.common.AppRun;
import com.mlh.model.Product;
import com.mlh.spider.handle.ProductHandler;
import com.mlh.spider.pageprocessor.product.ProductProcessor;



/**
 * @Description: 定时导入数据到mongodb和清洗数据(price_product)
 */
public class ImportProductJob extends Thread implements Job {
	
	private final static Log logger = Log.getLog(ImportProductJob.class);
	
	//每12个小时重新启动
	private  static long sleepTime = 12*60*60*1000l;	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ImportProductJob importProductJob = new ImportProductJob();
		importProductJob.start();
	}
	
	public static void main(String[] args) {
		AppRun.start();
		ImportProductJob importProductJob = new ImportProductJob();
		importProductJob.start();
	}
	
	public void run() {
		 int num = 1;
		 while(true) {
			 logger.debug("定时执行次数："+num);
			 System.out.println("定时执行次数："+num);
			 num++;
			 logger.debug("-----------执行mysql同步mongodb------------");
			 System.out.println("-----------执行mysql同步mongodb------------");
				Product product = new Product();
				MongoQuery query=new MongoQuery();
				int count = product.count();
				int synchrodata = 0;
				logger.debug("-----------需要同步数据："+count+"------------");
				System.out.println("-----------需要同步数据："+count+"------------");
				List<Product> updataProduct = new LinkedList<Product>();
				int degree = count>1000?(count/1000)+1:1;
				for (int i=degree,j=0;i>=j;j++) {
					int strat = j*1000;
					int end = 1000;
					query.use("price_product");
					List<Product> storage = product.findByStatus(strat,end);
					for (Product product2 : storage) {
						query.add(new MongoQuery().set("id", product2.getId())
						.set("productName",product2.getProductName()).set("breedName",product2.getBreedName())
						.set("area",product2.getArea()).set("areaNo",product2.getAreaNo())
						.set("op",product2.getOp()).set("miDiameterMax",product2.getMiDiameterMax())
						.set("miDiameterMin",product2.getMiDiameterMin()).set("totalPrice",product2.getTotalPrice())
						.set("source",product2.getSource()).set("details",product2.getDetails())
						.set("supplier",product2.getSupplier()).set("tel",product2.getTel())
						.set("contacts",product2.getContacts()).set("heightMax",product2.getHeightMax())
						.set("heightMin",product2.getHeightMin()).set("crownMax",product2.getCrownMax())
						.set("crownMin",product2.getCrownMin()).set("updateTime",product2.getUpdateTime())
						.set("createTime",product2.getCreateTime()).set("startingFare",product2.getStartingFare())
						.set("invoiceType",product2.getInvoiceType()));
					}
					Boolean reuslt = query.saveList();
					if(reuslt){
						updataProduct.addAll(storage);
						query=new MongoQuery();
						synchrodata +=1000;
						System.out.println("已同步进mongodb:"+synchrodata+"条");
					}
				}
				//保存进mongodb后更新mysql对应的Product状态
				if(updataProduct.size()>0){
					ProductProcessor productProcessor = new ProductProcessor(updataProduct);
					productProcessor.start();
				}
				System.out.println("----------------执行mysql同步mongodb结束--------------------------");
				logger.debug("----------------执行mysql同步mongodb结束--------------------------");
				try {
					System.out.println("------------执行爬取数据清洗-----------");
					logger.debug("------------执行爬取数据清洗-----------");
					ProductHandler.main(new String[]{});
				} catch (ParseException e1) {
					e1.printStackTrace();
					logger.debug("清洗失败！Exception：{}", e1);
				}
				try {
					//线程休眠12个小时
					ImportProductJob.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
					logger.debug("导入数据异常:{}",e);
				}
		 }
		
	}
}
