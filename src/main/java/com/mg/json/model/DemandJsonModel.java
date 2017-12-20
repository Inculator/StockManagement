package com.mg.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mg.csms.beans.Demand;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.controller.JsonReferenceInterface;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DemandJsonModel implements JsonHandlerInterface, JsonReferenceInterface {

	private List<Demand> demandList;
	private Map<Integer, Object> demandMap;

	@Override
	public void makeListAndMapFromJson() {
		demandMap = new HashMap<>();
		demandMap = getObjectFromJsonFile(Demand.class.getSimpleName());
		demandList = new ArrayList(demandMap.values());
	}

	public List<Demand> getDemandList() {
		return demandList;
	}

	public Map<Integer, Object> getDemandMap() {
		return demandMap;
	}

	public void setDemandMap(Demand demand) {
		this.demandMap.put(demand.getDemandId(), demand);
	}

	@Override
	public TypeReference<?> getTypeReference() {
		return  new TypeReference<Map<Integer, Demand>>() {
		};
	}
}
