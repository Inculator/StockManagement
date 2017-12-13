package com.mg.csms.beans;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mg.localdate.LocalDateDeserializer;
import com.mg.localdate.LocalDateSerializer;

public class Demand {
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate demandDate;
	private Integer demandId;
	private Integer quantity;
	private Integer coldNo;
	private Integer chalanNumber;
	private Integer billAmount;

	public LocalDate getDemandDate() {
		return demandDate;
	}

	public void setDemandDate(LocalDate demandDate) {
		this.demandDate = demandDate;
	}

	public Integer getDemandId() {
		return demandId;
	}

	public void setDemandId(Integer demandId) {
		this.demandId = demandId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getColdNo() {
		return coldNo;
	}

	public void setColdNo(Integer coldNo) {
		this.coldNo = coldNo;
	}

	public Integer getChalanNumber() {
		return chalanNumber;
	}

	public void setChalanNumber(Integer chalanNumber) {
		this.chalanNumber = chalanNumber;
	}

	public Integer getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Integer billAmount) {
		this.billAmount = billAmount;
	}


}
