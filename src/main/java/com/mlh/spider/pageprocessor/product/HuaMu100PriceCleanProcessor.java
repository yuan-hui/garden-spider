package com.mlh.spider.pageprocessor.product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.log.Log;
import com.mlh.common.AppRun;
import com.mlh.model.Content;
import com.mlh.model.Product;
import com.mysql.jdbc.StringUtils;

/**
 * 花木100 清洗处理器
 */
public class HuaMu100PriceCleanProcessor {

	private final static Log logger = Log.getLog(HuaMu100PriceCleanProcessor.class);
	
	public static Product getProduct(Content content){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Product product = new Product();
		product.setId(content.getId());
		product.setBreedName(StringUtils.isNullOrEmpty(content.getBreedName())?"":content.getBreedName());
		product.setProductName(content.getTitle());	
		product.setArea(content.getArea());
		product.setAreaNo(content.getAreaNo());
		product.setUpdateTime(sdf.format(content.getUpdateTime()));
		product.setCreateTime(sdf.format(content.getCreateTime()));
		product.setOp("ACT");
		product.setDetails(content.getSource());
		product.setContacts(content.getContacts());
		product.setTel(content.getTel());
		product.setInvoiceType("");
		product.setSupplier(content.getCompany());
		product.setTotalPrice(0.0);
		product.setSource("1");
		product.setStartingFare(getStartingFare(content.getPrice()));
		Double[] height = compare(content.getHeight());
		product.setHeightMax(height[1]);
		product.setHeightMin(height[0]);
		Double[] crown = compare(content.getCrown());
		product.setCrownMax(crown[1]);
		product.setCrownMin(crown[0]);
		Double[] miDiameter = compare(content.getHeight());
		product.setMiDiameterMax(miDiameter[1]);
		product.setMiDiameterMin(miDiameter[0]);
		return product;
	}
	
	//价格
	public static Double getStartingFare(String price){
			if(StringUtils.isNullOrEmpty(price))
				return 0.0;
		    Pattern pattern = Pattern.compile("[0-9]*");
	        Matcher isNum = pattern.matcher(price);
	        Double value = 0.0;
	        if(isNum.matches()) {
	        	value = Double.valueOf(price);
	        } else {
	            if(price.contains("---"))
	            price.replaceAll("---","-");
	            if(price.contains("-")){
	            	String[] strArray=null;
	            	strArray = price.split("-");
	            	if(strArray.length==2){
	            		Double num1 = Double.valueOf(strArray[0]);
	            		Double num2 = Double.valueOf(strArray[1]);
	            		value = (num1+num2)/2;
	            	}else{
	            		value = Double.valueOf(strArray[0]);
	            	}
	            }  
	        }
	      return value;
	}
	
	//(米径/胸径)、(冠幅)、(高度)、最大值(Double[1])最小值	(Double[0])
	public static Double[] compare(String content){
		Double[] value = new Double[2];
		if(StringUtils.isNullOrEmpty(content)){
			value[0]=value[1]=0.0;
		}else{
			if(content.contains("-")){
				String[] strArray=null;
	        	strArray = content.split("-");
	        	Double num1 = Double.valueOf(strArray[0]);
	        	Double num2 = Double.valueOf(strArray[1]);
	        	if(num1<num2) {
	        		value[0]=num1;
	        		value[1]=num2;
	        	}else{
	        		value[0]=num2;
	        		value[1]=num1;
	        	}
			}else{
				value[0]=value[1]=Double.valueOf(content);
			}
		}
		return value;
	}

	public static void main(String[] args) throws ParseException {
		AppRun.start();
		String _code = args[0];/*huamu100_price*/
		logger.info("-----------花木100 清洗开启---------------------");
		System.out.println("-----------花木100 清洗开启---------------------");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Content content  = new Content();
		List<Content> list= content.findByCodeAndTime(_code,sdf.parse("2016-09-27 00:00:00"));
		List<Product> productList = new LinkedList<Product>();
		logger.info("------------花木100待清洗数据"+list.size()+"条---------------");
		System.out.println("------------花木100待清洗数据"+list.size()+"条---------------");
		for (Content content1 : list) {	
			//如果产品名不存在，则跳出本次循环
			if(StringUtils.isNullOrEmpty(content1.getTitle()))return;
			Product product = getProduct(content1);
			productList.add(product);
		}
		logger.info("------------花木100已清洗数据"+productList.size()+"条---------------");
		System.out.println("------------花木100已清洗数据"+productList.size()+"条---------------");
		Product product =new Product();
		int[] reuslt =product.saveProducts(productList);
		logger.info("------------成功保存进price_product："+reuslt.length+"条---------------");
		System.out.println("------------成功保存进price_product："+reuslt.length+"条---------------");
	}
}
