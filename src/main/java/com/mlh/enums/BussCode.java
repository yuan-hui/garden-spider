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
	miaomu_pice("miaomu_pice","中国苗木网_价格");
	
	
	
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
