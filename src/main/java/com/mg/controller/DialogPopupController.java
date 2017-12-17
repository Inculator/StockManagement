package com.mg.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.mg.jsonhandler.JSONParser;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogPopupController {

	private static final String PASSWORD = "password";
	private static final String RESOURCES_LOGIN_PROPERTIES = "login.properties";
	private TextField driveValue;
	private PasswordField password;
	private Label l5;

	private void popupButtonAction(String key) {
		try (InputStream resourceStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(RESOURCES_LOGIN_PROPERTIES)) {
			Properties p = new Properties();
			p.load(resourceStream);

			if (password != null)
				if (password.getText().equalsIgnoreCase(p.getProperty(PASSWORD)))
					switch (key) {
					case "Backup":
						popupBackupAction();
						break;
					case "Both":
						popupDeleteAndBackupAction();
						break;
					default:
						break;
					}
				else
					l5.setText("Please enter a password");
			else
				l5.setText("Please enter a password");
		} catch (IOException e) {
			e.printStackTrace();
		}

		password.setText("");
		driveValue.setText("");
	}

	private void popupBackupAction() {
		File sourcePath = new File(getRunningDirectoryPath() + ":\\AU\\");
		File destinationPath = new File(driveValue.getText() + "\\ATTIUTTAM_" + LocalDate.now() + "\\AU\\");
		if (sourcePath.exists())
			try {
				deleteDirectory(destinationPath);
				FileUtils.copyDirectory(sourcePath, destinationPath);
				l5.setText("BACKUP SUCCESSFUL");
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			l5.setText("Source/Destination Paths missing...");
	}

	private void popupDeleteAndBackupAction() {
		File sourcePath = new File(getRunningDirectoryPath() + ":\\AU\\");
		File destinationPath = new File(driveValue.getText() + "\\ATTIUTTAM_" + LocalDate.now());
		if (sourcePath.exists())
			try {
				deleteDirectory(destinationPath);
				FileUtils.copyDirectory(sourcePath, destinationPath);
				deleteDirectory(sourcePath);
				l5.setText("BACKUP SUCCESSFUL. APPLICATION HAS BEEN RESET !");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static void deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				deleteDirectory(children[i]);
			}
		}
		dir.delete();
	}

	public void designScreen() {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		VBox dialogVbox = new VBox(20);
		dialog.setTitle("Popup for Delete/Backup");
		Label l1 = new Label("Drive for Backup");
		Label l3 = new Label("Password");
		l5 = new Label("");

		Button fileOpen = new Button("Open Backup Path");
		driveValue = new TextField();
		password = new PasswordField();
		Button popupBackup = new Button("BACKUP");
		Button popupDeleteAndBackup = new Button("BACKUP & DELETE");

		HBox hb = new HBox();
		hb.getChildren().addAll(l1, driveValue, fileOpen);
		hb.setSpacing(5);
		hb.setAlignment(Pos.CENTER);

		HBox hb2 = new HBox(2);
		hb2.getChildren().addAll(l3, password);
		hb2.setPadding(new Insets(2));
		hb2.setSpacing(5);
		hb2.setAlignment(Pos.CENTER);

		HBox hb4 = new HBox();
		hb4.getChildren().addAll(l5);
		hb4.setAlignment(Pos.CENTER);

		HBox buttons = new HBox(2);
		buttons.setPadding(new Insets(2));
		buttons.setSpacing(10);
		buttons.getChildren().addAll(popupBackup, popupDeleteAndBackup);
		buttons.setAlignment(Pos.CENTER);
		final DirectoryChooser directoryChooser = new DirectoryChooser();

		dialogVbox.getChildren().add(hb);
		dialogVbox.getChildren().add(hb2);
		dialogVbox.getChildren().add(buttons);
		dialogVbox.getChildren().add(hb4);

		fileOpen.setOnAction(e -> {
			File file = directoryChooser.showDialog(dialog);
			driveValue.setText(file.getPath());
		});

		popupBackup.setOnAction(event -> popupButtonAction("Backup"));
		popupDeleteAndBackup.setOnAction(event -> popupButtonAction("Both"));

		Scene dialogScene = new Scene(dialogVbox, 400, 200);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	private String getRunningDirectoryPath() {
		String directoryPath = "C";
		try {
			directoryPath = Paths.get(JSONParser.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.toString().substring(0, 1);
		} catch (URISyntaxException e) {
		}
		return directoryPath;
	}

}
