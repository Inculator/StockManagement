package com.mg.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mg.csms.beans.Item;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.controller.JsonReferenceInterface;
import com.mg.weld.TypeAnnotation;

@SuppressWarnings({ "unchecked", "rawtypes" })
@TypeAnnotation(ItemJsonModel.modelType)
public class ItemJsonModel implements JsonHandlerInterface, JsonReferenceInterface {

	public static final String modelType = Item.NAME;
	private List<Item> itemList;
	private Map<Integer, Object> itemMap;

	@Override
	public void makeListAndMapFromJson() {
		itemMap = new HashMap<>();
		itemMap = getObjectFromJsonFile(Item.class.getSimpleName());
		itemList = new ArrayList(itemMap.values());
	}

	public Map<Integer, Object> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Item item) {
		this.itemMap.put(item.getId(), item);
	}

	public List<Item> getItemList() {
		return itemList;
	}

	@Override
	public TypeReference<?> getTypeReference() {
		return new TypeReference<Map<Integer, Item>>() {
		};
	}

}
