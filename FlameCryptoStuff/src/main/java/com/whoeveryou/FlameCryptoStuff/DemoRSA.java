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
		// ������Կ�ԣ�����Ѿ����ɹ�,�����̾Ϳ���������
		if ((new File("RSAprikey.dat")).exists() == false) {
			if (generatekey() == false) {
				System.out.println("������Կ��ʧ��");
				return;
			}
			;
		}
		try {
			// ���빫Կ�ļ����ؽ���Կ
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					"RSApubkey.dat"));
			RSAPublicKey pubkey = (RSAPublicKey) in.readObject();
			in.close();
			// �ӹ�Կ�ж�������e��n
			BigInteger e = pubkey.getPublicExponent();
			BigInteger n = pubkey.getModulus();
			System.out.println("��Կ���� e�� " + e);
			System.out.println("��Կ���� n�� " + n);
			// �������ı���ɴ�����
			String plaintext = new String("TestOfRSA");
			byte plainbytes[] = plaintext.getBytes("UTF8");
			BigInteger plainBI = new BigInteger(plainbytes);
			System.out.println("���ģ�" + plaintext);
			System.out.println("���ı��룺" + plainBI.toString());
			// ��������
			BigInteger cipherBI = plainBI.modPow(e, n);
			System.out.println("���ı��룺 " + cipherBI.toString());
			// �����ı��浽�����ļ���
			String ciphertext = cipherBI.toString();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("RSACipher.dat")));
			out.write(ciphertext, 0, ciphertext.length());
			out.close();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			System.out.println("����ʱ��������!");
		}
		;
		try {
			// ͨ��˽Կ�ļ��ؽ�˽Կ
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					"RSAprikey.dat"));
			RSAPrivateKey prikey = (RSAPrivateKey) in.readObject();
			in.close();
			// ��ȡ˽Կ����
			BigInteger d = prikey.getPrivateExponent();
			BigInteger n = prikey.getModulus();
			System.out.println("˽Կ���� d��" + d);
			System.out.println("˽Կ���� n��" + n);
			// ��������
			BufferedReader incipher = new BufferedReader(new InputStreamReader(
					new FileInputStream("RSACipher.dat")));
			String ciphertext = incipher.readLine();
			incipher.close();
			BigInteger cihperBI = new BigInteger(ciphertext);
			// ����
			BigInteger plainBI = cihperBI.modPow(d, n);
			System.out.println("������ĵı��룺" + plainBI);
			byte[] plainbytes = plainBI.toByteArray();
			System.out.print("��������ģ�");
			for (int i = 0; i < plainbytes.length; i++) {
				System.out.print((char) plainbytes[i]);
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		;
	}

	// ������Կ�Եĺ���
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
			System.out.println("д����� prikeys ok");
			out = new ObjectOutputStream(new FileOutputStream("RSApubkey.dat"));
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