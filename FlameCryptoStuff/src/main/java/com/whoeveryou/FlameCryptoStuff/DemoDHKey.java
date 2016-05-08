package com.whoeveryou.FlameCryptoStuff;

import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.crypto.interfaces.*;

public class DemoDHKey {
	/*
	 * @author Ray
	 */
	public static void main(String argv[]) {
		try {
			DemoDHKey my = new DemoDHKey();
			my.run();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private void run() throws Exception {
		//Security.addProvider(new com.sun.crypto.provider.SunJCE());
		// Alice����DH��
		System.out.println("Alice create DH pairs");
		KeyPairGenerator AliceKpairGen = KeyPairGenerator.getInstance("DH");
		AliceKpairGen.initialize(1024);
		KeyPair AliceKpair = AliceKpairGen.generateKeyPair();
		// Alice���ɹ�����Կ AlicePubKeyEnc �����͸�Bob,�ں�DH�Ե����ӡ�
		// Bob���յ�Alice�ı����Ĺ�Կ,�������
		byte[] AlicePubKeyEnc = AliceKpair.getPublic().getEncoded();
		KeyFactory BobKeyFac = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(AlicePubKeyEnc);
		PublicKey AlicePubKey = BobKeyFac.generatePublic(x509KeySpec);
		System.out.println("Bob get Alice\'s Pubkey");
		// Bob��Ҫ�������Ӳ���,�������������ʼ������ DH��
		DHParameterSpec dhParamSpec = ((DHPublicKey) AlicePubKey).getParams();
		KeyPairGenerator BobKpairGen = KeyPairGenerator.getInstance("DH");
		BobKpairGen.initialize(dhParamSpec);
		KeyPair BobKpair = BobKpairGen.generateKeyPair();
		System.out.println("Bob create DH pairs");
		KeyAgreement BobKeyAgree = KeyAgreement.getInstance("DH");
		BobKeyAgree.init(BobKpair.getPrivate());
		System.out
				.println("Bob initial session key generator by his private key");
		// Bob������Alice����ĻỰ��Կ BobSessionKey
		BobKeyAgree.doPhase(AlicePubKey, true);
		SecretKey BobSessionKey = BobKeyAgree.generateSecret("DES");
		System.out.println("Bob create session key successfully");
		// Bob�����Լ��Ĺ�Կ�������͸�Alice
		byte[] BobPubKeyEnc = BobKpair.getPublic().getEncoded();
		System.out.println("Bob send his public key to Alice");
		// Alice���յ� BobPubKeyEnc������Bob�Ĺ�Կ
		KeyFactory AliceKeyFac = KeyFactory.getInstance("DH");
		x509KeySpec = new X509EncodedKeySpec(BobPubKeyEnc);
		PublicKey BobPubKey = AliceKeyFac.generatePublic(x509KeySpec);
		System.out.println("Alice get Bob's public key");
		// Alice ���Լ���˽Կ��ʼ���Ự��Կ��������ͬʱ��֤Bob�����
		KeyAgreement AliceKeyAgree = KeyAgreement.getInstance("DH");
		AliceKeyAgree.init(AliceKpair.getPrivate());
		System.out.println("Alice initialize Alice's session key generator");
		AliceKeyAgree.doPhase(BobPubKey, true);
		// Alice ���ɻỰ��Կ
		SecretKey AliceSessionKey = AliceKeyAgree.generateSecret("DES");
		System.out.println("Alice create session key successfully");
		// ����Alice��Bob������ͬ�ĻỰ��Կ
		if (AliceSessionKey.equals(BobSessionKey))
			System.out.println("Now, Alice & Bob have the same key");
		// Bob��BobSessionKey��Կ������Ϣ
		Cipher BobCipher = Cipher.getInstance("DES");
		BobCipher.init(Cipher.ENCRYPT_MODE, BobSessionKey);
		String BobMsg = "TestOfDHKey";
		System.out.println("Bob's plaintext: " + BobMsg);
		byte[] cleartext = BobMsg.getBytes();
		byte[] ciphertext = BobCipher.doFinal(cleartext);
		// Alice��AliceSessionKey��Կ����
		Cipher AliceCipher = Cipher.getInstance("DES");
		AliceCipher.init(Cipher.DECRYPT_MODE, AliceSessionKey);
		byte[] recovered = AliceCipher.doFinal(ciphertext);
		System.out.println("Alice decrypt Bob's cipher: "
				+ (new String(recovered)));
		if (!java.util.Arrays.equals(cleartext, recovered))
			throw new Exception("���ܺ���ԭ����Ϣ��ͬ");
		System.out.println("���ܳɹ�");
	}
}
