package com.mlh.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.mlh.buss.content.bean.IPBean;
import com.mlh.model.base.BaseIPList;

public class IPList extends BaseIPList<IPList>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1361914485347040825L;
	
	public static final IPList dao = new IPList();
	
	
	public List<IPList> findIPList() {
		return dao.find("select * from ip" );
	}


	public boolean save(IPBean ipBean) {
		boolean result = false;
		IPList ip = new IPList();
		ip.setIp(ipBean.getIp());
		ip.setPort(ipBean.getProt());
		
		deleteIPByip(ipBean.getIp());
		
		result = ip.save();
		
		
		return result;
	}


	private void deleteIPByip(String ip) {
		Db.update("DELETE  FROM ip  WHERE `ip` = ?",ip);
	}

}
