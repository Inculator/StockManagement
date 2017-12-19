package com.mg.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PaidBillController extends BillingController {

	@FXML
	private Button clearAndBackup;

	@Override
	protected void filterPaidElements() {
		billingItemList.removeIf(item -> !item.getIsPaid());
	}

	@FXML
	public void clearRecordsAction() {
		DialogPopupController.getInstance().designScreen();
	}
}
