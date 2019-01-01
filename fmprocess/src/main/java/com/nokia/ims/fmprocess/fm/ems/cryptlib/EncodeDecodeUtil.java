package com.nokia.ims.fmprocess.fm.ems.cryptlib;

import java.util.Base64;

public class EncodeDecodeUtil {
	
	public static String base64encode(String toEncode) {

		byte[] encode = Base64.getEncoder().encode(toEncode.getBytes());

		return new String(encode);
	}

	public static String base64decode(String toDecode) {

		byte[] decode = Base64.getDecoder().decode(toDecode.getBytes());

		return new String(decode);
	}

}
