package com.mlh.spider.pageprocessor.product;

import java.util.List;
import java.util.stream.Collectors;

import com.mlh.model.Product;

/**
 * 改变mysql-price_product导入状态
 */
public class ProductProcessor extends Thread{
	
	private List<Product> ids;
	
	public ProductProcessor(List<Product> ids) {
		this.ids=ids;
	}
		
	public void run() {
		Product product  = new Product();
		System.out.println("---------更新price_product【size："+ids.size()+" ,importStatus:Y】--------------");
		for (int i=ids.size(),j=0;i>j;j++) {
			int strat = j*100;
			int end = 100;
			product.updateProduct(ids.stream().skip(strat).limit(end).collect(Collectors.toList()));	
		}
		System.out.println("---------更新price_product结束--------------");
	}
}
