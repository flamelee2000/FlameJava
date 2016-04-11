package com.whoeveryou.FlameCryptoStuff;

import java.net.*;
import java.io.*;
import javax.net.ssl.*;

public class DemoSimpleHTTPSClient {
	public static void main(String args[]) throws Exception {
		try {
			/*
			 * @author Ray
			 */
			System.setProperty("javax.net.ssl.trustStore", "clienttrust");
			String hostname = "10.0.0.1";
			int port = 4443;
			SSLSocketFactory sslssf = (SSLSocketFactory) SSLSocketFactory
					.getDefault();
			Socket socket1 = sslssf.createSocket(hostname, port);
			OutputStream outstrm = socket1.getOutputStream();
			PrintStream out = new PrintStream(outstrm);
			InputStream instrm = socket1.getInputStream();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(instrm));
			out.println("Hi HTTPS Server, It's client.");
			out.println("");
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			out.close();
		} catch (IOException e) {
		}
	}
}
