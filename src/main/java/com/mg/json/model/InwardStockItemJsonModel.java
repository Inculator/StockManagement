package com.mg.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.csms.beans.InwardStockItem;
import com.mg.json.controller.JsonHandlerInterface;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class InwardStockItemJsonModel implements JsonHandlerInterface {

	private List<InwardStockItem> stockItemList;
	private Map<Integer, Object> stockItemMap;

	@Override
	public void makeListAndMapFromJson() {
		stockItemMap = new HashMap<>();
		stockItemMap = getObjectFromJsonFile("InwardStockItem");
		stockItemList = new ArrayList(stockItemMap.values());
	}

	public List<InwardStockItem> getStockItemList() {
		return stockItemList;
	}

	public Map<Integer, Object> getStockItemMap() {
		return stockItemMap;
	}

	public void setStockItemMap(InwardStockItem stockItem) {
		this.stockItemMap.put(stockItem.getRecordId(), stockItem);
	}
}
