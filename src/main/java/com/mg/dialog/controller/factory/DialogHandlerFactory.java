package com.mg.dialog.controller.factory;

import com.mg.filesystem.directory.DirectoryUtils;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@FunctionalInterface
public interface DialogHandlerFactory extends DirectoryUtils {

	public static final String ERROR_ADMIN = "Error taking backup. Please contact administrator for details.";

	public void popupHanderAction(TextField driveValue, Label labelMessage);

}
