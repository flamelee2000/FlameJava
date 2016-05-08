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
		// ������Կ�ԣ�����Ѿ����ɹ�,�����̾Ϳ���������
		// ���ͷ���˽Կ�ļ�prikey.datҪ�����ڱ��أ�����Կ�ļ�pubkey.dat��
		// ���͸����շ���
		if ((new java.io.File("prikey.dat")).exists() == false) {
			if (generatekey() == false) {
				System.out.println("������Կ��ʧ��");
				return;
			}
			;
		}
		// ���ͷ����ļ��ж���˽Կ,����Ϣ����ǩ���󱣴���һ���ļ�(msgwithsign.dat)�С�
		// Ȼ���ٰ�msgwithsign.dat���͸����շ���
		// ����ǩ�����ԷŽ�msgwithsign.dat�ļ���,Ҳ�ɷֱ���
		try {
			java.io.ObjectInputStream in = new java.io.ObjectInputStream(
					new java.io.FileInputStream("prikey.dat"));
			PrivateKey prikey = (PrivateKey) in.readObject();
			in.close();
			String msg = "TestOfDSA"; // Ҫǩ������Ϣ
			// ��˽Կ����Ϣ��������ǩ��
			java.security.Signature sign1 = java.security.Signature
					.getInstance("DSA");
			sign1.initSign(prikey);
			sign1.update(msg.getBytes());
			byte[] signofmsg = sign1.sign(); // ����Ϣ������ǩ��
			System.out.println("����ǩ����" + byte2hex(signofmsg));
			// �˴�����Ϣ������ǩ��������һ���ļ���
			java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
					new java.io.FileOutputStream("msgwithsign.dat"));
			out.writeObject(msg);
			out.writeObject(signofmsg);
			out.close();
			System.out.println("ǩ���������ļ��ɹ�");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			System.out.println("ǩ��ʱ��������!");
		}
		;
		// ���շ�ͨ��ĳ�ַ�ʽ�õ����ͷ��Ĺ�Կ��ǩ���ļ�,
		// Ȼ���ù�Կ,��ǩ��������֤��
		try {
			java.io.ObjectInputStream in = new java.io.ObjectInputStream(
					new java.io.FileInputStream("pubkey.dat"));
			PublicKey pubkey = (PublicKey) in.readObject();
			in.close();
			System.out.println("���뷽ʽ��" + pubkey.getFormat());
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
				System.out.println("��Ϣ���ݣ�" + msg);
				System.out.println("ǩ����Ч");
			} else
				System.out.println("������ǩ��");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		;
	}

	// ������Կ�Եĺ���
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
			System.out.println("д����� prikeys ok");
			out = new java.io.ObjectOutputStream(new java.io.FileOutputStream(
					"pubkey.dat"));
			out.writeObject(pubkey);
			out.close();
			System.out.println("д����� pubkeys ok");
			System.out.println("������Կ�Գɹ�");
			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			System.out.println("������Կ��ʧ��");
			return false;
		}
	}

	// ��byte[]ת����16���ƴ�
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
