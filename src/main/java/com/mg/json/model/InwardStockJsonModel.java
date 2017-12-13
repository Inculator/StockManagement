package com.mg.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.csms.beans.InwardStock;
import com.mg.json.controller.JsonHandlerInterface;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class InwardStockJsonModel implements JsonHandlerInterface {

	private List<InwardStock> stockList;
	private Map<Integer, Object> stockMap;

	@Override
	public void makeListAndMapFromJson() {
		stockMap = new HashMap<>();
		stockMap = getObjectFromJsonFile("InwardStock");
		stockList = new ArrayList(stockMap.values());
	}

	public List<InwardStock> getStockList() {
		return stockList;
	}

	public Map<Integer, Object> getStockMap() {
		return stockMap;
	}

	public void setStockMap(InwardStock stock) {
		this.stockMap.put(stock.getStockId(), stock);
	}
}
