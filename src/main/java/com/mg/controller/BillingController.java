package com.mg.controller;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mg.csms.beans.BillingDetails;
import com.mg.csms.beans.ColdStorage;
import com.mg.csms.beans.Demand;
import com.mg.csms.beans.InwardStock;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.model.BillingJsonModel;
import com.mg.json.model.ColdStorageJsonModel;
import com.mg.json.model.DemandJsonModel;
import com.mg.json.model.InwardStockJsonModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

/**
 * @author Mohak Gupta
 *
 */
public class BillingController {

	private static final String SELECT_ENTRY_MESSAGE = "Please select an entry in the above List View !!!";
	protected List<BillingDetails> billingItemList;
	private List<Demand> demandListToShow;
	private JsonHandlerInterface jsonStockModel;
	private JsonHandlerInterface jsonColdHandler;
	private JsonHandlerInterface jsonDemandHandler;
	private JsonHandlerInterface jsonBillingModel;

	@FXML
	private TableView<BillingDetails> stockListView;
	@FXML
	private TableColumn<BillingDetails, String> itemColdNo;
	@FXML
	private TableColumn<BillingDetails, String> itemQuantity;
	@FXML
	private TableColumn<BillingDetails, String> entryDate;
	@FXML
	private TableColumn<BillingDetails, String> itemLotNo;
	@FXML
	private TableColumn<BillingDetails, String> itemColdStore;
	@FXML
	private TableColumn<BillingDetails, String> itemBhada;
	@FXML
	private TableColumn<BillingDetails, String> itemBillAmount;
	@FXML
	private TableView<Demand> demandListTable;
	@FXML
	private TableColumn<Demand, String> demandTableDate;
	@FXML
	private TableColumn<Demand, String> demandTableQuantity;
	@FXML
	private TableColumn<Demand, String> demandTableChalanNumber;
	@FXML
	private TableColumn<Demand, String> demandBillAmount;
	@FXML
	private Button calculateBillButton;
	@FXML
	private Button isBillPaid;
	@FXML
	private TextField coldBhada;
	@FXML
	private Text successMessage;

	@FXML
	public void initialize() {
		initializeObjects();
		initializeTableViews();
		addTableRowAction();
		populateBillingListView();
		addButtonKeyAction();
	}

	private void initializeObjects() {
		demandListToShow = new ArrayList<>();
		billingItemList = new ArrayList<>();
		jsonStockModel = new InwardStockJsonModel();
		jsonBillingModel = new BillingJsonModel();
		jsonColdHandler = new ColdStorageJsonModel();
		jsonDemandHandler = new DemandJsonModel();
	}

	private void initializeTableViews() {
		initializeBillingTableView();
		initializeDemandListTableView();
	}

