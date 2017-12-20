package com.mg.initialize.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * @author Mohak Gupta
 *
 */
public class LoginController {

	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";
	private static final String RESOURCES_LOGIN_PROPERTIES = "login.properties";
	private static final String VIEW_MAIN_MENU_FXML = "/view/MainMenu.fxml";

	@FXML
	private AnchorPane apDesignPane;
	@FXML
	private Hyperlink createAccount;
	@FXML
	private TextField tfUserName;
	@FXML
	private PasswordField pfUserPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Text successMessage;

	@FXML
	public void initialize() {
		btnLogin.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				btnLogin();
		});
	}

	@FXML
	public void btnLogin() {
		try (InputStream resourceStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(RESOURCES_LOGIN_PROPERTIES)) {
			Properties p = new Properties();
			p.load(resourceStream);

//			if (tfUserName.getText().equalsIgnoreCase(p.getProperty(USERNAME))
//					&& pfUserPassword.getText().equalsIgnoreCase(p.getProperty(PASSWORD)))
				makePane(VIEW_MAIN_MENU_FXML);
//			else
				successMessage.setText("Invalid Credentials !!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void makePane(String fxmlPath) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
			Pane cmdPane = fxmlLoader.load();
			apDesignPane.getChildren().clear();
			apDesignPane.getChildren().add(cmdPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
