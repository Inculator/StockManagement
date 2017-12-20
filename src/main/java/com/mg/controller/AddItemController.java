package com.mg.controller;

import java.util.Collections;

import org.apache.log4j.Logger;

import com.mg.csms.beans.Item;
import com.mg.json.controller.JsonHandlerInterface;
import com.mg.json.model.ItemJsonModel;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class AddItemController {
	public static final Logger log = Logger.getLogger(AddItemController.class);

	@FXML
	private TextField itemName;
	@FXML
	private Button addItemButton;
	@FXML
	private ListView<Item> itemListView;

	private JsonHandlerInterface jsonItemModel;

	@FXML
	private Text successMessage;

	@FXML
	public void initialize() {
		addItemButton.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				addItem();
		});
		jsonItemModel = new ItemJsonModel();
		updateItemListView();
	}

	@FXML
	public void addItem() {
		updateItemListView();
		Item item = new Item(itemName.getText());
		if("".equalsIgnoreCase(item.getItemName()))
			successMessage.setText("Please enter some value !!");
		else{
			writeVyaapariObjectToJson(item);
			successMessage.setText("Item Added successfully !");
		}
		clearUI();
		updateItemListView();
	}

	private void writeVyaapariObjectToJson(Item item) {
		Integer maxKey = 0;
		if (!((ItemJsonModel) jsonItemModel).getItemMap().isEmpty())
			maxKey = Collections.max(((ItemJsonModel) jsonItemModel).getItemMap().keySet());
		item.setId(maxKey + 1);
		((ItemJsonModel) jsonItemModel).setItemMap(item);
		jsonItemModel.writeObjectToJson("ItemList", ((ItemJsonModel) jsonItemModel).getItemMap());
	}

	private void updateItemListView() {
		jsonItemModel.makeListAndMapFromJson();
		itemListView.setItems(FXCollections.observableList(((ItemJsonModel) jsonItemModel).getItemList()));
	}

	private void clearUI() {
		itemName.setText("");
	}

	@FXML
	protected void deleteItemAction(){
		((ItemJsonModel) jsonItemModel).getItemMap().remove(itemListView.getSelectionModel().getSelectedItem().getId());
		jsonItemModel.writeObjectToJson("ItemList", ((ItemJsonModel) jsonItemModel).getItemMap());
		updateItemListView();
	}

}