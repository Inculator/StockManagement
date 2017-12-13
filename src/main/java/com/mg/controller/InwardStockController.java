package com.mg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mg.csms.beans.InwardStock;
import com.mg.csms.beans.InwardStockItem;
import com.mg.csms.beans.Item;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.model.ColdStorageJsonModel;
import com.mg.json.model.InwardStockItemJsonModel;
import com.mg.json.model.InwardStockJsonModel;
import com.mg.json.model.VyaapariJsonModel;
import com.mg.jsonhandler.JSONParser;
import com.mg.utils.DateUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class InwardStockController {

	private static Logger log = Logger.getLogger(InwardStockController.class);

	List<InwardStockItem> addItemList;

	@FXML
	private ComboBox<String> coldStoreList;
	@FXML
	private ComboBox<String> vyaapariList;
	@FXML
	private ComboBox<String> itemList;
	@FXML
	private TextField totalQuantity;
	@FXML
	private TextField gadiNo;
	@FXML
	private DatePicker stockInwardDate;
	@FXML
	private TextField lotNo;
	@FXML
	private TextField coldNo;
	@FXML
	private TextField quantity;
	@FXML
	private TextField rate;
	@FXML
	private TableView<InwardStockItem> itemListTable;

	@FXML
	private TableColumn<InwardStockItem, String> tableLotNo;
	@FXML
	private TableColumn<InwardStockItem, String> tableColdNo;
	@FXML
	private TableColumn<InwardStockItem, String> tableItem;
	@FXML
	private TableColumn<InwardStockItem, String> tableQuantity;
	@FXML
	private TableColumn<InwardStockItem, String> tableRate;

	@FXML
	private Button addButton;
	@FXML
	private Button submitButton;

	@FXML
	private Text successMessage;

	private JsonHandlerInterface jsonStockModel;
	private JsonHandlerInterface jsonStockItemModel;
	private JsonHandlerInterface jsonColdHandler;
	private JsonHandlerInterface jsonVyaapariHandler;

	@FXML
	protected void initialize() {
		try {
			jsonStockModel = new InwardStockJsonModel();
			jsonStockItemModel = new InwardStockItemJsonModel();
			jsonColdHandler = new ColdStorageJsonModel();
			jsonVyaapariHandler = new VyaapariJsonModel();
		} catch (Exception e) {
			successMessage.setText("Database errors occoured");
		}
		initializeItems();
		initializeTable();
		initializeButtonKeyAction();
	}

	private void initializeItems() {
		addItemList = new ArrayList<>();
		DateUtils.initializeDate(stockInwardDate);
		coldStoreList.setItems(getColdStoreList());
		vyaapariList.setItems(getVyaapariList());
		itemList.setItems(makeItemsList());
	}

	private void initializeTable() {
		itemListTable.setEditable(true);
		tableLotNo.setCellValueFactory(new PropertyValueFactory<InwardStockItem, String>("lotNo"));
		tableColdNo.setCellValueFactory(new PropertyValueFactory<InwardStockItem, String>("coldNo"));
		tableItem.setCellValueFactory(new PropertyValueFactory<InwardStockItem, String>("item"));
		tableQuantity.setCellValueFactory(new PropertyValueFactory<InwardStockItem, String>("quantity"));
		tableRate.setCellValueFactory(new PropertyValueFactory<InwardStockItem, String>("rate"));
	}

	private void initializeButtonKeyAction() {
		addButton.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				addItemToList();
		});

		submitButton.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				submitStock();
		});
	}

	@FXML
	protected void addItemToList() {
		try {
			itemListTable.setItems(getInwardStockItem());
		} catch (Exception e) {
			successMessage.setText("Make sure you have entererd all fields correctly !");
		}
	}

	private ObservableList<InwardStockItem> getInwardStockItem() {
		addItemList.add(addInwardStockItem());
		clearUI();
		return FXCollections.observableList(addItemList);
	}

	private InwardStockItem addInwardStockItem() {
		InwardStockItem inwardStockItem = new InwardStockItem();
		inwardStockItem.setLotNo(Integer.parseInt(lotNo.getText()));
		inwardStockItem.setColdNo(Integer.parseInt(coldNo.getText()));
		inwardStockItem.setItem(itemList.getValue());
		inwardStockItem.setQuantity(Integer.parseInt(quantity.getText()));
		if (!rate.getText().isEmpty())
			inwardStockItem.setRate(Float.parseFloat(rate.getText()));
		return inwardStockItem;
	}

	private void clearUI() {
		lotNo.clear();
		coldNo.clear();
		quantity.clear();
		rate.clear();
	}

	@FXML
	protected void submitStock() {
		try {
			InwardStock stock = makeInwardStock(new InwardStock());
			writeInwardStockToJson(stock);
			makeInwardStockItemsAndSave(stock);
			successMessage.setText("Lot added successfully.");
		} catch (Exception e) {
			successMessage.setText("Make sure you have entererd all fields correctly !");
		}
		clearOverallUI();
	}

	private void writeInwardStockToJson(InwardStock stock) {
		Integer maxKey = 0;
		jsonStockModel.makeListAndMapFromJson();
		if (!((InwardStockJsonModel) jsonStockModel).getStockMap().isEmpty())
			maxKey = Collections.max(((InwardStockJsonModel) jsonStockModel).getStockMap().keySet());
		stock.setStockId(maxKey + 1);
		((InwardStockJsonModel) jsonStockModel).setStockMap(stock);
		jsonStockModel.writeObjectToJson("InwardStock", ((InwardStockJsonModel) jsonStockModel).getStockMap());
	}

	private void makeInwardStockItemsAndSave(InwardStock stock) {
		itemListTable.getItems().stream().forEach(itemStockItem -> {
			InwardStockItem item = new InwardStockItem();
			item.setColdNo(itemStockItem.getColdNo());
			item.setItem(itemStockItem.getItem());
			item.setQuantity(itemStockItem.getQuantity());
			item.setRate(itemStockItem.getRate());
			item.setLotNo(itemStockItem.getLotNo());
			item.setEntryDate(stock.getDate());
			item.setGadiNo(stock.getGadiNo());
			item.setStockId(stock.getStockId());
			item.setBalance(itemStockItem.getQuantity());
			writeItemToJson(item);
		});
	}

	private void writeItemToJson(InwardStockItem item) {
		Integer maxKey = 0;
		jsonStockItemModel.makeListAndMapFromJson();
		if (!((InwardStockItemJsonModel) jsonStockItemModel).getStockItemMap().isEmpty())
			maxKey = Collections.max(((InwardStockItemJsonModel) jsonStockItemModel).getStockItemMap().keySet());
		item.setRecordId(maxKey + 1);
		((InwardStockItemJsonModel) jsonStockItemModel).setStockItemMap(item);
		jsonStockItemModel.writeObjectToJson("InwardStockItem",
				((InwardStockItemJsonModel) jsonStockItemModel).getStockItemMap());
	}

	private InwardStock makeInwardStock(InwardStock inwardStock) {
		inwardStock.setColdId(
				Integer.parseInt(coldStoreList.getValue().substring(0, coldStoreList.getValue().indexOf(':'))));
		inwardStock.setDate(DateUtils.makeDate(stockInwardDate));
		inwardStock.setGadiNo(gadiNo.getText());
		inwardStock.setVyaapariId(
				Integer.parseInt(vyaapariList.getValue().substring(0, vyaapariList.getValue().indexOf(':'))));
		inwardStock.setQty(Integer.parseInt(totalQuantity.getText()));
		return inwardStock;
	}

	private void clearOverallUI() {
		totalQuantity.clear();
		gadiNo.clear();
		itemListTable.setItems(null);
		coldStoreList.setValue("");
		vyaapariList.setValue("");
		itemList.setValue("");
		addItemList = new ArrayList<>();
		clearUI();
	}

	private ObservableList<String> getColdStoreList() {
		jsonColdHandler.makeListAndMapFromJson();
		List<String> coldStoreNameList = new ArrayList<>();
		((ColdStorageJsonModel) jsonColdHandler).getColdStoreList()
		.forEach(cold -> coldStoreNameList.add(cold.getColdId() + ": " + cold.getColdName()));
		return FXCollections.observableList(coldStoreNameList);
	}

	private ObservableList<String> getVyaapariList() {
		List<String> vyaapariNameList = new ArrayList<>();
		jsonVyaapariHandler.makeListAndMapFromJson();
		((VyaapariJsonModel) jsonVyaapariHandler).getVyaapariList().forEach(
				vyaapari -> vyaapariNameList.add(vyaapari.getVyaapariId() + ": " + vyaapari.getVyaapariName()));
		return FXCollections.observableList(vyaapariNameList);
	}

	private ObservableList<String> makeItemsList() {
		List<String> itemsList = new ArrayList<>();
		try{
			Map<Integer, Object> itemMap = new JSONParser().getObjectFromJsonFile("ItemList");
			itemMap.entrySet().forEach(item -> itemsList.add(((Item)item.getValue()).getItemName()));
			Collections.sort(itemsList);
		} catch (IOException e) {
			log.debug(e.getMessage());
		}
		return FXCollections.observableList(itemsList);
	}

	@FXML
	public void deleteSelectedRow() {
		addItemList.remove(itemListTable.getSelectionModel().getSelectedItem());
		itemListTable.setItems(FXCollections.observableList(addItemList));
	}
}
