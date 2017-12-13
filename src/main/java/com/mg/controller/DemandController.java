package com.mg.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.mg.csms.beans.Demand;
import com.mg.csms.beans.InwardStockItem;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.model.BillingJsonModel;
import com.mg.json.model.DemandJsonModel;
import com.mg.json.model.InwardStockItemJsonModel;
import com.mg.utils.DateUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
public class DemandController {
	private static final String MESSAGE_SEPERATOR = " AND ";

	private static Logger log = Logger.getLogger(DemandController.class);

	@FXML
	private DatePicker demandDate;
	@FXML
	private TextField coldNo;
	@FXML
	private TextField quantity;
	@FXML
	private TextField chalanNumber;
	@FXML
	private TableView<Demand> itemListTable;
	@FXML
	private TableColumn<Demand, String> tableColdNo;
	@FXML
	private TableColumn<Demand, String> tableQuantity;
	@FXML
	private TableColumn<Demand, String> tableChalanNumber;
	@FXML
	private Text successMessage;
	List<Demand> addItemList;
	private StringBuilder errorMessage;
	@FXML
	private Button addDemandToListButton;
	@FXML
	private Button submitDemandButton;

	private JsonHandlerInterface jsonDemandModel;
	private JsonHandlerInterface jsonStockItemModel;
	private JsonHandlerInterface jsonBillingModel;

	@FXML
	private void initialize() {
		try {
			jsonDemandModel = new DemandJsonModel();
			jsonStockItemModel = new InwardStockItemJsonModel();
			jsonBillingModel = new BillingJsonModel();
		} catch (Exception e) {
			successMessage.setText("Database errors occoured");
		}
		errorMessage = new StringBuilder("");
		addItemList = new ArrayList<>();
		DateUtils.initializeDate(demandDate);
		itemListTable.setEditable(true);
		tableColdNo.setCellValueFactory(new PropertyValueFactory<Demand, String>("coldNo"));
		tableQuantity.setCellValueFactory(new PropertyValueFactory<Demand, String>("quantity"));
		tableChalanNumber.setCellValueFactory(new PropertyValueFactory<Demand, String>("chalanNumber"));
		initializeButtonKeyActions();
	}