	private void initializeBillingTableView() {
		stockListView.setEditable(true);
		entryDate.setCellValueFactory(new PropertyValueFactory<BillingDetails, String>("entryDate"));
		entryDate.setCellValueFactory(item -> {
			return new SimpleStringProperty(
					item.getValue().getEntryDate().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
		});
		itemColdNo.setCellValueFactory(new PropertyValueFactory<BillingDetails, String>("coldNo"));
		itemQuantity.setCellValueFactory(new PropertyValueFactory<BillingDetails, String>("quantity"));
		itemLotNo.setCellValueFactory(new PropertyValueFactory<BillingDetails, String>("lotNo"));
		itemColdStore.setCellValueFactory((CellDataFeatures<BillingDetails, String> data) -> {
			return initializeColdStorageName(data);
		});
		itemBhada.setCellValueFactory(new PropertyValueFactory<BillingDetails, String>("bhada"));
		itemBillAmount.setCellValueFactory(new PropertyValueFactory<BillingDetails, String>("totalBillAmount"));
	}

	private ObservableValue<String> initializeColdStorageName(CellDataFeatures<BillingDetails, String> data) {
		jsonStockModel.makeListAndMapFromJson();
		Optional<InwardStock> stock = ((InwardStockJsonModel) jsonStockModel).getStockList().stream()
				.filter(stockObject -> stockObject.getStockId() == data.getValue().getStockId()
						|| stockObject.getStockId().equals(data.getValue().getStockId()))
				.findAny();
		Optional<ColdStorage> coldObject = getColdStoreList().stream().filter(
				cold -> stock.get().getColdId() == cold.getColdId() || stock.get().getColdId().equals(cold.getColdId()))
				.findAny();
		if (coldObject.isPresent())
			return new SimpleStringProperty(coldObject.get().getColdName());
		return null;
	}

	private void initializeDemandListTableView() {
		demandListTable.setEditable(true);
		demandTableDate.setCellValueFactory(new PropertyValueFactory<Demand, String>("demandDate"));
		demandTableDate.setCellValueFactory(item -> {
			return new SimpleStringProperty(
					item.getValue().getDemandDate().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
		});
		demandTableQuantity.setCellValueFactory(new PropertyValueFactory<Demand, String>("quantity"));
		demandTableChalanNumber.setCellValueFactory(new PropertyValueFactory<Demand, String>("chalanNumber"));
		demandBillAmount.setCellValueFactory(new PropertyValueFactory<Demand, String>("billAmount"));
	}

	private void populateBillingListView() {
		jsonBillingModel.makeListAndMapFromJson();
		billingItemList = ((BillingJsonModel) jsonBillingModel).getBillingList();
		if (!billingItemList.isEmpty())
			filterPaidElements();
		stockListView.setItems(FXCollections.observableList(billingItemList));
	}

	private void addButtonKeyAction() {
		if (calculateBillButton != null)
			calculateBillButton.setOnKeyPressed(e -> {
				if (e.getCode().equals(KeyCode.ENTER))
					calculateBill();
			});
		if (isBillPaid != null)
			isBillPaid.setOnKeyPressed(e -> {
				if (e.getCode().equals(KeyCode.ENTER))
					billPaidAction();
			});
	}

	protected void filterPaidElements() {
		billingItemList.removeIf(item -> item.getIsPaid());
	}

	private void addTableRowAction() {
		stockListView.getSelectionModel().selectedIndexProperty().addListener(num -> {
			if (!stockListView.getSelectionModel().isEmpty())
				populateDemandTable(stockListView.getSelectionModel().selectedItemProperty().get().getColdNo());
		});
	}

	private void populateDemandTable(Integer coldNo) {
		jsonDemandHandler.makeListAndMapFromJson();
		demandListToShow = ((DemandJsonModel) jsonDemandHandler).getDemandList().stream()
				.filter(demand -> demand.getColdNo().toString().equalsIgnoreCase(coldNo.toString()))
				.collect(Collectors.toList());
		demandListTable.setItems(FXCollections.observableList(demandListToShow));
	}

	private List<ColdStorage> getColdStoreList() {
		jsonColdHandler.makeListAndMapFromJson();
		return ((ColdStorageJsonModel) jsonColdHandler).getColdStoreList();
	}

	@FXML
	private void calculateBill() {
		BillingDetails billObject = stockListView.getSelectionModel().selectedItemProperty().get();
		if (billObject == null)
			successMessage.setText(SELECT_ENTRY_MESSAGE);
		else {
			calculateBillForEachDemand(billObject);
			writeUpdatedObjectsToJson(billObject);
			refreshScreen();
			successMessage.setText("Bill Generated. Please check respective entry for bill amount.");
		}
	}

	private void writeUpdatedObjectsToJson(BillingDetails billObject) {
		updateBillingMapAndWriteToJson(billObject);
		jsonDemandHandler.writeObjectToJson(Demand.class.getSimpleName(), ((DemandJsonModel) jsonDemandHandler).getDemandMap());
	}

	private void calculateBillForEachDemand(BillingDetails billObject) {
		Integer bhada = Integer.parseInt(coldBhada.getText());
		ArrayList<Integer> billList = new ArrayList<>();
		demandListToShow.stream().forEach(demand -> {
			Integer months = (int) Math
					.ceil((double) ChronoUnit.DAYS.between(billObject.getEntryDate(), demand.getDemandDate()) / 30) + 1;
			Integer bill = demand.getQuantity() * bhada * months;
			demand.setBillAmount(bill);
			((DemandJsonModel) jsonDemandHandler).setDemandMap(demand);
			billList.add(bill);
		});
		billObject.setBhada(bhada);
		billObject.setTotalBillAmount(billList.stream().mapToInt(i -> i).sum());
	}

	@FXML
	private void billPaidAction() {
		final BillingDetails billObject = stockListView.getSelectionModel().selectedItemProperty().get();
		if (billObject == null)
			successMessage.setText(SELECT_ENTRY_MESSAGE);
		else {
			billObject.setIsPaid(true);
			updateBillingMapAndWriteToJson(billObject);
			refreshScreen();
			successMessage.setText("Bill marked paid. Please check Paid Entries Tab for Paid Bills.");
		}
	}

	private void updateBillingMapAndWriteToJson(final BillingDetails billObject) {
		((BillingJsonModel) jsonBillingModel).setBillingMap(billObject);
		jsonBillingModel.writeObjectToJson(BillingDetails.class.getSimpleName(), ((BillingJsonModel) jsonBillingModel).getBillingMap());
	}

	private void refreshScreen() {
		populateBillingListView();
		demandListTable.setItems(null);
		coldBhada.setText("");
	}
}
