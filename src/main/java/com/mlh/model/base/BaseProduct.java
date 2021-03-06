package com.mlh.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseProduct <M extends BaseProduct<M>> extends Model<M> implements IBean {

	public java.lang.String getId() {
		return get("id");
	}
	public void setId(java.lang.String id) {
		set("id", id);
	}
	public java.lang.String getProductName() {
		return get("productName");
	}
	public void setProductName(java.lang.String productName) {
		set("productName", productName);
	}
	public java.lang.String getBreedName() {
		return get("breedName");
	}
	public void setBreedName(java.lang.String breedName) {
		set("breedName", breedName);
	}
	public java.lang.String getArea() {
		return get("area");
	}
	public void setArea(java.lang.String area) {
		set("area", area);
	}
	public java.lang.String getAreaNo() {
		return get("areaNo");
	}
	public void setAreaNo(java.lang.String areaNo) {
		set("areaNo", areaNo);
	}
	public java.lang.String getOp() {
		return get("op");
	}
	public void setOp(java.lang.String op) {
		set("op", op);
	}
	public java.lang.Double getMiDiameterMax() {
		return get("miDiameterMax");
	}
	public void setMiDiameterMax(java.lang.Double miDiameterMax) {
		set("miDiameterMax", miDiameterMax);
	}
	public java.lang.Double getMiDiameterMin() {
		return get("miDiameterMin");
	}
	public void setMiDiameterMin(java.lang.Double miDiameterMin) {
		set("miDiameterMin", miDiameterMin);
	}
	public java.lang.Double getTotalPrice() {
		return get("totalPrice");
	}
	public void setTotalPrice(java.lang.Double totalPrice) {
		set("totalPrice", totalPrice);
	}
	public java.lang.Double getStartingFare() {
		return get("startingFare");
	}
	public void setStartingFare(java.lang.Double startingFare) {
		set("startingFare", startingFare);
	}
	public java.lang.String getSource() {
		return get("source");
	}
	public void setSource(java.lang.String source) {
		set("source", source);
	}
	public java.lang.String getDetails() {
		return get("details");
	}
	public void setDetails(java.lang.String details) {
		set("details", details);
	}
	public java.lang.String getSupplier() {
		return get("supplier");
	}
	public void setSupplier(java.lang.String supplier) {
		set("supplier", supplier);
	}
	public java.lang.String getTel() {
		return get("tel");
	}
	public void setTel(java.lang.String tel) {
		set("tel", tel);
	}
	public java.lang.String getContacts() {
		return get("contacts");
	}
	public void setContacts(java.lang.String contacts) {
		set("contacts", contacts);
	}
	public java.lang.Double getHeightMax() {
		return get("heightMax");
	}
	public void setHeightMax(java.lang.Double heightMax) {
		set("heightMax", heightMax);
	}
	public java.lang.Double getHeightMin() {
		return get("heightMin");
	}
	public void setHeightMin(java.lang.Double heightMin) {
		set("heightMin", heightMin);
	}
	public java.lang.Double getCrownMax() {
		return get("crownMax");
	}
	public void setCrownMax(java.lang.Double crownMax) {
		set("crownMax", crownMax);
	}
	public java.lang.Double getCrownMin() {
		return get("crownMin");
	}
	public void setCrownMin(java.lang.Double crownMin) {
		set("crownMin", crownMin);
	}
	public String getUpdateTime() {
		return get("updateTime");
	}
	public void setUpdateTime(java.lang.String updateTime) {
		set("updateTime", updateTime);
	}
	public java.lang.String getCreateTime() {
		return get("createTime");
	}
	public void setCreateTime(java.lang.String createTime) {
		set("createTime", createTime);
	}
	public java.lang.String getInvoiceType() {
		return get("invoiceType");
	}
	public void setInvoiceType(java.lang.String invoiceType) {
		set("invoiceType", invoiceType);
	}
	public java.lang.Long getCount() {
		return get("count");
	}
	public void setCount(java.lang.Long count) {
		set("count", count);
	}
}
