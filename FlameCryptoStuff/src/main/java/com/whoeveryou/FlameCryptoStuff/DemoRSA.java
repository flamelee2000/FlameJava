package com.whoeveryou.FlameCryptoStuff;

import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.crypto.interfaces.*;
import java.security.interfaces.*;
import java.math.*;
import java.io.*;

/*
 *	@author Ray
 */
public class DemoRSA {
	public static void main(String[] args)
			throws java.security.NoSuchAlgorithmException, java.lang.Exception {
		DemoRSA my = new DemoRSA();
		my.run();
	}

	public void run() {
		// 生成密钥对：如果已经生成过,本过程就可以跳过。
		if ((new File("RSAprikey.dat")).exists() == false) {
			if (generatekey() == false) {
				System.out.println("生成密钥对失败");
				return;
			}
			;
		}
		try {
			// 读入公钥文件，重建公钥
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					"RSApubkey.dat"));
			RSAPublicKey pubkey = (RSAPublicKey) in.readObject();
			in.close();
			// 从公钥中读出参数e和n
			BigInteger e = pubkey.getPublicExponent();
			BigInteger n = pubkey.getModulus();
			System.out.println("公钥参数 e： " + e);
			System.out.println("公钥参数 n： " + n);
			// 给定明文编码成大整数
			String plaintext = new String("TestOfRSA");
			byte plainbytes[] = plaintext.getBytes("UTF8");
			BigInteger plainBI = new BigInteger(plainbytes);
			System.out.println("明文：" + plaintext);
			System.out.println("明文编码：" + plainBI.toString());
			// 计算密文
			BigInteger cipherBI = plainBI.modPow(e, n);
			System.out.println("密文编码： " + cipherBI.toString());
			// 把密文保存到本地文件中
			String ciphertext = cipherBI.toString();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("RSACipher.dat")));
			out.write(ciphertext, 0, ciphertext.length());
			out.close();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			System.out.println("加密时发生错误!");
		}
		;
		try {
			// 通过私钥文件重建私钥
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					"RSAprikey.dat"));
			RSAPrivateKey prikey = (RSAPrivateKey) in.readObject();
			in.close();
			// 获取私钥参数
			BigInteger d = prikey.getPrivateExponent();
			BigInteger n = prikey.getModulus();
			System.out.println("私钥参数 d：" + d);
			System.out.println("私钥参数 n：" + n);
			// 读出密文
			BufferedReader incipher = new BufferedReader(new InputStreamReader(
					new FileInputStream("RSACipher.dat")));
			String ciphertext = incipher.readLine();
			incipher.close();
			BigInteger cihperBI = new BigInteger(ciphertext);
			// 解密
			BigInteger plainBI = cihperBI.modPow(d, n);
			System.out.println("解出明文的编码：" + plainBI);
			byte[] plainbytes = plainBI.toByteArray();
			System.out.print("解出的明文：");
			for (int i = 0; i < plainbytes.length; i++) {
				System.out.print((char) plainbytes[i]);
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		;
	}

	// 生成密钥对的函数
	public boolean generatekey() {
		try {
			java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator
					.getInstance("RSA");
			keygen.initialize(1024);
			KeyPair keys = keygen.genKeyPair();
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream("RSAprikey.dat"));
			out.writeObject(prikey);
			out.close();
			System.out.println("写入对象 prikeys ok");
			out = new ObjectOutputStream(new FileOutputStream("RSApubkey.dat"));
			out.writeObject(pubkey);
			out.close();
			System.out.println("写入对象 pubkeys ok");
			System.out.println("生成密钥对成功");
			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			System.out.println("生成密钥对失败");
			return false;
		}
	}

	// 将byte[]转换成16进制串
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