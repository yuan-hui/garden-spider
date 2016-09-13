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
	lvsemiaomu_qiaoguanmu("lvsemiaomu_qiaoguanmu", "绿色苗木_乔灌木");

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
