package com.whoeveryou;

import org.junit.Test;

public class FlameFileRWTest {

	@Test
	public void test1() {
		
		FlameSCIOutputFormatter FSIOF1 = new FlameSCIOutputFormatter();
		FSIOF1.setSeperator("\n");
		String fin="d:\\Cognitive Computation-001-050.txt";
		String fout=fin.substring(0,fin.lastIndexOf('\\')+1)+"o_"+fin.substring(fin.lastIndexOf('\\')+1);
		FSIOF1.setFileIn(fin);
		FSIOF1.setFileOut(fout);
		
		String str_test = "d:\\raw.txt";
		System.out.println("test result:" + str_test.substring(str_test.lastIndexOf('\\')+1)
				+ "ok");
		
		FSIOF1.doFormat();
	}
	
	@Test
	public void test2() {
		
		FlameFolderTraverse fft1=new FlameFolderTraverse("d:\\testdir");
		fft1.doTraverse(fft1.getPath());
	}
	
}
