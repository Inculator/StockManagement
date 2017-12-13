package com.mg.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mg.csms.beans.Item;
import com.mg.jsonhandler.JSONParser;
import com.mg.jsonhandler.JSONWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	private Text successMessage;

	@FXML
	public void initialize() {
		addItemButton.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				addItem();
		});
	}

	@FXML
	public void addItem() {
		try {
			if (itemName.getText().isEmpty())
				throw new IOException();

			Integer maxKey = 0;
			Item item = new Item(itemName.getText());
			Map<Integer, Object> itemMap = new JSONParser().getObjectFromJsonFile("ItemList");
			if (!itemMap.isEmpty())
				maxKey = Collections.max(itemMap.keySet());

			itemMap.entrySet().stream().forEach(itemFromMap -> {
				Integer innerMaxKey = 0;
				if (((Item) itemFromMap.getValue()).getItemName().equalsIgnoreCase(itemName.getText())) {
					innerMaxKey = ((Item) itemFromMap.getValue()).getId();
					item.setId(innerMaxKey);
				}
			});

			if (item.getId() == null)
				item.setId(maxKey + 1);

			itemMap.put(item.getId(), item);

			new JSONWriter().writeObjectToJson("ItemList", itemMap);
			clearUI();
			successMessage.setText("Item Added successfully !");
		} catch (IOException e1) {
			successMessage.setText("Error adding Item. Please make sure you enter some value.");
			log.error(e1.getMessage());
		}
	}

	private void clearUI() {
		itemName.setText("");
	}
}