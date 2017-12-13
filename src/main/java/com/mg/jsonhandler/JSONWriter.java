package com.mg.jsonhandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg.stock.constant.StockConstants;

/**
 * @author Mohak Gupta
 *
 */
public class JSONWriter {

	private static Logger log = Logger.getLogger(JSONWriter.class);

	public void writeObjectToJson(String fileName, Map<Integer, Object> jsonObject) {
		createDirectory(fileName);
		File file = createFileIfNotExist(fileName);
		writeObjectToFile(jsonObject, file);
	}

	private void writeObjectToFile(Map<Integer, Object> jsonObject, File file) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonObject);
		} catch (IOException e) {
			log.error("Error writting Object to Json File" + e);
		}
	}

	private File createFileIfNotExist(String fileName) {
		File file = new File(StockConstants.DIRECTORY_PATH + fileName + StockConstants.JSON_SUFFIX);
		try {
			if (!file.exists())
				file.createNewFile();
		} catch (IOException e) {
			log.error("Error Creating File : " + fileName + ": " + e);
		}
		return file;
	}

	private void createDirectory(String fileName) {
		Path path = Paths.get(StockConstants.DIRECTORY_PATH);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				log.error("Error Creating Directory : " + fileName + ": " + e);
			}
		}
	}
}
