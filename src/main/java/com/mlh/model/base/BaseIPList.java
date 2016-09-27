package com.mlh.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public abstract class BaseIPList <M extends BaseIPList<M>> extends Model<M> implements IBean{
	
	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}
	
	public void setIp(java.lang.String ip) {
		set("ip", ip);
	}

	public java.lang.String getIp() {
		return get("ip");
	}
	
	public void setPort(java.lang.String port) {
		set("port", port);
	}

	public java.lang.String getPort() {
		return get("port");
	}
	
}
