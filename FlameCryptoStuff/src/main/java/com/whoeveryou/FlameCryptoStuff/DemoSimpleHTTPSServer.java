package com.whoeveryou.FlameCryptoStuff;

import java.net.*;
import java.io.*;
import javax.net.ssl.*;

public class DemoSimpleHTTPSServer {
	/*
	 * @author Ray
	 */
	public static void main(String args[]) {
		int count = 0;
		try {
			System.setProperty("javax.net.ssl.keyStore", ".keystore");
			System.setProperty("javax.net.ssl.keyStorePassword", "abc123");
			SSLServerSocketFactory sslssf = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			ServerSocket servsock = sslssf.createServerSocket(4443);
			System.out.println("Web server is ready ¡­¡­");
			while (true) {
				Socket socket1 = servsock.accept(); // µÈ´ýÇëÇó
				PrintStream out = new PrintStream(socket1.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket1.getInputStream()));
				String msg = null;
				while ((msg = in.readLine()) != null) {
					System.out.println("Get message \"" + msg
							+ "\" from client.");
					if (msg.equals(""))
						break;
				}
				out.println("HTTP/1.0 200 OK\r\nContent-Type: text/html\r\n");
				out.println("<html> <head></head><body>You are welcome, Dear No. "
						+ (count++) + " visitor.</Body></html>");
				out.close();
				socket1.close();
				in.close();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
