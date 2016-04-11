package com.whoeveryou.FlameCryptoStuff;

import java.security.*;
import javax.crypto.*;

public class DemoAES {
	/*
	 * @author Ray
	 */
	public static void main(String[] args) {
		DemoAES my = new DemoAES();
		my.run();
	}

	public void run() {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		String Algorithm = "AES";
		String plaintext = "DemoOfAES";
		try {
			// 生成密钥
			KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
			SecretKey aeskey = keygen.generateKey();
			// 密钥材料存储于本地
			java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
					new java.io.FileOutputStream("aeskey.dat"));
			out.writeObject(aeskey);
			out.close();
			// 加密
			System.out.println(plaintext);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, aeskey);
			byte[] cipherByte = c1.doFinal(plaintext.getBytes());
			System.out.println(byte2hex(cipherByte));
			// 重建密钥
			java.io.ObjectInputStream in = new java.io.ObjectInputStream(
					new java.io.FileInputStream("aeskey.dat"));
			Key aeskey2 = (Key) in.readObject();
			in.close();
			// 解密
			c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, aeskey2);
			byte[] decryptoByte = c1.doFinal(cipherByte);
			System.out.println(byte2hex(decryptoByte));
			System.out.println(new String(decryptoByte));
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
}
