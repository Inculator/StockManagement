package com.mg.controller;

import java.util.Collections;

import org.apache.log4j.Logger;

import com.mg.csms.beans.ColdStorage;
import com.mg.csms.beans.Vyaapari;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.model.ColdStorageJsonModel;
import com.mg.json.model.VyaapariJsonModel;
import com.mg.utils.DateUtils;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

/**
 * @author Mohak Gupta
 *
 */
public class ColdVyaapariController {
	private static Logger log = Logger.getLogger(ColdVyaapariController.class);

	@FXML
	private DatePicker coldDate;
	@FXML
	private TextField coldName;
	@FXML
	private TextField coldAdd;
	@FXML
	private TextField coldPhone;
	@FXML
	private DatePicker vyaapariDate;
	@FXML
	private TextField vyaapariName;
	@FXML
	private TextField vyaapariAddress;
	@FXML
	private TextField vyaapariNumber;

	@FXML
	private TableView<ColdStorage> coldListView;
	@FXML
	private TableColumn<ColdStorage, String> listColdName;
	@FXML
	private TableColumn<ColdStorage, String> listColdPhone;
	@FXML
	private TableColumn<ColdStorage, String> listColdAddress;

	@FXML
	private TableView<Vyaapari> vyaapariListView;
	@FXML
	private TableColumn<Vyaapari, String> listVyaapariName;
	@FXML
	private TableColumn<Vyaapari, String> listVyaapariPhone;
	@FXML
	private TableColumn<Vyaapari, String> listVyaapariAddress;

	@FXML
	private Tab addVyaapari;
	@FXML
	private Tab addColdStorage;
	@FXML
	private Tab coldStoreListTab;
	@FXML
	private Tab vyaapariListTab;

	@FXML
	private Button addColdStoreButton;
	@FXML
	private Button addVyaapariButton;

	@FXML
	private Text successMessage;
	@FXML
	private Text successMessage1;

	private JsonHandlerInterface jsonColdHandler;
	private JsonHandlerInterface jsonVyaapariHandler;

	@FXML
	protected void initialize() {
		try {
			jsonColdHandler = new ColdStorageJsonModel();
			jsonVyaapariHandler = new VyaapariJsonModel();
		} catch (Exception e) {
			successMessage.setText("Error in File Handling.");
			log.error(e.getMessage());
		}
		initializeDate();
		makeColdStoreList();
		makeVyaapariList();
		initializeButtonKeyAction();
	}

	private void initializeDate() {
		DateUtils.initializeDate(coldDate);
		DateUtils.initializeDate(vyaapariDate);
	}

	private void makeColdStoreList() {
		jsonColdHandler.makeListAndMapFromJson();
		initializeColdTable();
	}

	private void makeVyaapariList() {
		jsonVyaapariHandler.makeListAndMapFromJson();
		initializeVyaapariTable();
	}

	private void initializeButtonKeyAction() {
		addColdStoreButton.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				addColdStorage();
		});

		addVyaapariButton.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				addVyaapari();
		});
	}

	private void initializeColdTable() {
		coldListView.setEditable(true);
		listColdName.setCellValueFactory(new PropertyValueFactory<ColdStorage, String>("coldName"));
		listColdPhone.setCellValueFactory(new PropertyValueFactory<ColdStorage, String>("phoneNo"));
		listColdAddress.setCellValueFactory(new PropertyValueFactory<ColdStorage, String>("address"));
		if (!((ColdStorageJsonModel) jsonColdHandler).getColdStoreList().isEmpty())
			coldListView.setItems(
					FXCollections.observableList(((ColdStorageJsonModel) jsonColdHandler).getColdStoreList()));
	}

	private void initializeVyaapariTable() {
		vyaapariListView.setEditable(true);
		listVyaapariName.setCellValueFactory(new PropertyValueFactory<Vyaapari, String>("vyaapariName"));
		listVyaapariPhone.setCellValueFactory(new PropertyValueFactory<Vyaapari, String>("phoneNo"));
		listVyaapariAddress.setCellValueFactory(new PropertyValueFactory<Vyaapari, String>("address"));
		if (!((VyaapariJsonModel) jsonVyaapariHandler).getVyaapariList().isEmpty())
			vyaapariListView.setItems(
					FXCollections.observableList(((VyaapariJsonModel) jsonVyaapariHandler).getVyaapariList()));
	}

	@FXML
	public void addColdStorage() {
		try {
			makeColdStoreList();
			ColdStorage cold = makeColdStorage(new ColdStorage());
			writeColdObjectToJson(cold);
			successMessage.setText("Cold added successfully.");
			makeColdStoreList();
			clearUI();
		} catch (Exception e) {
			successMessage.setText("Make sure you have entererd all fields correctly !");
		}
	}

	private void writeColdObjectToJson(ColdStorage cold) {
		Integer maxKey = 0;
		if (!((ColdStorageJsonModel) jsonColdHandler).getColdStoreMap().isEmpty())
			maxKey = Collections.max(((ColdStorageJsonModel) jsonColdHandler).getColdStoreMap().keySet());
		cold.setColdId(maxKey + 1);
		((ColdStorageJsonModel) jsonColdHandler).setColdStoreMap(cold);
		jsonColdHandler.writeObjectToJson("ColdStorage", ((ColdStorageJsonModel) jsonColdHandler).getColdStoreMap());
	}

	private ColdStorage makeColdStorage(ColdStorage cold) {
		cold.setAddress(coldAdd.getText());
		cold.setPhoneNo(Long.parseLong(coldPhone.getText()));
		cold.setDate(DateUtils.makeDate(coldDate));
		cold.setColdName(coldName.getText());
		return cold;
	}

	@FXML
	protected void addVyaapari() {
		try {
			makeVyaapariList();
			Vyaapari vyaapari = makeVyaapari(new Vyaapari());
			writeVyaapariObjectToJson(vyaapari);
			successMessage1.setText("Vyaapari added successfully.");
			makeVyaapariList();
			clearUI();
		} catch (Exception e) {
			successMessage1.setText("Make sure you have entererd all fields correctly !");
		}
	}

	private void writeVyaapariObjectToJson(Vyaapari vyaapari) {
		Integer maxKey = 0;
		if (!((VyaapariJsonModel) jsonVyaapariHandler).getVyaapariMap().isEmpty())
			maxKey = Collections.max(((VyaapariJsonModel) jsonVyaapariHandler).getVyaapariMap().keySet());
		vyaapari.setVyaapariId(maxKey + 1);
		((VyaapariJsonModel) jsonVyaapariHandler).setVyaapariMap(vyaapari);
		jsonVyaapariHandler.writeObjectToJson("Vyaapari", ((VyaapariJsonModel) jsonVyaapariHandler).getVyaapariMap());
	}

	private Vyaapari makeVyaapari(Vyaapari vyaapari) {
		vyaapari.setAddress(vyaapariAddress.getText());
		vyaapari.setPhoneNo(Long.parseLong(vyaapariNumber.getText()));
		vyaapari.setDate(DateUtils.makeDate(vyaapariDate));
		vyaapari.setVyaapariName(vyaapariName.getText());
		return vyaapari;
	}

	private void clearUI() {
		coldName.setText("");
		coldAdd.setText("");
		coldPhone.setText("");
		vyaapariName.setText("");
		vyaapariAddress.setText("");
		vyaapariNumber.setText("");
	}

}
