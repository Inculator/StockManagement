package com.mg.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mg.dialog.controller.factory.DialogHandlerFactory;
import com.mg.weld.WeldManager;

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

	private static final String ERROR_ADMIN = "Error taking backup. Please contact administrator for details.";

	private static Logger log = Logger.getLogger(DialogPopupController.class);

	private static DialogPopupController dialogPopupController;
	private static final String RESOURCES_LOGIN_PROPERTIES = "login.properties";
	private TextField driveValue;
	private PasswordField password;
	private Label labelMessage;

	public void designScreen() {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.WINDOW_MODAL);
		VBox dialogVbox = new VBox(20);
		dialog.setTitle("Popup for Delete/Backup");
		Label l1 = new Label("Drive for Backup");
		Label l3 = new Label("Password");
		labelMessage = new Label("");

		Button fileOpen = new Button("Open Backup Path");
		driveValue = new TextField();
		password = new PasswordField();
		Button popupBackup = new Button("BACKUP");
		Button popupDeleteAndBackup = new Button("BACKUP & RESET");

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
		hb4.getChildren().addAll(labelMessage);
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

	private void popupButtonAction(String key) {
		try (InputStream resourceStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(RESOURCES_LOGIN_PROPERTIES)) {
			Properties p = new Properties();
			p.load(resourceStream);

			if (password != null && driveValue != null){
				if (password.getText().equalsIgnoreCase(p.getProperty("password")))
					WeldManager.getInstance().find(DialogHandlerFactory.class, key).popupHanderAction(driveValue, labelMessage);
				else
					labelMessage.setText("Please enter a password");
				clearUI();
			}
			else
				labelMessage.setText("Please enter a password/ Drive value !");
		} catch (IOException e) {
			log.error(ERROR_ADMIN);
		}
	}

	private void clearUI() {
		password.setText("");
		driveValue.setText("");
	}

	public static DialogPopupController getInstance() {
		if (dialogPopupController == null)
			dialogPopupController = new DialogPopupController();
		return dialogPopupController;
	}

}
