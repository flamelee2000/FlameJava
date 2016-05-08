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
		// Alice生成DH对
		System.out.println("Alice create DH pairs");
		KeyPairGenerator AliceKpairGen = KeyPairGenerator.getInstance("DH");
		AliceKpairGen.initialize(1024);
		KeyPair AliceKpair = AliceKpairGen.generateKeyPair();
		// Alice生成公共密钥 AlicePubKeyEnc 并发送给Bob,内含DH对的种子。
		// Bob接收到Alice的编码后的公钥,将其解码
		byte[] AlicePubKeyEnc = AliceKpair.getPublic().getEncoded();
		KeyFactory BobKeyFac = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(AlicePubKeyEnc);
		PublicKey AlicePubKey = BobKeyFac.generatePublic(x509KeySpec);
		System.out.println("Bob get Alice\'s Pubkey");
		// Bob还要读出种子参数,再用这个参数初始化他的 DH对
		DHParameterSpec dhParamSpec = ((DHPublicKey) AlicePubKey).getParams();
		KeyPairGenerator BobKpairGen = KeyPairGenerator.getInstance("DH");
		BobKpairGen.initialize(dhParamSpec);
		KeyPair BobKpair = BobKpairGen.generateKeyPair();
		System.out.println("Bob create DH pairs");
		KeyAgreement BobKeyAgree = KeyAgreement.getInstance("DH");
		BobKeyAgree.init(BobKpair.getPrivate());
		System.out
				.println("Bob initial session key generator by his private key");
		// Bob生成与Alice共享的会话密钥 BobSessionKey
		BobKeyAgree.doPhase(AlicePubKey, true);
		SecretKey BobSessionKey = BobKeyAgree.generateSecret("DES");
		System.out.println("Bob create session key successfully");
		// Bob生成自己的公钥包并发送给Alice
		byte[] BobPubKeyEnc = BobKpair.getPublic().getEncoded();
		System.out.println("Bob send his public key to Alice");
		// Alice接收到 BobPubKeyEnc后解码出Bob的公钥
		KeyFactory AliceKeyFac = KeyFactory.getInstance("DH");
		x509KeySpec = new X509EncodedKeySpec(BobPubKeyEnc);
		PublicKey BobPubKey = AliceKeyFac.generatePublic(x509KeySpec);
		System.out.println("Alice get Bob's public key");
		// Alice 用自己的私钥初始化会话密钥生成器，同时验证Bob的身份
		KeyAgreement AliceKeyAgree = KeyAgreement.getInstance("DH");
		AliceKeyAgree.init(AliceKpair.getPrivate());
		System.out.println("Alice initialize Alice's session key generator");
		AliceKeyAgree.doPhase(BobPubKey, true);
		// Alice 生成会话密钥
		SecretKey AliceSessionKey = AliceKeyAgree.generateSecret("DES");
		System.out.println("Alice create session key successfully");
		// 现在Alice与Bob有了相同的会话密钥
		if (AliceSessionKey.equals(BobSessionKey))
			System.out.println("Now, Alice & Bob have the same key");
		// Bob用BobSessionKey密钥加密信息
		Cipher BobCipher = Cipher.getInstance("DES");
		BobCipher.init(Cipher.ENCRYPT_MODE, BobSessionKey);
		String BobMsg = "TestOfDHKey";
		System.out.println("Bob's plaintext: " + BobMsg);
		byte[] cleartext = BobMsg.getBytes();
		byte[] ciphertext = BobCipher.doFinal(cleartext);
		// Alice用AliceSessionKey密钥解密
		Cipher AliceCipher = Cipher.getInstance("DES");
		AliceCipher.init(Cipher.DECRYPT_MODE, AliceSessionKey);
		byte[] recovered = AliceCipher.doFinal(ciphertext);
		System.out.println("Alice decrypt Bob's cipher: "
				+ (new String(recovered)));
		if (!java.util.Arrays.equals(cleartext, recovered))
			throw new Exception("解密后与原文信息不同");
		System.out.println("解密成功");
	}
}
