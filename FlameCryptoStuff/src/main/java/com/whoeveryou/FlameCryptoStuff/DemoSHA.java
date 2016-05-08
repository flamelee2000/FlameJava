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
		System.out.println("��Ϣ��" + message1);
		// ����һ����ϢժҪ�࣬������ϢժҪ�㷨��
		java.security.MessageDigest md1 = java.security.MessageDigest
				.getInstance("SHA-384");
		// �����Ϣ
		md1.update(message1.getBytes());
		// ����ժҪ
		byte[] digest1 = md1.digest();
		System.out.println("ժҪ��" + byte2hex(digest1));
		// ���շ�����ͬ�ķ�����ʼ���������Ϣ��Ȼ��Ƚ�ժҪ�Ƿ���ͬ��
		String message2 = "TestOfMessageDigest";
		java.security.MessageDigest md2 = java.security.MessageDigest
				.getInstance("SHA-384");
		md2.update(message2.getBytes());
		System.out.println("��֤�����" + MessageDigest.isEqual(digest1, md2.digest()));
	}

	// ��byte[]��ת����16���ƴ�
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