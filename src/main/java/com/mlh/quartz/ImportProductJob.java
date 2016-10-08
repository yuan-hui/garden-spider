package com.mlh.quartz;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.ext.plugin.mongo.MongoQuery;
import com.mlh.model.Product;



/**
 * @Description: 定时导入数据到mongodb(price_product)
 */
public class ImportProductJob extends Thread implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ImportProductJob importProductJob = new ImportProductJob();
		importProductJob.start();
	}
	
	public void run() {
		System.out.println("-----------执行mysql同步mongodb------------");
		Product product = new Product();
		MongoQuery query=new MongoQuery();
		int count = product.count();
		int synchrodata = 0;
		System.out.println("-----------需要同步数据："+count+"------------");
		int degree = count>1000?(count/1000)+1:1;
		for (int i=degree,j=0;i>j;j++) {
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
				product.updateProduct(storage);
				query=new MongoQuery();
				synchrodata +=1000;
				System.out.println("已同步进mongodb:"+synchrodata+"条");
			}
		}
		System.out.println("----------------执行mysql同步mongodb结束--------------------------");
	}
}
