package com.mg.authentication.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.swing.filechooser.FileSystemView;

import com.mg.authentication.CheckAuthenticDrive;
import com.mg.stock.constant.StockConstants;

public class CheckDriveAuthentication implements CheckAuthenticDrive {

	private static final String DRIVE_NAME = "driveName";
	private static final String DRIVE_TYPE = "driveType";
	private static final String DRIVE = "drive";
	private Properties properties;

	@Override
	public boolean authenticationCheck() {
		getPropertiesForAuth();
		if (getRunningDirectoryPath().equalsIgnoreCase(properties.getProperty(DRIVE))) {
			FileSystemView fsv = FileSystemView.getFileSystemView();
			File[] f = File.listRoots();
			List<File> fileList = new ArrayList<>();
			fileList = Arrays.asList(f);
			String driveDisplayName = properties.getProperty(DRIVE_NAME) + " (" + properties.getProperty(DRIVE)
					+ ":)";
			return fileList.stream().anyMatch(file -> fsv.getSystemDisplayName(file).equalsIgnoreCase(driveDisplayName)
					&& fsv.getSystemTypeDescription(file).equalsIgnoreCase(properties.getProperty(DRIVE_TYPE)));
		}
		return false;
	}

	private void getPropertiesForAuth() {
		properties = new Properties();
		try (InputStream resourceStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(StockConstants.RESOURCES_AUTH_PROPERTIES)) {
			properties.load(resourceStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
