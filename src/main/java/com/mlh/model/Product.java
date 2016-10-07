package com.mlh.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.mlh.model.base.BaseProduct;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Product extends BaseProduct<Product> {
	public static final Product dao = new Product();

	/**
	 * @Description: 批量保存
	 */
	public int[] saveProducts(List<Product> productList) {
		return Db.batchSave(productList, productList.size());
	}
	
    /**
     * @Description: 删除数据
     * @param id
     */
	public void deleteByCId(String id) {
		Db.update("delete from price_product where id = ?", id);
	}
	
	/**
	  * @Description: 批量修改
	  */
	public int updateProduct(List<Product> productList) {	
		int i =0;
		for (Product product : productList) {
			 i+=Db.update("UPDATE price_product SET importStatus = 'Y' WHERE id = ?",product.getId());
		}
		return i;
	}
	
	/**
	 * @Description: 查询状态为N的数据
	 * @return
	 */
	public List<Product> findByStatus(int start,int end){
		String sql = "SELECT * FROM price_product WHERE importStatus = 'N' LIMIT ?,?";
		return dao.find(sql,start,end);
	}
	
	/**
	 * @Description: 统计
	 * @return
	 */
	public int count(){
		String sql = "SELECT count(id) as count FROM price_product WHERE importStatus = 'N'";
		long count =dao.find(sql).get(0).getCount();
		return (int)count;
	}
}