	private void initializeButtonKeyActions() {
		addDemandToListButton.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				addDemandToList();
		});
		submitDemandButton.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				submitDemand();
		});
	}

	@FXML
	private void addDemandToList() {
		itemListTable.setItems(getDemandItem());
	}

	private ObservableList<Demand> getDemandItem() {
		addItemList.add(addDemandItem());
		clearUI();
		return FXCollections.observableList(addItemList);
	}

	private Demand addDemandItem() {
		Demand demand = new Demand();
		demand.setQuantity(Integer.parseInt(quantity.getText()));
		demand.setColdNo(Integer.parseInt(coldNo.getText()));
		if (!chalanNumber.getText().isEmpty())
			demand.setChalanNumber(Integer.parseInt(chalanNumber.getText()));
		return demand;
	}

	private void clearUI() {
		coldNo.clear();
		quantity.clear();
		chalanNumber.clear();
	}

	@FXML
	private void submitDemand() {
		try {
			successMessage.setText("");
			makeDemandItemsAndSave();
		} catch (Exception e) {
			log.debug(e);
			successMessage.setText("Some Error Occured !!! Make sure you have entererd all fields correctly !");
		}
		if ("".equalsIgnoreCase(successMessage.getText()))
			successMessage.setText("Demand added successfully.");
		clearOverallUI();
	}

	private void makeDemandItemsAndSave() {
		itemListTable.getItems().stream().forEach(demandItem -> {
			Demand demand = new Demand();
			if (isValidColdNo(demandItem.getColdNo())) {
				demand.setColdNo(demandItem.getColdNo());
				demand.setDemandDate(DateUtils.makeDate(demandDate));
				demand.setQuantity(demandItem.getQuantity());
				if (demandItem.getChalanNumber() != null)
					demand.setChalanNumber(demandItem.getChalanNumber());
				if (updateStockItemListBalance(demandItem))
					writeDemandToJson(demand);
				else
					successMessage.setText(errorMessage.toString());
			} else
				successMessage.setText("Cold Id " + demandItem.getColdNo()
						+ " is not correct ! Verify that you made an entry in stock earlier.");
		});
	}

	private void writeDemandToJson(Demand demand) {
		Integer maxKey = 0;
		jsonDemandModel.makeListAndMapFromJson();
		if (!((DemandJsonModel) jsonDemandModel).getDemandMap().isEmpty())
			maxKey = Collections.max(((DemandJsonModel) jsonDemandModel).getDemandMap().keySet());
		demand.setDemandId(maxKey + 1);
		((DemandJsonModel) jsonDemandModel).setDemandMap(demand);
		jsonDemandModel.writeObjectToJson("Demand", ((DemandJsonModel) jsonDemandModel).getDemandMap());
	}

	private boolean updateStockItemListBalance(Demand demandItem) {
		jsonStockItemModel.makeListAndMapFromJson();
		Optional<InwardStockItem> item = ((InwardStockItemJsonModel) jsonStockItemModel).getStockItemList().stream()
				.filter(ele -> ele.getColdNo().toString().equalsIgnoreCase(demandItem.getColdNo().toString()))
				.findFirst();
		Boolean flag = false;
		if (item.isPresent())
			flag = ifItemPresent(demandItem, item);
		else
			flag = ifItemNotPresent();
		return flag;
	}

	private boolean ifItemNotPresent() {
		if ("".equalsIgnoreCase(successMessage.getText()))
			errorMessage.append("Cold No Does not exist. Please enter correct Cold No to place Demand.");
		else
			errorMessage.append(successMessage.getText() + MESSAGE_SEPERATOR
					+ "Cold No Does not exist. Please enter correct Cold No to place Demand.");
		return false;
	}

	private boolean ifItemPresent(Demand demandItem, Optional<InwardStockItem> item) {
		if (item.isPresent()) {
			Integer balance = item.get().getBalance() - demandItem.getQuantity();
			if (item.get().getBalance() == 0)
				if ("".equalsIgnoreCase(successMessage.getText()))
					errorMessage.append(" LOT " + item.get().getColdNo() + " Completed. Balance: 0");
				else
					errorMessage.append(successMessage.getText() + MESSAGE_SEPERATOR + " LOT " + item.get().getColdNo()
							+ "Completed. Balance: 0");
			else if (balance < 0)
				if ("".equalsIgnoreCase(successMessage.getText()))
					errorMessage.append("Insufficient quantity avaiable for COLD No: " + item.get().getColdNo()
							+ ". Available: " + item.get().getBalance());
				else
					errorMessage
							.append(errorMessage + MESSAGE_SEPERATOR + "Insufficient quantity avaiable for COLD No: "
									+ item.get().getColdNo() + ". Available: " + item.get().getBalance());
			else {
				item.get().setBalance(balance);
				jsonStockItemModel.makeListAndMapFromJson();
				((InwardStockItemJsonModel) jsonStockItemModel).setStockItemMap(item.get());
				jsonStockItemModel.writeObjectToJson("InwardStockItem",
						((InwardStockItemJsonModel) jsonStockItemModel).getStockItemMap());
				if (balance == 0)
					makeEntryInBillingFile(item.get());
				return true;
			}
			return false;
		}
		return false;
	}

	private void makeEntryInBillingFile(InwardStockItem inwardStockItem) {
		jsonBillingModel.makeListAndMapFromJson();
		((BillingJsonModel) jsonBillingModel).setBillingMap(((BillingJsonModel) jsonBillingModel)
				.makeBillingDetailsObjectFromInwardStockItemObject(inwardStockItem));
		jsonBillingModel.writeObjectToJson("Billing", ((BillingJsonModel) jsonBillingModel).getBillingMap());
	}

	private boolean isValidColdNo(Integer coldNo) {
		jsonStockItemModel.makeListAndMapFromJson();
		return ((InwardStockItemJsonModel) jsonStockItemModel).getStockItemList().stream()
				.anyMatch(item -> item.getColdNo().equals(coldNo));
	}

	private void clearOverallUI() {
		itemListTable.setItems(null);
		addItemList.clear();
		clearUI();
	}
}
