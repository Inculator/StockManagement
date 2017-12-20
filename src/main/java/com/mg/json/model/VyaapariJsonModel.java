package com.mg.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mg.csms.beans.Vyaapari;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.controller.JsonReferenceInterface;
import com.mg.weld.TypeAnnotation;

@SuppressWarnings({ "unchecked", "rawtypes" })
@TypeAnnotation(VyaapariJsonModel.modelType)
public class VyaapariJsonModel implements JsonHandlerInterface, JsonReferenceInterface {

	public static final String modelType = Vyaapari.NAME;
	private List<Vyaapari> vyaapariList;
	private Map<Integer, Object> vyaapariMap;

	@Override
	public void makeListAndMapFromJson() {
		vyaapariMap = new HashMap<>();
		vyaapariMap = getObjectFromJsonFile(Vyaapari.class.getSimpleName());
		vyaapariList = new ArrayList(vyaapariMap.values());
	}

	public Map<Integer, Object> getVyaapariMap() {
		return vyaapariMap;
	}

	public void setVyaapariMap(Vyaapari vyaapari) {
		this.vyaapariMap.put(vyaapari.getVyaapariId(), vyaapari);
	}

	public List<Vyaapari> getVyaapariList() {
		return vyaapariList;
	}

	@Override
	public TypeReference<?> getTypeReference() {
		return new TypeReference<Map<Integer, Vyaapari>>() {
		};
	}

}
