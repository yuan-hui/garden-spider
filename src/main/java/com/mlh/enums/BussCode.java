package com.mlh.enums;

/**
 * 
 * @Description: 常用业务编码
 * @author liujiecheng
 */
public enum BussCode {

	/**
	 * gooood-architecture
	 */
	lvsemiaomu_qiaoguanmu("lvsemiaomu_qiaoguanmu", "绿色苗木_乔灌木"),
	
	/**
	 *苗木第一站 
	 */
	miaomuzhan_miaomujiage("miaomuzhan_miaomujiage","第一苗木站_苗木价格"),
	
	/***
	 * 青青苗木站
	 */
	green321_qiaoguanmu("green321_qiaoguanmu","青青苗木_乔灌木"),
	
	/**
	 * 中国苗木网
	 */
	miaomu_price("miaomu_price","中国苗木网_价格"),
	/**
	 * 西北苗木
	 */
	xbmiaomu_maiomujiage("xbmiaomu_maiomujiage","西北苗木_绿化苗木价格"),
	
	/**
	 * 花木100
	 */
	huamu100_price("huamu100_price","花木100_苗木价格"),
	
	/**
	 * 597苗木网
	 */
	
	mm597_price("mm597_price","597苗木_苗木报价"),
	
	/**
	 * 中国园林网
	 */
	yuanlin_price("yuanlin_price","中国园林网"),
	
	/**
	 * 浙江园林网
	 */
	zjyuanlin_price("zjyuanlin_price","浙江园林网");
	
	
	
	private String code;

	private String name;

	private BussCode(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	};
}
