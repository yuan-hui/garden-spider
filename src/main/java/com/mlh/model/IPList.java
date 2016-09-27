package com.mlh.model;

import java.util.List;

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

}
