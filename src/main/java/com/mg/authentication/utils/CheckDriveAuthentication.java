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
	private Properties properties;

	@Override
	public boolean authenticationCheck() {
		getPropertiesForAuth();
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File[] f = File.listRoots();
		List<File> fileList = new ArrayList<>();
		fileList = Arrays.asList(f);
		return fileList.stream().anyMatch(file -> isCorrectPath(fsv, file));
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

	private boolean isCorrectPath(FileSystemView fsv, File file) {
		if (file.getAbsolutePath().substring(0, 1).equalsIgnoreCase(getRunningDirectoryPath()))
			return fsv.getSystemTypeDescription(file).equalsIgnoreCase(properties.getProperty(DRIVE_TYPE))
					&& fsv.getSystemDisplayName(file).contains(properties.getProperty(DRIVE_NAME));
		return false;
	}
}
