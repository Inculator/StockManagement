package com.mg.controller;

import com.mg.csms.beans.InwardStockItem;

public class CompletedStocksController extends StockInHandController {

	@Override
	protected boolean filterStockItemCondition(InwardStockItem element) {
		return element.getBalance() == 0;
	}

}
