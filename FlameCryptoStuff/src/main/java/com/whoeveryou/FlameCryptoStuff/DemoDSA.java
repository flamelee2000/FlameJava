package com.whoeveryou.FlameCryptoStuff;

import java.security.*;

/*
 *	@author Ray
 */
public class DemoDSA {
	public static void main(String[] args)
			throws java.security.NoSuchAlgorithmException, java.lang.Exception {
		DemoDSA my = new DemoDSA();
		my.run();
	}

	public void run() {
		// 生成密钥对：如果已经生成过,本过程就可以跳过。
		// 发送方的私钥文件prikey.dat要保存在本地，而公钥文件pubkey.dat则
		// 发送给接收方。
		if ((new java.io.File("prikey.dat")).exists() == false) {
			if (generatekey() == false) {
				System.out.println("生成密钥对失败");
				return;
			}
			;
		}
		// 发送方从文件中读入私钥,对消息进行签名后保存在一个文件(msgwithsign.dat)中。
		// 然后再把msgwithsign.dat发送给接收方，
		// 数字签名可以放进msgwithsign.dat文件中,也可分别发送
		try {
			java.io.ObjectInputStream in = new java.io.ObjectInputStream(
					new java.io.FileInputStream("prikey.dat"));
			PrivateKey prikey = (PrivateKey) in.readObject();
			in.close();
			String msg = "TestOfDSA"; // 要签名的信息
			// 用私钥对信息生成数字签名
			java.security.Signature sign1 = java.security.Signature
					.getInstance("DSA");
			sign1.initSign(prikey);
			sign1.update(msg.getBytes());
			byte[] signofmsg = sign1.sign(); // 对信息的数字签名
			System.out.println("数字签名：" + byte2hex(signofmsg));
			// 此处把信息和数字签名保存在一个文件中
			java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
					new java.io.FileOutputStream("msgwithsign.dat"));
			out.writeObject(msg);
			out.writeObject(signofmsg);
			out.close();
			System.out.println("签名并生成文件成功");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			System.out.println("签名时发生错误!");
		}
		;
		// 接收方通过某种方式得到发送方的公钥和签名文件,
		// 然后用公钥,对签名进行验证。
		try {
			java.io.ObjectInputStream in = new java.io.ObjectInputStream(
					new java.io.FileInputStream("pubkey.dat"));
			PublicKey pubkey = (PublicKey) in.readObject();
			in.close();
			System.out.println("编码方式：" + pubkey.getFormat());
			in = new java.io.ObjectInputStream(new java.io.FileInputStream(
					"msgwithsign.dat"));
			String msg = (String) in.readObject();
			byte[] signofmsg = (byte[]) in.readObject();
			in.close();
			java.security.Signature sign2 = java.security.Signature
					.getInstance("DSA");
			sign2.initVerify(pubkey);
			sign2.update(msg.getBytes());
			if (sign2.verify(signofmsg)) {
				System.out.println("消息内容：" + msg);
				System.out.println("签名有效");
			} else
				System.out.println("非正常签名");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		;
	}

	// 生成密钥对的函数
	public boolean generatekey() {
		try {
			java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator
					.getInstance("DSA");
			keygen.initialize(512);
			KeyPair keys = keygen.genKeyPair();
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();

			java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
					new java.io.FileOutputStream("prikey.dat"));
			out.writeObject(prikey);
			out.close();
			System.out.println("写入对象 prikeys ok");
			out = new java.io.ObjectOutputStream(new java.io.FileOutputStream(
					"pubkey.dat"));
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
