package com.whoeveryou;

import java.io.File;

public class FlameFolderTraverse {

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public FlameFolderTraverse(String path) {
		this.path = path;
	}

	public void doTraverse(String path1) {
		File file = new File(path1);
		if (file.listFiles() == null)
			return;
		for (File tmp : file.listFiles()) {
			if (tmp.isFile()) {
				// System.out.println(tmp.getAbsolutePath());
				FlameSCIOutputFormatter FSIOF1 = new FlameSCIOutputFormatter();
				String fin = tmp.getAbsolutePath();

				if (tmp.getName().substring(0, 2).equals("o_"))
					continue;
				String fout = fin.substring(0, fin.lastIndexOf('\\') + 1)
						+ "o_" + fin.substring(fin.lastIndexOf('\\') + 1);
				FSIOF1.setFileIn(fin);
				FSIOF1.setFileOut(fout);
				FSIOF1.doFormat();

			} else {
				doTraverse(tmp.getAbsolutePath());
			}
		}
	}

}
