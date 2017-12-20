package com.mg.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mg.csms.beans.InwardStockItem;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.controller.JsonReferenceInterface;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class InwardStockItemJsonModel implements JsonHandlerInterface, JsonReferenceInterface {

	private List<InwardStockItem> stockItemList;
	private Map<Integer, Object> stockItemMap;

	@Override
	public void makeListAndMapFromJson() {
		stockItemMap = new HashMap<>();
		stockItemMap = getObjectFromJsonFile(InwardStockItem.class.getSimpleName());
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

	@Override
	public TypeReference<?> getTypeReference() {
		return  new TypeReference<Map<Integer, InwardStockItem>>() {
		};
	}
}
