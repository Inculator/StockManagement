package com.mg.controller;

import java.util.Collections;

import org.apache.log4j.Logger;

import com.mg.csms.beans.Item;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.model.ItemJsonModel;
import com.mg.stock.constant.StockConstants;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class AddItemController {

	public static final Logger log = Logger.getLogger(AddItemController.class);

	private JsonHandlerInterface jsonItemModel;
	@FXML
	private TextField itemName;
	@FXML
	private Button addItemButton;
	@FXML
	private ListView<Item> itemListView;
	@FXML
	private Text successMessage;

	@FXML
	public void initialize() {
		jsonItemModel = new ItemJsonModel();
		updateItemListView();
		addItemButton.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				addItem();
		});
	}

	@FXML
	public void addItem() {
		Item item = new Item(itemName.getText());
		if ("".equalsIgnoreCase(item.getItemName()))
			successMessage.setText(StockConstants.VALUE_ERROR_MESSAGE);
		else {
			writeItemObjectToJson(item);
			successMessage.setText("Item Added successfully !");
		}
		clearUI();
		updateItemListView();
	}

	private void writeItemObjectToJson(Item item) {
		Integer maxKey = 0;
		if (!((ItemJsonModel) jsonItemModel).getItemMap().isEmpty())
			maxKey = Collections.max(((ItemJsonModel) jsonItemModel).getItemMap().keySet());
		item.setId(maxKey + 1);
		((ItemJsonModel) jsonItemModel).setItemMap(item);
		jsonItemModel.writeObjectToJson(Item.class.getSimpleName(), ((ItemJsonModel) jsonItemModel).getItemMap());
	}

	private void updateItemListView() {
		jsonItemModel.makeListAndMapFromJson();
		itemListView.setItems(FXCollections.observableList(((ItemJsonModel) jsonItemModel).getItemList()));
	}

	private void clearUI() {
		itemName.setText("");
	}

	@FXML
	protected void deleteItemAction() {
		if (itemListView.getItems() == null || itemListView.getSelectionModel().getSelectedItem() == null)
			successMessage.setText("Select a Record to Delete !");
		else {
			((ItemJsonModel) jsonItemModel).getItemMap()
					.remove(itemListView.getSelectionModel().getSelectedItem().getId());
			jsonItemModel.writeObjectToJson(Item.class.getSimpleName(), ((ItemJsonModel) jsonItemModel).getItemMap());
			updateItemListView();
			successMessage.setText("Item Removed !");
		}
	}

}