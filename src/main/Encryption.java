package main;

import java.math.*;
import java.security.*;

import javax.management.RuntimeErrorException;

public class Encryption {

	public static String SHA1(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			byte[] result = md.digest(str.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 0x16).substring(1));
			}
			return sb.toString();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
