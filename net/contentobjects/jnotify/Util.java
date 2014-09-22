package net.contentobjects.jnotify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Util {
	public static String getMaskDesc(int mask) {
		StringBuffer s = new StringBuffer();
		if ((mask & JNotify.FILE_CREATED) != 0) {
			s.append("FILE_CREATED|");
		}
		if ((mask & JNotify.FILE_DELETED) != 0) {
			s.append("FILE_DELETED|");
		}
		if ((mask & JNotify.FILE_MODIFIED) != 0) {
			s.append("FILE_MODIFIED|");
		}
		if ((mask & JNotify.FILE_RENAMED) != 0) {
			s.append("FILE_RENAMED|");
		}
		if (s.length() > 0) {
			return s.substring(0, s.length() - 1);
		} else {
			return "UNKNOWN";
		}
	}

	public static boolean loadLibrary(String lName) {
		FileOutputStream os = null;
		InputStream is = null;
		String tmpFilePath = System.getProperty("java.io.tmpdir") + lName;
		try {
			is = Util.class.getResourceAsStream("/" + lName);
			if (is != null) {
				byte[] buffer = new byte[4096];
				os = new FileOutputStream(tmpFilePath);
				int read;
				while ((read = is.read(buffer)) != -1) {
					os.write(buffer, 0, read);
				}
				os.close();
				is.close();
				System.load(tmpFilePath);
				return true;
			}
		} catch (Throwable e) {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException localIOException) {
			}
		} finally {
			File file = new File(tmpFilePath);
			if (file.exists()) {
				file.delete();
			}
		}
		return false;
	}
}
