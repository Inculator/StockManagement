package com.mg.controller;

public class PaidBillController extends BillingController{

	protected void filterPaidElements() {
		billingItemList.removeIf(item -> !item.getIsPaid());
	}
}
