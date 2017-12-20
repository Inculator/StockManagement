package com.mg.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mg.csms.beans.ColdStorage;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.controller.JsonReferenceInterface;
import com.mg.weld.TypeAnnotation;

@SuppressWarnings({ "unchecked", "rawtypes" })
@TypeAnnotation(ColdStorageJsonModel.modelType)
public class ColdStorageJsonModel implements JsonHandlerInterface, JsonReferenceInterface {

	public static final String modelType = ColdStorage.NAME;

	private List<ColdStorage> coldStoreList;
	private Map<Integer, Object> coldStoreMap;

	@Override
	public void makeListAndMapFromJson() {
		coldStoreMap = new HashMap<>();
		coldStoreMap = getObjectFromJsonFile(ColdStorage.class.getSimpleName());
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

	@Override
	public TypeReference<?> getTypeReference() {
		return new TypeReference<Map<Integer, ColdStorage>>() {
		};
	}
}
