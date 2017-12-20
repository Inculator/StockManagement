package com.mg.dialog.controller.factory;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import com.mg.jsonhandler.JSONParser;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@FunctionalInterface
public interface DialogHandlerFactory {

	public static final String ERROR_ADMIN = "Error taking backup. Please contact administrator for details.";

	public void popupHanderAction(TextField driveValue, Label labelMessage);

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
