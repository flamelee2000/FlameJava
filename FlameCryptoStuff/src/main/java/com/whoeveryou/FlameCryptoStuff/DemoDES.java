package com.whoeveryou.FlameCryptoStuff;

import java.security.*;
import javax.crypto.*;

public class DemoDES {
	/*
	 * @author Ray
	 */
	public static void main(String[] args) {
		DemoDES my = new DemoDES();
		my.run();
	}

	public void run() {

		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		String Algorithm = "DES";
		String plaintext = "DemoOfDES";
		try {
			System.out.println("明文：" + plaintext);
			// 通过KeyGenerator产生DES密钥
			KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
			SecretKey DESkey = keygen.generateKey();
			System.out.println("密钥随机产生");
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, DESkey);
			byte[] cipherByte = c1.doFinal(plaintext.getBytes());
			System.out.println("密文：" + byte2bin(cipherByte));
			System.out.println("密文：" + byte2hex(cipherByte));
			// 解密
			c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, DESkey);
			byte[] decryptoByte = c1.doFinal(cipherByte);
			System.out.println("明文：" + byte2bin(decryptoByte));
			System.out.println("明文：" + byte2hex(decryptoByte));
			System.out.println("明文：" + new String(decryptoByte));
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
	}

	public static String byte2hex(byte[] ba) {
		String strout = "";
		for (int i = 0; i < ba.length; i++) {
			int j = ba[i] < 0 ? ba[i] + 256 : ba[i];
			String str = Integer.toHexString(j);
			while (str.length() < 2)
				str = '0' + str;
			if (i < ba.length - 1)
				strout += (str + "-");
			else
				strout += str;
		}
		return strout.toUpperCase();
	}

	public static String byte2bin(byte[] ba) {
		String strout = "";
		for (int i = 0; i < ba.length; i++) {
			int j = ba[i] < 0 ? ba[i] + 256 : ba[i];
			String str = Integer.toBinaryString(j);
			while (str.length() < 8)
				str = '0' + str;
			if (i < ba.length - 1)
				strout += (str + "");
			else
				strout += str;
		}
		return strout.toUpperCase();
	}
}
