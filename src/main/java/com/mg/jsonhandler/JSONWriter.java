package com.mg.jsonhandler;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.utils.FilesPathUtils;

/**
 * @author Mohak Gupta
 *
 */
public class JSONWriter {

	private static Logger log = Logger.getLogger(JSONWriter.class);

	public void writeObjectToJson(String fileName, Map<Integer, Object> jsonObject) {
		File file = FilesPathUtils.getInstance().createDirectoryAndFileIfNotExist(fileName);
		writeObjectToJsonFile(jsonObject, file);
	}

	private void writeObjectToJsonFile(Map<Integer, Object> jsonObject, File file) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonObject);
		} catch (IOException e) {
			log.error("Error writting Object to Json File" + e);
		}
	}

}
