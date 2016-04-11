package com.whoeveryou.FlameCryptoStuff;

import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class DemoMAC {
	/*
	 * @author Ray
	 */
	public static void main(String args[]) throws Exception {
		// 设置密钥
		String key = "helloworld";
		System.out.println("密钥：" + key);
		SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "HMACMD5");
		// 生成MAC实例并初始化
		Mac mac1 = Mac.getInstance("HmacMD5");
		mac1.init(sks);
		String msg = "TestofMAC";
		System.out.println("消息：" + msg);
		mac1.update(msg.getBytes());
		System.out.println("MAC: " + byte2hex(mac1.doFinal()));
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
