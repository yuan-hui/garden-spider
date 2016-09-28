package com.mlh.spider.pageprocessor.product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mlh.model.Content;
import com.mlh.model.Product;
import com.mysql.jdbc.StringUtils;

/**
 * 中华园林 清洗处理器
 */
public class LvsemiaomuQiaoguanmuCleanProcessor {
	
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
		product.setTel(getTel(content.getTel()));
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
		Double[] miDiameter = compare(content.getMidiameter());
		product.setMiDiameterMax(miDiameter[1]);
		product.setMiDiameterMin(miDiameter[0]);
		return product;
	}
	
	//价格
	public static Double getStartingFare(String price){
		Double value = 0.0;
		if(StringUtils.isNullOrEmpty(price))return value;
		//去中文
		String regEX="[\u4e00-\u9fa5]";  
		Pattern p=Pattern.compile(regEX);  
		Matcher m=p.matcher(price);  
		price=m.replaceAll("").trim();  
		//去符号
		price = price.replaceAll("/", "");
		price = price.replaceAll(",", "");
		value = Double.valueOf(price);
        return value;
	}
	
	//电话
	public static String getTel(String tel){
		String _tel = "";
		if(StringUtils.isNullOrEmpty(tel)) return _tel;
		tel = tel.replaceAll("86-", "");
		String[] new_tel = tel.split(",");
		_tel=new_tel[0];
		return _tel;
	}
	
	//(米径/胸径)、(冠幅)、(高度)、最大值(Double[1])最小值	(Double[0])
	public static Double[] compare(String content){
		Double[] value = new Double[2];
		if(StringUtils.isNullOrEmpty(content)){
			value[0]=value[1]=0.0;
		}else{
			//去中文
			String regEX="[\u4e00-\u9fa5]";  
			Pattern p=Pattern.compile(regEX);  
			Matcher m=p.matcher(content);  
			content=m.replaceAll("").trim();  
			
			if(content.contains("-")){
				String[] strArray=null;
	        	strArray = content.split("-");
	        	if(strArray.length>2){
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
	        		value[0]=value[1]=0.0;
	        	}
			}else{
				value[0]=value[1]=Double.valueOf(content);
			}
		}
		return value;
	}
		
    //获取当天时间
	public static Date getTime(String open) throws ParseException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat current  = new SimpleDateFormat("yyyy-MM-dd");
			String time = current.format(new Date());	
			if(open.equals("Y")){
				//全量查询
				return sdf.parse("2000-09-01 00:00:00");
			}
			return sdf.parse(time+" 00:00:00");
	}
	
	public static void main(String[] args) throws ParseException {
		String _code = args[0];
		String _open = args[1];
		System.out.println("-------------中华园林 清洗开启-------------");
		Content content  = new Content();
		List<Content> list= content.findByCodeAndTime(_code,getTime(_open));
		List<Product> productList = new LinkedList<Product>();
		System.out.println("-------------中华园林待清洗数据"+list.size()+"条-------------");
		for (Content content1 : list) {	
			//如果产品名不存在，则跳出本次循环
			if(StringUtils.isNullOrEmpty(content1.getTitle()))return;
			Product product = getProduct(content1);
			productList.add(product);
		}
		System.out.println("-------------中华园林已清洗数据"+productList.size()+"条-------------");
		if(productList.size()>0){
			Product product =new Product();
			int[] reuslt =product.saveProducts(productList);
			System.out.println("-------------成功保存进price_product："+reuslt.length+"条-------------");	
		}else{
			System.out.println("-------------无数据保存进price_product-------------");	
		}
	}
}
