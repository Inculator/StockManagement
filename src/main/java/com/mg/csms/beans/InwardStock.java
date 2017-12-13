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
public class InwardStock implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer stockId;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate date;
	private Integer coldId;
	private Integer vyaapariId;
	private Integer qty;
	private String gadiNo;

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getColdId() {
		return coldId;
	}

	public void setColdId(Integer coldId) {
		this.coldId = coldId;
	}

	public Integer getVyaapariId() {
		return vyaapariId;
	}

	public void setVyaapariId(Integer vyaapariId) {
		this.vyaapariId = vyaapariId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getGadiNo() {
		return gadiNo;
	}

	public void setGadiNo(String gadiNo) {
		this.gadiNo = gadiNo;
	}

}
