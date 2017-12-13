package com.mg.utils;

import java.time.LocalDate;

import javafx.scene.control.DatePicker;

public class DateUtils {

	private static final String DATEFORMAT = "dd/MM/yyyy";

	private DateUtils() {
	}

	public static LocalDate makeDate(DatePicker date) {
		return date.getValue();
	}

	public static void initializeDate(DatePicker date) {
		date.setPromptText(DATEFORMAT);
		date.setValue(LocalDate.now());
	}
}
