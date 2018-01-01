package com.mg.filesystem.directory;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import com.mg.jsonhandler.JSONParser;

public interface DirectoryUtils {

	default void deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				deleteDirectory(children[i]);
			}
		}
		dir.delete();
	}

	default String getRunningDirectoryPath() {
		String directoryPath = "C";
		try {
			directoryPath = Paths.get(JSONParser.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.toString().substring(0, 1);
		} catch (URISyntaxException e) {
		}
		return directoryPath;
	}
}
