package com.whoeveryou;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlameSCIOutputFormatter {

	private String seperator;
	private String fileIn;
	private String fileOut;
	List<String> unusedPropertiesSet;
	private String lastProperty;

	public FlameSCIOutputFormatter() {
		seperator = "\n";
		fileIn = "d:\\raw1.txt";
		fileOut = "d:\\raw2.txt";
		unusedPropertiesSet = new ArrayList<String>();
		unusedPropertiesSet.add("PT");
		unusedPropertiesSet.add("VL");
		unusedPropertiesSet.add("IS");
		unusedPropertiesSet.add("BP");
		unusedPropertiesSet.add("EP");
		unusedPropertiesSet.add("DI");
		unusedPropertiesSet.add("PD");
		unusedPropertiesSet.add("BP");
		unusedPropertiesSet.add("PY");
		unusedPropertiesSet.add("SO");
		unusedPropertiesSet.add("ZB");
		unusedPropertiesSet.add("Z8");
		unusedPropertiesSet.add("ZR");
		unusedPropertiesSet.add("ZS");
		unusedPropertiesSet.add("TC");
		unusedPropertiesSet.add("Z9");
		unusedPropertiesSet.add("SN");
		unusedPropertiesSet.add("EI");
		unusedPropertiesSet.add("UT");
		unusedPropertiesSet.add("ER");
		unusedPropertiesSet.add("SI");
		unusedPropertiesSet.add("RI");
		unusedPropertiesSet.add("OI");
		unusedPropertiesSet.add("FN");
		unusedPropertiesSet.add("VR");
		unusedPropertiesSet.add("EF");
		unusedPropertiesSet.add("AR");
		lastProperty = "QQ";
	}

	public String getSeperator() {
		return seperator;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public String getFileIn() {
		return fileIn;
	}

	public void setFileIn(String fileIn) {
		this.fileIn = fileIn;
	}

	public String getFileOut() {
		return fileOut;
	}

	public void setFileOut(String fileOut) {
		this.fileOut = fileOut;
	}

	public List<String> getUnusedPropertiesSet() {
		return unusedPropertiesSet;
	}

	public void setUnusedPropertiesSet(List<String> unusedPropertiesSet) {
		this.unusedPropertiesSet = unusedPropertiesSet;
	}

	public void doFormat() {
		try {
			// read file content from file
			StringBuffer sb = new StringBuffer("");
			FileReader reader = new FileReader(fileIn);
			BufferedReader br = new BufferedReader(reader);
			String str = null;
			while ((str = br.readLine()) != null) {
				// read every line
				if (str.trim().isEmpty())
					continue;
				String currentProperty = str.substring(0, 2);
				if (unusedPropertiesSet.contains(currentProperty))
					continue;
				if (currentProperty.trim().isEmpty() && lastProperty.equals("AU"))
					continue;
				sb.append(str + seperator);
				if (!(currentProperty.trim().isEmpty()))
						lastProperty = str.substring(0, 2);
			}
			br.close();
			reader.close();
			// write string to file
			FileWriter writer = new FileWriter(fileOut);
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write(fileIn.substring(fileIn.lastIndexOf('\\')+1)+"\n");
			bw.write(sb.toString());
			bw.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}