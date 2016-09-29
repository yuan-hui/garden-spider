package com.mlh.spider.pageprocessor.product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.mlh.common.AppRun;
import com.mlh.model.Content;
import com.mlh.model.Product;
import com.mysql.jdbc.StringUtils;

/**
 * 中国园林网 清洗处理器
 */
public class YuanLinPriceCleanProcessor {

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
			if(StringUtils.isNullOrEmpty(price))
				return 0.0;
		    Pattern pattern = Pattern.compile("[0-9]*");
	        Matcher isNum = pattern.matcher(price);
	        Double value = 0.0;
	        if(isNum.matches()) {
	        	value = Double.valueOf(price);
	        } else {
	        	//去中文
	    		String regEX="[\u4e00-\u9fa5]";  
	    		Pattern p=Pattern.compile(regEX);  
	    		Matcher m=p.matcher(price);  
	    		price=m.replaceAll("").trim();  
	    		//去字符
	    		regEX ="[`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~~！一@#￥%……&*―（）——+|{}【】‘；,/：”“’。，、？]"; 
	    		p=Pattern.compile(regEX);  
	    		m=p.matcher(price);  
	    		price=m.replaceAll("").trim();         
	            value = Double.valueOf(price);
	        }
	      return value;
	}
	
	//电话
	public static String getTel(String tel){
		String _tel = "";
		if(StringUtils.isNullOrEmpty(tel)) return _tel;
		//""替换为,
		tel.replaceAll(" ", ",");
		//字符替换为,
		String regEX ="[`~!@#$%^&*()+=|{}':;,'一\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；/：”“’。，、？]"; 
		Pattern p=Pattern.compile(regEX);  
		Matcher m=p.matcher(tel);  
		tel=m.replaceAll(",").trim();  
		//中文替换为""
		regEX="[\u4e00-\u9fa5]";
		p=Pattern.compile(regEX);  
		m=p.matcher(tel); 
		tel=m.replaceAll("").trim();
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
	        		Double num1 = Double.valueOf(strArray[0].trim().equals("")?"0":strArray[0]);
		        	Double num2 = Double.valueOf(strArray[1].trim().equals("")?"0":strArray[1]);
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
				value[0]=value[1]=Double.valueOf(content.trim().equals("")?"0":content);
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
		AppRun.start();
		String _code = "yuanlin_price";//args[0];
		String _open = "Y";//args[1];
		System.out.println("-------------中国园林 清洗开启-------------");
		Content content  = new Content();
		List<Content> list= content.findByCodeAndTime(_code,getTime(_open));
		List<Product> productList = new LinkedList<Product>();
		System.out.println("-------------中国园林待清洗数据"+list.size()+"条-------------");
		for (Content content1 : list) {	
			//如果产品名不存在，则跳出本次循环
			if(StringUtils.isNullOrEmpty(content1.getTitle()))continue;
			//无效的产品名
			if(content1.getTitle().contains("??"))continue;
			Product product = getProduct(content1);
			productList.add(product);
		}
		System.out.println("-------------中国园林已清洗数据"+productList.size()+"条-------------");
		if(productList.size()>0){
			int data = productList.size();
			System.out.println("开始同步数据："+data+"条");
			int degree = data>100?(data/100)+1:1;
			Product product =new Product();
			int savaDate = 0;
			for (int i=degree,j=0;i>j;j++) {
				int strat = j*100;
				int end = 100;
				int[] reuslt =product.saveProducts(productList.stream().skip(strat).limit(end).collect(Collectors.toList()));
				savaDate+=reuslt.length;
				System.out.println("已同步数据"+savaDate+"条,剩余"+(productList.size()-savaDate)+"条数据");
			}
			System.out.println("-------------成功保存进price_product："+savaDate+"条-------------");
		}else{
			System.out.println("-------------无数据保存进price_product-------------");	
		}
	}
}