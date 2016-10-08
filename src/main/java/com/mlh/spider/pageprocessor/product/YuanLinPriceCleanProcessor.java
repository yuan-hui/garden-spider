package com.mlh.spider.pageprocessor.product;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mlh.common.AppRun;
import com.mlh.model.Content;
import com.mlh.model.Product;
import com.mysql.jdbc.StringUtils;

/**
 * 中国园林网 清洗处理器
 */
public class YuanLinPriceCleanProcessor extends Thread{

	private String code;
	
	public YuanLinPriceCleanProcessor(String code) {
		this.code=code;
	}
	
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
		product.setContacts(StringUtils.isNullOrEmpty(content.getContacts())?"":content.getContacts());
		product.setTel(getTel(content.getTel()));
		product.setInvoiceType("");
		product.setSupplier(StringUtils.isNullOrEmpty(content.getCompany())?"":content.getCompany());
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
	        	price=price.replaceAll("一", "-");
	        	//去中文
	    		String regEX="[\u4e00-\u9fa5]";  
	    		Pattern p=Pattern.compile(regEX);  
	    		Matcher m=p.matcher(price);  
	    		price=m.replaceAll("").trim();  
	    		//去字符
	    		regEX ="[`~!@#$%^&*()+=|{}':;'\\[\\]<>/?~~！一@#￥%……&*―（）——+|{}【】‘；,/：”“’。，、？]"; 
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
		return StringUtils.isNullOrEmpty(_tel)?"":_tel;
	}

	//(米径/胸径)、(冠幅)、(高度)、最大值(Double[1])最小值	(Double[0])
	public static Double[] compare(String content){
		Double[] value = new Double[2];
		if(StringUtils.isNullOrEmpty(content)){
			value[0]=value[1]=0.0;
		}else{
			content=content.replaceAll("一", "-");
			//去中文
			String regEX="[\u4e00-\u9fa5]";  
			Pattern p=Pattern.compile(regEX);  
			Matcher m=p.matcher(content);  
			content=m.replaceAll("").trim();  
			//过滤字母
			regEX="[A-Za-z]"; 
			p=Pattern.compile(regEX);  
			m=p.matcher(content);  
			content=m.replaceAll("").trim(); 
			//字符替换为-
			regEX ="[`~!@#$%^&*()+=|{}':;,'_―一\\[\\]<>/?！@#￥%……&*（）——+|{}【】‘；/：”“’。，、？]"; 
			p=Pattern.compile(regEX);  
			m=p.matcher(content);  
			content=m.replaceAll("-").trim();	
			if(content.contains("-")){
				String[] strArray=null;
	        	strArray = content.split("-");
	        	if(strArray.length>1){
	        		Double num1 = Double.valueOf(strArray[0].trim().equals("")?"0":strArray[0]);
		        	Double num2 = Double.valueOf(strArray[strArray.length-1].trim().equals("")?"0":strArray[strArray.length-1]);
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
	
/*	public static void main(String[] args) {
		AppRun.start();
		YuanLinPriceCleanProcessor a = new YuanLinPriceCleanProcessor("yuanlin_price");
		a.start();
	}*/

	public void run() {//yuanlin_price
		System.out.println("-------------中国园林 清洗开启-------------");
		Content content  = new Content();
		Product product = new Product();
		List<Content> contentList= new LinkedList<Content>();
		List<String> ids =new LinkedList<String>();;
		List<Product> productList;
		int count = content.count(code);
		System.out.println("-------------中国园林【待清洗数据"+count+"条】-------------");
		if(count>0){
			int degree = count>1000?(count/1000)+1:1;
			int savaDate = 0;
			for (int i=degree,j=0;i>j;j++) {
				productList = new LinkedList<Product>();
				int strat = j*1000;
				int end = 1000;
				contentList = content.findByCode(code, strat, end);
				for (Content content1 : contentList) {	
					//如果产品名不存在，则跳出本次循环
					if(StringUtils.isNullOrEmpty(content1.getTitle()))continue;
					Product pr = getProduct(content1);
					productList.add(pr);
					ids.add(content1.getId());
				}	
				int[] reuslt = product.saveProducts(productList);
				savaDate+=reuslt.length;
				System.out.println("中国园林【已同步数据："+savaDate+"条,剩余"+(count-savaDate)+"条数据】");
			}
			System.out.println("中国园林【已清洗数据："+ids.size()+"条】");
			//更改爬取数据状态（线程）
			ContentProcessor ContentProcessor = new ContentProcessor(code,ids);
			ContentProcessor.start();	
			System.out.println("-------------中国园林【成功保存进price_product："+savaDate+"条】-------------");	
		}else{
			System.out.println("-------------中国园林【无数据保存进price_product】-------------");	
		}
	}
}
