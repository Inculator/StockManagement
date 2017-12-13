package com.mg.csms.beans;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mg.localdate.LocalDateDeserializer;
import com.mg.localdate.LocalDateSerializer;

/**
 * @author Mohak Gupta
 *
 */
public class InwardStockItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer recordId;
	private Integer stockId;
	private Integer lotNo;
	private Integer coldNo;
	private String item;
	private Integer quantity;
	private Float rate;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate entryDate;
	private String gadiNo;
	private Integer balance;

	public InwardStockItem() {
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Integer getLotNo() {
		return lotNo;
	}

	public void setLotNo(Integer lotNo) {
		this.lotNo = lotNo;
	}

	public Integer getColdNo() {
		return coldNo;
	}

	public void setColdNo(Integer coldNo) {
		this.coldNo = coldNo;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public LocalDate getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	public String getGadiNo() {
		return gadiNo;
	}

	public void setGadiNo(String gadiNo) {
		this.gadiNo = gadiNo;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

}
