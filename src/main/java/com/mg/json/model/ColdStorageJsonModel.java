package com.mg.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mg.csms.beans.ColdStorage;
import com.mg.json.controller.JsonHandlerInterface;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ColdStorageJsonModel implements JsonHandlerInterface {

	private List<ColdStorage> coldStoreList;
	private Map<Integer, Object> coldStoreMap;

	@Override
	public void makeListAndMapFromJson() {
		coldStoreMap = new HashMap<>();
		coldStoreMap = getObjectFromJsonFile("ColdStorage");
		coldStoreList = new ArrayList(coldStoreMap.values());
	}

	public List<ColdStorage> getColdStoreList() {
		return coldStoreList;
	}

	public Map<Integer, Object> getColdStoreMap() {
		return coldStoreMap;
	}

	public void setColdStoreMap(ColdStorage cold) {
		this.coldStoreMap.put(cold.getColdId(), cold);
	}

}
