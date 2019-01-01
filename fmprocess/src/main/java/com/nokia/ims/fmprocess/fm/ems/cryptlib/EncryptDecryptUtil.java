package com.nokia.ims.fmprocess.fm.ems.cryptlib;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EncryptDecryptUtil {

	public static String encrypt(String binPath, String toEncrypt) {
		return executeCmd(binPath + toEncrypt);
	}

	public static String decrypt(String binPath, String toDecrypt) {
		return executeCmd(binPath + " " + toDecrypt);
	}

	public static String executeCmd(String command) {
		String output = "";
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				String line = "";
				while ((line = reader.readLine()) != null) {
					output = output +line;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
}
