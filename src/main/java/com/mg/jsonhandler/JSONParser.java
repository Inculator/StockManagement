package com.mg.jsonhandler;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.csms.beans.BillingDetails;
import com.mg.csms.beans.ColdStorage;
import com.mg.csms.beans.Demand;
import com.mg.csms.beans.InwardStock;
import com.mg.csms.beans.InwardStockItem;
import com.mg.csms.beans.Item;
import com.mg.csms.beans.Vyaapari;
import com.mg.json.controller.JsonReferenceInterface;
import com.mg.stock.constant.StockConstants;
import com.mg.weld.WeldManager;

/**
 * @author Mohak Gupta
 *
 */
public class JSONParser {
	private static Logger log = Logger.getLogger(JSONParser.class);

	public Map<Integer, Object> getObjectFromJsonFile(String fileName) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<?> referenceObject = null;

		File file = new File(
				getRunningDirectoryPath() + StockConstants.DIRECTORY_PATH + fileName + StockConstants.JSON_SUFFIX);
		if (file.exists())
			referenceObject = WeldManager.getInstance().find(JsonReferenceInterface.class, fileName).getTypeReference();
		else
			return new HashMap<Integer, Object>();
		return mapper.readValue(file, referenceObject);
	}

	private TypeReference<?> makeReferenceObject(String fileName, TypeReference<?> referenceObject) {

		referenceObject = WeldManager.getInstance().find(JsonReferenceInterface.class, fileName).getTypeReference();
		switch (fileName) {
		case "ColdStorage":
			referenceObject = new TypeReference<Map<Integer, ColdStorage>>() {
			};
			break;
		case "Vyaapari":
			referenceObject = new TypeReference<Map<Integer, Vyaapari>>() {
			};
			break;
		case "InwardStock":
			referenceObject = new TypeReference<Map<Integer, InwardStock>>() {
			};
			break;
		case "InwardStockItem":
			referenceObject = new TypeReference<Map<Integer, InwardStockItem>>() {
			};
			break;
		case "Demand":
			referenceObject = new TypeReference<Map<Integer, Demand>>() {
			};
			break;
		case "Item":
			referenceObject = new TypeReference<Map<Integer, Item>>() {
			};
			break;
		case "BillingDetails":
			referenceObject = new TypeReference<Map<Integer, BillingDetails>>() {
			};
			break;
		default:
			break;
		}
		return referenceObject;
	}

	private String getRunningDirectoryPath() {
		String directoryPath = "C";
		try {
			directoryPath = Paths.get(JSONParser.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.toString().substring(0, 1);
		} catch (URISyntaxException e) {
			log.error("Unable to get the working Directory");
		}
		return directoryPath;
	}
}
