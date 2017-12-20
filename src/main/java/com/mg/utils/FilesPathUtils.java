package com.mg.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import com.mg.jsonhandler.JSONParser;
import com.mg.stock.constant.StockConstants;

/**
 * @author Mohak Gupta
 *
 */
public class FilesPathUtils {
	private static Logger log = Logger.getLogger(FilesPathUtils.class);

	private static FilesPathUtils filesPathUtils;

	public static FilesPathUtils getInstance() {
		if (filesPathUtils == null)
			filesPathUtils = new FilesPathUtils();
		return filesPathUtils;
	}

	public String getRunningDirectoryPath() {
		String directoryPath = "C";
		try {
			directoryPath = Paths.get(JSONParser.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.toString().substring(0, 1);
		} catch (URISyntaxException e) {
			log.error("Unable to get the working Directory");
		}
		return directoryPath;
	}

	public File createDirectoryAndFileIfNotExist(String fileName) {
		Path path = Paths.get(getRunningDirectoryPath() + StockConstants.DIRECTORY_PATH);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				log.error("Error Creating Directory : " + e);
			}
		}
		return createFileIfNotExist(fileName);
	}

	private File createFileIfNotExist(String fileName) {
		File file = new File(
				getRunningDirectoryPath() + StockConstants.DIRECTORY_PATH + fileName + StockConstants.JSON_SUFFIX);
		try {
			if (!file.exists())
				file.createNewFile();
		} catch (IOException e) {
			log.error("Error Creating File : " + fileName + ": " + e);
		}
		return file;
	}
}
