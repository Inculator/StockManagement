package com.mg.csms;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

public class TestMain {

	public static void main(String[] args) {

		/*
		 * LocalDate endofCentury = LocalDate.of(2018, 12, 01); LocalDate now =
		 * LocalDate.now();
		 *
		 * System.out.println(ChronoUnit.DAYS.between(now, endofCentury));
		 * System.out.println((int) Math.ceil((double)
		 * ChronoUnit.DAYS.between(now, endofCentury) / 30));
		 */
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
			System.out.println("Drive: " + f[i]);
			System.out.println("Display name: " + fsv.getSystemDisplayName(f[i]));
			System.out.println("Is drive: " + fsv.isDrive(f[i]));
			System.out.println("Is floppy: " + fsv.isFloppyDrive(f[i]));
			System.out.println("Readable: " + f[i].canRead());
			System.out.println("Writable: " + f[i].canWrite());
			System.out.println("Total space: " + f[i].getTotalSpace());
			System.out.println("Usable space: " + f[i].getUsableSpace());
			System.out.println(fsv.getSystemTypeDescription(f[i]));
		}

	}

}
