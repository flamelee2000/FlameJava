package com.whoeveryou.FlameCryptoStuff;

import java.security.MessageDigest;

public class DemoSHA {
	/*
	 * @author Ray
	 */
	public DemoSHA() throws Exception {
	}

	public static void main(String[] args) throws Exception {
		String message1 = "TestOfMessageDigest";
		System.out.println("消息：" + message1);
		// 生成一个消息摘要类，定义消息摘要算法。
		java.security.MessageDigest md1 = java.security.MessageDigest
				.getInstance("SHA-384");
		// 添加消息
		md1.update(message1.getBytes());
		// 计算摘要
		byte[] digest1 = md1.digest();
		System.out.println("摘要：" + byte2hex(digest1));
		// 接收方用相同的方法初始化，添加消息，然后比较摘要是否相同。
		String message2 = "TestOfMessageDigest";
		java.security.MessageDigest md2 = java.security.MessageDigest
				.getInstance("SHA-384");
		md2.update(message2.getBytes());
		System.out.println("验证结果：" + MessageDigest.isEqual(digest1, md2.digest()));
	}

	// 将byte[]型转换成16进制串
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