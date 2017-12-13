package com.mg.csms.beans;

/**
 * @author Mohak Gupta
 *
 */
public class BillingDetails extends InwardStockItem {

	private static final long serialVersionUID = 1L;

	private Integer bhada;
	private Integer totalBillAmount;
	private Boolean isPaid = false;

	public Integer getBhada() {
		return bhada;
	}

	public void setBhada(Integer bhada) {
		this.bhada = bhada;
	}

	public Integer getTotalBillAmount() {
		return totalBillAmount;
	}

	public void setTotalBillAmount(Integer totalBillAmount) {
		this.totalBillAmount = totalBillAmount;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

}
