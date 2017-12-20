package com.mg.dialog.controller.factory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.mg.weld.TypeAnnotation;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@TypeAnnotation("Backup")
public class BackupController implements DialogHandlerFactory {

	private static Logger log = Logger.getLogger(BackupController.class);

	public BackupController(){
		// Needed for Weld
	}

	@Override
	public void popupHanderAction(TextField driveValue, Label labelMessage) {
		File sourcePath = new File(getRunningDirectoryPath() + ":\\AU\\");
		File destinationPath = new File(driveValue.getText() + "\\ATTIUTTAM_" + LocalDate.now() + "\\AU\\");
		if (sourcePath.exists())
			try {
				deleteDirectory(destinationPath);
				FileUtils.copyDirectory(sourcePath, destinationPath);
				labelMessage.setText("BACKUP SUCCESSFUL");
			} catch (IOException e) {
				log.error(ERROR_ADMIN);
			}
		else
			labelMessage.setText("Source/Destination Paths missing...");
	}

}
