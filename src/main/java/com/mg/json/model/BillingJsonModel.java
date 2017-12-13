package com.mg.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.csms.beans.BillingDetails;
import com.mg.csms.beans.InwardStockItem;
import com.mg.json.controller.JsonHandlerInterface;

/**
 * @author Mohak Gupta
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BillingJsonModel implements JsonHandlerInterface {

	private List<BillingDetails> billingList;
	private Map<Integer, Object> billingMap;

	@Override
	public void makeListAndMapFromJson() {
		billingMap = new HashMap<>();
		billingMap = getObjectFromJsonFile("Billing");
		billingList = new ArrayList(billingMap.values());
	}

	public List<BillingDetails> getBillingList() {
		return billingList;
	}

	public Map<Integer, Object> getBillingMap() {
		return billingMap;
	}

	public void setBillingMap(BillingDetails billingObject) {
		this.billingMap.put(billingObject.getColdNo(), billingObject);
	}

	public BillingDetails makeBillingDetailsObjectFromInwardStockItemObject(InwardStockItem inwardStockItem) {
		BillingDetails bill = new BillingDetails();
		bill.setEntryDate(inwardStockItem.getEntryDate());
		bill.setColdNo(inwardStockItem.getColdNo());
		bill.setLotNo(inwardStockItem.getLotNo());
		bill.setQuantity(inwardStockItem.getQuantity());
		bill.setRecordId(inwardStockItem.getRecordId());
		bill.setStockId(inwardStockItem.getStockId());
		bill.setItem(inwardStockItem.getItem());
		bill.setRate(inwardStockItem.getRate());
		bill.setGadiNo(inwardStockItem.getGadiNo());
		bill.setBalance(inwardStockItem.getBalance());
		return bill;
	}
}
