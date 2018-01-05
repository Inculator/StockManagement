package com.mg.authentication.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class EncryptDecryptUtils {

	private EncryptDecryptUtils(){}

	public static String encrypt(String value){
		try {
			byte[] encryptArray = Base64.encodeBase64(value.getBytes());
			return new String(encryptArray,"UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}

	public static String decrypt(String value){
		try {
			byte[] dectryptArray = value.getBytes();
			byte[] decarray = Base64.decodeBase64(dectryptArray);
			return new String(decarray,"UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}
}
