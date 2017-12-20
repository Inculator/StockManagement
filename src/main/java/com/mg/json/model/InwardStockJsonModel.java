package com.mg.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mg.csms.beans.InwardStock;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.controller.JsonReferenceInterface;
import com.mg.weld.TypeAnnotation;

@SuppressWarnings({ "unchecked", "rawtypes" })
@TypeAnnotation(InwardStockJsonModel.modelType)
public class InwardStockJsonModel implements JsonHandlerInterface, JsonReferenceInterface {

	public static final String modelType = InwardStock.NAME;
	private List<InwardStock> stockList;
	private Map<Integer, Object> stockMap;

	@Override
	public void makeListAndMapFromJson() {
		stockMap = new HashMap<>();
		stockMap = getObjectFromJsonFile(InwardStock.class.getSimpleName());
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

	@Override
	public TypeReference<?> getTypeReference() {
		return new TypeReference<Map<Integer, InwardStock>>() {
		};
	}
}
