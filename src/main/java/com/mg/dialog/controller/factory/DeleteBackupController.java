package com.mg.dialog.controller.factory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.mg.csms.beans.BillingDetails;
import com.mg.csms.beans.Demand;
import com.mg.csms.beans.InwardStock;
import com.mg.csms.beans.InwardStockItem;
import com.mg.weld.TypeAnnotation;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@TypeAnnotation("Both")
public class DeleteBackupController implements DialogHandlerFactory {

	private static Logger log = Logger.getLogger(DeleteBackupController.class);

	public DeleteBackupController() {
		// Needed for Weld
	}

	@Override
	public void popupHanderAction(TextField driveValue, Label labelMessage) {
		File sourcePath = new File(getRunningDirectoryPath() + ":\\AU\\");
		File destinationPath = new File(driveValue.getText() + "\\ATTIUTTAM_" + LocalDate.now());
		if (sourcePath.exists())
			try {
				deleteDirectory(destinationPath);
				FileUtils.copyDirectory(sourcePath, destinationPath);
				deleteDirectory(sourcePath);
				labelMessage.setText("BACKUP SUCCESSFUL. APPLICATION HAS BEEN RESET ! RESTART APPLICATION");
			} catch (IOException e) {
				log.error(ERROR_ADMIN);
			}
	}

	@Override
	public void deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++)
				deleteDirectory(children[i]);
		}
		if (isFileToBeDeleted(dir))
			dir.delete();
	}

	private boolean isFileToBeDeleted(File dir) {
		return dir.getName().contains(InwardStockItem.class.getSimpleName())
				|| dir.getName().contains(InwardStock.class.getSimpleName())
				|| dir.getName().contains(Demand.class.getSimpleName())
				|| dir.getName().contains(BillingDetails.class.getSimpleName());
	}
}
