package com.mg.json.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mg.jsonhandler.JSONParser;
import com.mg.jsonhandler.JSONWriter;

@FunctionalInterface
public interface JsonHandlerInterface {

	public final static Logger log = Logger.getLogger(JsonHandlerInterface.class);
	public static final JSONWriter jsonWriter = new JSONWriter();
	public static final JSONParser jsonParser = new JSONParser();

	default void writeObjectToJson(String fileName, Map<Integer, Object> jsonObject) {
		jsonWriter.writeObjectToJson(fileName, jsonObject);
	}

	default Map<Integer, Object> getObjectFromJsonFile(String fileName) {
		try {
			return jsonParser.getObjectFromJsonFile(fileName);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public void makeListAndMapFromJson();

}
