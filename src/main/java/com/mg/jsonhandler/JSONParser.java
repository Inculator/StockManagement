package com.mg.jsonhandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.json.controller.JsonReferenceInterface;
import com.mg.stock.constant.StockConstants;
import com.mg.utils.FilesPathUtils;
import com.mg.weld.WeldManagerFactory;

/**
 * @author Mohak Gupta
 *
 */
public class JSONParser {

	public Map<Integer, Object> getObjectFromJsonFile(String fileName) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<?> referenceObject = null;

		File file = new File(FilesPathUtils.getInstance().getRunningDirectoryPath() + StockConstants.DIRECTORY_PATH
				+ fileName + StockConstants.JSON_SUFFIX);
		if (file.exists())
			referenceObject = WeldManagerFactory.getInstance().find(JsonReferenceInterface.class, fileName).getTypeReference();
		else
			return new HashMap<Integer, Object>();
		return mapper.readValue(file, referenceObject);
	}
}
