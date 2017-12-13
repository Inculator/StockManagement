package com.mg.initialize.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * @author Mohak Gupta
 *
 */
public class LoadTabFxmlActionController {

	@FXML
	private Pane secPane;
	@FXML
	private Tab stockEntryTab;
	@FXML
	private Tab stockinHandTab;
	@FXML
	private Tab billingTab;
	@FXML
	private Tab paidBillTab;
	@FXML
	private Tab demandTab;
	@FXML
	private Tab coldVyaapariTab;
	@FXML
	private Tab completedstocksTab;
	@FXML
	private Tab addItemTab;

	@FXML
	protected void initialize() {
		stockEntryTab.setOnSelectionChanged(event -> loadTabBasedFXML(stockEntryTab, "/view/StockEntry.fxml"));
		stockinHandTab.setOnSelectionChanged(event -> loadTabBasedFXML(stockinHandTab, "/view/StockInHand.fxml"));
		demandTab.setOnSelectionChanged(event -> loadTabBasedFXML(demandTab, "/view/Demand.fxml"));
		billingTab.setOnSelectionChanged(event -> loadTabBasedFXML(billingTab, "/view/Billing.fxml"));
		paidBillTab.setOnSelectionChanged(event -> loadTabBasedFXML(paidBillTab, "/view/PaidBill.fxml"));
		coldVyaapariTab.setOnSelectionChanged(event -> loadTabBasedFXML(coldVyaapariTab, "/view/ColdStorage.fxml"));
		completedstocksTab.setOnSelectionChanged(event -> loadTabBasedFXML(completedstocksTab, "/view/CompletedStocks.fxml"));
		addItemTab.setOnSelectionChanged(event -> loadTabBasedFXML(addItemTab, "/view/AddItem.fxml"));
	}

	private void loadTabBasedFXML(Tab tab, String fxmlPath) {
		try {
			AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource(fxmlPath));
			tab.setContent(anchorPane);
		} catch (IOException e) {
		}
	}
}
