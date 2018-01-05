package com.mg.csms;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.codec.binary.Base64;

public class TestMain {

	public static void main(String[] args) {

//		USB1();

		encryptDecrypt();


	}

	private static void encryptDecrypt() {
		try {
			String strip = "Data";
			byte[] encryptArray = Base64.encodeBase64(strip.getBytes());
			String encstr = new String(encryptArray,"UTF-8");
			System.out.println("Enc   >>  "+ encstr);

			String strDec = "QVRUSVVUVEFN";
			byte[] dectryptArray = strDec.getBytes();
			byte[] decarray = Base64.decodeBase64(dectryptArray);
			String decstr = new String(decarray,"UTF-8");
			System.out.println("Dec  >>>  "+ decstr);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static void USB1() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File[] f = File.listRoots();
		List<File> fileList = new ArrayList<>();
		fileList = Arrays.asList(f);

		boolean isAuthenticDrive = fileList.stream()
				.anyMatch(file -> fsv.getSystemDisplayName(file).equalsIgnoreCase("MOHAK GUPTA (G:)")
						&& fsv.getSystemTypeDescription(file).equalsIgnoreCase("USB Drive"));

		System.out.println(isAuthenticDrive);

		for (int i = 0; i < f.length; i++) {
			System.out.println("--------------------------------------");
			System.out.println("Drive: " + f[i].getAbsolutePath().substring(0, 1));
			System.out.println("Display name: " + fsv.getSystemDisplayName(f[i]));
			System.out.println("Is drive: " + fsv.isDrive(f[i]));
			System.out.println("Is floppy: " + fsv.isFloppyDrive(f[i]));
			System.out.println("Readable: " + f[i].canRead());
			System.out.println("Writable: " + f[i].canWrite());
			System.out.println("Total space: " + f[i].getTotalSpace());
			System.out.println("Usable space: " + f[i].getUsableSpace());
			System.out.println(fsv.getSystemTypeDescription(f[i]).contains("Removable"));
		}
	}
}
